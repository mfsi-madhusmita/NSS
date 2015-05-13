package be.nss.vit2print.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Spring Java Configuration
 */
@Configuration
@ComponentScan(basePackages = "be.nss.vit2print")
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

}
