package ua.kharkov.kture.ot.view.swing.additional.serializationdialogsdialog;

import ua.kharkov.kture.ot.view.declaration.viewers.Window;

import javax.inject.Inject;
import javax.swing.*;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 06.05.11
 */
public class OpenDialogWindow extends AbstractSerializationDialog {
    @Inject
    public OpenDialogWindow(Window.ApplicationPresenter presenter) {
        super(presenter);
    }

    @Override
    protected int selectDialog(JFileChooser fileChooser) {
        return fileChooser.showOpenDialog(null);
    }

    @Override
    protected void actionPerform(Window.ApplicationPresenter presenter, String absolutePath) {
        presenter.load(absolutePath);
    }
}
