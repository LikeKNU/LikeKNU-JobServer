package ac.knu.likeknujobserver.configure;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;

@Profile("!test")
@Component
public class FirebaseInitializer {

    private final FirebaseProperties firebaseProperties;

    public FirebaseInitializer(FirebaseProperties firebaseProperties) {
        this.firebaseProperties = firebaseProperties;
    }

    @PostConstruct
    void init() throws IOException {
        FileInputStream serviceAccount = new FileInputStream(firebaseProperties.getKeyPath());
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp.initializeApp(options);
    }
}
