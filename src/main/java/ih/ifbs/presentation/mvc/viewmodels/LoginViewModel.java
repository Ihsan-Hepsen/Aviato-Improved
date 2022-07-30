package ih.ifbs.presentation.mvc.viewmodels;
import javax.validation.constraints.NotBlank;

public class LoginViewModel {

    @NotBlank(message = "You must enter your user name to login.")
    private String username;

    @NotBlank(message = "You must enter your password to login.")
    private String password;

    public LoginViewModel() {

    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
