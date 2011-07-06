package ua.kharkov.kture.ot.shared.navigation.places;

import ua.kharkov.kture.ot.view.declaration.additionalwindows.AdditionalWindow;

import java.util.List;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 25.04.11
 */
public class ErrorMessagePlace extends NonMainWindowPlace {
    private final String errorKey;

    public ErrorMessagePlace(String errorKey) {
        this.errorKey = errorKey;
    }

    public String getErrorKey() {
        return errorKey;
    }

    @Override
    public void handleCurrentWindows(List<AdditionalWindow> currentWindows) {
        while (!currentWindows.isEmpty()) {
            AdditionalWindow w = currentWindows.iterator().next();
            w.close();
            currentWindows.remove(w);
        }

    }
}
