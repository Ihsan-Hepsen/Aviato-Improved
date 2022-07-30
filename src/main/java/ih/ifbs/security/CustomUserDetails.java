package ih.ifbs.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import java.util.Collection;

public class CustomUserDetails extends User {
    private final int userId;

    public CustomUserDetails(int userId, String username,
                             String password,
                             Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.userId = userId;
    }

    public long getId() {
        return userId;
    }

    @Override
    public String getUsername() {
        return super.getUsername();
    }
}
