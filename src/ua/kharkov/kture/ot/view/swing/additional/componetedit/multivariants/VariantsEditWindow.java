package ua.kharkov.kture.ot.view.swing.additional.componetedit.multivariants;

import com.google.inject.name.Named;
import sun.awt.VerticalBagLayout;
import ua.kharkov.kture.ot.common.Money;
import ua.kharkov.kture.ot.common.localization.MessageBundle;
import ua.kharkov.kture.ot.common.math.Probability;
import ua.kharkov.kture.ot.shared.simpleobjects.ComponentDTO;
import ua.kharkov.kture.ot.shared.simpleobjects.VariantDTO;
import ua.kharkov.kture.ot.view.declaration.additionalwindows.ComponentsEditUnitOfWork;
import ua.kharkov.kture.ot.view.declaration.viewers.Window;
import ua.kharkov.kture.ot.view.swing.additional.AbstractAdditionalWindow;

import javax.inject.Inject;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;
import java.util.List;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 24.04.11
 */
@Deprecated
public class VariantsEditWindow extends AbstractAdditionalWindow {
    private MessageBundle bundle;

    private JComboBox componentsList;
    private JButton addVariantButton;
    private JButton removeVariantButton;
    private JTable variantTable;
    private Map<ComponentDTO, DefaultTableModel> variantsTableModels;

    private static final int DEFAULT_INDEX = 0;

    private List<ComponentDTO> components;
    private Window.ApplicationPresenter presenter;
    private ComponentsEditUnitOfWork transaction;

    private DefaultTableModel getNewTableModel() {
        return new DefaultTableModel(new String[]{
                bundle.getMessage("label.variant"),
                bundle.getMessage("label.probability"),
                bundle.getMessage("label.cost"),
                "_hidden"
        }, 0);
    }

    private static final int NAME_COLUMN = 0;
    private static final int PROBABILITY_COLUMN = 1;
    private static final int COST_COLUMN = 2;
    private static final int HIDDEN_COLUMN = 3;

    @Override
    protected void go() {
        if (components.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    bundle.getMessage("error.noComponents"),
                    bundle.getMessage("error.noComponentsTitle"),
                    JOptionPane.WARNING_MESSAGE);
            this.setVisible(false);
            return;
        }

        this.setTitle(bundle.getMessage("window.componentedit"));
        this.setLayout(new VerticalBagLayout());

