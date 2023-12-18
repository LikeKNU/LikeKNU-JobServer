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

            loggingNotificationsError(batchResponse);
        } catch (FirebaseMessagingException e) {
            log.error("Failed to send push notifications", e);
        }
    }

    private static void loggingNotificationsError(BatchResponse batchResponse) {
        batchResponse.getResponses()
                .stream()
                .filter(response -> !response.isSuccessful())
                .peek(response -> log.info("response.getMessageId() = {}", response.getMessageId()))
                .map(SendResponse::getException)
                .map(FirebaseMessagingException::getMessagingErrorCode)
                .peek(errorCode -> log.info("errorCode = {}", errorCode));
    }
}
