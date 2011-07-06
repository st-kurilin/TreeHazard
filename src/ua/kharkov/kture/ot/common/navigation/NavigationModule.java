package ua.kharkov.kture.ot.common.navigation;

import ua.kharkov.kture.ot.common.eventbus.EventBus;
import ua.kharkov.kture.ot.common.guice.EventBusDependedModule;
import ua.kharkov.kture.ot.shared.navigation.MappingCreatedEvent;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 25.04.11
 */
public class NavigationModule extends EventBusDependedModule {
    public NavigationModule(EventBus eventBus) {
        super(eventBus);
    }

    @Override
    protected void configure() {
        final Navigator navigator = new NavigatorImpl();
        bind(Navigator.class).toInstance(navigator);
        eventBus.addHandler(MappingCreatedEvent.class, new EventBus.Handler<MappingCreatedEvent>() {
            @Override
            public void handle(MappingCreatedEvent event) {
                navigator.addWindowDescription(event.getPlace(), event.getProvider(), event.getErrorKey());
            }
        });
    }
}
