package ua.kharkov.kture.ot.service.locations;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import ua.kharkov.kture.ot.common.math.Point;
import ua.kharkov.kture.ot.view.declaration.SizeRetriever;

import javax.inject.Inject;
import java.util.Arrays;

import static com.google.common.collect.Ordering.natural;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 27.04.11
 */

public class HierarchyValidator extends LocationValidator {
    @Inject
    SizeRetriever sizeRetriever;

    @Override
    public Point newLocation() {
        Preconditions.checkNotNull(wish);
        //[stas] just for fun
        return new Point(natural().sortedCopy(ImmutableList.of(wish.getX(), 0, sizeRetriever.windowSize().getWidth() - sizeRetriever.getComponentsSize().getWidth())).get(1),
                Ordering.natural().sortedCopy(Arrays.asList(natural().min(Lists.asList(sizeRetriever.windowSize().getHeight() - sizeRetriever.getMaxElementSize().getHeight(), Collections2.transform(lowest, new Function<Point, Integer>() {
                    @Override
                    public Integer apply(Point input) {
                        return input.getY();
                    }
                }).toArray(new Integer[]{}))) - (lowest.isEmpty() ? 0 : sizeRetriever.getMaxElementSize().getHeight()), natural().max(Lists.asList(Point.HIGHEST.getY(), Collections2.transform(highest, new Function<Point, Integer>() {
                    @Override
                    public Integer apply(Point input) {
                        return input.getY();
                    }
                }).toArray(new Integer[]{}))) + (highest.isEmpty() ? 0 : sizeRetriever.getMaxElementSize().getHeight()), wish.getY())).get(1));
    }
}
