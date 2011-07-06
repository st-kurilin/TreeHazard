package ua.kharkov.kture.ot.common.menu;

import ua.kharkov.kture.ot.common.localization.FakeMessageBundle;
import ua.kharkov.kture.ot.common.localization.MessageBundle;

import java.util.List;

import static com.google.common.collect.ImmutableList.copyOf;
import static com.google.common.collect.Lists.newLinkedList;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 07.05.11
 */
public abstract class AbstractToolbarMenu {
    private final List<MenuBlock> menus = newLinkedList();
    private final MessageBundle bundle;

    public List<MenuBlock> getMenus() {
        configure();
        return copyOf(menus);
    }

    protected abstract void configure();

    public MenuBlock menu(String title) {
        MenuBlock toRet = new MenuBlock(bundle.getMessage(title));
        menus.add(toRet);
        return toRet;
    }

    protected AbstractToolbarMenu(MessageBundle bundle) {
        this.bundle = bundle;
    }

    protected AbstractToolbarMenu() {
        this(new FakeMessageBundle());
    }
}
