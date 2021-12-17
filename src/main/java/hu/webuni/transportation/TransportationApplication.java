package hu.webuni.transportation;

import hu.webuni.transportation.config.InitDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class TransportationApplication implements CommandLineRunner {

    @Autowired
    InitDb initDb;

    public static void main(String[] args) {
        SpringApplication.run(TransportationApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
     initDb.createTestAddresses();
    }
}
