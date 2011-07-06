package ua.kharkov.kture.ot.common.navigation;

import ua.kharkov.kture.ot.shared.navigation.places.Place;
import ua.kharkov.kture.ot.view.declaration.additionalwindows.WindowProvider;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 25.04.11
 */
public interface Navigator {

    void goTo(Place place);

    <P extends Place> void addWindowDescription(Class<P> place, WindowProvider<P> windowProvider, String errorKey);
}
