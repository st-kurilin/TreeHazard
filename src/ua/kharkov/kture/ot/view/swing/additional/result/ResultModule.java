package ua.kharkov.kture.ot.view.swing.additional.result;

import com.google.inject.name.Names;
import ua.kharkov.kture.ot.common.eventbus.EventBus;
import ua.kharkov.kture.ot.common.localization.MessageBundle;
import ua.kharkov.kture.ot.common.localization.StringBasedBundle;
import ua.kharkov.kture.ot.shared.navigation.places.ResultsPlace;
import ua.kharkov.kture.ot.view.swing.additional.AbstractWindowModule;

import java.util.Locale;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 14.05.11
 */
public class ResultModule extends AbstractWindowModule {
    public ResultModule(EventBus eventBus) {
        super(eventBus);
    }

    @Override
    protected void configure() {
        bindPlace(ResultsPlace.class).onValidationFailErrorMessage("variantsNotFilled").toWindow(ResultWindow.class);
        MessageBundle bundle = new StringBasedBundle("ResultWindow", new Locale("ru", "RU"));
        bind(MessageBundle.class).annotatedWith(Names.named("resultWindow")).toInstance(bundle);
    }
}
