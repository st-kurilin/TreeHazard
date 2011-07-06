package ua.kharkov.kture.ot.view.swing.utils.menuconstructors;

import ua.kharkov.kture.ot.common.menu.MenuItem;

import javax.inject.Inject;
import javax.swing.*;
import java.util.List;

import static com.google.common.collect.Lists.transform;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 07.05.11
 */
public class PopupMenuConstructor extends SwingMenuConstructor<JPopupMenu> {
    @Inject
    protected PopupMenuConstructor(ItemConverter itemsConverter) {
        super(itemsConverter, new JPopupMenu());
    }

    public JPopupMenu construct(List<MenuItem> menus) {
        for (JComponent jMenu : transform(menus, itemsConverter)) {
            base.add(jMenu);
        }
        return base;

    }
}
