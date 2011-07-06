package ua.kharkov.kture.ot.creation;

import com.google.inject.Provides;
import ua.kharkov.kture.ot.common.eventbus.EventBus;
import ua.kharkov.kture.ot.common.guice.EventBusDependedModule;
import ua.kharkov.kture.ot.presenters.ApplicationPresenter;
import ua.kharkov.kture.ot.service.locations.HierarchyValidator;
import ua.kharkov.kture.ot.service.locations.LocationValidator;
import ua.kharkov.kture.ot.shared.OptimizerCriterionKeeper;
import ua.kharkov.kture.ot.view.declaration.additionalwindows.optimization.MinimizationOptimizationCriteria;
import ua.kharkov.kture.ot.view.declaration.viewers.Window;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 25.04.11
 */
public class AdditionalClassesBinding extends EventBusDependedModule {
    public AdditionalClassesBinding(EventBus eventBus) {
        super(eventBus);
    }

    @Override
    protected void configure() {
        bind(EventBus.class).toInstance(eventBus);
        bind(LocationValidator.class).to(HierarchyValidator.class);
        bind(Window.ApplicationPresenter.class).to(ApplicationPresenter.class);
    }

    @Provides
    MinimizationOptimizationCriteria provideDefaultCriterion(OptimizerCriterionKeeper optimizerCriterionKeeper) {
        return optimizerCriterionKeeper;
    }
}
