package ua.kharkov.kture.ot.view.swing.additional.componetedit.multivariants;

import com.google.inject.name.Named;
import sun.awt.VerticalBagLayout;
import ua.kharkov.kture.ot.common.localization.MessageBundle;
import ua.kharkov.kture.ot.common.math.Probability;
import ua.kharkov.kture.ot.common.math.ProbabilityFormatter;
import ua.kharkov.kture.ot.shared.simpleobjects.ComponentDTO;
import ua.kharkov.kture.ot.shared.simpleobjects.VariantDTO;
import ua.kharkov.kture.ot.view.declaration.additionalwindows.ComponentsEditUnitOfWork;
import ua.kharkov.kture.ot.view.declaration.viewers.Window;
import ua.kharkov.kture.ot.view.swing.additional.AbstractAdditionalWindow;
import ua.kharkov.kture.ot.view.swing.additional.componetedit.MoneyCellRenderer;
import ua.kharkov.kture.ot.view.swing.additional.componetedit.ProbabilityEditor;
import ua.kharkov.kture.ot.view.swing.additional.componetedit.ProbabilityRenderer;

import javax.inject.Inject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Collections.sort;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 22.05.11
 */
public class NewVariantsEditWindow extends AbstractAdditionalWindow {
    private MessageBundle bundle;
    private ProbabilityFormatter probabilityFormatter;
    private JComboBox componentsList;
    private JButton addVariantButton;
    private JButton removeVariantButton;

    private JTable table;
    //private Map<ComponentDTO, DefaultTableModel> variantsTableModels;

    private static final int DEFAULT_INDEX = 0;

    private List<ComponentDTO> components;
    private Window.ApplicationPresenter presenter;
    private ComponentsEditUnitOfWork transaction;

    @Override
    public boolean isValidToDisplay() {
        return !components.isEmpty();
    }

    @Override
    protected void go() {
        this.setTitle(bundle.getMessage("window.componentedit"));
        this.setLayout(new VerticalBagLayout());
        addVariantsManipulationsButtons();
        JPanel variantsPanel = new JPanel(new BorderLayout());
        table = new JTable();
        table.setModel(new TableProvider(components.get(DEFAULT_INDEX), transaction, bundle).get());
        table.getTableHeader().setReorderingAllowed(false);

        table.setDefaultRenderer(Double.class, new MoneyCellRenderer());
        table.setDefaultRenderer(Probability.class, new ProbabilityRenderer());
        table.setDefaultEditor(Probability.class, ProbabilityEditor.create());
        variantsPanel.add(table.getTableHeader(), BorderLayout.PAGE_START);
        variantsPanel.add(table);
        this.getContentPane().add(variantsPanel);

        addResultButtons();
        pack();
        repaint();
    }

    private void addResultButtons() {
        JPanel finalPanel = new JPanel();

        JButton applyButton = new JButton(bundle.getMessage("label.apply"));
        applyButton.addActionListener(applyActionListener());
        finalPanel.add(applyButton);

        JButton cancelButton = new JButton(bundle.getMessage("label.cancel"));
        cancelButton.addActionListener(cancelActionListener());
        finalPanel.add(cancelButton);

        this.getContentPane().add(finalPanel);
    }

    private ActionListener cancelActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                endEditing();
                transaction.rollback();
                close();
            }
        };
    }

    private ActionListener applyActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                endEditing();
                transaction.commit();
                close();
            }
        };
    }

    private void addVariantsManipulationsButtons() {
        JPanel componentsPanel = new JPanel();
        componentsList = componentsList();
        componentsPanel.add(componentsList);
        this.getContentPane().add(componentsPanel);

        JPanel variantsPlusMinusPanel = new JPanel();
        addVariantButton = new JButton(bundle.getMessage("label.addVariant"));
        addVariantButton.addActionListener(addVariantActionListener());
        variantsPlusMinusPanel.add(addVariantButton);
        removeVariantButton = new JButton(bundle.getMessage("label.removeVariant"));
        removeVariantButton.addActionListener(removeVariantActionListener());
        variantsPlusMinusPanel.add(removeVariantButton);
        this.getContentPane().add(variantsPlusMinusPanel);

    }

    private ActionListener removeVariantActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                endEditing();
                int[] rowsToDelete = table.getSelectedRows();
                if (rowsToDelete.length == 0) { //if any row is selected
                    JOptionPane.showMessageDialog(
                            (Component) e.getSource(),
                            bundle.getMessage("error.noRowSelected"),
                            bundle.getMessage("error.noRowSelectedTitle"),
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    ComponentDTO component = components.get(componentsList.getSelectedIndex());
                    for (int i = rowsToDelete.length - 1; i >= 0; i--) {
                        transaction.remove(component, component.getVariants().get(rowsToDelete[i]));
                    }
                    table.setModel(new TableProvider(component, transaction, bundle).get());
                }
            }
        };
    }

    private ActionListener addVariantActionListener() {

        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                endEditing();
                ComponentDTO component = components.get(componentsList.getSelectedIndex());
                VariantDTO newVariant = new VariantDTO("");
                transaction.add(component, newVariant);
                table.setModel(new TableProvider(component, transaction, bundle).get());

                Dimension oldSize = getSize();
                pack();
                Dimension newSize = getSize();
                setSize((int) Math.max(newSize.getWidth(), oldSize.getWidth()), (int) Math.max(newSize.getHeight() + 20, oldSize.getHeight()));

                repaint();
            }
        };
    }

    private JComboBox componentsList() {
        sort(components);
        final JComboBox componentsList = new JComboBox(components.toArray());
        componentsList.setSelectedIndex(DEFAULT_INDEX);
        componentsList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                endEditing();
                ComponentDTO component = components.get(componentsList.getSelectedIndex());
                table.setModel(new TableProvider(component, transaction, bundle).get());
                repaint();
            }
        });
        return componentsList;
    }

    private void showErrorMessage() {
        JOptionPane.showMessageDialog(
                this,
                bundle.getMessage("error.noComponents"),
                bundle.getMessage("error.noComponentsTitle"),
                JOptionPane.WARNING_MESSAGE);
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

    @Override
    public void repaint() {
        super.repaint();
    }

    private void endEditing() {
        if (table.isEditing()) {
            table.getCellEditor().stopCellEditing();
        }
    }

    @Inject
    public void setProbabilityFormatter(ProbabilityFormatter probabilityFormatter) {
        this.probabilityFormatter = probabilityFormatter;
    }
}
