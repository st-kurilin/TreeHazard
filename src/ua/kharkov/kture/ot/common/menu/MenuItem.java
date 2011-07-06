package ua.kharkov.kture.ot.common.menu;

import com.google.common.base.Preconditions;
import ua.kharkov.kture.ot.common.eventbus.EventBus;
import ua.kharkov.kture.ot.shared.navigation.places.Place;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 07.05.11
 */
public class MenuItem<T> implements Item {
    private final T returned;
    private final String title;
    private ItemBinding binding;

    MenuItem(T returned, String title) {
        this.returned = returned;
        this.title = title;
    }

    public T toPlace(Place place) {
        checkNull(binding);
        binding = new ToPlace(place);
        return returned;
    }

    public T toPlace(Class<? extends Place> place) {
        checkNull(binding);
        binding = new ToPlace(create(place));
        return returned;
    }

    public T toEvent(EventBus.Event event) {
        checkNull(binding);
        binding = new ToEvent(event);
        return returned;
    }

    public T toEvent(Class<? extends EventBus.Event> event) {
        checkNull(binding);
        binding = new ToEvent(create(event));
        return returned;
    }

    public MenuBlock submenu() {
        checkNull(binding);
        MenuBlock menuBlock = new MenuBlock(title);
        binding = new ToSubMenu(menuBlock);
        return menuBlock;
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        Preconditions.checkNotNull(binding);
        return binding.accept(visitor);
    }

    private abstract class ItemBinding<T> implements Item {
        T additionalInfo;

        private ItemBinding(T additionalInfo) {
            this.additionalInfo = additionalInfo;
        }
    }

    private class ToPlace extends ItemBinding<Place> {

        private ToPlace(Place additionalInfo) {
            super(additionalInfo);
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitBindedToPlace(title, additionalInfo);
        }
    }

    private class ToEvent extends ItemBinding<EventBus.Event> {

        private ToEvent(EventBus.Event additionalInfo) {
            super(additionalInfo);
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitBindedToEvent(title, additionalInfo);
        }
    }

    private class ToSubMenu extends ItemBinding<MenuBlock> {
        private ToSubMenu(MenuBlock additionalInfo) {
            super(additionalInfo);
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitSubmenu(additionalInfo);
        }
    }

    private <T> T create(Class<T> toCreate) {
        try {
            return toCreate.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("error on getting instance. check that class have empty constructor");
        } catch (IllegalAccessException e) {
            throw new RuntimeException("error on getting instance. check that class have empty constructor");
        }
    }

    private void checkNull(Object object) {
        if (object != null) {
            throw new RuntimeException("Object already initialized to " + object);
        }
    }
}