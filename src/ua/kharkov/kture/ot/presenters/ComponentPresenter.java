package ua.kharkov.kture.ot.presenters;

import com.google.common.base.Function;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import ua.kharkov.kture.ot.common.connector.FieldObserver;
import ua.kharkov.kture.ot.common.math.Logic;
import ua.kharkov.kture.ot.common.math.Probability;
import ua.kharkov.kture.ot.model.Component;
import ua.kharkov.kture.ot.shared.VariantVO;
import ua.kharkov.kture.ot.shared.simpleobjects.VariantDTO;
import ua.kharkov.kture.ot.view.declaration.viewers.ComponentView;

import java.util.Collection;

import static com.google.common.collect.Lists.newLinkedList;


/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 03.04.11
 */
public class ComponentPresenter extends FigurePresenter<Component, ComponentView> implements ComponentView.Presenter {
    private final HazardPresenter parent;

    @Inject
    public ComponentPresenter(@Assisted HazardPresenter parent, Component model, @Assisted ComponentView view) {
        super(model, view);
        this.parent = parent;
    }

    @Override
    public void changeProbability(Probability probability) {
        VariantVO variant = new VariantVO(probability);
        getModel().addVariant(variant);
    }

    @FieldObserver("label.probability")
    public void onProbabilityChanged() {
        getView().setProbability(getModel().getBaseVariant().getCrashProbability());
    }

    @Override
    public void changeBrokenEventTitle(String title) {
        getModel().setBrokenEventTitle(title);
    }

    @FieldObserver("BrokenEventTitle")
    public void onBrokenEventTitleChanged() {
        getView().setBrokenEventTitle(getModel().getBrokenEventTitle());
    }

    @Override
    public void decompose(Logic logic) {
        delete();
        parent.addEventChild(getModel().getBrokenEventTitle(), logic, getModel().getLocation());
    }

    @Override
    public void removeVariant(String name) {
        getModel().removeVariant(name);
    }

    @Override
    public void addVariant(VariantDTO variant) {
        getModel().addVariant(new VariantVO(variant.getName(), variant.getCrashProbability(), variant.getCost()));
    }

    @Override
    public void markVariantAsBase(String variant) {
        getModel().markAsBase(variant);
    }

    @FieldObserver("variants")
    public void onVariantChange() {
        Collection<VariantDTO> dtos = newLinkedList();
        VariantDTO base = null;
        for (VariantVO vo : getModel().getVariants()) {
            VariantDTO dto = converter.apply(vo);
            dtos.add(dto);
            if (getModel().getBaseVariant().equals(vo)) {
                base = dto;
            }
        }
        getView().setVariants(dtos);
        getView().setBaseVariant(base);
    }

    private static final Function<VariantVO, VariantDTO> converter = new Function<VariantVO, VariantDTO>() {
        @Override
        public VariantDTO apply(VariantVO input) {
            return new VariantDTO(input.getName(), input.getCrashProbability(), input.getCost());
        }
    };
}
