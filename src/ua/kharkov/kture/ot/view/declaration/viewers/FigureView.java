package ua.kharkov.kture.ot.view.declaration.viewers;

import ua.kharkov.kture.ot.common.math.Point;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 12.02.11
 */
public interface FigureView<P extends FigureView.Presenter> {
    interface Presenter {
        void move(Point p);

        void rename(String title);

        void setFocus(boolean focus);

        void delete();
    }

    void setTitle(String title);

    void setLocation(Point point);

    void addChild(FigureView child);

    void setPresenter(P presenter);

    void dispose();

    void setFocus(boolean focus);
}
