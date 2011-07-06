package ua.kharkov.kture.ot.view.swing.additional;

import ua.kharkov.kture.ot.view.declaration.additionalwindows.AdditionalWindow;

import javax.swing.*;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 06.05.11
 */
public abstract class AbstractAdditionalWindow extends JFrame implements AdditionalWindow {
    @Override
    public void display() {
        go();
        setVisible(true);
    }

    protected abstract void go();

    @Override
    public void close() {
        dispose();
    }

    @Override
    public boolean isValidToDisplay() {
        return true;
    }
}
