package SharedKernel;

import java.util.ArrayList;
import java.util.List;

public class EventBus {

    private final List<EventHandler<?>> handlers = new ArrayList<>();

    public void register(EventHandler<?> handler) {
        handlers.add(handler);
    }

    public void publish(DomainEvent event) {
        for (EventHandler handler : handlers) {
            try {
                handler.handle(event);
            } catch (ClassCastException ignored) {
            }
        }
    }
}