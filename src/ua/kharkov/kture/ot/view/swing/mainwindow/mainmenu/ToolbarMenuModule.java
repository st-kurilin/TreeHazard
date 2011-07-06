package ua.kharkov.kture.ot.view.swing.mainwindow.mainmenu;

import ua.kharkov.kture.ot.common.localization.MessageBundle;
import ua.kharkov.kture.ot.common.localization.StringBasedBundle;
import ua.kharkov.kture.ot.common.menu.AbstractToolbarMenu;
import ua.kharkov.kture.ot.shared.navigation.places.*;
import ua.kharkov.kture.ot.view.swing.changemodel.events.NewTreeEvent;

import java.util.Locale;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 07.05.11
 */
public class ToolbarMenuModule extends AbstractToolbarMenu {

    @Override
    protected void configure() {
        MessageBundle bundle = new StringBasedBundle("MainMenu", new Locale("ru", "RU"));


        menu(bundle.getMessage("File"))
                .bind(bundle.getMessage("New")).toEvent(new NewTreeEvent())
                .separator()
                .bind(bundle.getMessage("Save")).toPlace(new SavePlace())
                .bind(bundle.getMessage("Load")).toPlace(LoadPlace.class);
        //.bind("Exit").toPlace(new SavePlace()); //TODO: wrongPlace
        menu(bundle.getMessage("SystemCalc"))
                .bind(bundle.getMessage("VariantsEdit")).toPlace(OneVariantPerComponentEditPlace.class)
                .bind(bundle.getMessage("Result")).toPlace(ResultsPlace.class);
        menu(bundle.getMessage("Optimization"))
                .bind(bundle.getMessage("VariantsEdit")).toPlace(VariantsCrudPlace.class)
                .bind(bundle.getMessage("Optimize")).toPlace(OptimizationPlace.class);
        menu(bundle.getMessage("Help"))
                .bind(bundle.getMessage("About")).toPlace(AboutPlace.class)
                .bind(bundle.getMessage("Info")).toPlace(InfoPlace.class);
    }
}
