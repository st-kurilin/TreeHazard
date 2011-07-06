package ua.kharkov.kture.ot.common.menu;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 07.05.11
 */
class Separator implements Item {
    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitSeparator();
    }
}
