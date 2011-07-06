package ua.kharkov.kture.ot.view.swing.mainwindow.connection;

import com.google.common.base.Function;
import com.google.common.collect.HashMultimap;
import com.google.inject.Inject;
import org.apache.log4j.Logger;
import ua.kharkov.kture.ot.shared.simpleobjects.FigureDTO;
import ua.kharkov.kture.ot.view.swing.utils.Shape2D;

import javax.inject.Singleton;
import java.awt.*;
import java.util.Collection;

import static com.google.common.collect.Collections2.transform;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 08.05.11
 */
@Singleton
public class FiguresConnector {
    private static Logger LOG = Logger.getLogger(FiguresConnector.class);
    private final HashMultimap<FigureDTO, FigureDTO> shapes = HashMultimap.create();
    private final ConnectionStrategy connectionStrategy;
    private final Function<FigureDTO, Shape2D> converter;

    @Inject
    public FiguresConnector(ConnectionStrategy connectionStrategy, Function<FigureDTO, Shape2D> converter) {
        this.connectionStrategy = connectionStrategy;
        this.converter = converter;
    }

    public void addShape(FigureDTO parent, FigureDTO added) {
        shapes.put(parent, added);
        LOG.debug("pair added " + parent + "->" + added);
    }


    public void removeShape(FigureDTO shape) {
        shapes.removeAll(shape);
        shapes.values().remove(shape);
    }

    public void redraw(Graphics graphics) {
        for (FigureDTO parent : shapes.keySet()) {
            Shape2D parentAsShape = converter.apply(parent);
            Collection<Shape2D> childrenShapes = transform(shapes.get(parent), converter);
            connectionStrategy.connect(parentAsShape, childrenShapes, graphics);
        }
    }
}
