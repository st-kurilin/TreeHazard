package ua.kharkov.kture.ot.presenters;

import com.google.inject.Inject;
import org.apache.log4j.Logger;
import ua.kharkov.kture.ot.common.navigation.Navigator;
import ua.kharkov.kture.ot.service.serialization.SerializationService;
import ua.kharkov.kture.ot.shared.navigation.places.ErrorMessagePlace;
import ua.kharkov.kture.ot.view.declaration.viewers.Window;

import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.io.IOException;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 02.04.11
 */
@Singleton
public class ApplicationPresenter implements Window.ApplicationPresenter {
    private static Logger LOG = Logger.getLogger(ApplicationPresenter.class);
    private HazardPresenter rootPresenter;
    private final SerializationService serializationService;
    private final Navigator navigator;
    private final Window view;
    private final Provider<HazardPresenter> rootProvider;

    @Inject
    public ApplicationPresenter(SerializationService serializationService, Window view, Navigator navigator, @Named("root") Provider<HazardPresenter> rootProvider) {
        this.view = view;
        this.serializationService = serializationService;
        this.navigator = navigator;
        this.rootProvider = rootProvider;
    }

    @Override
    public void newTree() {
        rootPresenter.delete();
        rootPresenter = rootProvider.get();
    }

    @Override
    public void save(String file) {
        try {
            LOG.debug("save " + rootPresenter.getModel() + " to " + file);
            serializationService.save(file, rootPresenter.getModel());
        } catch (IOException e) {
            LOG.error("error while saving", e);
            navigator.goTo(new ErrorMessagePlace("outputError"));
        }
    }

    @Override
    public void load(String file) {
        try {
            rootPresenter.delete();
            rootPresenter = serializationService.load(file);
        } catch (IOException e) {
            LOG.error("error while loading", e);
            navigator.goTo(new ErrorMessagePlace("inputError"));
        }
    }

    @Override
    public void exit() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setRootPresenter(HazardPresenter rootPresenter) {
        this.rootPresenter = rootPresenter;
    }


    public void go() {
        view.draw();
    }
}
