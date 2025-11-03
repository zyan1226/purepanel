package cn.yzdoit.purepanel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class PurepanelBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(PurepanelBackendApplication.class, args);
    }

}
