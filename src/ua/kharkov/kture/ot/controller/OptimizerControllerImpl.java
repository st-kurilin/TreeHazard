package ua.kharkov.kture.ot.controller;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import ua.kharkov.kture.ot.common.math.ComparableNumber;
import ua.kharkov.kture.ot.model.Component;
import ua.kharkov.kture.ot.model.Figure;
import ua.kharkov.kture.ot.model.Hazard;
import ua.kharkov.kture.ot.model.Visitor;
import ua.kharkov.kture.ot.service.optimization.OptimizationService;
import ua.kharkov.kture.ot.shared.VariantVO;
import ua.kharkov.kture.ot.view.declaration.additionalwindows.optimization.Criteria;
import ua.kharkov.kture.ot.view.declaration.additionalwindows.optimization.MinimizationOptimizationCriteria;
import ua.kharkov.kture.ot.view.declaration.additionalwindows.optimization.OptimizerController;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Collections2.filter;
import static com.google.common.collect.Collections2.transform;
import static com.google.common.collect.Sets.newHashSet;

/**
 * Some simple impl. Should be optimized + add cache
 *
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 10.05.11
 */
public class OptimizerControllerImpl implements OptimizerController {
    private final OptimizationService service;
    private final Provider<Hazard> root;

    @Inject
    public OptimizerControllerImpl(OptimizationService service, @Named("model.root") Provider<Hazard> root) {
        this.service = service;
        this.root = root;
    }

    @Override
    public ComparableNumber valueByCriteria(final SystemDescriber systemVariant, MinimizationOptimizationCriteria criteria) {
        return service.valueByCriteria(root.get(), systemVariant, criteria);
    }

    @Override
    public SystemDescriber bestBy(MinimizationOptimizationCriteria criteria) {
        return service.bestBy(root.get(), all(), criteria);
    }

    @Override
    public Collection<SystemDescriber> all() {
        //TODO: undo copy past
        return Collections2.transform(service.retrieveSystemVariants(allComponents(root.get()), new Function<Component, Set<VariantVO>>() {
            @Override
            public Set<VariantVO> apply(Component input) {
                return input.getVariants();
            }
        }), new Function<Map<Component, VariantVO>, SystemDescriber>() {
            @Override
            public SystemDescriber apply(Map<Component, VariantVO> input) {
                return new SystemDescriber(input);
            }
        });
    }

    @Override
    public Collection<SystemDescriber> withoutWorstByComponents() {
        return Collections2.transform(service.retrieveSystemVariants(allComponents(root.get()), new Function<Component, Set<VariantVO>>() {
            @Override
            public Set<VariantVO> apply(final Component component) {
                return Sets.filter(component.getVariants(), new Predicate<VariantVO>() {
                    @Override
                    public boolean apply(VariantVO candidate) {
                        for (VariantVO each : component.getVariants()) {
                            if (!each.equals(candidate)
                                    && each.getCost().compareTo(candidate.getCost()) <= 0
                                    && each.getCrashProbability().compareTo(candidate.getCrashProbability()) <= 0) {
                                return false;
                            }
                        }
                        return true;
                    }
                });
            }
        }), new Function<Map<Component, VariantVO>, SystemDescriber>() {
            @Override
            public SystemDescriber apply(Map<Component, VariantVO> input) {
                return new SystemDescriber(input);
            }
        });
    }

    @Override
    public Collection<SystemDescriber> withoutWorstBySystemVariant() {
        final Collection<SystemDescriber> preFiltered = withoutWorstByComponents();
        return filter(preFiltered, new Predicate<SystemDescriber>() {
            @Override
            public boolean apply(SystemDescriber candidate) {
                for (SystemDescriber each : preFiltered) {
                    if (!each.equals(candidate)
                            && valueByCriteria(each, Criteria.COST).compareTo(valueByCriteria(candidate, Criteria.COST)) >= 0
                            && valueByCriteria(each, Criteria.CRASH_PROBABILITY).compareTo(valueByCriteria(candidate, Criteria.CRASH_PROBABILITY)) >= 0) {
                        return false;
                    }
                }
                return true;
            }
        });
    }

    private Collection<Component> allComponents(Figure figure) {
        return figure.accept(new Visitor<Collection<Component>>() {
            @Override
            public Collection<Component> visitComponent(Component component) {
                return ImmutableList.of(component);
            }

            @Override
            public Collection<Component> visitCalculatedEvent(Hazard hazard) {
                Collection<Component> toRet = newHashSet();
                for (Collection<Component> branch : transform(hazard.getChilds(), new Function<Figure, Collection<Component>>() {
                    @Override
                    public Collection<Component> apply(Figure input) {
                        return allComponents(input);
                    }
                })) {
                    toRet.addAll(branch);
                }
                return toRet;
            }
        });
    }

    @Override
    public Collection<SystemDescriber> withoutWorst() {
        return withoutWorstByComponents();
    }


    @Override
    //it worst by all  Criteria if contains at least one such variant
    public Collection<SystemDescriber> filterWorstByAllCriteria(Collection<SystemDescriber> all) {
        Preconditions.checkNotNull(all);
        Collection<SystemDescriber> filtered = filter(all, new Predicate<SystemDescriber>() {
            @Override
            public boolean apply(SystemDescriber input) {
                for (Component component : allComponents(root.get())) {
                    if (isWorst(component, input.apply(component))) {
                        return false;
                    }
                }
                return true;
            }
        });
        return filtered.isEmpty() ? all : filtered;
    }

    private boolean isWorst(Component component, VariantVO input) {
        if (component.getVariants().size() == 1) return false;
        for (VariantVO other : component.getVariants()) {
            if (other.getCost().compareTo(input.getCost()) < 0
                    && other.getCrashProbability().compareTo(input.getCrashProbability()) < 0) {
                return false;
            }
        }
        return true;
    }
}
