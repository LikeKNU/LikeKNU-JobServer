package ac.knu.likeknujobserver.announcement.api.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class FineTuningModelResponse {

    private List<ChatCompletionChoice> choices;

    public String getContent() {
        return choices.get(0).getMessage().getContent();
    }
}
