package ua.kharkov.kture.ot.view.swing.mainwindow.config;

import com.google.inject.Provider;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import ua.kharkov.kture.ot.common.eventbus.EventBus;
import ua.kharkov.kture.ot.common.guice.EventBusDependedModule;
import ua.kharkov.kture.ot.creation.events.ComponentViewCreatedEvent;
import ua.kharkov.kture.ot.creation.events.HazardViewCreatedEvent;
import ua.kharkov.kture.ot.creation.events.RootViewCreatedEvent;
import ua.kharkov.kture.ot.view.declaration.viewers.FigureView;
import ua.kharkov.kture.ot.view.swing.events.redraw.MainWindowDirtyEvent;
import ua.kharkov.kture.ot.view.swing.mainwindow.SwingWindow;
import ua.kharkov.kture.ot.view.swing.utils.Widget;

import static com.google.inject.matcher.Matchers.subclassesOf;
import static ua.kharkov.kture.ot.common.guice.MethodMatchers.withName;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 09.05.11
 */
public class DomainElementsModule extends EventBusDependedModule {
    private Provider<SwingWindow> window;

    public DomainElementsModule(EventBus eventBus) {
        super(eventBus);
    }

    @Override
    protected void configure() {
        window = getProvider(SwingWindow.class);
        attacheNewElementsCreatedHandlers();
        attacheRemoveElementsHandler();
    }

    private void attacheNewElementsCreatedHandlers() {
        eventBus.addHandler(ComponentViewCreatedEvent.class,
                new EventBus.Handler<ComponentViewCreatedEvent>() {
                    @Override
                    public void handle(ComponentViewCreatedEvent event) {
                        window.get().addWidget((Widget) event.getView());
                        eventBus.fireEvent(new MainWindowDirtyEvent());
                    }
                });
        eventBus.addHandler(HazardViewCreatedEvent.class,
                new EventBus.Handler<HazardViewCreatedEvent>() {
                    @Override
                    public void handle(HazardViewCreatedEvent event) {
                        window.get().addWidget((Widget) event.getView());
                        eventBus.fireEvent(new MainWindowDirtyEvent());
                    }
                });
        eventBus.addHandler(RootViewCreatedEvent.class,
                new EventBus.Handler<RootViewCreatedEvent>() {
                    @Override
                    public void handle(RootViewCreatedEvent event) {
                        window.get().addWidget((Widget) event.getView());
                        eventBus.fireEvent(new MainWindowDirtyEvent());
                    }
                });
    }

    private void attacheRemoveElementsHandler() {
        bindInterceptor(subclassesOf(FigureView.class).and(subclassesOf(Widget.class)), withName("dispose"), new MethodInterceptor() {
            @Override
            public Object invoke(MethodInvocation methodInvocation) throws Throwable {
                Widget target = (Widget) methodInvocation.getThis();
                window.get().removeWidget(target);
                return methodInvocation.proceed();
            }
        });
    }


}
