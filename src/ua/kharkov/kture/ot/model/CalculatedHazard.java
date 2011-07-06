package ua.kharkov.kture.ot.model;

import com.google.common.base.Function;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.assistedinject.Assisted;
import ua.kharkov.kture.ot.common.math.Logic;
import ua.kharkov.kture.ot.common.math.Point;
import ua.kharkov.kture.ot.service.locations.LocationValidator;

import static com.google.common.collect.Collections2.transform;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 26.04.11
 */
public class CalculatedHazard extends Hazard {
    private final Hazard parent;
    private final Provider<LocationValidator> locationValidator;

    @Inject
    public CalculatedHazard(@Assisted Logic logic, @Assisted Hazard parent, @Assisted Point location, @Assisted String title, Provider<LocationValidator> locationValidator) {
        super(logic, location, title, locationValidator);
        this.parent = parent;
        this.locationValidator = locationValidator;
        this.location = location;
    }

    @Override
    public void setLocation(Point wish) {
        super.setLocation(locationValidator.get()
                .highest(parent.getLocation())
                .lowest(transform(getChilds(), new Function<Figure, Point>() {
                    @Override
                    public Point apply(Figure input) {
                        return input.getLocation();
                    }
                }))
                .wish(wish).newLocation());
    }

    @Override
    public void delete() {
        super.delete();
        parent.removeChild(this);
    }
}
