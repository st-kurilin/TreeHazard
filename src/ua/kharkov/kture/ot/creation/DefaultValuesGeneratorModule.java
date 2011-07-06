package ua.kharkov.kture.ot.creation;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import ua.kharkov.kture.ot.common.localization.MessageBundle;
import ua.kharkov.kture.ot.common.localization.StringBasedBundle;
import ua.kharkov.kture.ot.common.math.Logic;
import ua.kharkov.kture.ot.common.math.Point;

import java.util.Locale;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 25.04.11
 */
public class DefaultValuesGeneratorModule extends AbstractModule {
    @Override
    protected void configure() {
        MessageBundle bundle = new StringBasedBundle("ValuesGenerator", new Locale("ru", "RU"));
        bind(MessageBundle.class)
                .annotatedWith(Names.named("values generator"))
                .toInstance(bundle);
        bind(String.class).annotatedWith(Names.named("component title")).toProvider(ComponentTitlesProvider.class);
        bind(String.class).annotatedWith(Names.named("broken event title")).toInstance("broken");
        bind(String.class).annotatedWith(Names.named("hazard title")).toProvider(HazardTitlesProvider.class);

        bind(String.class).annotatedWith(Names.named("default.root.title"))
                .toInstance(bundle.getMessage("root.title"));
        bind(Logic.class).annotatedWith(Names.named("default.root.logic")).toInstance(Logic.AND);
        bind(Point.class).annotatedWith(Names.named("default.root.location")).toInstance(new Point(50, 20));
    }

    @Singleton
    private static class ComponentTitlesProvider implements Provider<String> {
        private final MessageBundle localizator;
        private int counter = 0;

        @Inject
        private ComponentTitlesProvider(@Named("values generator") MessageBundle localizator) {
            this.localizator = localizator;
        }

        @Override
        public String get() {
            return localizator.getMessage("component") + " " + "№" + (++counter);
        }
    }

    @Singleton
    private static class HazardTitlesProvider implements Provider<String> {
        private final MessageBundle localizator;
        private int counter = 0;

        @Inject
        private HazardTitlesProvider(@Named("values generator") MessageBundle localizator) {
            this.localizator = localizator;
        }

        @Override
        public String get() {
            return localizator.getMessage("hazard") + " " + "№" + (++counter);
        }
    }
}
