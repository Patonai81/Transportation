package hu.webuni.transportation;

import hu.webuni.transportation.config.InitDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;

import javax.transaction.Transactional;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@ServletComponentScan
public class TransportationApplication implements CommandLineRunner {

    @Autowired
    InitDb initDb;

    public static void main(String[] args) {
        SpringApplication.run(TransportationApplication.class, args);
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
   //  initDb.createTestData();
    }
}
