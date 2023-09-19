package ac.knu.likeknujobserver.announcement.api;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "openai")
public class OpenAIProperties {

    private String token;
    private String fineTuningUrl;
    private String fineTuningModel;
}
