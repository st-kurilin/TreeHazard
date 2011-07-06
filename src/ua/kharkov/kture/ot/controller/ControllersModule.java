package ua.kharkov.kture.ot.controller;

import ua.kharkov.kture.ot.common.eventbus.EventBus;
import ua.kharkov.kture.ot.common.guice.EventBusDependedModule;
import ua.kharkov.kture.ot.common.guice.InstanceKeeper;
import ua.kharkov.kture.ot.creation.events.RootViewCreatedEvent;
import ua.kharkov.kture.ot.model.Hazard;
import ua.kharkov.kture.ot.service.optimization.OptimizationService;
import ua.kharkov.kture.ot.view.declaration.additionalwindows.optimization.OptimizerController;

import static com.google.inject.name.Names.named;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 28.05.11
 */
public class ControllersModule extends EventBusDependedModule {
    InstanceKeeper<Hazard> root = new InstanceKeeper<Hazard>();

    public ControllersModule(EventBus eventBus) {
        super(eventBus);
    }

    @Override
    protected void configure() {
        eventBus.addHandler(RootViewCreatedEvent.class, new EventBus.Handler<RootViewCreatedEvent>() {
            @Override
            public void handle(RootViewCreatedEvent event) {
                root.set(event.getPresenter().getModel());
            }
        });
        bind(Hazard.class).annotatedWith(named("model.root")).toProvider(root);
        bind(OptimizerController.class).toInstance(new OptimizerControllerImpl(new OptimizationService(), root));
    }
}
