package ua.kharkov.kture.ot.view.swing.mainwindow;

import com.google.inject.Provides;
import com.google.inject.Singleton;
import ua.kharkov.kture.ot.common.eventbus.EventBus;
import ua.kharkov.kture.ot.common.guice.EventBusDependedModule;
import ua.kharkov.kture.ot.view.declaration.viewers.Window;
import ua.kharkov.kture.ot.view.swing.mainwindow.config.DomainElementsModule;
import ua.kharkov.kture.ot.view.swing.mainwindow.config.RedrawModule;
import ua.kharkov.kture.ot.view.swing.mainwindow.connection.ConnectionModule;
import ua.kharkov.kture.ot.view.swing.mainwindow.figuresmoving.FigureMovingModule;
import ua.kharkov.kture.ot.view.swing.mainwindow.focus.FocusModule;
import ua.kharkov.kture.ot.view.swing.mainwindow.mainmenu.ToolbarMenuModule;
import ua.kharkov.kture.ot.view.swing.mainwindow.popupmenu.PopupMenuModule;
import ua.kharkov.kture.ot.view.swing.utils.menuconstructors.ToolbarMenuConstructor;

import javax.inject.Named;
import javax.swing.*;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 07.05.11
 */
public class MainWindowModule extends EventBusDependedModule {

    public MainWindowModule(EventBus eventBus) {
        super(eventBus);
    }

    @Override
    protected void configure() {
        install(new DomainElementsModule(eventBus));
        install(new RedrawModule(eventBus));

        install(new ConnectionModule(eventBus));
        install(new FigureMovingModule(eventBus));
        install(new FocusModule(eventBus));
        install(new PopupMenuModule(eventBus));
    }

    @Provides
    @Singleton
        //TODO: move to config
    Window windowProvider(@Named("swing.main window") SwingWindow swingWindow) {
        return swingWindow;
    }

    @Provides
    @Singleton
    @Named("swing.main window")
    SwingWindow mainWindowProvider(JMenuBar menuBar, EventBus eventBus) {
        SwingWindow toRet = new SwingWindow(eventBus);
        toRet.setJMenuBar(menuBar);
        return toRet;
    }


    @Provides
    SwingWindow swingWindowProvider(Window window) {
        return (SwingWindow) window;
    }

    @Provides
    JMenuBar provideMenuBar(ToolbarMenuConstructor menuConstructor, ToolbarMenuModule menus) {
        return menuConstructor.construct(menus.getMenus());
    }
}



