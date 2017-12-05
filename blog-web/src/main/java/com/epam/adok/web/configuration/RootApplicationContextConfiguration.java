package com.epam.adok.web.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScans(value = {
        @ComponentScan("com.epam.adok.core"),
        @ComponentScan("com.epam.adok.web")
})
public class RootApplicationContextConfiguration {

}
