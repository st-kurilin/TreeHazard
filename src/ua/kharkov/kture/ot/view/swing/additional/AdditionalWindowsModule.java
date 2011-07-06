package ua.kharkov.kture.ot.view.swing.additional;

import ua.kharkov.kture.ot.common.eventbus.EventBus;
import ua.kharkov.kture.ot.common.guice.EventBusDependedModule;
import ua.kharkov.kture.ot.view.swing.additional.about.AboutModule;
import ua.kharkov.kture.ot.view.swing.additional.componetedit.ComponentsTransactionalEditModule;
import ua.kharkov.kture.ot.view.swing.additional.errors.ErrorsMessagesModule;
import ua.kharkov.kture.ot.view.swing.additional.help.InfoModule;
import ua.kharkov.kture.ot.view.swing.additional.optimization.OptimizationModule;
import ua.kharkov.kture.ot.view.swing.additional.result.ResultModule;
import ua.kharkov.kture.ot.view.swing.additional.serializationdialogsdialog.SerializationDialogsModule;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 25.04.11
 */
public class AdditionalWindowsModule extends EventBusDependedModule {
    public AdditionalWindowsModule(EventBus eventBus) {
        super(eventBus);
    }

    @Override
    protected void configure() {
        install(new SerializationDialogsModule(eventBus));
        install(new ErrorsMessagesModule(eventBus));
        install(new ComponentsTransactionalEditModule(eventBus));
        install(new OptimizationModule(eventBus));
        install(new ResultModule(eventBus));
        install(new AboutModule(eventBus));
        install(new InfoModule(eventBus));
    }
}
