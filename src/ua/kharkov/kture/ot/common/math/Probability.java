package ua.kharkov.kture.ot.common.math;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 12.02.11
 */
public final class Probability extends ComparableNumber {
    private final double value;

    private Probability(double probability) {
        super(probability);
        this.value = probability;
    }

    /**
     * @param value number between 0 and 100
     * @return probability instance
     * @throws IllegalArgumentException for illegal numbers as argument
     */
    public static Probability commonForm(double value) {
        return scientificForm(value / 100.);
    }

    /**
     * @param value number between 0 and 1
     * @return probability instance
     * @throws IllegalArgumentException for illegal numbers as argument
     */
    public static Probability scientificForm(double value) {
        checkBetween(value, 0, 1);
        return new Probability(value / 1.);
    }

    /**
     * @return probability as number between 0 and 100
     */
    public double inCommonForm() {
        return value * 100.;
    }

    /**
     * @return probability as number between 0 and 1
     */
    public double inScientificForm() {
        return value;
    }

    public int compareTo(Probability other) {
        return Double.compare(inScientificForm(), other.inScientificForm());
    }

    private static void checkBetween(double value, int min, int max) {
        if (value < min || value > max) {
            throw new IllegalArgumentException("value " + value + " not in range [" + min + " ; " + max + "]");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Probability that = (Probability) o;

        return Double.compare(that.value, value) == 0;

    }

    @Override
    public int hashCode() {
        long temp = value != +0.0d ? Double.doubleToLongBits(value) : 0L;
        return (int) (temp ^ (temp >>> 32));
    }
}
