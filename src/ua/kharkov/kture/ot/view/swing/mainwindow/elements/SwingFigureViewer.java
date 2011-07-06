package ua.kharkov.kture.ot.view.swing.mainwindow.elements;

import ua.kharkov.kture.ot.common.Dimension;
import ua.kharkov.kture.ot.common.math.Point;
import ua.kharkov.kture.ot.shared.simpleobjects.FigureDTO;
import ua.kharkov.kture.ot.view.declaration.viewers.FigureView;
import ua.kharkov.kture.ot.view.swing.mainwindow.elements.additional.TreeTextArea;
import ua.kharkov.kture.ot.view.swing.utils.ChangeDtoField;
import ua.kharkov.kture.ot.view.swing.utils.SwingUtils;
import ua.kharkov.kture.ot.view.swing.utils.Widget;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 12.02.11
 */
public abstract class SwingFigureViewer<P extends FigureView.Presenter> extends JPanel implements FigureView<P>, Widget {
    protected boolean inFocus;
    protected FigureDTO dto;
    protected Presenter presenter;
    protected TreeTextArea title;


    public SwingFigureViewer(FigureDTO dto) {
        this.dto = dto;
        setLayout(null);
        this.setFocusable(false);
        this.setOpaque(false);
        int x = dto.getLocation().getX();
        int y = dto.getLocation().getY();
        setLocation(x, y);


        title = new TreeTextArea();
        title.setText(dto.getTitle());
        title.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                presenter.rename(((TreeTextArea) e.getComponent()).getText());
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }
        });
        add(title);
    }

    @Override
    @ChangeDtoField("title")
    public void setTitle(String title) {
    }

    protected abstract Dimension getElementSize();

    public Presenter getPresenter() {
        return presenter;
    }

    @Override
    public void dispose() {

    }

    @ChangeDtoField("location")
    @Override
    public void setLocation(Point location) {
        setLocation(SwingUtils.convert(location));
    }

    @Override
    public void setLocation(int x, int y) {
        super.setLocation(x, y);
        getBounds().setLocation(x, y);
//        getParent().repaint();
    }

    @Override
    public void setLocation(java.awt.Point point) {
        super.setLocation(point);
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        getBounds().setSize(width, height);
    }

    @Override
    public java.awt.Point getLocation() {
        return getBounds().getLocation();
    }

    @Override
    public void setFocus(boolean focus) {

    }

    @Override
    public void draw(Graphics g, JPanel panel) {

    }

    @Override
    public void addChild(FigureView child) {
    }

    public FigureDTO getDto() {
        return dto;
    }

}
