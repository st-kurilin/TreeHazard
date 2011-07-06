package ua.kharkov.kture.ot.view.declaration.viewers;

import ua.kharkov.kture.ot.common.math.Logic;
import ua.kharkov.kture.ot.common.math.Probability;
import ua.kharkov.kture.ot.shared.simpleobjects.VariantDTO;

import java.util.Collection;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 12.02.11
 */
public interface ComponentView extends FigureView<ComponentView.Presenter> {
    interface Presenter extends FigureView.Presenter {
        void changeProbability(Probability probability);

        void changeBrokenEventTitle(String title);

        void decompose(Logic logic);

        void removeVariant(String variantName);

        void addVariant(VariantDTO variant);

        void markVariantAsBase(String variantName);
    }

    void setProbability(Probability probability);

    void setBrokenEventTitle(String title);

    void setVariants(Collection<VariantDTO> variants);

    void setBaseVariant(VariantDTO base);
}
