package 123;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

/**
 * Application BootStrap
 * @author: binchao
 */
@SpringBootApplication
@ComponentScans(
        // ram component base pkg
        @ComponentScan("123")
)
public class AppBootstrap {

    public static void main(String[] args) {
        SpringApplication.run(AppBootstrap.class);
    }

}
