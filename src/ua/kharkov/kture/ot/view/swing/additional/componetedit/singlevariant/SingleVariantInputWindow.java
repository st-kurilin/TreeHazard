package ua.kharkov.kture.ot.view.swing.additional.componetedit.singlevariant;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import ua.kharkov.kture.ot.common.localization.MessageBundle;
import ua.kharkov.kture.ot.common.math.Probability;
import ua.kharkov.kture.ot.shared.simpleobjects.ComponentDTO;
import ua.kharkov.kture.ot.shared.simpleobjects.VariantDTO;
import ua.kharkov.kture.ot.view.declaration.additionalwindows.ComponentsEditUnitOfWork;
import ua.kharkov.kture.ot.view.swing.additional.AbstractAdditionalWindow;
import ua.kharkov.kture.ot.view.swing.additional.componetedit.MoneyCellRenderer;
import ua.kharkov.kture.ot.view.swing.additional.componetedit.ProbabilityEditor;
import ua.kharkov.kture.ot.view.swing.additional.componetedit.ProbabilityRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collection;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 20.05.11
 */
public class SingleVariantInputWindow extends AbstractAdditionalWindow {
    Collection<ComponentDTO> components;
    ComponentsEditUnitOfWork transaction;
    MessageBundle bundle;
    JTable table;

    @Override
    public boolean isValidToDisplay() {
        return !components.isEmpty();
    }

    public void go() {
        addBaseVariants();
        fillPanel();
        pack();
        addOnCloseListener();
        repaint();
    }

    private void addOnCloseListener() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                transaction.rollback();
            }
        });
    }

    private void fillPanel() {
        setSize(500, getHeight());
        setLayout(new GridBagLayout());

        add(applyButton(), applyButtonConstrains());
        add(cancelButton(), cancelButtonConstrains());

        table = new TableProvider(components, transaction, bundle).get();
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);
        table.getTableHeader().setReorderingAllowed(false);
        //TODO: should be money instead of double
        table.setDefaultRenderer(Double.class, new MoneyCellRenderer());
        table.setDefaultRenderer(Probability.class, new ProbabilityRenderer());
        JFormattedTextField textField = new JFormattedTextField();
        textField.setHorizontalAlignment(JFormattedTextField.RIGHT);
        table.setDefaultEditor(Probability.class, ProbabilityEditor.create());
        add(table.getTableHeader(), tableHeaderConstrains());

        add(table, tableConstrains()

        );
    }

    private GridBagConstraints tableHeaderConstrains() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.gridheight = 1;
        constraints.ipadx = 0;
        constraints.ipady = 0;
        constraints.weightx = 2;
        constraints.weighty = 0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.PAGE_START;
        return constraints;
    }

    private GridBagConstraints tableConstrains() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        constraints.gridheight = 1;
        constraints.ipadx = 0;
        constraints.ipady = 0;
        constraints.weightx = 2;
        constraints.weighty = 2;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.PAGE_START;
        return constraints;
    }

    private GridBagConstraints cancelButtonConstrains() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.NORTHEAST;
        return constraints;
    }

    private GridBagConstraints applyButtonConstrains() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        return constraints;
    }

    private JButton cancelButton() {
        JButton button = new JButton(bundle.getMessage("label.cancel"));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                transaction.rollback();
                close();
            }
        });
        return button;
    }

    private JButton applyButton() {
        JButton button = new JButton(bundle.getMessage("label.apply"));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (table.isEditing()) {
                    table.getCellEditor().stopCellEditing();
                }
                transaction.commit();
                close();
            }
        });
        return button;
    }


    private void addBaseVariants() {
        for (ComponentDTO component : components) {
            if (component.getVariants().isEmpty()) {
                transaction.add(component, new VariantDTO("base"));
            }
        }
    }


    @Inject
    public void setComponents(Collection<ComponentDTO> components) {
        this.components = components;
    }

    @Inject
    public void setTransaction(ComponentsEditUnitOfWork transaction) {
        this.transaction = transaction;
    }

    @javax.inject.Inject
    public void setBundle(@Named("componentEdit") MessageBundle bundle) {
        this.bundle = bundle;
    }

    @Override
    public void close() {
        transaction.rollback();
        dispose();
    }
}