package ApplicationConfigurations;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("ws-observer/src/configs")
public class ApplicationConfigurations {

}


//@Configuration
//@PropertySource("file:config.properties")
//public class ApplicationConfiguration {
//
//    @Autowired
//    private Environment env;
//
//    public void foo() {
//        env.getProperty("gMapReportUrl");
//    }
//
//}