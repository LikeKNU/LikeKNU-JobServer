package ac.knu.likeknujobserver.announcement.ai;

import ac.knu.likeknujobserver.announcement.value.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class OpenAIFineTuning {

    private final ChatClient chatClient;

    public OpenAIFineTuning(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public Tag abstractTagOfAnnouncement(String announcementTitle) {
        SystemMessage systemMessage = new SystemMessage("A bot that only speaks words that tag the entered sentence.");
        UserMessage userMessage = new UserMessage(announcementTitle);
        ChatResponse chatResponse = chatClient.call(new Prompt(List.of(systemMessage, userMessage)));
        String content = chatResponse.getResult()
                .getOutput()
                .getContent();
        return Tag.of(content);
    }
}
