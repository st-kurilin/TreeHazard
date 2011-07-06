package ua.kharkov.kture.ot.common.connector;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

// TODO: addWidget comments
public class ListenerWrapper<L> implements Observer {
    private L presenter;
    //contains strings in lower case; actually it's kind of function pointers
    private Map<String, Method> listenerMap;
    private Set<Method> whenCommitDone;
    private Set<Method> whenDispose;

    {
        whenCommitDone = new HashSet<Method>();
        whenDispose = new HashSet<Method>();
    }

    public ListenerWrapper(L presenter) {
        this.presenter = presenter;
        listenerMap = retrievePresenterInfo(presenter);
    }

    @Override
    public void update(Object observable, String type) {
        if (listenerMap.containsKey(type.toLowerCase())) {
            invokeMethod(listenerMap.get(type.toLowerCase()));
        }
        for (Method m : whenCommitDone) {
            invokeMethod(m);
        }
    }

    @Override
    public void dispose(Object observable) {
        for (Method m : whenDispose) {
            invokeMethod(m);
        }
    }

    private void invokeMethod(Method m) {
        try {
            m.invoke(presenter, Arrays.asList().toArray());
        } catch (Exception e) {
            RuntimeException runtimeException = new RuntimeException("model-presenter communication error" + m.getName());
            runtimeException.initCause(e);
            throw runtimeException;
        }
    }

    private Map<String, Method> retrievePresenterInfo(Object presenter) {
        Map<String, Method> res = new HashMap<String, Method>();
        for (Method method : presenter.getClass().getMethods()) {
            for (Annotation annotation : method.getAnnotations()) {
                if (annotation.annotationType().equals(FieldObserver.class)) {
                    String propertyName = ((FieldObserver) annotation).value();
                    res.put(propertyName.toLowerCase(), method);
                }
                if (annotation.annotationType().equals(WhenCommitDone.class)) {
                    whenCommitDone.add(method);
                }
                if (annotation.annotationType().equals(WhenModelDisposed.class)) {
                    whenDispose.add(method);
                }
            }
        }
        return res;
    }
}
