package ua.kharkov.kture.ot.view.swing.mainwindow.popupmenu;

import com.google.inject.name.Named;
import ua.kharkov.kture.ot.common.localization.MessageBundle;
import ua.kharkov.kture.ot.common.menu.AbstractPopupMenu;
import ua.kharkov.kture.ot.shared.navigation.places.*;

import javax.inject.Inject;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 07.05.11
 */
public class DefaultMenu extends AbstractPopupMenu {
    @Inject
    public DefaultMenu(@Named("popup menu") MessageBundle messageBundle) {
        super(messageBundle);
    }

    @Override
    protected void configure() {
        bind("Result").toPlace(ResultsPlace.class);
        bind("Variants").toPlace(VariantsCrudPlace.class);
        bind("Optimization").toPlace(OptimizationPlace.class);
        bind("Load").toPlace(LoadPlace.class);
        bind("Save").toPlace(SavePlace.class);
    }
}
