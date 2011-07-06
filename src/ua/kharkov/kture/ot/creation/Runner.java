package ua.kharkov.kture.ot.creation;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import ua.kharkov.kture.ot.common.eventbus.EventBus;
import ua.kharkov.kture.ot.common.eventbus.SimpleEventBus;
import ua.kharkov.kture.ot.common.navigation.NavigationModule;
import ua.kharkov.kture.ot.controller.ControllersModule;
import ua.kharkov.kture.ot.creation.mvp.ModelRelatedModule;
import ua.kharkov.kture.ot.presenters.ApplicationPresenter;
import ua.kharkov.kture.ot.presenters.HazardPresenter;
import ua.kharkov.kture.ot.service.ServicesModule;
import ua.kharkov.kture.ot.shared.OptimizerCriterionKeeper;
import ua.kharkov.kture.ot.shared.PostInitEvent;
import ua.kharkov.kture.ot.view.declaration.additionalwindows.optimization.Criteria;
import ua.kharkov.kture.ot.view.swing.SwingViewModule;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 01.03.11
 */
public class Runner {
    private static Logger LOG = Logger.getLogger(Runner.class);

    static {
        PropertyConfigurator.configure("log4j.properties");
    }

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        LOG.info("application started");
        EventBus eventBus = new SimpleEventBus();
        Injector injector = Guice.createInjector(
                new ServicesModule(),
                new NavigationModule(eventBus),
                new DefaultValuesGeneratorModule(),
                new ModelRelatedModule(),
                new AdditionalClassesBinding(eventBus),
                new ControllersModule(eventBus),
                new SwingViewModule(eventBus)
        );
        ApplicationPresenter application = injector.getInstance(ApplicationPresenter.class);
        application.setRootPresenter(injector.getInstance(Key.get(HazardPresenter.class, Names.named("root"))));
        injector.getInstance(OptimizerCriterionKeeper.class).set(Criteria.CRASH_PROBABILITY);
        eventBus.fireEvent(new PostInitEvent());
        application.go();
    }
}
