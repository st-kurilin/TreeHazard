package ua.kharkov.kture.ot.service.optimization;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 29.05.11
 */
public class ControllerTestUsage {
    /*public static OptimizerController optimizationController(Hazard root) {
        return new OptimizerControllerImpl(new OptimizationService(), constant(root));
    }

    public static void testVariant(OptimizerController controller, OptimizerController.SystemDescriber actualVariants, Map<Criteria, Number> expectedValue) {
        compareSystemVariantsValues(controller, ImmutableList.of(actualVariants), expectedValue);
    }

    public static void compareSystemVariantsValues(
            OptimizerController controller,
            Collection<OptimizerController.SystemDescriber> actualVariants,
            Map<Criteria, Number>... expectedValuesArray) {
        assertEquals("different number of variants ", actualVariants.size(), expectedValuesArray.length);
        Set<Criteria> allUsableCriteria = retrieveAllCriteria(expectedValuesArray);
        Collection<Map<Criteria, Number>> actualValues = calcValues(controller, actualVariants, allUsableCriteria);
        Collection<Map<Criteria, Number>> expectedValues = asList(expectedValuesArray);
        checkState(actualValues.size() == expectedValues.size());
        if (!CollectionEquality.isPatterning(actualValues, expectedValues, new Comparator<Number>() {
            @Override
            public int compare(Number o1, Number o2) {
                if (o1 == null && o2 == null) {
                    return 0;
                }
                if (o1 == null) {
                    return -1;
                }
                if (o2 == null) {
                    return +1;
                }

                //TODO
                return Double.compare(o1.doubleValue(), o2.doubleValue());
            }
        })) {
            print(actualValues);
            fail();
        }
    }

    private static void print(Collection<Map<Criteria, Number>> maps) {
        //TODO: make output more readable
        for (Map<Criteria, Number> map : maps) {
            System.out.println(map);
        }
    }

    private static Collection<Map<Criteria, Number>> calcValues(final OptimizerController controller, Collection<OptimizerController.SystemDescriber> actualVariants, final Set<Criteria> criteria) {
        return transform(actualVariants, new Function<OptimizerController.SystemDescriber, Map<Criteria, Number>>() {
            @Override
            public Map<Criteria, Number> apply(OptimizerController.SystemDescriber input) {
                Map<Criteria, Number> toRet = newHashMap();
                for (Criteria c : criteria) {
                    toRet.put(c, controller.valueByCriteria(input, c));
                }
                return toRet;
            }
        });
    }


    private static Set<Criteria> retrieveAllCriteria(Map<Criteria, Number>[] expectedValues) {
        Set<Criteria> allUsableCriteria = newHashSet();
        for (Map<Criteria, Number> map : expectedValues) {
            allUsableCriteria.addAll(map.keySet());
        }
        return allUsableCriteria;
    }

    public static ImmutableMap.Builder<Criteria, Number> systemVariant() {
        return ImmutableMap.builder();
    } */
}
