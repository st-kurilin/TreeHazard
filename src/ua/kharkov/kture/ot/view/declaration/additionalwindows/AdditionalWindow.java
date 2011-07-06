package ua.kharkov.kture.ot.view.declaration.additionalwindows;

import ua.kharkov.kture.ot.shared.navigation.places.Place;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 25.04.11
 */
public interface AdditionalWindow<P extends Place> {
    void display();

    void close();

    boolean isValidToDisplay();
}
