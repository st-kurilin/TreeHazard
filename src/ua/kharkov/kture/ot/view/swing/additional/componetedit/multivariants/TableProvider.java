package ua.kharkov.kture.ot.view.swing.additional.componetedit.multivariants;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import ua.kharkov.kture.ot.common.Money;
import ua.kharkov.kture.ot.common.localization.MessageBundle;
import ua.kharkov.kture.ot.common.math.Probability;
import ua.kharkov.kture.ot.shared.simpleobjects.ComponentDTO;
import ua.kharkov.kture.ot.shared.simpleobjects.VariantDTO;
import ua.kharkov.kture.ot.view.declaration.additionalwindows.ComponentsEditUnitOfWork;
import ua.kharkov.kture.ot.view.swing.utils.RowBasedTableModel;

import javax.inject.Provider;
import javax.swing.table.TableModel;
import java.util.List;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.Lists.transform;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 22.05.11
 */
public class TableProvider implements Provider<TableModel> {
    ComponentsEditUnitOfWork transaction;
    MessageBundle bundle;
    List<ColumnVariantRow> data;

    public TableProvider(final ComponentDTO component, ComponentsEditUnitOfWork transaction, MessageBundle bundle) {
        this.transaction = transaction;
        this.bundle = bundle;


        this.data = transform(component.getVariants(),
                new Function<VariantDTO, ColumnVariantRow>() {
                    @Override
                    public ColumnVariantRow apply(VariantDTO input) {
                        return new ColumnVariantRow(component, input);
                    }
                });
    }

    @Override
    public TableModel get() {
        return componentRowModel();
    }

    @SuppressWarnings("unchecked") //TODO: deal with it
    private TableModel componentRowModel() {
        return RowBasedTableModel.<ColumnVariantRow>builder()
                .editedColumn(bundle.getMessage("variant.title"), String.class)
                .accessor(new RowBasedTableModel.ValueAccessor<ColumnVariantRow, String>() {
                    @Override
                    public String getValue(ColumnVariantRow row) {
                        return row.variant.getName();
                    }

                    @Override
                    public void setValue(ColumnVariantRow row, String newValue) {
                        transaction.newVariantName(row.component, row.variant, newValue);
                    }
                })
                .end()

                .editedColumn(bundle.getMessage("crashProbability"), Probability.class)
                .accessor(new RowBasedTableModel.ValueAccessor<ColumnVariantRow, Probability>() {
                    @Override
                    public Probability getValue(ColumnVariantRow row) {
                        return row.variant.getCrashProbability() == null
                                ? null
                                : row.variant.getCrashProbability();
                    }

                    @Override
                    public void setValue(ColumnVariantRow row, Probability newValue) {
                        transaction.newCrashProbability(row.component, row.variant, newValue);
                    }
                })
                .end()

                .editedColumn(bundle.getMessage("cost"), Double.class)
                .accessor(new RowBasedTableModel.ValueAccessor<ColumnVariantRow, Double>() {
                    @Override
                    public Double getValue(ColumnVariantRow row) {
                        if (row.variant.getCost() == null) {
                            return null;
                        }
                        return row.variant.getCost().getDollars();
                    }

                    @Override
                    public void setValue(ColumnVariantRow row, Double newValue) {
                        if (newValue != null) {
                            transaction.newCost(row.component, row.variant, new Money(newValue));
                        }
                    }
                })
                .end()

                .setData(data)
                .build();
    }

    private static class ColumnVariantRow {
        ComponentDTO component;
        VariantDTO variant;

        private ColumnVariantRow(ComponentDTO component, VariantDTO variant) {
            this.component = component;
            this.variant = variant;
            Preconditions.checkNotNull(component);
            Preconditions.checkNotNull(variant);
            checkState(component.getVariants().contains(variant));
        }
    }
}

