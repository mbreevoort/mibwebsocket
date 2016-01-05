package nl.mib.websocket;


import nl.mib.websocket.data.ClockStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/clock").withSockJS();
    }

    @Bean
    public ClockStream clockStream() {
        return new ClockStream();
    }

    @Bean
    public String startStream(ClockStream clockStream, SimpMessagingTemplate messagingTemplate) {

        Runnable clocktask = () -> {
            clockStream.clockStream()//
                    .forEach(c -> {
                                messagingTemplate.convertAndSend("/topic/clock", c);
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                    );
        };
        new Thread(clocktask).start();
        return "started";
    }
}