package ua.kharkov.kture.ot.view.swing.additional.help;

import ua.kharkov.kture.ot.common.localization.MessageBundle;
import ua.kharkov.kture.ot.view.swing.additional.AbstractAdditionalWindow;

import javax.inject.Inject;
import javax.inject.Named;
import javax.swing.*;

public class InfoWindow extends AbstractAdditionalWindow {

    MessageBundle bundle;

    @Inject
    public void setBundle(@Named("info") MessageBundle bundle) {
        this.bundle = bundle;
    }

    @Override
    protected void go() {
        setTitle("Info");
        setSize(500, 600);
        setResizable(true);
        JTextArea text = new JTextArea(bundle.getMessage("text"));
        text.setEditable(false);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(text,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        getContentPane().add(scrollPane);
    }
}
