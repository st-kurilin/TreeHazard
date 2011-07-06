package ua.kharkov.kture.ot.common.menu;

import ua.kharkov.kture.ot.common.eventbus.EventBus;
import ua.kharkov.kture.ot.shared.navigation.places.Place;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 07.05.11
 */
public interface Item {
    <R> R accept(Visitor<R> visitor);

    interface Visitor<R> {
        R visitBindedToPlace(String title, Place place);

        R visitBindedToEvent(String title, EventBus.Event event);

        R visitSeparator();

        R visitSubmenu(MenuBlock additionalInfo);
    }
}
