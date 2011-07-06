package ua.kharkov.kture.ot.common.eventbus;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.apache.log4j.Logger;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 16.04.11
 */
public final class SimpleEventBus implements EventBus {
    private static Logger LOG = Logger.getLogger(SimpleEventBus.class);

    private final Multimap<Class<? extends Event>, Handler<? extends Event>> handlers = HashMultimap.create();

    @Override
    public <T extends Event> void addHandler(Class<T> eventType, Handler<T> handler) {
        handlers.put(eventType, handler);
    }

    @Override
    @SuppressWarnings("unchecked")  //you can't put anything else in handlers
    public void fireEvent(Event event) {
        LOG.debug("fired " + event);
        for (Handler handler : handlers.get(event.getClass())) {
            LOG.debug("handled by " + handler);
            handler.handle(event);
        }
    }
}
