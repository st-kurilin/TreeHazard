package ua.kharkov.kture.ot.view.swing.mainwindow.config;

import com.google.inject.Provider;
import com.google.inject.matcher.Matchers;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import ua.kharkov.kture.ot.common.eventbus.EventBus;
import ua.kharkov.kture.ot.common.guice.EventBusDependedModule;
import ua.kharkov.kture.ot.view.declaration.viewers.FigureView;
import ua.kharkov.kture.ot.view.swing.events.redraw.MainWindowDirtyEvent;
import ua.kharkov.kture.ot.view.swing.mainwindow.SwingWindow;
import ua.kharkov.kture.ot.view.swing.utils.Dirty;

import static com.google.inject.matcher.Matchers.subclassesOf;


/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 09.05.11
 */
public class RedrawModule extends EventBusDependedModule {
    Provider<SwingWindow> window;

    public RedrawModule(EventBus eventBus) {
        super(eventBus);
    }

    @Override
    protected void configure() {
        window = getProvider(SwingWindow.class);
        attacheRedrawHandler();
        bindInterceptor(subclassesOf(FigureView.class), Matchers.annotatedWith(Dirty.class), new MethodInterceptor() {
            @Override
            public Object invoke(MethodInvocation methodInvocation) throws Throwable {
                eventBus.fireEvent(new MainWindowDirtyEvent());
                return methodInvocation.proceed();
            }
        });
    }


    private void attacheRedrawHandler() {
        eventBus.addHandler(MainWindowDirtyEvent.class,
                new EventBus.Handler<MainWindowDirtyEvent>() {
                    @Override
                    public void handle(MainWindowDirtyEvent event) {
                        window.get().repaint();
                    }
                });
    }
}
