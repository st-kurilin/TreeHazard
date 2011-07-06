package ua.kharkov.kture.ot.view.declaration.viewers;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 12.02.11
 */
public interface Window {
    interface ApplicationPresenter {
        void newTree();

        void save(String file);

        void load(String file);

        void exit();
    }

    void draw();
}
