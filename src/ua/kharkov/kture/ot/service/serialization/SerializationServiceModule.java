package ua.kharkov.kture.ot.service.serialization;

import com.google.inject.AbstractModule;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 22.04.11
 */
public class SerializationServiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(SerializationService.class).to(XmlSerializationService.class);

    }
}
