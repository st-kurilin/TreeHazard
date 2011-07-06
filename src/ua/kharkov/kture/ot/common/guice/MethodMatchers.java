package ua.kharkov.kture.ot.common.guice;

import com.google.inject.matcher.AbstractMatcher;

import java.lang.reflect.Method;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 23.04.11
 */
public class MethodMatchers {
    public static AbstractMatcher<Method> withName(final String... names) {
        return new AbstractMatcher<Method>() {
            @Override
            public boolean matches(Method method) {
                String methodName = method.getName();
                for (String name : names) {
                    if (methodName.equals(name)) {
                        return true;
                    }
                }
                return false;
            }
        };
    }
}
