package ua.kharkov.kture.ot.service;

import com.google.inject.AbstractModule;
import ua.kharkov.kture.ot.service.componentoptions.ComponentPropertiesEditModule;
import ua.kharkov.kture.ot.service.optimization.OptimizationModule;
import ua.kharkov.kture.ot.service.serialization.SerializationServiceModule;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 22.04.11
 */
public class ServicesModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new SerializationServiceModule());
        install(new OptimizationModule());
        install(new ComponentPropertiesEditModule());
    }
}
