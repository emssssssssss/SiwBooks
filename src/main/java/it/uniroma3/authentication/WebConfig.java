package it.uniroma3.authentication;

import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AppProperties props;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String uploadPath = Paths.get(props.getUploadDir()).toAbsolutePath().toString() + "/";
        registry
            .addResourceHandler("/uploads/images/**")
            .addResourceLocations("file:" + uploadPath);
    }

}
