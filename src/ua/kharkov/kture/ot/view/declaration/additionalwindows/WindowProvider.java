package ua.kharkov.kture.ot.view.declaration.additionalwindows;

import ua.kharkov.kture.ot.shared.navigation.places.Place;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 06.05.11
 */
public interface WindowProvider<P extends Place> {
    AdditionalWindow get(P place);
}
