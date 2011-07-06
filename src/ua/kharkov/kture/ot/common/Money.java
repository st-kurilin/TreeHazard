package ua.kharkov.kture.ot.common;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 12.02.11
 */
public class Money implements Comparable<Money> {
    private final double dollars;

    public Money(double money) {
        this.dollars = money;
    }

    public double getDollars() {
        return dollars;
    }

    @Override
    public int compareTo(Money money) {
        return Double.compare(dollars, money.getDollars());
    }

    public static Money parseMoney(String s) {
        return new Money(Double.parseDouble(s));
    }

    @Override
    public String toString() {
        return dollars + "";
    }
}
