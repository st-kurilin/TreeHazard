package ua.kharkov.kture.ot.view.swing.utils.menuconstructors;

import ua.kharkov.kture.ot.common.menu.MenuBlock;

import javax.inject.Inject;
import javax.swing.*;
import java.util.List;

import static com.google.common.collect.Lists.transform;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 07.05.11
 */

//TODO: [stas] JMenuBar vs ToolBar
public class ToolbarMenuConstructor extends SwingMenuConstructor<JMenuBar> {

    @Inject
    protected ToolbarMenuConstructor(ItemConverter itemsConverter) {
        super(itemsConverter, new JMenuBar());
    }

    //TODO: [stas] setLocation method to SwingMenuConstructor
    public JMenuBar construct(List<MenuBlock> menus) {
        for (JMenu jMenu : transform(menus, new MenuConverter(itemsConverter))) {
            base.add(jMenu);
        }
        return base;
    }
}
