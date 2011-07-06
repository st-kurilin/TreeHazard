package ua.kharkov.kture.ot.view.swing.mainwindow.elements;

import com.google.inject.assistedinject.Assisted;
import ua.kharkov.kture.ot.common.Dimension;
import ua.kharkov.kture.ot.common.math.Point;
import ua.kharkov.kture.ot.common.math.Probability;
import ua.kharkov.kture.ot.shared.simpleobjects.ComponentDTO;
import ua.kharkov.kture.ot.shared.simpleobjects.VariantDTO;
import ua.kharkov.kture.ot.view.declaration.viewers.ComponentView;
import ua.kharkov.kture.ot.view.swing.utils.ChangeDtoField;
import ua.kharkov.kture.ot.view.swing.utils.Dirty;
import ua.kharkov.kture.ot.view.swing.utils.content.ConstantKeeper;

import javax.inject.Inject;
import java.awt.*;
import java.util.Collection;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 12.02.11
 */
public class SwingComponentViewer extends SwingFigureViewer<ComponentView.Presenter> implements ComponentView {


    @Inject
    public SwingComponentViewer(@Assisted ComponentDTO dto) {
        super(dto);

        Dimension titleSize = new Dimension((int) (ConstantKeeper.COMPONENT_SIZE.getWidth() / Math.sqrt(2)), (int) (ConstantKeeper.COMPONENT_SIZE.getHeight() / Math.sqrt(2)));

        title.setBounds(
                ConstantKeeper.COMPONENT_SIZE.getWidth() / 2 - titleSize.getWidth() / 2,
                ConstantKeeper.COMPONENT_SIZE.getHeight() / 2 - titleSize.getHeight() / 2,
                titleSize.getWidth(),
                titleSize.getHeight()
        );

        setSize(ConstantKeeper.COMPONENT_SIZE.getWidth(), ConstantKeeper.COMPONENT_SIZE.getHeight());
    }

    @Override
    @ChangeDtoField("brokenEventTitle")
    public void setBrokenEventTitle(String brokenEventTitle) {
    }

    @Override
    @ChangeDtoField("variants")
    public void setVariants(Collection<VariantDTO> variants) {
    }

    @Override
    @ChangeDtoField("baseVariant")
    public void setBaseVariant(VariantDTO base) {
    }

    @Override
    public void setPresenter(ComponentView.Presenter presenter) {
        this.presenter = presenter;
    }


    @Override
    @ChangeDtoField("title")
    public void setTitle(String title) {
        this.title.setText(dto.getTitle());
    }

    @Override
    @ChangeDtoField("probability")
    public void setProbability(Probability probability) {
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(0xF9F9F9));
        g.fillOval(0, 0, ConstantKeeper.COMPONENT_SIZE.getWidth() - 1, ConstantKeeper.COMPONENT_SIZE.getHeight() - 1);
        g.setColor(Color.gray);
        g.drawOval(0, 0, ConstantKeeper.COMPONENT_SIZE.getWidth() - 1, ConstantKeeper.COMPONENT_SIZE.getHeight() - 1);
//        if (isInFocus()) {
//            g.setColor(Color.red);
//        } else {
//            g.setColor(Color.green);
//        }

    }

    protected Dimension getElementSize() {
        return ConstantKeeper.COMPONENT_SIZE;
    }

    @Override
    @Dirty
    public void dispose() {
    }

    @Override
    public ua.kharkov.kture.ot.view.declaration.viewers.ComponentView.Presenter getPresenter() {
        return (ua.kharkov.kture.ot.view.declaration.viewers.ComponentView.Presenter) super.getPresenter();
    }

    @Override
    public boolean contains(Point location) {
        double centerX = getLocation().getX() + ConstantKeeper.COMPONENT_SIZE.getWidth() / 2;
        double centerY = getLocation().getY() + ConstantKeeper.COMPONENT_SIZE.getHeight() / 2;
        return Math.sqrt(Math.pow(location.getX() - centerX, 2) + Math.pow(location.getY() - centerY, 2)) < ConstantKeeper.COMPONENT_SIZE.getWidth();
    }

}
