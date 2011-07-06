package ua.kharkov.kture.ot.shared.navigation;

import ua.kharkov.kture.ot.common.eventbus.EventBus;
import ua.kharkov.kture.ot.shared.navigation.places.Place;
import ua.kharkov.kture.ot.view.declaration.additionalwindows.WindowProvider;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 06.05.11
 */
public class MappingCreatedEvent implements EventBus.Event {
    private final Class<? extends Place> place;
    private final WindowProvider provider;
    private final String errorKey;

    public MappingCreatedEvent(Class<? extends Place> place, WindowProvider<?> provider, String errorKey) {
        this.place = place;
        this.provider = provider;
        this.errorKey = errorKey;
    }

    public Class<? extends Place> getPlace() {
        return place;
    }

    public WindowProvider getProvider() {
        return provider;
    }

    public String getErrorKey() {
        return errorKey;
    }
}
