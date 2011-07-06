package ua.kharkov.kture.ot.common.navigation;

import com.google.common.collect.Maps;
import ua.kharkov.kture.ot.shared.navigation.places.ErrorMessagePlace;
import ua.kharkov.kture.ot.shared.navigation.places.Place;
import ua.kharkov.kture.ot.view.declaration.additionalwindows.AdditionalWindow;
import ua.kharkov.kture.ot.view.declaration.additionalwindows.WindowProvider;

import java.util.Map;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 25.04.11
 */
public class NavigatorImpl implements Navigator {
    private final Map<Class<?>, WindowProvider> factories = Maps.newHashMap();
    private final Map<Class<?>, String> errorKeys = Maps.newHashMap();
    private AdditionalWindow currentAdditionalWindow;

    @Override
    public <P extends Place> void addWindowDescription(Class<P> place, WindowProvider<P> windowProvider, String errorKey) {
        factories.put(place, windowProvider);
        errorKeys.put(place, errorKey);
    }

    @Override
    public void goTo(Place place) {
        closeCurrentAdditionalWindow();
        currentAdditionalWindow = createWindow(place);
        showNewWindow(place);
    }

    private void closeCurrentAdditionalWindow() {
        if (currentAdditionalWindow != null) {
            currentAdditionalWindow.close();
        }
    }

    @SuppressWarnings("unchecked")  //map is private final. access protected by add method.
    private AdditionalWindow createWindow(Place place) {
        if (!factories.containsKey(place.getClass())) {
            throw new AssertionError("Place unmapped: " + place.getClass());
        }
        WindowProvider factory = factories.get(place.getClass());
        return factory.get(place);
    }

    private void showErrorMessage(Place place) {
        if (!errorKeys.containsKey(place.getClass())) {
            throw new AssertionError("There is no error message for " + place.getClass());
        }
        goTo(new ErrorMessagePlace(errorKeys.get(place.getClass())));
    }

    private void showNewWindow(Place place) {
        if (!currentAdditionalWindow.isValidToDisplay()) {
            showErrorMessage(place);
            return;
        }
        currentAdditionalWindow.display();
    }
}
