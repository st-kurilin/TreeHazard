package ua.kharkov.kture.ot.view.swing.mainwindow.figuresmoving;

import ua.kharkov.kture.ot.common.MouseButtonType;
import ua.kharkov.kture.ot.common.eventbus.EventBus;
import ua.kharkov.kture.ot.common.math.Point;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 08.05.11
 */
public class MouseActionEvent implements EventBus.Event {
    public enum ActionType {
        PRESSED, RELEASED, DRAGGED;

        @Deprecated
        public MouseActionEvent create(Point location) {
            return new MouseActionEvent(location, this, MouseButtonType.LEFT);
        }

        public MouseActionEvent create(Point location, MouseButtonType buttonType) {
            return new MouseActionEvent(location, this, buttonType);
        }
    }

    private final Point location;
    private final ActionType actionType;
    private final MouseButtonType buttonType;

    public MouseActionEvent(Point location, ActionType actionType, MouseButtonType buttonType) {
        this.location = location;
        this.actionType = actionType;
        this.buttonType = buttonType;
    }

    public Point getLocation() {
        return location;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public MouseButtonType getButtonType() {
        return buttonType;
    }
}
