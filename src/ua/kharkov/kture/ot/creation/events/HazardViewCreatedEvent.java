package ua.kharkov.kture.ot.creation.events;

import ua.kharkov.kture.ot.common.eventbus.EventBus;
import ua.kharkov.kture.ot.presenters.HazardPresenter;
import ua.kharkov.kture.ot.shared.simpleobjects.HazardDTO;
import ua.kharkov.kture.ot.view.declaration.viewers.CalculatedEventView;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 16.04.11
 */

public class HazardViewCreatedEvent implements EventBus.Event {
    private final CalculatedEventView parent;
    private final HazardPresenter presenter;
    private final CalculatedEventView view;
    private final HazardDTO dto;

    public HazardViewCreatedEvent(CalculatedEventView parent, HazardPresenter presenter, CalculatedEventView view, HazardDTO dto) {
        this.parent = parent;
        this.presenter = presenter;
        this.view = view;
        this.dto = dto;
    }

    public CalculatedEventView getParent() {
        return parent;
    }

    public CalculatedEventView getView() {
        return view;
    }

    public HazardDTO getDto() {
        return dto;
    }

    public HazardPresenter getPresenter() {
        return presenter;
    }
}
