package ua.kharkov.kture.ot.view.swing.additional;

import com.google.inject.Provider;
import ua.kharkov.kture.ot.common.eventbus.EventBus;
import ua.kharkov.kture.ot.common.guice.EventBusDependedModule;
import ua.kharkov.kture.ot.shared.navigation.MappingCreatedEvent;
import ua.kharkov.kture.ot.shared.navigation.places.Place;
import ua.kharkov.kture.ot.view.declaration.additionalwindows.AdditionalWindow;
import ua.kharkov.kture.ot.view.declaration.additionalwindows.WindowProvider;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 06.05.11
 */
public abstract class AbstractWindowModule extends EventBusDependedModule {
    public AbstractWindowModule(EventBus eventBus) {
        super(eventBus);
    }

    protected PlaceBinder bindPlace(Class<? extends Place> place) {
        return new PlaceBinder(place);
    }

    protected class PlaceBinder {
        Class<? extends Place> place;
        WindowProvider provider;
        String errorKey;


        PlaceBinder(Class<? extends Place> place) {
            this.place = place;
        }

        public void toWindow(Class<? extends AdditionalWindow> window) {
            toProvider(getProvider(window));

        }

        public void toPlaceDependedProvider(final WindowProvider<?> provider) {
            eventBus.fireEvent(new MappingCreatedEvent(place, provider, errorKey));
        }

        public void toProvider(final Provider<? extends AdditionalWindow> provider) {
            toPlaceDependedProvider(new WindowProvider() {
                @Override
                public AdditionalWindow get(Place place) {
                    return provider.get();
                }
            });
        }

        public PlaceBinder onValidationFailErrorMessage(String errorKey) {
            this.errorKey = errorKey;
            return this;
        }
    }
}
