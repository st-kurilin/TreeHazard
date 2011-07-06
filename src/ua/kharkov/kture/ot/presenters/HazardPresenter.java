package ua.kharkov.kture.ot.presenters;

import com.google.inject.Inject;
import ua.kharkov.kture.ot.common.connector.FieldObserver;
import ua.kharkov.kture.ot.common.math.Logic;
import ua.kharkov.kture.ot.common.math.Point;
import ua.kharkov.kture.ot.common.navigation.Navigator;
import ua.kharkov.kture.ot.creation.mvp.CalculatedEventFactory;
import ua.kharkov.kture.ot.creation.mvp.ComponentPresenterFactory;
import ua.kharkov.kture.ot.model.Hazard;
import ua.kharkov.kture.ot.shared.navigation.places.ErrorMessagePlace;
import ua.kharkov.kture.ot.view.declaration.viewers.CalculatedEventView;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 03.04.11
 */
public class HazardPresenter extends FigurePresenter<Hazard, CalculatedEventView> implements CalculatedEventView.Presenter {
    private final ComponentPresenterFactory componentProvider;
    private final CalculatedEventFactory calculatedEventProvider;
    private final Navigator navigator;

    @Inject
    public HazardPresenter(Hazard model, CalculatedEventView view, ComponentPresenterFactory componentProvider, CalculatedEventFactory calculatedEventProvider, Navigator navigator) {
        super(model, view);
        this.componentProvider = componentProvider;
        this.calculatedEventProvider = calculatedEventProvider;
        this.navigator = navigator;
    }

    @Override
    public void changeLogic(Logic newLogic) {
        if (validateState(newLogic, getModel().getChilds().size())) {
            getModel().setLogic(newLogic);
        }
    }

    /*returns true if changes can be applied*/
    private boolean validateState(Logic logic, int size) {
        if (logic.isValidNumberOfArguments(size)) {
            return true;
        } else {
            navigator.goTo(new ErrorMessagePlace("IllegalNumbersOfArgumentsForLogicElement"));
            return false;
        }
    }

    @FieldObserver("Logic")
    public void onLogicChanged() {
        getView().setLogic(getModel().getLogic());
    }

    @Override
    public void addComponentChild(Point location) {
        if (!childCanBeAdded()) {
            return;
        }
        componentProvider.create(this, location);
    }

    private boolean childCanBeAdded() {
        return validateState(getModel().getLogic(), getModel().getChilds().size() + 1);
    }

    @Override
    public void addEventChild(Logic logic, Point location) {
        if (!childCanBeAdded()) {
            return;
        }
        calculatedEventProvider.create(this, logic, location);
    }

    public void addEventChild(String brokenEventTitle, Logic logic, Point location) {
        calculatedEventProvider.create(this, brokenEventTitle, logic, location);
    }

    @FieldObserver("twins")
    public void onNumberChanged() {
        getView().setTwins(getModel().getTwins());
    }

    @Override
    public void twins(int number) {
        getModel().setTwins(number);
    }

    @FieldObserver("probability")
    public void onProbabilityChanged() {
        getView().setProbability(getModel().calcProbability());
    }
}
