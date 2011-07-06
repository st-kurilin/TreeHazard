package ua.kharkov.kture.ot.view.swing.additional.componetedit.singlevariant;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import ua.kharkov.kture.ot.common.Money;
import ua.kharkov.kture.ot.common.localization.MessageBundle;
import ua.kharkov.kture.ot.common.math.Probability;
import ua.kharkov.kture.ot.shared.simpleobjects.ComponentDTO;
import ua.kharkov.kture.ot.shared.simpleobjects.VariantDTO;
import ua.kharkov.kture.ot.view.declaration.additionalwindows.ComponentsEditUnitOfWork;
import ua.kharkov.kture.ot.view.swing.utils.RowBasedTableModel;

import javax.inject.Provider;
import javax.swing.*;
import javax.swing.table.TableModel;
import java.util.Collection;
import java.util.List;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.Lists.transform;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 21.05.11
 */
public class TableProvider implements Provider<JTable> {
    ComponentsEditUnitOfWork transaction;
    MessageBundle bundle;
    List<ColumnComponentRow> data;

    public TableProvider(Collection<ComponentDTO> components, ComponentsEditUnitOfWork transaction, MessageBundle bundle) {
        this.transaction = transaction;
        this.bundle = bundle;
        this.data = transform(Lists.newArrayList(components), new Function<ComponentDTO, ColumnComponentRow>() {
            @Override
            public ColumnComponentRow apply(ComponentDTO input) {
                return new ColumnComponentRow(input, input.getVariants().iterator().next());
            }
        });
    }

    @Override
    public JTable get() {
        return new JTable(componentRowModel());
    }

    @SuppressWarnings("unchecked") //TODO: deal with it
    private TableModel componentRowModel() {
        return RowBasedTableModel.<ColumnComponentRow>builder()
                .editedColumn(bundle.getMessage("component.title"), String.class)
                .accessor(new RowBasedTableModel.ValueAccessor<ColumnComponentRow, String>() {
                    @Override
                    public String getValue(ColumnComponentRow row) {
                        return row.component.getTitle();
                    }

                    @Override
                    public void setValue(ColumnComponentRow row, String newValue) {
                        transaction.renameComponent(row.component, newValue);
                    }
                })
                .end()

/*
                .editedColumn(bundle.getMessage("component.brokenTitle"), String.class)
                .accessor(new RowBasedTableModel.ValueAccessor<ColumnComponentRow, String>() {
                    @Override
                    public String getDollars(ColumnComponentRow row) {
                        return row.component.getBrokenEventTitle();
                    }

                    @Override
                    public void setValue(ColumnComponentRow row, String newValue) {
                        transaction.changeBrokenTitle(row.component, newValue);
                    }
                })
                .end()
*/

                .editedColumn(bundle.getMessage("crashProbability"), Probability.class)
                .accessor(new RowBasedTableModel.ValueAccessor<ColumnComponentRow, Probability>() {
                    @Override
                    public Probability getValue(ColumnComponentRow row) {
                        return row.variant.getCrashProbability() == null
                                ? null
                                : row.variant.getCrashProbability();
                    }

                    @Override
                    public void setValue(ColumnComponentRow row, Probability newValue) {
                        transaction.newCrashProbability(row.component, row.variant, newValue);
                    }
                })
                .end()

                .editedColumn(bundle.getMessage("cost"), Double.class)
                .accessor(new RowBasedTableModel.ValueAccessor<ColumnComponentRow, Double>() {
                    @Override
                    public Double getValue(ColumnComponentRow row) {
                        if (row.variant.getCost() == null) {
                            return null;
                        }
                        return row.variant.getCost().getDollars();
                    }

                    @Override
                    public void setValue(ColumnComponentRow row, Double newValue) {
                        if (newValue != null) {
                            transaction.newCost(row.component, row.variant, new Money(newValue));
                        }
                    }
                })
                .end()

                .setData(data)
                .build();
    }

    private static class ColumnComponentRow {
        ComponentDTO component;
        VariantDTO variant;

        private ColumnComponentRow(ComponentDTO component, VariantDTO variant) {
            this.component = component;
            this.variant = variant;
            Preconditions.checkNotNull(component);
            Preconditions.checkNotNull(variant);
            checkState(component.getVariants().contains(variant));
        }
    }
}
