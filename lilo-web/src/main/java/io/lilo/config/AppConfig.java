package io.lilo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import io.lilo.config.DBConfig;

@Configuration
@Import({
        DBConfig.class
})
public class AppConfig {
}
