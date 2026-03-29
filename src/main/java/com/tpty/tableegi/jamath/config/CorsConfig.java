package com.tpty.tableegi.jamath.config;


import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Slf4j
public class CorsConfig implements WebMvcConfigurer {


    @Value("${spring.boot.google.profiles}")
    private String PROFILE_DIR;


    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> servletContainer() {
        return server -> server.addAdditionalTomcatConnectors(httpConnector());
    }

    private Connector httpConnector() {
        log.info("Cors SSL Enabled");
        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
        connector.setScheme("http");
        connector.setPort(8080);
        connector.setSecure(false);
        connector.setRedirectPort(8443);
        return connector;
    }

    @Override   public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Map URL path /profiles/** to the local folder /uploads/profiles/
        registry.addResourceHandler("/profiles/**").addResourceLocations("file:" + PROFILE_DIR);
    }

}
