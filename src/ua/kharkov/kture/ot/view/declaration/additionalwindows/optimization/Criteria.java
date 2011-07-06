package ua.kharkov.kture.ot.view.declaration.additionalwindows.optimization;

import ua.kharkov.kture.ot.common.math.ComparableNumber;
import ua.kharkov.kture.ot.common.math.Probability;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 10.05.11
 */
public enum Criteria implements MinimizationOptimizationCriteria {
    COST {
        @Override
        public ComparableNumber apply(Double crashProbability, Double cost) {
            return new ComparableNumber(cost);
        }
    }, CRASH_PROBABILITY {
        @Override
        public ComparableNumber apply(Double crashProbability, Double cost) {
            return Probability.scientificForm(crashProbability);
        }
    }, COST_MULTI_PROBABILITY {
        @Override
        public ComparableNumber apply(Double crashProbability, Double cost) {
            return new ComparableNumber(cost * crashProbability);
        }
    }, COST_DIVIDED_BY_PROBABILITY_KOEF {
        @Override
        public ComparableNumber apply(Double crashProbability, Double cost) {
            return new ComparableNumber(cost * probabilityKoef(crashProbability).doubleValue());
        }
    }, AVERAGE_COST {
        @Override
        public ComparableNumber apply(Double crashProbability, Double cost) {
            //TODO: FIX_ME [stas] fake impl
            return new ComparableNumber(cost * (1 + crashProbability));
        }
    }, AVERAGE_COST_DIVIDED_BY_PROBABILITY_KOEF {
        @Override
        public ComparableNumber apply(Double crashProbability, Double cost) {
            return new ComparableNumber(AVERAGE_COST.apply(crashProbability, cost).doubleValue() / probabilityKoef(crashProbability).doubleValue());
        }
    };


    private static ComparableNumber probabilityKoef(Double crashProbability) {
        return new ComparableNumber(1 - crashProbability);
    }
}
