package ua.kharkov.kture.ot.common.guice;


import com.google.inject.Provider;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 28.05.11
 */
public class InstanceKeeper<T> implements Provider<T> {
    private T instance;

    @Override
    public T get() {
        return instance;
    }

    public void set(T instance) {
        this.instance = instance;
    }

    public static <T> Provider<T> constant(final T constant) {
        return new Provider<T>() {
            @Override
            public T get() {
                return constant;
            }
        };
    }
}
