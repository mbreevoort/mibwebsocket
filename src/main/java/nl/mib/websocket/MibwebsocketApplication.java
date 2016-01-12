package nl.mib.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class MibwebsocketApplication extends WebMvcConfigurerAdapter {

    @Autowired
    Environment env;

    //@Value("${cam.images.root.dir}")
    //public static String FILE_MNT_SHARE_DATA_MOTION_CAM1;// = "file:///mnt/share/DATA/motion/cam1/";
    public static final String PROPERTY_CAM_IMAGES_ROOT_DIR = "cam.images.root.dir";
    public static final String IMAGES_CAM1 = "/images/cam1/";

    public static void main(String[] args) {
        SpringApplication.run(MibwebsocketApplication.class, args);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String dir = env.getRequiredProperty(PROPERTY_CAM_IMAGES_ROOT_DIR);
        registry.addResourceHandler(IMAGES_CAM1 + "**")
                .addResourceLocations(
                        dir)
                .setCachePeriod(0);

    }
}
