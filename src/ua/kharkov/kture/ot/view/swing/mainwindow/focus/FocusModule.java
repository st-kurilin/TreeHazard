package ua.kharkov.kture.ot.view.swing.mainwindow.focus;

import com.google.inject.Provider;
import org.apache.log4j.Logger;
import ua.kharkov.kture.ot.common.MouseButtonType;
import ua.kharkov.kture.ot.common.eventbus.EventBus;
import ua.kharkov.kture.ot.common.guice.EventBusDependedModule;
import ua.kharkov.kture.ot.view.declaration.viewers.FigureView;
import ua.kharkov.kture.ot.view.swing.events.ShowPopupMenuEvent;
import ua.kharkov.kture.ot.view.swing.innerrepo.ViewInnerRepository;
import ua.kharkov.kture.ot.view.swing.mainwindow.SwingWindow;
import ua.kharkov.kture.ot.view.swing.mainwindow.figuresmoving.MouseActionEvent;
import ua.kharkov.kture.ot.view.swing.utils.Widget;

import java.util.Map;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 08.05.11
 */
public class FocusModule extends EventBusDependedModule {
    private static Logger LOG = Logger.getLogger(FocusModule.class);

    private FigureView.Presenter inFocus;

    private Provider<SwingWindow> window;
    private Provider<ViewInnerRepository> repository;

    public FocusModule(EventBus eventBus) {
        super(eventBus);
    }


    @Override
    protected void configure() {
        bind(FocusElementKeeper.class).toInstance(new FocusElementKeeper() {
            @Override
            public FigureView.Presenter getFocusedElement() {
                return inFocus;
            }
        });
        initProviders();
        attacheFocusChangedEventHandler();
        attacheMouseFocusChanger();
    }

    private void initProviders() {
        window = getProvider(SwingWindow.class);
        repository = getProvider(ViewInnerRepository.class);
    }

    private void attacheFocusChangedEventHandler() {
        eventBus.addHandler(FocusChangedEvent.class, new EventBus.Handler<FocusChangedEvent>() {
            @Override
            public void handle(FocusChangedEvent event) {
                changeFocus(event.getInFocus());
            }
        });
    }

    private void attacheMouseFocusChanger() {
        eventBus.addHandler(MouseActionEvent.class, new EventBus.Handler<MouseActionEvent>() {
            @Override
            public void handle(MouseActionEvent event) {
                switch (event.getActionType()) {
                    case PRESSED: {
                        Map<FigureView<?>, FigureView.Presenter> presenters = repository.get().getPresenters();
                        Widget inLocation = window.get().getWidgetByLocation(event.getLocation());
                        if (inLocation == null) {
                            changeFocus(null);
                        } else {
                            changeFocus(presenters.get(inLocation));
                        }
                        if (event.getButtonType() == MouseButtonType.RIGHT) {
                            eventBus.fireEvent(new ShowPopupMenuEvent(event.getLocation()));
                        }
                    }
                }

            }
        });
    }


    private void changeFocus(FigureView.Presenter newFocus) {
        LOG.debug("focus changed to " + newFocus);
        if (inFocus != null) {
            inFocus.setFocus(false);
        }
        if (inFocus != newFocus) {
            inFocus = newFocus;
            //it is null in unfocus mode
            if (inFocus != null) {
                inFocus.setFocus(true);
            }
            eventBus.fireEvent(FocusChangedEvent.focusOn(inFocus));
        }
    }
}
