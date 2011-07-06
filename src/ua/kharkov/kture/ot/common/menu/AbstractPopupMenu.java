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
public abstract class AbstractPopupMenu {
    protected final MessageBundle messageBundle;

    protected AbstractPopupMenu(MessageBundle messageBundle) {
        this.messageBundle = messageBundle;
    }

    protected AbstractPopupMenu() {
        this(new FakeMessageBundle());
    }

    private final List<MenuItem> menus = newLinkedList();

    public List<MenuItem> getMenus() {
        configure();
        return copyOf(menus);
    }

    protected abstract void configure();

    public MenuItem<MenuItem> bind(String title) {
        MenuItem<MenuItem> toRet = new MenuItem<MenuItem>(null, messageBundle.getMessage(title));
        menus.add(toRet);
        return toRet;
    }

}
