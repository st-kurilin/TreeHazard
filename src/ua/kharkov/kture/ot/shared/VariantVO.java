package ua.kharkov.kture.ot.shared;

import com.google.common.base.Preconditions;
import ua.kharkov.kture.ot.common.Money;
import ua.kharkov.kture.ot.common.math.Probability;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 12.02.11
 */
public class VariantVO {
    private String name;
    private Probability crashProbability;
    private Money cost;

    public VariantVO(String name, Probability crashProbability, Money cost) {
        Preconditions.checkNotNull(name);
        this.name = name;
        this.crashProbability = crashProbability;
        this.cost = cost;
    }

    public VariantVO(String name, Probability crashProbability) {
        this(name, crashProbability, null);
    }

    public VariantVO(String name, Money cost) {
        this(name, null, cost);
    }

    public VariantVO(Probability crashProbability) {
        this.crashProbability = crashProbability;
    }


    public Probability getCrashProbability() {
        return crashProbability;
    }

    public Money getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VariantVO variantVO = (VariantVO) o;

        if (cost != null ? !cost.equals(variantVO.cost) : variantVO.cost != null) return false;
        if (crashProbability != null ? !crashProbability.equals(variantVO.crashProbability) : variantVO.crashProbability != null)
            return false;
        return !(name != null ? !name.equals(variantVO.name) : variantVO.name != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (crashProbability != null ? crashProbability.hashCode() : 0);
        result = 31 * result + (cost != null ? cost.hashCode() : 0);
        return result;
    }
}
