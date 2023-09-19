package ac.knu.likeknujobserver.announcement.api;

import ac.knu.likeknujobserver.announcement.value.Tag;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OpenAI {

    private final OpenAIProperties openAIProperties;
    private final RestTemplate restTemplate;

    public OpenAI(OpenAIProperties openAIProperties, RestTemplate restTemplate) {
        this.openAIProperties = openAIProperties;
        this.restTemplate = restTemplate;
    }

    public Tag abstractTagOfAnnouncement(String announcementTitle) {
        return null;
    }
}
