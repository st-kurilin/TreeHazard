package ua.kharkov.kture.ot.view.swing.mainwindow.popupmenu;

import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import ua.kharkov.kture.ot.common.eventbus.EventBus;
import ua.kharkov.kture.ot.common.guice.EventBusDependedModule;
import ua.kharkov.kture.ot.common.localization.MessageBundle;
import ua.kharkov.kture.ot.common.localization.StringBasedBundle;
import ua.kharkov.kture.ot.common.math.Point;
import ua.kharkov.kture.ot.shared.PostInitEvent;
import ua.kharkov.kture.ot.view.declaration.viewers.CalculatedEventView;
import ua.kharkov.kture.ot.view.declaration.viewers.ComponentView;
import ua.kharkov.kture.ot.view.declaration.viewers.FigureView;
import ua.kharkov.kture.ot.view.swing.events.ShowPopupMenuEvent;
import ua.kharkov.kture.ot.view.swing.mainwindow.SwingWindow;
import ua.kharkov.kture.ot.view.swing.mainwindow.focus.FocusElementKeeper;
import ua.kharkov.kture.ot.view.swing.utils.menuconstructors.PopupMenuConstructor;

import javax.swing.*;
import java.util.Locale;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 07.05.11
 */
public class PopupMenuModule extends EventBusDependedModule {
    public PopupMenuModule(EventBus eventBus) {
        super(eventBus);
    }

    @Override
    protected void configure() {
        bind(MessageBundle.class).annotatedWith(Names.named("popup menu"))
                .toInstance(new StringBasedBundle("PopupMenu", new Locale("ru", "RU")));

        final Provider<SwingWindow> windowProvider = getProvider(SwingWindow.class);
        final Provider<JPopupMenu> defaultMenuProvider = getProvider(Key.get(JPopupMenu.class, Names.named("default")));
        final Provider<JPopupMenu> hazardMenuProvider = getProvider(Key.get(JPopupMenu.class, Names.named("hazard")));
        final Provider<JPopupMenu> componentMenuProvider = getProvider(Key.get(JPopupMenu.class, Names.named("component")));
        final Provider<FocusElementKeeper> focusKeeper = getProvider(FocusElementKeeper.class);
        eventBus.addHandler(PostInitEvent.class, new EventBus.Handler<PostInitEvent>() {
            @Override
            public void handle(PostInitEvent event) {
                eventBus.addHandler(ShowPopupMenuEvent.class, new EventBus.Handler<ShowPopupMenuEvent>() {
                    @Override
                    public void handle(ShowPopupMenuEvent event) {
                        FigureView.Presenter inFocus = focusKeeper.get().getFocusedElement();
                        if (inFocus == null) {
                            displayMenu(defaultMenuProvider.get(), event.getLocation());
                        } else {
                            if (inFocus instanceof CalculatedEventView.Presenter) {
                                displayMenu(hazardMenuProvider.get(), event.getLocation());
                            } else if (inFocus instanceof ComponentView.Presenter) {
                                displayMenu(componentMenuProvider.get(), event.getLocation());
                            } else {
                                throw new RuntimeException("unrecognized focus " + focusKeeper.get());
                            }
                        }
                    }

                    private void displayMenu(JPopupMenu popupMenu, Point location) {
                        popupMenu.show(windowProvider.get(), location.getX(), location.getY());
                    }
                });
            }
        }
        );
    }

    @Provides
    @Named("default")
    JPopupMenu providesDefaultPopupMenu(PopupMenuConstructor menuConstructor, DefaultMenu menu) {
        return menuConstructor.construct(menu.getMenus());
    }

    @Provides
    @Named("hazard")
    JPopupMenu providesHazardPopupMenu(PopupMenuConstructor menuConstructor, HazardMenu menu) {
        return menuConstructor.construct(menu.getMenus());
    }

    @Provides
    @Named("component")
    JPopupMenu providesComponentPopupMenu(PopupMenuConstructor menuConstructor, ComponentMenu menu) {
        return menuConstructor.construct(menu.getMenus());
    }

}
