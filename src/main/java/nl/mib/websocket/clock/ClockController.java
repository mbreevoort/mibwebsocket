package nl.mib.websocket.clock;

import nl.mib.websocket.data.ClockStream;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ClockController {

    @MessageMapping("/clock")
    @SendTo("/topic/clock")
    public Long clock(ClockMessage message) throws Exception {

        Thread.sleep(3000); // simulated delay
        return ClockStream.clockStream().findFirst().getAsLong();
    }

}
