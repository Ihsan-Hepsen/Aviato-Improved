package ih.ifbs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IntlFlightBrowsingSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(IntlFlightBrowsingSystemApplication.class, args);

        // FOR CONSOLE APPLICATION ONLY!!
//        ConfigurableApplicationContext context =
//                SpringApplication.run(IntlFlightBrowsingSystemApplication.class, args);
//        View view = context.getBean(View.class);
//        view.displayMainMenu();
//        context.close();
        // FOR CONSOLE APPLICATION ONLY!!
    }
}
