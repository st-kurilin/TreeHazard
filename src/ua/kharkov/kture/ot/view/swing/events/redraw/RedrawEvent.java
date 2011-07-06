package ua.kharkov.kture.ot.view.swing.events.redraw;

import ua.kharkov.kture.ot.common.eventbus.EventBus;

import javax.swing.*;
import java.awt.*;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 16.04.11
 */
public class RedrawEvent implements EventBus.Event {
    private final Graphics graphics;
    private final JPanel panel;

    public RedrawEvent(Graphics graphics, JPanel panel) {
        this.graphics = graphics;
        this.panel = panel;
    }

    public Graphics getGraphics() {
        return graphics;
    }

    public JPanel getPanel() {
        return panel;
    }
}