        JPanel componentsPanel = new JPanel();
        componentsList = new JComboBox(components.toArray());
        componentsList.setSelectedIndex(DEFAULT_INDEX);
        componentsList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ComponentDTO component = components.get(componentsList.getSelectedIndex());
                DefaultTableModel model = variantsTableModels.get(component);
                variantTable.setModel(model);
                repaint();
            }
        });
        componentsPanel.add(componentsList);
        this.getContentPane().add(componentsPanel);

        JPanel variantsPlusMinusPanel = new JPanel();
        addVariantButton = new JButton("+");
        addVariantButton.addActionListener(new AddVariantActionListener());
        variantsPlusMinusPanel.add(addVariantButton);
        removeVariantButton = new JButton("-");
        removeVariantButton.addActionListener(new RemoveVariantActionListener());
        variantsPlusMinusPanel.add(removeVariantButton);
        this.getContentPane().add(variantsPlusMinusPanel);

        JPanel variantsPanel = new JPanel(new BorderLayout());
        variantsTableModels = new HashMap<ComponentDTO, DefaultTableModel>();
        for (ComponentDTO c : components) {
            DefaultTableModel tableModel = getNewTableModel();
            if (c.getVariants() != null) {
                for (VariantDTO v : c.getVariants()) {
                    tableModel.addRow(new Object[]{v.getName(), v.getCrashProbability().inCommonForm(), v.getCost(), v});
                }
            }
            variantsTableModels.put(c, tableModel);
        }
        variantTable = new JTable(variantsTableModels.get(components.get(DEFAULT_INDEX)));
        variantTable.addPropertyChangeListener(new EditVariantActionListener());

        variantsPanel.add(variantTable.getTableHeader(), BorderLayout.PAGE_START);
        variantsPanel.add(variantTable);
        this.getContentPane().add(variantsPanel);
        this.setVisible(true);

        JPanel finalPanel = new JPanel();

        JButton applyButton = new JButton(bundle.getMessage("label.apply"));
        applyButton.addActionListener(new ApplyActionListener());
        finalPanel.add(applyButton);

        JButton cancelButton = new JButton(bundle.getMessage("label.cancel"));
        cancelButton.addActionListener(new CancelActionListener());
        finalPanel.add(cancelButton);

        this.getContentPane().add(finalPanel);

        repaint();
    }

    @Inject
    public void setTransaction(ComponentsEditUnitOfWork transaction) {
        this.transaction = transaction;
    }

    @Inject
    public void setComponents(Collection<ComponentDTO> components) {
        this.components = new ArrayList<ComponentDTO>(components);
    }

    @Inject
    public void setPresenter(ua.kharkov.kture.ot.view.declaration.viewers.Window.ApplicationPresenter presenter) {
        this.presenter = presenter;
    }

    @Inject
    public void setBundle(@Named("componentEdit") MessageBundle bundle) {
        this.bundle = bundle;
    }

    private class AddVariantActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ComponentDTO component = components.get(componentsList.getSelectedIndex());
            VariantDTO newVariant = new VariantDTO("");
            transaction.add(component, newVariant);

            DefaultTableModel model = variantsTableModels.get(component);
            model.addRow(new Object[]{"", "", "", newVariant});
            variantTable.setModel(model);

            repaint();

        }
    }

    private class RemoveVariantActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int rowToDelete = variantTable.getSelectedRow();
            if (rowToDelete == -1) { //if any row is selected
                JOptionPane.showMessageDialog(
                        (Component) e.getSource(),
                        bundle.getMessage("error.noRowSelected"),
                        bundle.getMessage("error.noRowSelectedTitle"),
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            ComponentDTO component = components.get(componentsList.getSelectedIndex());
            DefaultTableModel model = variantsTableModels.get(component);
            VariantDTO variant = (VariantDTO) ((Vector) model.getDataVector().get(rowToDelete)).get(HIDDEN_COLUMN);
            transaction.remove(component, variant);

            model.removeRow(rowToDelete);
            variantTable.setModel(model);
            repaint();
        }
    }

    private class EditVariantActionListener implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getOldValue() != null && evt.getNewValue() == null) { //hardcoded
                ComponentDTO component = components.get(componentsList.getSelectedIndex());
                DefaultTableModel model = variantsTableModels.get(component);
                int selectedRow = variantTable.getSelectedRow();
                if (selectedRow == -1) {
                    return;
                }
                Vector variantRow = (Vector) model.getDataVector().get(selectedRow);
                VariantDTO variant = (VariantDTO) variantRow.get(HIDDEN_COLUMN);
                variant.setName((String) variantRow.get(NAME_COLUMN));


                try {
                    setNewVariantNumericValue(variant, variantRow, PROBABILITY_COLUMN);
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(
                            (Component) evt.getSource(),
                            bundle.getMessage("error.badNumberFormatProbability"),
                            bundle.getMessage("error.badNumberFormatTitle"),
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    setNewVariantNumericValue(variant, variantRow, COST_COLUMN);
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(
                            (Component) evt.getSource(),
                            bundle.getMessage("error.badNumberFormatMoney"),
                            bundle.getMessage("error.badNumberFormatTitle"),
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                transaction.markAsDirty(component, variant);
            }
        }

        private void setNewVariantNumericValue(VariantDTO variant, Vector variantRow, int column) {
            String newValue = String.valueOf(variantRow.get(column));
            if (newValue.matches("\\d+.?\\d*")) {
                switch (column) {
                    case COST_COLUMN:
                        variant.setCost(new Money(Integer.valueOf(newValue)));
                        break;
                    case PROBABILITY_COLUMN:
                        variant.setCrashProbability(Probability.scientificForm(Double.valueOf(newValue)));
                        break;
                    default:
                        throw new IllegalStateException("No such column");
                }
            } else if (newValue.trim().isEmpty()) {
                //nothing to do;
            } else {
                throw new NumberFormatException();
            }
        }

    }

    @Override
    public void repaint() {
        super.repaint();
        variantTable.getColumnModel().getColumn(HIDDEN_COLUMN).setMaxWidth(0);
        variantTable.getColumnModel().getColumn(HIDDEN_COLUMN).setMinWidth(0);
        variantTable.getColumnModel().getColumn(HIDDEN_COLUMN).setPreferredWidth(0);

        pack();
    }

    private static List<ComponentDTO> generateComponents() {
        List<ComponentDTO> components = new ArrayList<ComponentDTO>();

        VariantDTO var1 = new VariantDTO();
        var1.setName("Chinese lamp");
        var1.setCost(new Money(100));
        var1.setCrashProbability(Probability.scientificForm(0.3));

        VariantDTO var2 = new VariantDTO();
        var2.setName("Japanese lamp");
        var2.setCost(new Money(300));
        var2.setCrashProbability(Probability.scientificForm(0.1));

        Set<VariantDTO> variants1 = new HashSet<VariantDTO>();
        variants1.add(var1);
        variants1.add(var2);

        ComponentDTO com1 = new ComponentDTO();
        com1.setBrokenEventTitle("Bad lamp");
        com1.setVariants(variants1);

        VariantDTO var3 = new VariantDTO();
        var3.setName("Ninja");
        var3.setCost(new Money(1500));
        var3.setCrashProbability(Probability.scientificForm(0.06));

        VariantDTO var4 = new VariantDTO();
        var4.setName("Russian strojbat");
        var4.setCost(new Money(150));
        var4.setCrashProbability(Probability.scientificForm(0.6));

        VariantDTO var5 = new VariantDTO();
        var5.setName("SAS");
        var5.setCost(new Money(500));
        var5.setCrashProbability(Probability.scientificForm(0.5));

        Collection<VariantDTO> variants2 = new HashSet<VariantDTO>();
        variants2.add(var3);
        variants2.add(var4);
        variants2.add(var5);

        ComponentDTO com2 = new ComponentDTO();
        com2.setBrokenEventTitle("Idiotic security");
        com2.setVariants(variants2);

        components.add(com1);
        components.add(com2);

        return components;
    }

    private class ApplyActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (variantTable.isEditing()) {
                variantTable.getCellEditor().stopCellEditing();
            }
            transaction.commit();
            close();
        }
    }

    private class CancelActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (variantTable.isEditing()) {
                variantTable.getCellEditor().stopCellEditing();
            }
            transaction.rollback();
            close();
        }
    }

}

