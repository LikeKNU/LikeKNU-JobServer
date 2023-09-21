package ac.knu.likeknujobserver.announcement.api;

import ac.knu.likeknujobserver.announcement.api.dto.FineTuningModelRequest;
import ac.knu.likeknujobserver.announcement.api.dto.FineTuningModelResponse;
import ac.knu.likeknujobserver.announcement.value.Tag;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
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
        String url = openAIProperties.getFineTuningUrl();
        String fineTuningModel = openAIProperties.getFineTuningModel();
        String token = openAIProperties.getToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<FineTuningModelRequest> httpEntity =
                new HttpEntity<>(FineTuningModelRequest.of(fineTuningModel, announcementTitle), headers);

        FineTuningModelResponse fineTuningModelResponse =
                restTemplate.postForObject(url, httpEntity, FineTuningModelResponse.class);
        String content = fineTuningModelResponse.getContent();

        return Tag.of(content);
    }
}
