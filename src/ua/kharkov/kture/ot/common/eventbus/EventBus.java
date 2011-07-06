package ua.kharkov.kture.ot.common.eventbus;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 16.04.11
 */
public interface EventBus {
    <T extends Event> void addHandler(Class<T> eventType, Handler<T> handler);

    void fireEvent(Event event);

    interface Event {
    }

    interface Handler<E extends Event> {
        void handle(E event);
    }
}
