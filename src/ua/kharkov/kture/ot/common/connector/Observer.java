package ua.kharkov.kture.ot.common.connector;

public interface Observer {
    void update(Object observable, String type);

    void dispose(Object observable);
}
