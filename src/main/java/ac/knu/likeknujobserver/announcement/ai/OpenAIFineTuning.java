package ac.knu.likeknujobserver.announcement.ai;

import ac.knu.likeknujobserver.announcement.value.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class OpenAIFineTuning {

    private final ChatModel chatModel;

    public OpenAIFineTuning(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public Tag abstractTagOfAnnouncement(String announcementTitle) {
        SystemMessage systemMessage = new SystemMessage("A bot that only speaks words that tag the entered sentence.");
        UserMessage userMessage = new UserMessage(announcementTitle);
        ChatResponse chatResponse = chatModel.call(new Prompt(List.of(systemMessage, userMessage)));
        String content = chatResponse.getResult()
                .getOutput()
                .getContent();
        return Tag.of(content);
    }
}
