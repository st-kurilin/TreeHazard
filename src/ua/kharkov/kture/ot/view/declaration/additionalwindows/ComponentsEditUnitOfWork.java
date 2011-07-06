package ua.kharkov.kture.ot.view.declaration.additionalwindows;

import ua.kharkov.kture.ot.common.Money;
import ua.kharkov.kture.ot.common.math.Probability;
import ua.kharkov.kture.ot.shared.simpleobjects.ComponentDTO;
import ua.kharkov.kture.ot.shared.simpleobjects.VariantDTO;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 11.05.11
 */
public interface ComponentsEditUnitOfWork {
    // add new variant to component
    void add(ComponentDTO component, VariantDTO variant);

    @Deprecated
        //use remove(component, variant) instead
    void remove(VariantDTO variant);

    //remove variant from component
    void remove(ComponentDTO component, VariantDTO variant);

    void newVariantName(ComponentDTO component, VariantDTO variant, String newName);

    void newCost(ComponentDTO component, VariantDTO variant, Money money);

    void newCrashProbability(ComponentDTO component, VariantDTO variant, Probability probability);

    // after variant edited this method should be called
    void markAsDirty(ComponentDTO component, VariantDTO variant);

    //change component's name
    void renameComponent(ComponentDTO dto, String newName);

    //change component's broken title
    void changeBrokenTitle(ComponentDTO dto, String newBrokenTitle);

    //should be called after all changes
    void commit();

    //should be called for operation canceled
    void rollback();
}