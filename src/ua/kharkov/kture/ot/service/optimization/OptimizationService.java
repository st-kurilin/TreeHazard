package ua.kharkov.kture.ot.service.optimization;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;
import ua.kharkov.kture.ot.common.Money;
import ua.kharkov.kture.ot.common.math.ComparableNumber;
import ua.kharkov.kture.ot.common.math.Probability;
import ua.kharkov.kture.ot.model.Component;
import ua.kharkov.kture.ot.model.Figure;
import ua.kharkov.kture.ot.model.Hazard;
import ua.kharkov.kture.ot.model.Visitor;
import ua.kharkov.kture.ot.shared.VariantVO;
import ua.kharkov.kture.ot.view.declaration.additionalwindows.optimization.MinimizationOptimizationCriteria;

import java.util.*;

import static com.google.common.base.Preconditions.checkPositionIndex;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 27.05.11
 */
public class OptimizationService {

    public ComparableNumber valueByCriteria(Hazard root, final Function<Component, VariantVO> variantChooser, MinimizationOptimizationCriteria criteria) {
        Preconditions.checkNotNull(variantChooser);
        Preconditions.checkNotNull(criteria);
        Probability crashProbability = new ProbabilityCalculatorImpl().calculateCrashProbability(root, variantChooser);
        Money totalCost = new Money(calcCost(root, variantChooser));
        return criteria.apply(crashProbability.inScientificForm(), totalCost.getDollars());

    }

    private double calcCost(Figure root, final Function<Component, VariantVO> variantChooser) {
        return root.accept(new Visitor<Double>() {
            @Override
            public Double visitComponent(Component component) {
                Preconditions.checkNotNull(component);
                return variantChooser.apply(component).getCost().getDollars();
            }

            @Override
            public Double visitCalculatedEvent(Hazard hazard) {
                double res = 0;
                for (Figure figure : hazard.getChilds()) {
                    res += calcCost(figure, variantChooser);
                }
                return res;
            }
        });
    }

    public <T extends Function<Component, VariantVO>> T bestBy(final Hazard root, Collection<T> systemVariant, final MinimizationOptimizationCriteria criteria) {
        checkPositionIndex(1, systemVariant.size());
        Preconditions.checkNotNull(criteria);
        return Ordering.from(new Comparator<Function<Component, VariantVO>>() {
            @Override
            public int compare(Function<Component, VariantVO> o1, Function<Component, VariantVO> o2) {
                return valueByCriteria(root, o1, criteria).compareTo(valueByCriteria(root, o2, criteria));
            }
        }).min(systemVariant);
    }

    public Collection<Map<Component, VariantVO>> retrieveSystemVariants(Collection<Component> components, Function<Component, Set<VariantVO>> function) {
        Collection<Map<Component, VariantVO>> toRet = Sets.newHashSet();
        List<Component> orderedComponents = new ArrayList<Component>(components);
        Set<List<VariantVO>> systemVariants = Sets.cartesianProduct(Lists.transform(orderedComponents, function));
        for (List<VariantVO> systemVariant : systemVariants) {
            ImmutableMap.Builder<Component, VariantVO> builder = ImmutableMap.builder();
            Iterator<Component> it = orderedComponents.iterator();
            for (VariantVO variant : systemVariant) {
                builder.put(it.next(), variant);
            }
            toRet.add(builder.build());
        }
        return toRet;
    }
}
