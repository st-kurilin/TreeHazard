package ua.kharkov.kture.ot.common.math;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 23.06.11
 */
public class ComparableNumber extends Number implements Comparable<Number> {
    private final Number target;

    public ComparableNumber(Number target) {
        this.target = target;
    }

    @Override
    public int intValue() {
        return target.intValue();
    }

    @Override
    public long longValue() {
        return target.longValue();
    }

    @Override
    public float floatValue() {
        return target.floatValue();
    }

    @Override
    public double doubleValue() {
        return target.doubleValue();
    }

    @Override
    public byte byteValue() {
        return target.byteValue();
    }

    @Override
    public short shortValue() {
        return target.shortValue();
    }

    @Override
    public int compareTo(Number o) {
        return Double.compare(target.doubleValue(), o.doubleValue());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ComparableNumber that = (ComparableNumber) o;

        return target.equals(that.target);

    }

    @Override
    public int hashCode() {
        return target.hashCode();
    }
}
