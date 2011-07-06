package ua.kharkov.kture.ot.view.swing.mainwindow.elements;

import com.google.inject.assistedinject.Assisted;
import ua.kharkov.kture.ot.common.Dimension;
import ua.kharkov.kture.ot.common.math.Logic;
import ua.kharkov.kture.ot.common.math.Point;
import ua.kharkov.kture.ot.common.math.Probability;
import ua.kharkov.kture.ot.shared.simpleobjects.HazardDTO;
import ua.kharkov.kture.ot.view.declaration.viewers.CalculatedEventView;
import ua.kharkov.kture.ot.view.swing.utils.Dirty;
import ua.kharkov.kture.ot.view.swing.utils.content.ConstantKeeper;
import ua.kharkov.kture.ot.view.swing.utils.content.ImageKeeper;

import javax.inject.Inject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com Date: 12.02.11
 */
public class SwingLogicElementViewer extends
        SwingFigureViewer<CalculatedEventView.Presenter> implements
        CalculatedEventView {

    protected JSpinner twinsSpinner;
    protected HazardDTO dto;

    @Inject
    public SwingLogicElementViewer(@Assisted HazardDTO dto) {
        super(dto);
        this.dto = dto;

        title.setBounds(1, ConstantKeeper.TITLE_LINE_HEIGHT, ConstantKeeper.TEXT_SIZE.getWidth() - 1, ConstantKeeper.TEXT_SIZE.getHeight());

        this.twinsSpinner = new JSpinner();
        twinsSpinner.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                getPresenter().twins((Integer) ((JSpinner) e.getComponent()).getValue());
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }
        });
        twinsSpinner.setValue(dto.getTwins());
        twinsSpinner.setBounds((ConstantKeeper.CALC_COMPONENT_SIZE.getWidth() - ConstantKeeper.SPINNER_SIZE.getWidth()) / 2, ConstantKeeper.CALC_COMPONENT_SIZE.getHeight() + 5, ConstantKeeper.SPINNER_SIZE.getWidth(), ConstantKeeper.SPINNER_SIZE.getHeight());
        add(twinsSpinner);

        Dimension size = new ConstantKeeper().getCalculatedEventsSize(dto.getLogic(), dto.getTwins());
        setSize(size.getWidth(), size.getHeight());
    }

    @Override
    @Dirty
    public void setLogic(Logic newLogic) {
        dto.setLogic(newLogic);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(0xDDDDDD));
        g.fillRect(0, 0,
                ConstantKeeper.CALC_COMPONENT_SIZE.getWidth(),
                ConstantKeeper.CALC_COMPONENT_SIZE.getHeight());
        if (isFocusOwner()) {
            g.setColor(Color.red);
        } else {
            g.setColor(Color.gray);
        }
        g.drawRect(0, 0,
                ConstantKeeper.CALC_COMPONENT_SIZE.getWidth() - 1,
                ConstantKeeper.CALC_COMPONENT_SIZE.getHeight());
        g.setColor(Color.black);

        g.drawLine(
                ConstantKeeper.CALC_COMPONENT_SIZE.getWidth() / 2,
                ConstantKeeper.CALC_COMPONENT_SIZE.getHeight(),
                ConstantKeeper.CALC_COMPONENT_SIZE.getWidth() / 2,
                ConstantKeeper.CALC_COMPONENT_SIZE.getHeight() + ConstantKeeper.LOGICAL_LINE_LENGTH);

        int twins = getTwins();
        twinsSpinner.setVisible(twins > 1);

        g.drawImage(
                ImageKeeper.INSTANCE.getLogicElementImage(dto.getLogic(), twins),
                (ConstantKeeper.CALC_COMPONENT_SIZE.getWidth()
                        - ImageKeeper.INSTANCE.getLogicElementImage(dto.getLogic(), twins).getWidth(null)) / 2,
                ConstantKeeper.CALC_COMPONENT_SIZE.getHeight() + ConstantKeeper.LOGICAL_LINE_LENGTH,
                null);
    }

    @Override
    protected Dimension getElementSize() {
        int twins = getTwins();
        return new Dimension(Math.max((int) ConstantKeeper.CALC_COMPONENT_SIZE
                .getWidth(),
                ImageKeeper.INSTANCE.getLogicElementImage(dto.getLogic(), twins)
                        .getWidth(null)), ImageKeeper.INSTANCE
                .getLogicElementImage(dto.getLogic(), twins).getHeight(null)
                + 2
                * ConstantKeeper.LOGICAL_LINE_LENGTH
                + (int) ConstantKeeper.CALC_COMPONENT_SIZE.getHeight());
    }

    @Override
    public void setPresenter(CalculatedEventView.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    @Dirty
    public void setTwins(int number) {
        twinsSpinner.setValue(number);
    }

    public int getTwins() {
        return (Integer) twinsSpinner.getValue();
    }

    @Override
    public void setProbability(Probability probability) {
    }

    @Override
    public ua.kharkov.kture.ot.view.declaration.viewers.CalculatedEventView.Presenter getPresenter() {
        return (ua.kharkov.kture.ot.view.declaration.viewers.CalculatedEventView.Presenter) super
                .getPresenter();
    }

    @Override
    @Dirty
    public void dispose() {
//    	getParent().remove(this);
    }

    @Override
    public void draw(Graphics g, JPanel panel) {
        repaint();
    }

    @Override
    public boolean contains(Point location) {
        return true;
    }

}