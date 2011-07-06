package ua.kharkov.kture.ot.view.swing.additional.about;

import com.google.inject.name.Names;
import ua.kharkov.kture.ot.common.eventbus.EventBus;
import ua.kharkov.kture.ot.common.localization.MessageBundle;
import ua.kharkov.kture.ot.common.localization.StringBasedBundle;
import ua.kharkov.kture.ot.shared.navigation.places.AboutPlace;
import ua.kharkov.kture.ot.view.swing.additional.AbstractWindowModule;

import java.util.Locale;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 23.05.11
 */
public class AboutModule extends AbstractWindowModule {
    public AboutModule(EventBus eventBus) {
        super(eventBus);
    }

    @Override
    protected void configure() {
        bind(MessageBundle.class).annotatedWith(Names.named("about"))
                .toInstance(new StringBasedBundle("About", new Locale("ru", "RU")));
        bindPlace(AboutPlace.class).toWindow(AboutWindow.class);
    }
}