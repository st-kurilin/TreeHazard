package ua.kharkov.kture.ot.service.optimization;

import com.google.common.base.Function;
import ua.kharkov.kture.ot.common.math.Probability;
import ua.kharkov.kture.ot.model.Component;
import ua.kharkov.kture.ot.model.Figure;
import ua.kharkov.kture.ot.model.Hazard;
import ua.kharkov.kture.ot.model.Visitor;
import ua.kharkov.kture.ot.shared.VariantVO;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 12.05.11
 */
public class ProbabilityCalculatorImpl implements ProbabilityCalculator {


    //TODO: FIX_ME wrong overriding
    @Override
    public Probability calculateCrashProbability(Hazard root, Function<Component, VariantVO> variantChooser) {
        return calculateCrashProbability((Figure) root, variantChooser);
    }

    public Probability calculateCrashProbability(Figure root, final Function<Component, VariantVO> variantChooser) {
        return root.accept(new Visitor<Probability>() {
            @Override
            public Probability visitComponent(Component component) {
                return variantChooser.apply(component).getCrashProbability();
            }

            @Override
            public Probability visitCalculatedEvent(Hazard hazard) {
                List<Probability> base = newArrayList();
                for (Figure figure : hazard.getChilds()) {
                    base.add(ProbabilityCalculatorImpl.this.calculateCrashProbability(figure, variantChooser));
                }
                return Probability.commonForm(Math.pow(hazard.getLogic().calcTotalProbability(base).inCommonForm(), hazard.getTwins()));
            }
        });
    }

}
