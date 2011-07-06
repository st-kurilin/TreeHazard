package ua.kharkov.kture.ot.view.swing.utils.menuconstructors;

import com.google.common.base.Function;
import ua.kharkov.kture.ot.common.eventbus.EventBus;
import ua.kharkov.kture.ot.common.menu.Item;
import ua.kharkov.kture.ot.common.menu.MenuBlock;
import ua.kharkov.kture.ot.common.navigation.Navigator;
import ua.kharkov.kture.ot.shared.navigation.places.Place;

import javax.inject.Inject;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 07.05.11
 */
abstract class SwingMenuConstructor<T extends JComponent> {
    protected final ItemConverter itemsConverter;
    protected final T base;


    protected SwingMenuConstructor(ItemConverter itemsConverter, T base) {
        this.itemsConverter = itemsConverter;
        this.base = base;
    }

    static class ItemsDispatcher implements Item.Visitor<JComponent> {
        private final EventBus eventBus;
        private final Navigator navigator;

        @Inject
        private ItemsDispatcher(EventBus eventBus, Navigator navigator) {
            this.eventBus = eventBus;
            this.navigator = navigator;
        }

        @Override
        public JComponent visitBindedToPlace(String title, final Place place) {
            JMenuItem displayedMenuItem = new JMenuItem(title);
            displayedMenuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    navigator.goTo(place);
                }
            });
            return displayedMenuItem;
        }

        @Override
        public JComponent visitBindedToEvent(String title, final EventBus.Event event) {
            JMenuItem displayedMenuItem = new JMenuItem(title);
            displayedMenuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    eventBus.fireEvent(event);
                }
            });
            return displayedMenuItem;
        }

        @Override
        public JComponent visitSeparator() {
            return new JSeparator();
        }

        @Override
        public JComponent visitSubmenu(MenuBlock additionalInfo) {
            return new MenuConverter(new ItemConverter(this)).apply(additionalInfo);
        }
    }

    protected static final class ItemConverter implements Function<Item, JComponent> {
        ItemsDispatcher dispatcher;

        @Inject
        ItemConverter(ItemsDispatcher dispatcher) {
            this.dispatcher = dispatcher;
        }

        @Override
        public JComponent apply(Item input) {
            return input.accept(dispatcher);
        }
    }

    protected static final class MenuConverter implements Function<MenuBlock, JMenu> {
        ItemConverter itemConverter;

        MenuConverter(ItemConverter itemConverter) {
            this.itemConverter = itemConverter;
        }

        @Override
        public JMenu apply(MenuBlock input) {
            final JMenu toRet = new JMenu(input.getTitle());
            for (Item item : input.getItems()) {
                toRet.add(itemConverter.apply(item));
            }
            return toRet;
        }
    }
}
