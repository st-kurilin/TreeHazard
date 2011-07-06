package ua.kharkov.kture.ot.view.swing.additional.serializationdialogsdialog;

import ua.kharkov.kture.ot.common.eventbus.EventBus;
import ua.kharkov.kture.ot.shared.navigation.places.LoadPlace;
import ua.kharkov.kture.ot.shared.navigation.places.SavePlace;
import ua.kharkov.kture.ot.view.swing.additional.AbstractWindowModule;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 06.05.11
 */
public class SerializationDialogsModule extends AbstractWindowModule {
    public SerializationDialogsModule(EventBus eventBus) {
        super(eventBus);
    }

    @Override
    protected void configure() {
        bindPlace(SavePlace.class).toWindow(SaveDialogWindow.class);
        bindPlace(LoadPlace.class).toWindow(OpenDialogWindow.class);
    }
}
