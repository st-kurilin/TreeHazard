package ua.kharkov.kture.ot.view.swing.additional.help;

import com.google.inject.name.Names;
import ua.kharkov.kture.ot.common.eventbus.EventBus;
import ua.kharkov.kture.ot.common.localization.MessageBundle;
import ua.kharkov.kture.ot.common.localization.StringBasedBundle;
import ua.kharkov.kture.ot.shared.navigation.places.InfoPlace;
import ua.kharkov.kture.ot.view.swing.additional.AbstractWindowModule;

import java.util.Locale;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 23.05.11
 */
public class InfoModule extends AbstractWindowModule {
    public InfoModule(EventBus eventBus) {
        super(eventBus);
    }

    @Override
    protected void configure() {
        bind(MessageBundle.class).annotatedWith(Names.named("info"))
                .toInstance(new StringBasedBundle("Info", new Locale("ru", "RU")));
        bindPlace(InfoPlace.class).toWindow(InfoWindow.class);
    }
}