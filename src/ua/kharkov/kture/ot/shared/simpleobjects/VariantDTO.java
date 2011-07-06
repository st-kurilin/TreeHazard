package ua.kharkov.kture.ot.shared.simpleobjects;

import ua.kharkov.kture.ot.common.Money;
import ua.kharkov.kture.ot.common.math.Probability;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 23.04.11
 */
public final class VariantDTO implements Cloneable {
    private String name;
    private Probability crashProbability;
    private Money cost;

    public VariantDTO() {
    }

    public VariantDTO(String name, Probability crashProbability, Money cost) {
        this.name = name;
        this.crashProbability = crashProbability;
        this.cost = cost;
    }

    public VariantDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Probability getCrashProbability() {
        return crashProbability;
    }

    public void setCrashProbability(Probability crashProbability) {
        this.crashProbability = crashProbability;
    }

    public Money getCost() {
        return cost;
    }

    public void setCost(Money cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VariantDTO that = (VariantDTO) o;

        return name.equals(that.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public final VariantDTO clone() {
        try {
            return (VariantDTO) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
