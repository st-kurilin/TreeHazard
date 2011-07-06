package ua.kharkov.kture.ot.view.swing.changemodel;

import com.google.inject.Provider;
import ua.kharkov.kture.ot.common.eventbus.EventBus;
import ua.kharkov.kture.ot.common.guice.EventBusDependedModule;
import ua.kharkov.kture.ot.common.math.Point;
import ua.kharkov.kture.ot.view.declaration.viewers.CalculatedEventView;
import ua.kharkov.kture.ot.view.declaration.viewers.ComponentView;
import ua.kharkov.kture.ot.view.declaration.viewers.FigureView;
import ua.kharkov.kture.ot.view.declaration.viewers.Window;
import ua.kharkov.kture.ot.view.swing.changemodel.events.*;
import ua.kharkov.kture.ot.view.swing.innerrepo.ViewInnerRepository;
import ua.kharkov.kture.ot.view.swing.mainwindow.focus.FocusElementKeeper;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 09.05.11
 */
public class ModelChangesModule extends EventBusDependedModule {
    private Provider<FocusElementKeeper> focusKeeper;
    private Provider<ChildPositionGenerator> positionGenerator;
    private Provider<ViewInnerRepository> repository;
    private Provider<Window.ApplicationPresenter> application;

    public ModelChangesModule(EventBus eventBus) {
        super(eventBus);
    }

    @Override
    protected void configure() {
        bind(ChildPositionGenerator.class).to(SimpleChildPositionGenerator.class);

        focusKeeper = getProvider(FocusElementKeeper.class);
        positionGenerator = getProvider(ChildPositionGenerator.class);
        repository = getProvider(ViewInnerRepository.class);
        application = getProvider(Window.ApplicationPresenter.class);
        bindEvents();
    }

    private void bindEvents() {
        newModelElements();
        editingModelElements();
        removingModelElements();
    }

    private void removingModelElements() {
        eventBus.addHandler(DeleteSelectedElement.class, new EventBus.Handler<DeleteSelectedElement>() {
            @Override
            public void handle(DeleteSelectedElement event) {
                getPresenter().delete();
            }
        });
    }

    private void editingModelElements() {
        eventBus.addHandler(ChangeLogicEvent.class, new EventBus.Handler<ChangeLogicEvent>() {
            @Override
            public void handle(ChangeLogicEvent event) {
                getHazardPresenter().changeLogic(event.getLogic());
            }
        });
        eventBus.addHandler(ChangeTwinsEvent.class, new EventBus.Handler<ChangeTwinsEvent>() {
            @Override
            public void handle(ChangeTwinsEvent event) {
                getHazardPresenter().twins(event.getTwins());
            }
        });
        eventBus.addHandler(ChangeTitleEvent.class, new EventBus.Handler<ChangeTitleEvent>() {
            @Override
            public void handle(ChangeTitleEvent event) {
                getPresenter().rename(event.getNewTitle());
            }
        });
        eventBus.addHandler(DecomposeComponent.class, new EventBus.Handler<DecomposeComponent>() {
            @Override
            public void handle(DecomposeComponent event) {
                getComponentPresenter().decompose(event.getLogic());
            }
        });
    }

    private void newModelElements() {
        eventBus.addHandler(CreateNewComponentEvent.class, new EventBus.Handler<CreateNewComponentEvent>() {
            @Override
            public void handle(CreateNewComponentEvent event) {
                CalculatedEventView parentView = (CalculatedEventView) repository.get().getPresenters().inverse().get(getPresenter());
                Point childLocation = positionGenerator.get().generatePosition(parentView);
                getHazardPresenter().addComponentChild(childLocation);
//                presenter.addVariant(new VariantDTO("default", Probability.commonForm(50), new Money(100)));
            }
        });
        eventBus.addHandler(CreateNewHazard.class, new EventBus.Handler<CreateNewHazard>() {
            @Override
            public void handle(CreateNewHazard event) {
                CalculatedEventView parentView = (CalculatedEventView) repository.get().getPresenters().inverse().get(getPresenter());
                Point childLocation = positionGenerator.get().generatePosition(parentView);
                getHazardPresenter().addEventChild(event.getLogic(), childLocation);
            }
        });
        eventBus.addHandler(NewTreeEvent.class, new EventBus.Handler<NewTreeEvent>() {
            @Override
            public void handle(NewTreeEvent event) {
                application.get().newTree();
            }
        });
    }

    //TODO: use visitor
    private CalculatedEventView.Presenter getHazardPresenter() {
        FigureView.Presenter inFocus = focusKeeper.get().getFocusedElement();
        if (inFocus == null || !(inFocus instanceof CalculatedEventView.Presenter)) {
            throw new RuntimeException("unexpected focus:" + inFocus);
        }
        return (CalculatedEventView.Presenter) inFocus;
    }

    private ComponentView.Presenter getComponentPresenter() {
        FigureView.Presenter inFocus = focusKeeper.get().getFocusedElement();
        if (inFocus == null || !(inFocus instanceof ComponentView.Presenter)) {
            throw new RuntimeException("unexpected focus:" + inFocus);
        }
        return (ComponentView.Presenter) inFocus;
    }

    private FigureView.Presenter getPresenter() {
        return focusKeeper.get().getFocusedElement();
    }
}
