package ac.knu.likeknujobserver.announcement.api.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatCompletionMessage {

    private String role;
    private String content;

    protected ChatCompletionMessage() {
    }

    @Builder
    public ChatCompletionMessage(String role, String content) {
        this.role = role;
        this.content = content;
    }

    public static ChatCompletionMessage ofSystem() {
        return ChatCompletionMessage.builder()
                .role("system")
                .content("A bot that only speaks words that tag the entered sentence.")
                .build();
    }

    public static ChatCompletionMessage ofUser(String content) {
        return ChatCompletionMessage.builder()
                .role("user")
                .content(content)
                .build();
    }
}
