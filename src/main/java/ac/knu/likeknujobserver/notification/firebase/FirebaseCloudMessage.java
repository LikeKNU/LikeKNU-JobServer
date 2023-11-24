package ac.knu.likeknujobserver.notification.firebase;

import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MulticastMessage;
import org.springframework.stereotype.Component;

import java.util.List;

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
            BatchResponse batchResponse = FirebaseMessaging.getInstance().sendEachForMulticast(multicastMessage);
        } catch (FirebaseMessagingException e) {
            //TODO FCM error handling
        }
    }
}
