package ua.kharkov.kture.ot.view.declaration.additionalwindows.optimization;

import ua.kharkov.kture.ot.common.math.ComparableNumber;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 10.05.11
 */
public interface MinimizationOptimizationCriteria {

    //@return comparable value for corresponding variant. Less -> better
    ComparableNumber apply(Double crashProbability, Double cost);
}
