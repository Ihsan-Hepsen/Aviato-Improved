package ih.ifbs.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //@formatter:off
        http
                .httpBasic()
                    .authenticationEntryPoint(httpStatusEntryPoint())
                    .and()
                .authorizeRequests()
                    .antMatchers("/", "/home", "/register", "/register-airline", "/gui", "/graphql/**")
                        .permitAll()
                    .antMatchers(HttpMethod.GET, "/api/**")
                        .permitAll()
                    .antMatchers(HttpMethod.GET, "/passengers/add").hasRole("ADMIN")
                    .antMatchers(HttpMethod.GET, "/flights/add").hasRole("ADMIN")
                    .antMatchers(HttpMethod.GET, "/passengers").denyAll()
                .regexMatchers(HttpMethod.GET, "/(flights|airline(s)?)(/details)?(\\?.+)?")
                        .permitAll()
                    .regexMatchers(HttpMethod.GET, ".+\\.(js|css|map|jpg|png|woff|woff2)(\\?.*)?")
                        .permitAll()
                    .anyRequest()
                        .authenticated()
                    .and()
                .csrf()
                    .and()
                .formLogin()
                    .loginPage("/login")
                        .permitAll()
                    .and()
                .logout()
                    .permitAll();
        //@formatter:on
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private HttpStatusEntryPoint httpStatusEntryPoint() {
        return new HttpStatusEntryPoint(HttpStatus.FORBIDDEN);
    }

}
