package ua.kharkov.kture.ot.common.menu;

import java.util.List;

import static com.google.common.collect.ImmutableList.copyOf;
import static com.google.common.collect.Lists.newLinkedList;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 07.05.11
 */
public class MenuBlock {
    private final List<Item> items = newLinkedList();
    private final String title;

    MenuBlock(String title) {
        this.title = title;
    }

    public MenuItem<MenuBlock> bind(String itemTitle) {
        MenuItem<MenuBlock> toRet = new MenuItem<MenuBlock>(this, itemTitle);
        items.add(toRet);
        return toRet;
    }

    public MenuBlock separator() {
        items.add(new Separator());
        return this;
    }

    public String getTitle() {
        return title;
    }

    public List<Item> getItems() {
        return copyOf(items);
    }
}