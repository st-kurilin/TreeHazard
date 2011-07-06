package ua.kharkov.kture.ot.model;

import com.google.common.base.Function;
import com.google.common.collect.Sets;
import com.google.inject.Provider;
import org.apache.log4j.Logger;
import ua.kharkov.kture.ot.common.math.Logic;
import ua.kharkov.kture.ot.common.math.Point;
import ua.kharkov.kture.ot.common.math.Probability;
import ua.kharkov.kture.ot.service.locations.LocationValidator;

import java.util.Collections;
import java.util.Set;

import static com.google.common.collect.Collections2.transform;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 12.02.11
 */
public abstract class Hazard extends Figure {
    private static Logger LOG = Logger.getLogger(Hazard.class);
    private final Provider<LocationValidator> locationValidator;
    private final Set<Figure> childs;
    private Logic logic;
    private int twins = 1;

    {
        childs = Sets.newHashSet();
    }

    public Hazard(Logic logic, Point location, String title, Provider<LocationValidator> locationValidator) {
        this.locationValidator = locationValidator;
        this.title = title;
        this.logic = logic;
        this.location = location;
    }

    public Set<Figure> getChilds() {
        return Collections.unmodifiableSet(childs);
    }

    /*probably should use some validation about before condition*/
    public void addChild(Figure child) {
        childs.add(child);
        listener.update(this, "childs");
    }

    /*probably should use some validation about before condition*/
    public void removeChild(Figure child) {
        LOG.warn("there is no such child " + child);
        childs.remove(child);
        listener.update(this, "childs");
        listener.update(this, "probability");
    }

    public Logic getLogic() {
        return logic;
    }

    public void setLogic(Logic logic) {
        this.logic = logic;
        listener.update(this, "logic");
    }

    public int getTwins() {
        return twins;
    }

    public void setTwins(int twins) {
        this.twins = twins;
        listener.update(this, "twins");
        listener.update(this, "probability");
    }

    @Deprecated
    public Probability calcProbability() {
        return Probability.commonForm(50.);
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitCalculatedEvent(this);
    }

    public void delete() {
        while (!childs.isEmpty()) {
            childs.iterator().next().delete();
        }
        childs.clear();
        super.delete();
    }

    @Override
    public void setLocation(Point wish) {
        super.setLocation(locationValidator.get()
                .lowest(transform(getChilds(), new Function<Figure, Point>() {
                    @Override
                    public Point apply(Figure input) {
                        return input.getLocation();
                    }
                }))
                .wish(wish).newLocation());
    }
}
