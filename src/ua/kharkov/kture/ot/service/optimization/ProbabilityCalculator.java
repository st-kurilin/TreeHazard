package ua.kharkov.kture.ot.service.optimization;

import com.google.common.base.Function;
import ua.kharkov.kture.ot.common.math.Probability;
import ua.kharkov.kture.ot.model.Component;
import ua.kharkov.kture.ot.model.Hazard;
import ua.kharkov.kture.ot.shared.VariantVO;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 12.05.11
 */
public interface ProbabilityCalculator {
    public Probability calculateCrashProbability(Hazard root, Function<Component, VariantVO> variantChooser);
}
