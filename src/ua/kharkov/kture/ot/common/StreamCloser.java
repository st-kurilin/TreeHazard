package ua.kharkov.kture.ot.common;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 17.04.11
 */
public class StreamCloser {
    public static void close(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException ignored) {
        }
    }
}
