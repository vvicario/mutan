package mutant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author vvicario
 */
@SpringBootApplication
@EnableAsync
@EnableAutoConfiguration
public class MutantApplication {

    public static void main(String[] args) {
        SpringApplication.run(MutantApplication.class, args);
    }

}

