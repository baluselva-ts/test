package com.tekion.rolesandpermissionsv2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.tekion.rolesandpermissionsv2",
        "com.tekion.arorapostgres",
        "com.tekion.commons"
})
public class Rolesandpermissionsv2Application {

    public static void main(String[] args) {
        SpringApplication.run(Rolesandpermissionsv2Application.class, args);
    }

}
