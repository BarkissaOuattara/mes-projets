package bf.company.safety.service;

import bf.company.safety.model.Alert;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FcmNotificationService {

    public void sendAlert(Alert alert, List<String> tokens) {
        if (tokens.isEmpty() || FirebaseApp.getApps().isEmpty()) {
            return;
        }

        MulticastMessage message = MulticastMessage.builder()
                .setNotification(Notification.builder()
                        .setTitle(alert.getTitle())
                        .setBody(alert.getMessage())
                        .build())
                .putData("alertId", alert.getId().toString())
                .putData("level", alert.getLevel().name())
                .putData("targetZone", alert.getTargetZone())
                .addAllTokens(tokens)
                .build();

        FirebaseMessaging.getInstance().sendEachForMulticastAsync(message);
    }
}
