package ua.kharkov.kture.ot.view.swing.additional.errors;

import com.google.inject.name.Names;
import ua.kharkov.kture.ot.common.eventbus.EventBus;
import ua.kharkov.kture.ot.common.localization.MessageBundle;
import ua.kharkov.kture.ot.common.localization.StringBasedBundle;
import ua.kharkov.kture.ot.shared.navigation.places.ErrorMessagePlace;
import ua.kharkov.kture.ot.view.declaration.additionalwindows.AdditionalWindow;
import ua.kharkov.kture.ot.view.declaration.additionalwindows.WindowProvider;
import ua.kharkov.kture.ot.view.swing.additional.AbstractWindowModule;

import java.util.Locale;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 25.04.11
 */
public class ErrorsMessagesModule extends AbstractWindowModule {
    public ErrorsMessagesModule(EventBus eventBus) {
        super(eventBus);
    }

    @Override
    protected void configure() {
        final MessageBundle bundle = new StringBasedBundle("ErrorMessages", new Locale("ru", "RU"));
        bind(MessageBundle.class).annotatedWith(Names.named("error messages")).toInstance(bundle);
        bindPlace(ErrorMessagePlace.class).toPlaceDependedProvider(new WindowProvider<ErrorMessagePlace>() {
            @Override
            public AdditionalWindow get(ErrorMessagePlace place) {
                return new ErrorMessageWindow(bundle, place.getErrorKey());
            }
        });
    }
}
