package co.edu.ustavillavicencio.comeya.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class WSNotification {

    SimpMessagingTemplate messagingTemplate;

    // Enviar notificación a todos los clientes conectados
    // El mensaje se envía a la ruta "/all/notifications"
    // El mensaje se recibe en la ruta "/app/application"
    @MessageMapping("/application")
    @SendTo("/all/notifications")
    public String sendNotification(final String message) {
        return message;
    }

}
