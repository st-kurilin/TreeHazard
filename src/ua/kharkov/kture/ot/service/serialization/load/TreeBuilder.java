package ua.kharkov.kture.ot.service.serialization.load;

import ua.kharkov.kture.ot.creation.mvp.CalculatedEventFactory;
import ua.kharkov.kture.ot.creation.mvp.ComponentPresenterFactory;
import ua.kharkov.kture.ot.presenters.ComponentPresenter;
import ua.kharkov.kture.ot.presenters.HazardPresenter;
import ua.kharkov.kture.ot.service.serialization.load.beans.ComponentBean;
import ua.kharkov.kture.ot.service.serialization.load.beans.FigureBean;
import ua.kharkov.kture.ot.service.serialization.load.beans.HazardBean;
import ua.kharkov.kture.ot.shared.VariantVO;
import ua.kharkov.kture.ot.shared.simpleobjects.VariantDTO;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 20.04.11
 */
//TODO: [stas] extract interface
public class TreeBuilder {

    private final Provider<HazardPresenter> rootPresenterProvider;
    private final ComponentPresenterFactory componentProvider;
    private final CalculatedEventFactory calculatedEventProvider;

    @Inject
    public TreeBuilder(@Named("root") Provider<HazardPresenter> rootPresenterProvider, ComponentPresenterFactory componentProvider, CalculatedEventFactory calculatedEventProvider) {
        this.rootPresenterProvider = rootPresenterProvider;
        this.componentProvider = componentProvider;
        this.calculatedEventProvider = calculatedEventProvider;
    }

    public HazardPresenter build(HazardBean rootData) {
        HazardPresenter rootPresenter = rootPresenterProvider.get();
        fillHazardItem(rootPresenter, rootData);
        return rootPresenter;
    }

    private void fillHazardItem(HazardPresenter presenter, HazardBean data) {
        presenter.move(data.getLocation());
        presenter.rename(data.getTitle());
        presenter.changeLogic(data.getLogic());
        presenter.twins(data.getTwins());
        for (FigureBean bean : data.getChilds()) {
            if (bean instanceof HazardBean) {
                HazardPresenter childPresenter = calculatedEventProvider.create(presenter, ((HazardBean) bean).getLogic(), bean.getLocation());
                fillHazardItem(childPresenter, (HazardBean) bean);
            } else if (bean instanceof ComponentBean) {
                ComponentPresenter childPresenter = componentProvider.create(presenter, bean.getLocation());
                fillComponentItem(childPresenter, (ComponentBean) bean);
            } else
                throw new AssertionError("unknown bean type " + bean.getClass());
        }
    }

    private void fillComponentItem(ComponentPresenter presenter, ComponentBean data) {
        presenter.rename(data.getTitle());
        presenter.changeBrokenEventTitle(data.getBrokenEventTitle());
        for (VariantVO variant : data.getVariants()) {
            presenter.addVariant(new VariantDTO(variant.getName(), variant.getCrashProbability(), variant.getCost()));
        }
    }
}
