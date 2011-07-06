package ua.kharkov.kture.ot.common.localization;

import java.util.Locale;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 17.04.11
 */
public class StringBasedBundle implements MessageBundle {
    private final Utf8Container bundle;

    public StringBasedBundle(String title, Locale locale) {
        this.bundle = new Utf8Container(title, locale);
    }

    public String getMessage(String key) {
        return (String) bundle.getObject(key);
    }
}
