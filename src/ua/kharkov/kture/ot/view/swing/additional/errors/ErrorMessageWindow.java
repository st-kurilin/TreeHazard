package ua.kharkov.kture.ot.view.swing.additional.errors;

import ua.kharkov.kture.ot.common.localization.MessageBundle;
import ua.kharkov.kture.ot.view.declaration.additionalwindows.AdditionalWindow;

import javax.inject.Inject;
import javax.inject.Named;
import javax.swing.*;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 25.04.11
 */
public class ErrorMessageWindow implements AdditionalWindow {
    private static final String titleKey = "title";
    private final String messageKey;
    private final MessageBundle messages;

    @Inject
    public ErrorMessageWindow(@Named("error messages") MessageBundle messages, String messageKey) {
        this.messages = messages;
        this.messageKey = messageKey;
    }

    @Override
    public void display() {
        String title = messages.getMessage(titleKey);
        String message = messages.getMessage(messageKey);
        JOptionPane.showMessageDialog(new JFrame(), message, title, JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void close() {
    }

    @Override
    public boolean isValidToDisplay() {
        return true;
    }
}
