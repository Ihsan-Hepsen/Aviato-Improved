package ih.ifbs.domain;

import io.leangen.graphql.annotations.GraphQLQuery;

public enum Role {
    @GraphQLQuery(name = "PASSENGER", description = "Application role: passenger.")
    PASSENGER("ROLE_USER"),
    @GraphQLQuery(name = "AIRLINE", description = "Application role: airline.")
    AIRLINE("ROLE_ADMIN");

    private final String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
