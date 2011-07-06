package ua.kharkov.kture.ot.service.componentoptions;

import com.google.inject.AbstractModule;
import ua.kharkov.kture.ot.view.declaration.additionalwindows.ComponentsEditUnitOfWork;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 11.05.11
 */
public class ComponentPropertiesEditModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ComponentsEditUnitOfWork.class).to(ComponentsEditUnitOfWorkImpl.class);
    }
}
