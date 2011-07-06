package ua.kharkov.kture.ot.view.swing;

import com.google.inject.assistedinject.FactoryModuleBuilder;
import ua.kharkov.kture.ot.common.eventbus.EventBus;
import ua.kharkov.kture.ot.common.guice.EventBusDependedModule;
import ua.kharkov.kture.ot.creation.mvp.ModelRelatedModule;
import ua.kharkov.kture.ot.view.declaration.SizeRetriever;
import ua.kharkov.kture.ot.view.declaration.viewers.CalculatedEventView;
import ua.kharkov.kture.ot.view.declaration.viewers.ComponentView;
import ua.kharkov.kture.ot.view.swing.additional.AdditionalWindowsModule;
import ua.kharkov.kture.ot.view.swing.changemodel.ModelChangesModule;
import ua.kharkov.kture.ot.view.swing.innerrepo.ViewInnerRepositoryModule;
import ua.kharkov.kture.ot.view.swing.mainwindow.MainWindowModule;
import ua.kharkov.kture.ot.view.swing.mainwindow.elements.SwingComponentViewer;
import ua.kharkov.kture.ot.view.swing.mainwindow.elements.SwingLogicElementViewer;
import ua.kharkov.kture.ot.view.swing.utils.content.ConstantKeeper;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 11.04.11
 */
public class SwingViewModule extends EventBusDependedModule {
    public SwingViewModule(EventBus eventBus) {
        super(eventBus);
    }

    @Override
    protected void configure() {
        install(new MainWindowModule(eventBus));
        install(new ModelChangesModule(eventBus));
        bind(SizeRetriever.class).to(ConstantKeeper.class);

        install(new FactoryModuleBuilder()
                .implement(CalculatedEventView.class, SwingLogicElementViewer.class)
                .build(ModelRelatedModule.HazardViewFactory.class));
        install(new FactoryModuleBuilder()
                .implement(ComponentView.class, SwingComponentViewer.class)
                .build(ModelRelatedModule.ComponentViewFactory.class));
        install(new FactoryModuleBuilder()
                .implement(CalculatedEventView.class, SwingLogicElementViewer.class)
                .build(ModelRelatedModule.RootViewFactory.class));

        install(new AdditionalWindowsModule(eventBus));
        install(new ViewInnerRepositoryModule(eventBus));
    }
}
