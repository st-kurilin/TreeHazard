package ua.kharkov.kture.ot.shared.navigation.places;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 25.04.11
 */
public abstract class NonMainWindowPlace extends AbstractPlace {
    public boolean hidePreviousWindow() {
        return false;
    }

    public boolean hideMainWindow() {
        return false;
    }
}
