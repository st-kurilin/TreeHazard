package ua.kharkov.kture.ot.creation.events;

import ua.kharkov.kture.ot.common.eventbus.EventBus;
import ua.kharkov.kture.ot.presenters.ComponentPresenter;
import ua.kharkov.kture.ot.shared.simpleobjects.ComponentDTO;
import ua.kharkov.kture.ot.view.declaration.viewers.CalculatedEventView;
import ua.kharkov.kture.ot.view.declaration.viewers.ComponentView;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 16.04.11
 */
public class ComponentViewCreatedEvent implements EventBus.Event {
    private final CalculatedEventView parent;
    private final ComponentPresenter presenter;
    private final ComponentView view;
    private final ComponentDTO dto;

    public ComponentViewCreatedEvent(CalculatedEventView parent, ComponentPresenter presenter, ComponentView view, ComponentDTO dto) {
        this.parent = parent;
        this.presenter = presenter;
        this.view = view;
        this.dto = dto;
    }

    public CalculatedEventView getParent() {
        return parent;
    }

    public ComponentView getView() {
        return view;
    }

    public ComponentDTO getDto() {
        return dto;
    }

    public ComponentPresenter getPresenter() {
        return presenter;
    }
}
