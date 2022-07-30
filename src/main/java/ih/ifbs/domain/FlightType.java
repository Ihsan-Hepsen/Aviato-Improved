package ih.ifbs.domain;

import ih.ifbs.util.LookupUtil;
import io.leangen.graphql.annotations.GraphQLQuery;

public enum FlightType {
    @GraphQLQuery(name = "COMM", description = "Flight Type: Commercial.")
    COMM("Commercial"),
    @GraphQLQuery(name = "PRIV", description = "Flight Type: Private.")
    PRIV("Private");

    private final String value;

    FlightType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    static public FlightType lookup(String id) {
        return LookupUtil.lookup(FlightType.class, id);
    }
}
