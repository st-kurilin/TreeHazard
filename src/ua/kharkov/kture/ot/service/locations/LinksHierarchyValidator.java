package ua.kharkov.kture.ot.service.locations;

import com.google.common.base.Preconditions;
import com.google.common.collect.Ordering;
import ua.kharkov.kture.ot.common.Dimension;
import ua.kharkov.kture.ot.common.math.Point;
import ua.kharkov.kture.ot.view.declaration.SizeRetriever;

import javax.inject.Inject;

public class LinksHierarchyValidator extends LocationValidator {

    @Inject
    SizeRetriever sizeRetriever;

    @Override
    public Point newLocation() {
        Preconditions.checkNotNull(wish);

        Point[] h = highest.toArray(new Point[]{});
        int minY = 0;
        int maxY = Integer.MAX_VALUE;

        for (Point hi : highest) {
            minY = Math.max(minY, hi.getY() + sizeRetriever.getMaxElementSize().getHeight());
        }
        for (Point hi : lowest) {
            maxY = Math.min(maxY, hi.getY());
        }
        return new Point(Math.max(wish.getX(), 0), Math.max(minY, Math.min(wish.getY(), maxY)));

    }

    private Point addDelta(Point point) {
        Dimension delta = sizeRetriever.getMaxElementSize();
        return new Point(point.getX() + delta.getWidth(), point.getY() + delta.getHeight());
    }

    private static final Ordering<Point> DIST_FROM_O_BY_Y = new Ordering<Point>() {
        @Override
        public int compare(Point left, Point right) {
            return left.getY() - right.getY();
        }
    };

}
