package ua.kharkov.kture.ot.view.swing.mainwindow.connection;

import com.google.common.base.Function;
import com.google.inject.Provider;
import com.google.inject.Provides;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;
import ua.kharkov.kture.ot.common.eventbus.EventBus;
import ua.kharkov.kture.ot.common.guice.EventBusDependedModule;
import ua.kharkov.kture.ot.common.math.Logic;
import ua.kharkov.kture.ot.creation.events.ComponentViewCreatedEvent;
import ua.kharkov.kture.ot.creation.events.HazardViewCreatedEvent;
import ua.kharkov.kture.ot.shared.simpleobjects.ComponentDTO;
import ua.kharkov.kture.ot.shared.simpleobjects.FigureDTO;
import ua.kharkov.kture.ot.shared.simpleobjects.HazardDTO;
import ua.kharkov.kture.ot.view.declaration.SizeRetriever;
import ua.kharkov.kture.ot.view.declaration.viewers.FigureView;
import ua.kharkov.kture.ot.view.swing.events.redraw.RedrawEvent;
import ua.kharkov.kture.ot.view.swing.innerrepo.ViewInnerRepository;
import ua.kharkov.kture.ot.view.swing.utils.Shape2D;

import static com.google.inject.matcher.Matchers.subclassesOf;
import static ua.kharkov.kture.ot.common.guice.MethodMatchers.withName;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 08.05.11
 */
public class ConnectionModule extends EventBusDependedModule {
    private static Logger LOG = Logger.getLogger(ConnectionModule.class);

    public ConnectionModule(EventBus eventBus) {
        super(eventBus);
    }

    @Override
    protected void configure() {
        bind(ConnectionStrategy.class).to(SimpleConnector.class);

        Provider<FiguresConnector> connector = getProvider(FiguresConnector.class);
        redrawHandlers(connector);
        addingNewHandlers(connector);
        removingHandlers(connector);
    }

    private void redrawHandlers(final Provider<FiguresConnector> connector) {
        eventBus.addHandler(RedrawEvent.class, new EventBus.Handler<RedrawEvent>() {
            @Override
            public void handle(RedrawEvent event) {
                connector.get().redraw(event.getGraphics());
            }
        });
    }

    private void addingNewHandlers(final Provider<FiguresConnector> connector) {
        final Provider<ViewInnerRepository> repo = getProvider(ViewInnerRepository.class);
        eventBus.addHandler(HazardViewCreatedEvent.class, new EventBus.Handler<HazardViewCreatedEvent>() {
            @Override
            public void handle(HazardViewCreatedEvent event) {
                FigureDTO parent = repo.get().getDto(event.getParent());
                FigureDTO current = event.getDto();
                connector.get().addShape(parent, current);
            }
        });
        eventBus.addHandler(ComponentViewCreatedEvent.class, new EventBus.Handler<ComponentViewCreatedEvent>() {
            @Override
            public void handle(ComponentViewCreatedEvent event) {
                FigureDTO parent = repo.get().getDto(event.getParent());
                FigureDTO current = event.getDto();
                connector.get().addShape(parent, current);
            }
        });
    }

    private void removingHandlers(final Provider<FiguresConnector> connector) {
        final Provider<ViewInnerRepository> repo = getProvider(ViewInnerRepository.class);
        bindInterceptor(subclassesOf(FigureView.class), withName("dispose"), new MethodInterceptor() {
            @Override
            public Object invoke(MethodInvocation methodInvocation) throws Throwable {
                FigureView target = (FigureView) methodInvocation.getThis();
                FigureDTO removedDto = repo.get().getDto(target);
                connector.get().removeShape(removedDto);
                return methodInvocation.proceed();
            }
        });
    }

    //TODO: [stas] implement visitor
    @Provides
    Function<FigureDTO, Shape2D> provideConverter(final SizeRetriever sizeRetriever) {
        return new Function<FigureDTO, Shape2D>() {
            @Override
            public Shape2D apply(FigureDTO input) {
                if (input instanceof HazardDTO) {
                    HazardDTO casted = (HazardDTO) input;
                    Logic logic = casted.getLogic();
                    int twins = casted.getTwins();
                    return new Shape2D(input.getLocation(), sizeRetriever.getCalculatedEventsSize(logic, twins));
                } else if (input instanceof ComponentDTO) {
                    return new Shape2D(input.getLocation(), sizeRetriever.getComponentsSize());
                } else {
                    throw new AssertionError("unexpected input " + input);
                }
            }
        };
    }
}
