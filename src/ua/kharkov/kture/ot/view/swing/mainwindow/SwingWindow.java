package ua.kharkov.kture.ot.view.swing.mainwindow;

import com.google.common.collect.Sets;
import org.apache.log4j.Logger;
import ua.kharkov.kture.ot.common.eventbus.EventBus;
import ua.kharkov.kture.ot.common.math.Point;
import ua.kharkov.kture.ot.view.swing.events.redraw.RedrawEvent;
import ua.kharkov.kture.ot.view.swing.mainwindow.elements.SwingFigureViewer;
import ua.kharkov.kture.ot.view.swing.mainwindow.figuresmoving.MouseActionEvent;
import ua.kharkov.kture.ot.view.swing.utils.SwingUtils;
import ua.kharkov.kture.ot.view.swing.utils.Widget;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Set;


public class SwingWindow extends JFrame implements ua.kharkov.kture.ot.view.declaration.viewers.Window {
    private static Logger LOG = Logger.getLogger(SwingWindow.class);

    private static final long serialVersionUID = 1L;
    private final JPanel panel;
    private final Set<Widget> widgets = Sets.newHashSet();
    private final EventBus eventBus;

    public SwingWindow(EventBus eventBus) {
        this.eventBus = eventBus;
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics graphics) {
                super.paintComponent(graphics);
                onRedraw(graphics, panel);
            }
        }
        ;
        setTitle("Hazard Trees");   //TODO: localize
        panel.setLayout(null);
        panel.setFocusable(true);
        panel.setBackground(new Color(0xfcfcfc));
        this.getGlassPane();
        this.setContentPane(panel);
        bind();
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void bind() {
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                LOG.debug("mousePressed");
                eventBus.fireEvent(MouseActionEvent.ActionType.PRESSED.create(
                        SwingUtils.convert(e.getPoint()),
                        SwingUtils.convert(e.getButton())));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                LOG.debug("mouseReleased");
                eventBus.fireEvent(MouseActionEvent.ActionType.RELEASED.create(
                        SwingUtils.convert(e.getPoint()),
                        SwingUtils.convert(e.getButton())));
            }
        });
        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                LOG.debug("mouseDragged");
                eventBus.fireEvent(MouseActionEvent.ActionType.DRAGGED.create(
                        SwingUtils.convert(e.getPoint()),
                        SwingUtils.convert(e.getButton())));
            }
        });
    }

    protected final void onRedraw(Graphics graphics, JPanel panel) {
        eventBus.fireEvent(new RedrawEvent(graphics, panel));
        for (Widget widget : widgets) {
            widget.draw(graphics, panel);
        }
    }


    public void addWidget(Widget widget) {
        panel.add((SwingFigureViewer<?>) widget);
        repaint();
    }

    public void removeWidget(Widget widget) {
        panel.remove((SwingFigureViewer<?>) widget);
        repaint();
    }

    public Widget getWidgetByLocation(Point location) {
        return panel.getComponentAt(location.getX(), location.getY()) == panel ? null : (Widget) panel.getComponentAt(location.getX(), location.getY());
    }

    @Override
    public void draw() {
        panel.setVisible(true);
        setVisible(true);
    }


}