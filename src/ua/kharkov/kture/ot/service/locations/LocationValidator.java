package ua.kharkov.kture.ot.service.locations;

import com.google.common.collect.Sets;
import ua.kharkov.kture.ot.common.math.Point;

import java.util.Collection;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 03.04.11
 */
public abstract class LocationValidator {
    Point wish;
    Collection<Point> highest = Sets.newHashSet();
    Collection<Point> lowest = Sets.newHashSet();

    public LocationValidator wish(Point wish) {
        this.wish = wish;
        return this;
    }

    public LocationValidator highest(Point highest) {
        this.highest.add(highest);
        return this;
    }

    public LocationValidator lowest(Point lowest) {
        this.lowest.add(lowest);
        return this;
    }

    //adds
    public LocationValidator highest(Collection<Point> highest) {
        this.highest.addAll(highest);
        return this;
    }

    //adds
    public LocationValidator lowest(Collection<Point> lowest) {
        this.lowest.addAll(lowest);
        return this;
    }

    LocationValidator() {
    }

    public abstract Point newLocation();
}
