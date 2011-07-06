package ua.kharkov.kture.ot.service.serialization;

import ua.kharkov.kture.ot.model.Hazard;
import ua.kharkov.kture.ot.presenters.HazardPresenter;

import java.io.IOException;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 17.04.11
 */
public interface SerializationService {
    void save(String fileName, Hazard root) throws IOException;

    HazardPresenter load(String fileName) throws IOException;
}
