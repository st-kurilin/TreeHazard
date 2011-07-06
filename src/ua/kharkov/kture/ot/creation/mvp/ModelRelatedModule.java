package ua.kharkov.kture.ot.creation.mvp;

import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.name.Named;
import ua.kharkov.kture.ot.common.connector.ListenerWrapper;
import ua.kharkov.kture.ot.common.eventbus.EventBus;
import ua.kharkov.kture.ot.common.math.Logic;
import ua.kharkov.kture.ot.common.math.Point;
import ua.kharkov.kture.ot.common.navigation.Navigator;
import ua.kharkov.kture.ot.creation.events.ComponentViewCreatedEvent;
import ua.kharkov.kture.ot.creation.events.HazardViewCreatedEvent;
import ua.kharkov.kture.ot.creation.events.RootViewCreatedEvent;
import ua.kharkov.kture.ot.model.CalculatedHazard;
import ua.kharkov.kture.ot.model.Component;
import ua.kharkov.kture.ot.model.Hazard;
import ua.kharkov.kture.ot.model.RootHazard;
import ua.kharkov.kture.ot.presenters.ComponentPresenter;
import ua.kharkov.kture.ot.presenters.HazardPresenter;
import ua.kharkov.kture.ot.shared.simpleobjects.ComponentDTO;
import ua.kharkov.kture.ot.shared.simpleobjects.HazardDTO;
import ua.kharkov.kture.ot.view.declaration.viewers.CalculatedEventView;
import ua.kharkov.kture.ot.view.declaration.viewers.ComponentView;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 01.03.11
 */
public class ModelRelatedModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new FactoryModuleBuilder().build(ComponentFactory.class));
        install(new FactoryModuleBuilder()
                .implement(Hazard.class, CalculatedHazard.class)
                .build(CalculatedHazardFactory.class));

    }

    @Provides
    ComponentPresenterFactory provideComponentPresenterFactory(final ComponentFactory modelFactory, final ComponentViewFactory viewersFactory, final EventBus eventBus) {
        return new ComponentPresenterFactory() {
            @Override
            public ComponentPresenter create(
                    @Assisted HazardPresenter parentPresenter,
                    @Assisted Point location) {
                Component model = modelFactory.create(parentPresenter.getModel(), location);
                ComponentDTO dto = new ComponentDTO();
                dto.setTitle(model.getTitle());
                dto.setBrokenEventTitle(model.getBrokenEventTitle());
                dto.setLocation(model.getLocation());
                ComponentView view = viewersFactory.creteComponentViewer(dto);
                ComponentPresenter presenter = new ComponentPresenter(parentPresenter, model, view);
                model.setListener(new ListenerWrapper<ComponentPresenter>(presenter));
                view.setPresenter(presenter);
                parentPresenter.getModel().addChild(model);
                eventBus.fireEvent(new ComponentViewCreatedEvent(parentPresenter.getView(), presenter, view, dto));

                return presenter;
            }
        };
    }

    @Provides
    CalculatedEventFactory provideCalculatedEventFactory(
            final ComponentFactory componentFactory,
            final CalculatedHazardFactory hazardFactory,
            final HazardViewFactory viewersFactory,
            @Named("hazard title") final Provider<String> calculatedEventNamesGenerator,
            final ComponentPresenterFactory componentPresenterFactory,
            final EventBus eventBus,
            final Navigator navigator) {
        return new CalculatedEventFactory() {
            @Override
            public HazardPresenter create(@Assisted HazardPresenter parent, @Assisted String title, @Assisted Logic logic, @Assisted Point location) {
                Hazard model = hazardFactory.create(parent.getModel(), location, logic, title);

                HazardDTO dto = new HazardDTO();
                dto.setTitle(model.getTitle());
                dto.setLocation(location);
                dto.setLogic(logic);
                dto.setTwins(1);

                CalculatedEventView view = viewersFactory.createHazardView(dto);
                HazardPresenter presenter = new HazardPresenter(model, view, componentPresenterFactory, this, navigator);
                view.setPresenter(presenter);
                model.setListener(new ListenerWrapper<HazardPresenter>(presenter));
                parent.getModel().addChild(model);
                eventBus.fireEvent(new HazardViewCreatedEvent(parent.getView(), presenter, view, dto));
                return presenter;
            }

            @Override
            public HazardPresenter create(@Assisted HazardPresenter parent, @Assisted Logic logic, @Assisted Point location) {
                return create(parent, calculatedEventNamesGenerator.get(), logic, location);
            }
        };
    }

    @Provides
    @Named("root")
    HazardPresenter provideRootPresenter(RootHazard model,
                                         RootViewFactory viewersFactory,
                                         ComponentPresenterFactory componentPresenterFactory,
                                         CalculatedEventFactory calculatedEventFactory,
                                         final EventBus eventBus,
                                         Navigator navigator) {
        HazardDTO dto = new HazardDTO();
        dto.setTitle(model.getTitle());
        dto.setLocation(model.getLocation());
        dto.setLogic(model.getLogic());
        dto.setTwins(1);
        CalculatedEventView view = viewersFactory.createRoot(dto);
        HazardPresenter presenter = new HazardPresenter(model, view, componentPresenterFactory, calculatedEventFactory, navigator);
        view.setPresenter(presenter);
        model.setListener(new ListenerWrapper<HazardPresenter>(presenter));
        eventBus.fireEvent(new RootViewCreatedEvent(presenter, view, dto));
        return presenter;
    }


    interface CalculatedHazardFactory {
        Hazard create(@Assisted Hazard parent, @Assisted Point location, @Assisted Logic logic, @Assisted String title);
    }

    interface ComponentFactory {
        Component create(@Assisted Hazard parent, @Assisted Point location);
    }

    public interface HazardViewFactory {
        CalculatedEventView createHazardView(HazardDTO dto);
    }

    public interface RootViewFactory {
        CalculatedEventView createRoot(HazardDTO dto);
    }

    public interface ComponentViewFactory {
        ComponentView creteComponentViewer(@Assisted ComponentDTO dto);
    }
}
