package ua.kharkov.kture.ot.common.math;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.Lists.newArrayList;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 30.05.11
 */
public class CollectionEquality {
    public static <K, V> boolean isPatterning(Collection<Map<K, V>> left, Collection<Map<K, V>> right, Comparator<V> comparator) {
        if (left.size() != right.size()) {
            return false;
        }
        if (left.size() == 0) {
            return true;
        }
        final Map<K, V> v = right.iterator().next();
        Collection<Map<K, V>> matches = Collections2.filter(right, new Predicate<Map<K, V>>() {
            @Override
            public boolean apply(Map<K, V> pattern) {
                return matches(pattern, v);
            }
        });
        for (Map<K, V> pattern : matches) {
            if (isPatterning(collectionWithout(left, v, comparator), collectionWithout(right, pattern, comparator), comparator)) {
                return true;
            }
        }
        return false;
    }

    private static <K, V> Collection<Map<K, V>> collectionWithout(Collection<Map<K, V>> collection, Map<K, V> value, Comparator<V> comparator) {
        Collection<Map<K, V>> copy = newArrayList(collection);
        Map<K, V> toDel = null;
        for (Map<K, V> map : copy) {
            boolean matchers = true;
            //TODO use value key??
            for (K key : value.keySet()) {
                if (comparator.compare(map.get(key), value.get(key)) != 0) {
                    matchers = false;

                }
            }
            if (matchers) {
                checkState(toDel == null);
                toDel = map;
            }
        }
        checkState(toDel != null);
        copy.remove(toDel);
        return copy;
    }

    private static <K, V> boolean matches(Map<K, V> pattern, Map<K, V> testable) {
        for (K key : testable.keySet()) {
            if (!pattern.get(key).equals(testable.get(key))) {
                return false;
            }
        }
        return true;
    }
}
