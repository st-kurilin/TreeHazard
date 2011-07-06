package ua.kharkov.kture.ot.shared.navigation.places;

import ua.kharkov.kture.ot.view.declaration.additionalwindows.AdditionalWindow;

import java.util.List;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 25.04.11
 */
public interface Place {
    void handleCurrentWindows(List<AdditionalWindow> currentWindows);
}
