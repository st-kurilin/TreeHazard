package ua.kharkov.kture.ot.view.declaration;

/**
 * Wraps all inner commands in single
 *
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 23.04.11
 */
public interface Transactional {
    void commit();

    interface Command {
        void execute();

        void cancel();
    }
}
