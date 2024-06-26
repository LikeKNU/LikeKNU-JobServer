package ac.knu.likeknujobserver.notification.firebase;

import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.SendResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class FirebaseCloudMessage {

    private static final String MESSAGE_DATA_KEY = "url";

    public void sendMessage(String title, String body, String dataUrl, List<String> tokens) {
        MulticastMessage multicastMessage = MulticastMessage.builder()
                .setNotification(com.google.firebase.messaging.Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .build())
                .putData(MESSAGE_DATA_KEY, dataUrl)
                .addAllTokens(tokens)
                .build();

        try {
            BatchResponse batchResponse = FirebaseMessaging.getInstance()
                    .sendEachForMulticast(multicastMessage);
            int successCount = batchResponse.getSuccessCount();
            log.info("Push notifications successfully sent = {}, total = {}", successCount, tokens.size());

            loggingNotificationsError(batchResponse, tokens);
        } catch (FirebaseMessagingException e) {
            log.error("Failed to send push notifications", e);
        }
    }

    private void loggingNotificationsError(BatchResponse batchResponse, List<String> tokens) {
        List<SendResponse> responses = batchResponse.getResponses();
        for (int i = 0; i < responses.size(); i++) {
            SendResponse response = responses.get(i);
            if (!response.isSuccessful()) {
                log.info("token = {}", tokens.get(i));
                FirebaseMessagingException messagingException = response.getException();
                log.info("exception = ", messagingException);
                log.info("messagingErrorCode = {}", messagingException.getMessagingErrorCode());
            }
        }
    }
}
