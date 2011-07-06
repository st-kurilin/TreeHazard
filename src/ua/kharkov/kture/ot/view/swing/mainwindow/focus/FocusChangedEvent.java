package ua.kharkov.kture.ot.view.swing.mainwindow.focus;

import ua.kharkov.kture.ot.common.eventbus.EventBus;
import ua.kharkov.kture.ot.view.declaration.viewers.FigureView;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 08.05.11
 */
public class FocusChangedEvent implements EventBus.Event {
    private final FigureView.Presenter inFocus;

    private FocusChangedEvent(FigureView.Presenter inFocus) {
        this.inFocus = inFocus;
    }

    public static FocusChangedEvent focusOn(FigureView.Presenter presenter) {
        return new FocusChangedEvent(presenter);
    }

    public static FocusChangedEvent unfocus() {
        return new FocusChangedEvent(null);
    }

    public FigureView.Presenter getInFocus() {
        return inFocus;
    }
}
