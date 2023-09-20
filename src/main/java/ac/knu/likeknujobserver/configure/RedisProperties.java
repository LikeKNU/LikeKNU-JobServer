package ac.knu.likeknujobserver.configure;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ConfigurationProperties(prefix = "redis")
@Component
public class RedisProperties {

    private String host;
    private int port;
    private String password;
}
