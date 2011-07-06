package ua.kharkov.kture.ot.view.swing.mainwindow.connection;

import ua.kharkov.kture.ot.view.swing.utils.Shape2D;
import ua.kharkov.kture.ot.view.swing.utils.content.ConstantKeeper;

import java.awt.*;
import java.util.Collection;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 15.04.11
 */
/*connects parent's footer middle with children middle upper part */
public class SimpleConnector implements ConnectionStrategy {
    @Override
    public void connect(Shape2D parent, Collection<Shape2D> shape2Ds, Graphics graphics) {
        graphics.setColor(Color.black);
        graphics.drawLine(
                parent.getLocation().getX() + parent.getSize().getWidth() / 2,
                parent.getLocation().getY() + parent.getSize().getHeight() - ConstantKeeper.LOGICAL_LINE_LENGTH - 50,
                parent.getLocation().getX() + parent.getSize().getWidth() / 2,
                parent.getLocation().getY() + parent.getSize().getHeight()
        );
        for (Shape2D child : shape2Ds) {
            drawConnectionLine(parent, child, graphics);
        }
    }

    private void drawConnectionLine(Shape2D parent, Shape2D child, Graphics graphics) {
        int parentConnectionPointX = parent.getLocation().getX() + parent.getSize().getWidth() / 2;
        int parentConnectionPointY = parent.getLocation().getY() + parent.getSize().getHeight();
        int childConnectionPointX = child.getLocation().getX() + child.getSize().getWidth() / 2;
        int childConnectionPointY = child.getLocation().getY();
        graphics.drawLine(parentConnectionPointX, parentConnectionPointY,
                childConnectionPointX, childConnectionPointY);
    }
}
