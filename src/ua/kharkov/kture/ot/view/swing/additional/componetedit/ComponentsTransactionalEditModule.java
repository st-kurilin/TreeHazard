package ua.kharkov.kture.ot.view.swing.additional.componetedit;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Provides;
import com.google.inject.name.Names;
import ua.kharkov.kture.ot.common.eventbus.EventBus;
import ua.kharkov.kture.ot.common.localization.MessageBundle;
import ua.kharkov.kture.ot.common.localization.StringBasedBundle;
import ua.kharkov.kture.ot.service.componentoptions.ComponentsEditUnitOfWorkImpl;
import ua.kharkov.kture.ot.shared.navigation.places.OneVariantPerComponentEditPlace;
import ua.kharkov.kture.ot.shared.navigation.places.VariantsCrudPlace;
import ua.kharkov.kture.ot.shared.simpleobjects.ComponentDTO;
import ua.kharkov.kture.ot.view.declaration.viewers.ComponentView;
import ua.kharkov.kture.ot.view.swing.additional.AbstractWindowModule;
import ua.kharkov.kture.ot.view.swing.additional.componetedit.multivariants.NewVariantsEditWindow;
import ua.kharkov.kture.ot.view.swing.additional.componetedit.singlevariant.SingleVariantInputWindow;
import ua.kharkov.kture.ot.view.swing.innerrepo.ViewInnerRepository;

import java.util.Collection;
import java.util.Locale;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 23.04.11
 */
public class ComponentsTransactionalEditModule extends AbstractWindowModule {
    public ComponentsTransactionalEditModule(EventBus eventBus) {
        super(eventBus);
    }

    @Override
    protected void configure() {
        bindPlace(VariantsCrudPlace.class).onValidationFailErrorMessage("variantsNotFilled").toWindow(NewVariantsEditWindow.class);
        bindPlace(OneVariantPerComponentEditPlace.class).onValidationFailErrorMessage("variantsNotFilled").toWindow(SingleVariantInputWindow.class);

        bind(MessageBundle.class).annotatedWith(Names.named("componentEdit"))
                .toInstance(new StringBasedBundle("ComponentEdit", new Locale("ru", "RU")));
    }

    @Provides
    Collection<ComponentDTO> provideComponentDTOs(ViewInnerRepository infoKeeper) {
        return infoKeeper.getComponentsDtos().values();
    }

    @Provides
    ComponentsEditUnitOfWorkImpl provideEditUnitOfWork(final ViewInnerRepository infoKeeper) {
        ImmutableMap.Builder<ComponentDTO, ComponentView.Presenter> builder = ImmutableMap.builder();
        for (ComponentView view : infoKeeper.getComponentsDtos().keySet()) {
            ComponentDTO dto = infoKeeper.getComponentsDtos().get(view);
            ComponentView.Presenter presenter = (ComponentView.Presenter) infoKeeper.getPresenters().get(view);
            builder.put(dto, presenter);
        }
        return new ComponentsEditUnitOfWorkImpl(builder.build());
    }
}
