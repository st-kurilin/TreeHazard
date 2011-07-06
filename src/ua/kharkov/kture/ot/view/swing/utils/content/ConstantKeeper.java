package ua.kharkov.kture.ot.view.swing.utils.content;

import ua.kharkov.kture.ot.common.Dimension;
import ua.kharkov.kture.ot.common.math.Logic;
import ua.kharkov.kture.ot.view.declaration.SizeRetriever;
import ua.kharkov.kture.ot.view.swing.mainwindow.SwingWindow;

import javax.inject.Inject;
import javax.inject.Named;

public class ConstantKeeper implements SizeRetriever {

    public static final int TITLE_LINE_HEIGHT = 20;

    public static final int COMPONENT_BORDER = 10;
    public static final int LOGICAL_LINE_LENGTH = 20;

    public static final Dimension TEXT_SIZE = new Dimension(119, 50);
    public static final Dimension SPINNER_SIZE = new Dimension(40, 20);

    public static final Dimension CALC_COMPONENT_SIZE = new Dimension(120, TITLE_LINE_HEIGHT + TEXT_SIZE.getHeight());
    public static final Dimension COMPONENT_SIZE = new Dimension(100, 100);
    @Inject
    @Named("swing.main window")
    SwingWindow mainWindow;

    @Override
    public Dimension getComponentsSize() {
        return COMPONENT_SIZE;
    }

    @Override
    public Dimension getCalculatedEventsSize(
            Logic logic, int twins) {
        return new Dimension(Math.max(
                (int) ConstantKeeper.CALC_COMPONENT_SIZE.getWidth(), ImageKeeper.INSTANCE.getLogicElementImage(logic, twins).getWidth(null)),
                ImageKeeper.INSTANCE.getLogicElementImage(logic, twins).getHeight(null) + 2 * ConstantKeeper.LOGICAL_LINE_LENGTH +
                        (int) ConstantKeeper.CALC_COMPONENT_SIZE.getHeight());
    }

    @Override
    public Dimension getRootSize(Logic logic, int twins) {
        return getCalculatedEventsSize(logic, twins);
    }

    @Override
    public Dimension getMaxElementSize() {
        return getRootSize(Logic.AND, 2);
    }

    @Override
    public Dimension windowSize() {
        return new Dimension((int) mainWindow.getSize().getWidth(), (int) mainWindow.getSize().getHeight());
    }
}
