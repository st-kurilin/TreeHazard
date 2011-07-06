package ua.kharkov.kture.ot.view.swing.additional.componetedit.events;

import ua.kharkov.kture.ot.common.eventbus.EventBus;
import ua.kharkov.kture.ot.shared.VariantVO;

/**
 * User: akril
 * Date: 4/21/11
 * Time: 9:05 PM
 */
public class VariantEvent implements EventBus.Event {
    private VariantVO variant;

    public VariantVO getVariant() {
        return variant;
    }
}
