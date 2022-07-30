package ih.ifbs.presentation.api;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import ih.ifbs.resolvers.AirlineResolver;
import ih.ifbs.resolvers.FlightResolver;
import ih.ifbs.resolvers.PassengerResolver;
import ih.ifbs.services.AirlineService;
import ih.ifbs.services.FlightService;
import ih.ifbs.services.PassengerService;
import io.leangen.graphql.GraphQLSchemaGenerator;
import io.leangen.graphql.metadata.strategy.query.AnnotatedResolverBuilder;
import io.leangen.graphql.metadata.strategy.value.jackson.JacksonValueMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class GraphQLController {

    private final GraphQL graphQL;

    @Autowired
    public GraphQLController(AirlineResolver airlineResolver, FlightResolver flightResolver, PassengerResolver passengerResolver) {
        GraphQLSchema schema = new GraphQLSchemaGenerator()
                .withResolverBuilders(
                        // resolve by annotations
                        new AnnotatedResolverBuilder())
                .withOperationsFromSingleton(airlineResolver)
                .withOperationsFromSingleton(flightResolver)
                .withOperationsFromSingleton(passengerResolver)
                .withValueMapperFactory(new JacksonValueMapperFactory())
                .generate();
        graphQL = GraphQL.newGraphQL(schema).build();
    }

    @PostMapping(value = "/graphql")
    public Map<String, Object> graphql(@RequestBody Map<String, Object> request, HttpServletRequest raw) {
        ExecutionResult executionResult = graphQL.execute(
                ExecutionInput.newExecutionInput()
                        .query(String.valueOf(request.get("query")))
                        .operationName((String) request.get("operationName"))
                        .context(raw)
                        .build()
        );
        return executionResult.toSpecification();
    }

}
