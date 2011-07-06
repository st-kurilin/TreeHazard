package ua.kharkov.kture.ot.view.swing.additional.componetedit;

import ua.kharkov.kture.ot.common.math.Probability;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Scanner;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 24.06.11
 */
//TODO: extract interface
//TODO: should have different toString methods - for representation and editing

//TODO: ATTENTION : DO NOT MAKE IT PUBLIC BEFORE TODO IMPLEMENTING
class ProbabilityIO {
    private final Locale LOCALE = Locale.US;

    public String toString(Probability probability) {
        return new DecimalFormat("#0.######", new DecimalFormatSymbols(LOCALE))
                .format((probability).inScientificForm());
    }

    public Probability parse(String source, Probability defaultValue) {
        Scanner scanner = new Scanner(source);
        scanner.useLocale(LOCALE);
        if (!scanner.hasNextDouble()) {
            return defaultValue;
        }
        Double parsed = scanner.nextDouble();
        if (parsed == null || parsed < 0 || parsed > 1) {
            return defaultValue;
        }
        if (scanner.hasNext()) {
            return defaultValue;
        }
        return Probability.scientificForm(parsed);
    }
}
