package ih.ifbs.domain;

import io.leangen.graphql.annotations.GraphQLQuery;

import javax.persistence.*;

@Entity
@Table(name = "APPLICATION_USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GraphQLQuery(name = "id", description = "A user's ID.")
    private Integer id;

    @Column(nullable = false, name = "name")
    @GraphQLQuery(name = "username", description = "User's username.")
    private String username;

    @Column(nullable = false)
    @GraphQLQuery(name = "email", description = "User's email.")
    private String email;

    @Column(nullable = false)
    @GraphQLQuery(name = "password", description = "User's password.")
    private String password;

    @Column(name = "age", nullable = false)
    @GraphQLQuery(name = "age", description = "User's age.")
    private int age;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    @GraphQLQuery(name = "gender", description = "User's gender.")
    private final Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @GraphQLQuery(name = "role", description = "User's role in the application.")
    private Role role;

    protected User() {
        gender = null;
    }

    public User(String email, String username, String password, Role role) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
        this.gender = null;
    }

    public User(String username, String email, String password,
                int age, Gender gender, Role role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.age = age;
        this.gender = gender;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {

    }
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
