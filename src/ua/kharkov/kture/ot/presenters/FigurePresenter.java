package ua.kharkov.kture.ot.presenters;

import ua.kharkov.kture.ot.common.connector.FieldObserver;
import ua.kharkov.kture.ot.common.connector.WhenModelDisposed;
import ua.kharkov.kture.ot.common.math.Point;
import ua.kharkov.kture.ot.model.Figure;
import ua.kharkov.kture.ot.view.declaration.viewers.FigureView;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 05.04.11
 */
public abstract class FigurePresenter<M extends Figure, V extends FigureView> implements FigureView.Presenter {
    private final M model;
    private final V view;

    protected FigurePresenter(M model, V view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void move(Point p) {

        model.setLocation(p);
    }

    @FieldObserver("location")
    public void onLocationChanged() {
        view.setLocation(model.getLocation());
    }

    @Override
    public void rename(String title) {
        model.setTitle(title);
    }

    @FieldObserver("title")
    public void onTitleChanged() {
        view.setTitle(model.getTitle());
    }

    public M getModel() {
        return model;
    }

    public V getView() {
        return view;
    }

    @Override
    public void setFocus(boolean focus) {
        view.setFocus(focus);
    }

    @Override
    public void delete() {
        model.delete();
    }

    @WhenModelDisposed
    public void onModelDeleted() {
        view.dispose();
    }
}
