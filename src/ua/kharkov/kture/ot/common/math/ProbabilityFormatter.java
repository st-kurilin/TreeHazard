package ua.kharkov.kture.ot.common.math;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 21.06.11
 */
public class ProbabilityFormatter {
    String format(Probability probability) {
        return probability.inScientificForm() + "";
    }
}
