package nl.mib.websocket.data;

import org.springframework.messaging.handler.annotation.SendTo;

import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.StreamSupport;

public class ClockStream {

    public LongStream clockStream() {
        final PrimitiveIterator.OfLong iterator = new PrimitiveIterator.OfLong() {


            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public long nextLong() {

                return System.currentTimeMillis();
            }
        };

        return StreamSupport.longStream(Spliterators.spliteratorUnknownSize(
                iterator,
                Spliterator.ORDERED | Spliterator.IMMUTABLE | Spliterator.NONNULL), false);
    }

//    @SendTo("/topic/clock")
//    public Long emit(long timems) {
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//
//        }
//        return timems;
//    }
}
