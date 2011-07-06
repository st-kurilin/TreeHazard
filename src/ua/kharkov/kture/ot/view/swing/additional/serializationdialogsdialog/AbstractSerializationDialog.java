package ua.kharkov.kture.ot.view.swing.additional.serializationdialogsdialog;

import ua.kharkov.kture.ot.view.declaration.additionalwindows.AdditionalWindow;
import ua.kharkov.kture.ot.view.declaration.viewers.Window;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 06.05.11
 */
public abstract class AbstractSerializationDialog implements AdditionalWindow {
    private JFileChooser fileChooser;
    protected final Window.ApplicationPresenter presenter;

    public AbstractSerializationDialog(Window.ApplicationPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void display() {
        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileFilter() {
            @Override
            public String getDescription() {
                return "FailFiles";
            }

            @Override
            public boolean accept(File f) {
                return f.isDirectory() || f.getName().endsWith(".xml");
            }
        });
        int retVal = selectDialog(fileChooser);
        if (retVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            actionPerform(presenter, file.getAbsolutePath());
        }
    }

    protected abstract void actionPerform(Window.ApplicationPresenter presenter, String absolutePath);

    @Override
    public void close() {
        fileChooser.setVisible(false);
    }

    @Override
    public boolean isValidToDisplay() {
        return true;
    }

    protected abstract int selectDialog(JFileChooser fileChooser);
}

