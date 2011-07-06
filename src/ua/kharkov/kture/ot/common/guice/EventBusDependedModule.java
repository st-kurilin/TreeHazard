package ua.kharkov.kture.ot.common.guice;

import com.google.inject.AbstractModule;
import ua.kharkov.kture.ot.common.eventbus.EventBus;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 06.05.11
 */
public abstract class EventBusDependedModule extends AbstractModule {
    protected final EventBus eventBus;

    public EventBusDependedModule(EventBus eventBus) {
        this.eventBus = eventBus;
    }
}
