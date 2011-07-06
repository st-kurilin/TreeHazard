package ua.kharkov.kture.ot.view.declaration.additionalwindows.optimization;

import com.google.common.base.Function;
import ua.kharkov.kture.ot.common.math.ComparableNumber;
import ua.kharkov.kture.ot.model.Component;
import ua.kharkov.kture.ot.shared.VariantVO;

import java.util.Collection;
import java.util.Map;

import static com.google.common.collect.Collections2.transform;
import static com.google.common.collect.Maps.newHashMap;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 10.05.11
 */
public interface OptimizerController {
    /**
     * @param systemVariant combination of variants. on for each component
     * @param criteria      comparable number retriever
     * @return value that calculated by criteria to corresponding variant
     */
    ComparableNumber valueByCriteria(SystemDescriber systemVariant, MinimizationOptimizationCriteria criteria);

    //returns best by criteria variant
    SystemDescriber bestBy(MinimizationOptimizationCriteria criteria);

    //@returns all system variants
    Collection<SystemDescriber> all();

    @Deprecated
    Collection<SystemDescriber> filterWorstByAllCriteria(Collection<SystemDescriber> systemVariants);

    //return collection with out worst by all criteria variant. Returns All variants if they are same
    @Deprecated
    Collection<SystemDescriber> withoutWorst();

    Collection<SystemDescriber> withoutWorstByComponents();

    Collection<SystemDescriber> withoutWorstBySystemVariant();

    class SystemDescriber implements Function<Component, VariantVO> {
        private final Map<Component, VariantVO> map;

        public SystemDescriber(Map<Component, VariantVO> map) {
            this.map = map;
        }

        public Map<String, String> configuration() {
            Map<String, String> results = newHashMap();
            for (Map.Entry<Component, VariantVO> each : map.entrySet()) {
                results.put(each.getKey().getTitle(), each.getValue().getName());
            }
            return results;
        }

        @Deprecated
        public Collection<String> variantNames() {
            //TODO
            return transform(map.values(), new Function<VariantVO, String>() {
                @Override
                public String apply(VariantVO input) {
                    return input.getName();
                }
            });
        }

        @Override
        public VariantVO apply(Component input) {
            return map.get(input);
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            SystemDescriber that = (SystemDescriber) o;

            return !(map != null ? !configuration().equals(that.configuration()) : that.map != null);

        }

        @Override
        public int hashCode() {
            return configuration().hashCode();
        }
    }
}
