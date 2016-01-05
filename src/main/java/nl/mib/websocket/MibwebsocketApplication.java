package nl.mib.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class MibwebsocketApplication extends WebMvcConfigurerAdapter {

    public static final String FILE_MNT_SHARE_DATA_MOTION_CAM1 = "file:///mnt/share/DATA/motion/cam1/";
    public static final String IMAGES_CAM1 = "/images/cam1/";

    public static void main(String[] args) {
        SpringApplication.run(MibwebsocketApplication.class, args);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler(IMAGES_CAM1 + "**")
                .addResourceLocations(
                        FILE_MNT_SHARE_DATA_MOTION_CAM1)
                .setCachePeriod(0);

    }
}
