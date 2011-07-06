package ua.kharkov.kture.ot.model;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 09.04.11
 */
public interface Visitor<R> {
    R visitComponent(Component component);

    R visitCalculatedEvent(Hazard hazard);
}
