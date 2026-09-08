package dearest.dearestshop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Value("${file.fileDir}")
    private String fileDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println("===== IMAGE PATH =====");
        System.out.println("fileDir = " + fileDir);


        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:"+fileDir);

    }

}
