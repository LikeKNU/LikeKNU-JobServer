package ac.knu.likeknujobserver.announcement.api.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class FineTuningModelRequest {

    private final String model;
    private final List<ChatCompletionMessage> messages;

    @Builder
    public FineTuningModelRequest(String model, List<ChatCompletionMessage> messages) {
        this.model = model;
        this.messages = messages;
    }

    public static FineTuningModelRequest of(String fineTuningModel, String content) {
        return FineTuningModelRequest.builder()
                .model(fineTuningModel)
                .messages(List.of(ChatCompletionMessage.ofSystem(), ChatCompletionMessage.ofUser(content)))
                .build();
    }
}
