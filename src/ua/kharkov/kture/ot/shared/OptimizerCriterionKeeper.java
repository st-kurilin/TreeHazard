package ua.kharkov.kture.ot.shared;

import com.google.inject.Provider;
import ua.kharkov.kture.ot.common.math.ComparableNumber;
import ua.kharkov.kture.ot.view.declaration.additionalwindows.optimization.MinimizationOptimizationCriteria;

import javax.inject.Singleton;

/**
 * Keeps default OptimizationCriteria. It can be changed in OptimizationWindow
 *
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 16.05.11
 */
@Singleton
public class OptimizerCriterionKeeper implements Provider<MinimizationOptimizationCriteria>, MinimizationOptimizationCriteria {
    MinimizationOptimizationCriteria criteria;

    public void set(MinimizationOptimizationCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public MinimizationOptimizationCriteria get() {
        return criteria;
    }

    @Override
    public ComparableNumber apply(Double crashProbability, Double cost) {
        return criteria.apply(crashProbability, cost);
    }
}
