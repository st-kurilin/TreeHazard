package ua.kharkov.kture.ot.model;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import ua.kharkov.kture.ot.common.math.Logic;
import ua.kharkov.kture.ot.common.math.Point;
import ua.kharkov.kture.ot.service.locations.LocationValidator;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 26.04.11
 */
public class RootHazard extends Hazard {
    @Inject
    public RootHazard(@Named("default.root.logic") Logic logic, @Named("default.root.location") Point location, @Named("default.root.title") String title, Provider<LocationValidator> locationValidator) {
        super(logic, location, title, locationValidator);
    }
}
