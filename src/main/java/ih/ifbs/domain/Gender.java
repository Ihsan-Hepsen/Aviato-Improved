package ih.ifbs.domain;

import ih.ifbs.util.LookupUtil;
import io.leangen.graphql.annotations.GraphQLQuery;

public enum Gender {
    @GraphQLQuery(name = "F", description = "Gender: Female")
    F("Female"),
    @GraphQLQuery(name = "M", description = "Gender: Male")
    M("Male");

    private final String gender;

    Gender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    static public Gender lookup(String id) {
        return LookupUtil.lookup(Gender.class, id);
    }
}
