package ua.kharkov.kture.ot.common.localization;

import com.google.common.base.Preconditions;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 09.05.11
 */
class Utf8Container extends ResourceBundle {
    private static Logger LOG = Logger.getLogger(Utf8Container.class);
    private final PropertyResourceBundle bundle;


    public Utf8Container(String title, Locale locale) {
        this.bundle = (PropertyResourceBundle) ResourceBundle.getBundle(title, locale);
    }


    protected String handleGetObject(String key) {
        Preconditions.checkNotNull(key);
        LOG.debug("retrieve>" + key);
        String value = (String) bundle.handleGetObject(key);
        Preconditions.checkNotNull(value);
        try {
            return new String(value.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            RuntimeException exception = new RuntimeException("error while string decode");
            exception.initCause(e);
            throw exception;
        }
    }

    @Override
    public Enumeration<String> getKeys() {
        return bundle.getKeys();
    }

    public String getMessage(String key) {
        return (String) bundle.getObject(key);
    }
}
