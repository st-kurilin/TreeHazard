package ua.kharkov.kture.ot.common.math;

import java.util.List;

import static ua.kharkov.kture.ot.common.math.Probability.scientificForm;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 12.02.11
 */
public enum Logic {
    AND() {
        @Override
        public Probability calcTotalProbability(List<Probability> probabilities) {
            if (probabilities.size() == 0) {
                return defaultValue();
            }
            double res = 1;
            for (Probability p : probabilities) {
                res *= p.inScientificForm();
            }
            return Probability.scientificForm(res);
        }
    }, OR() {
        @Override
        public Probability calcTotalProbability(List<Probability> probabilities) {
            if (probabilities.size() == 0) {
                return defaultValue();
            }
            double res = 1;
            for (Probability p : probabilities) {
                res *= 1 - p.inScientificForm();
            }
            return Probability.scientificForm(1 - res);
        }
    }, NOT() {
        @Override
        public Probability calcTotalProbability(List<Probability> probabilities) {
            return Probability.scientificForm(1 - AND.calcTotalProbability(probabilities).inScientificForm());
        }

        @Override
        public boolean isValidNumberOfArguments(int number) {
            return number <= 1;
        }

    }, AND_WITH_ORDER() {
        @Override
        public Probability calcTotalProbability(List<Probability> probabilities) {
            //TODO: [stas] implement spec needed
            return scientificForm(AND.calcTotalProbability(probabilities).inScientificForm() / probabilities.size());
        }
    };

    private static Probability defaultValue() {
        return Probability.scientificForm(1);
    }

    /*should be overridden for special cases*/
    public boolean isValidNumberOfArguments(int number) {
        return number >= 0;
    }

    public abstract Probability calcTotalProbability(List<Probability> probabilities);
}
