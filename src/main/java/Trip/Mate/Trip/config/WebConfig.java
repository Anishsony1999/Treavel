package Trip.Mate.Trip.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Serve static files from hotelImages and packageImages
        registry.addResourceHandler("/hotelImages/**")
                .addResourceLocations("file:/D:/company_projects/tripmanagement_java/TripV2/Treavel/src/main/resources/static/hotelImages/")
                .setCachePeriod(0);

        registry.addResourceHandler("/packageImages/**")
                .addResourceLocations("file:/D:/company_projects/tripmanagement_java/TripV2/Treavel/src/main/resources/static/packageImages/")
                .setCachePeriod(0);

        registry.addResourceHandler("/memoryImages/**")
                .addResourceLocations("file:/D:/company_projects/tripmanagement_java/TripV2/Treavel/src/main/resources/static/memoryImages/")
                .setCachePeriod(0);
    }
}