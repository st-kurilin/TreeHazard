package ua.kharkov.kture.ot.service.locations;

import ua.kharkov.kture.ot.common.math.Point;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 17.04.11
 */
public class FakeLocationValidator extends LocationValidator {
    @Override
    public Point newLocation() {
        return wish;
    }
}
