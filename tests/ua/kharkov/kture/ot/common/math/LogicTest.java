package ua.kharkov.kture.ot.common.math;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.collections.Lists.newArrayList;


/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 03.06.11
 */
public class LogicTest {
   /* @Test(dataProvider = "properValues")
    public void shouldCalculateWellInRegularSituations(Logic logic, List<Probability> values, Double expectedResult) {
        assertEquals(expectedResult, logic.calcTotalProbability(values).inScientificForm(), 0.001);
    }

    @Test(dataProvider = "valuesWithNull", expectedExceptions = NullPointerException.class)
    public void shouldThrowNpeIfOneOfElementsIsNull(Logic logic, List<Probability> values) {
        checkNotNull(logic);
        checkNotNull(values);

        logic.calcTotalProbability(values);
    }

    @Test(dataProvider = "logics")
    public void shouldReturnOneForEmptyArguments(Logic logic) {
        logic.calcTotalProbability(Collections.<Probability>emptyList());
    }

    @Test(dataProvider = "logics", expectedExceptions = NullPointerException.class)
    public void shouldThrowNpeIfCollectionIsNull(Logic logic) {
        logic.calcTotalProbability(null);
    }


    @DataProvider(name = "properValues")
    public Object[][] properValues() {
        return new Object[][]{
                {Logic.AND, of(0.2), 0.2},
                {Logic.AND, of(0.5, 0.5), 0.25},
                {Logic.AND, of(0.5, .0, 0.5), .0},
                {Logic.AND, of(0.3, 0.3), 0.09},
                {Logic.AND, of(0.3, 0.4), 0.12},
                {Logic.AND, of(0.3, 0.7, 0.4), 0.084},
                {Logic.OR, of(0.2, 0.3), 0.44},
                {Logic.OR, of(0.2, 0.2), 0.36},
                {Logic.OR, of(0.2, 0.9), 0.92},
                {Logic.OR, of(0.9, 0.8), 0.98},
                {Logic.OR, of(0.5, 0.5), 0.75},
                {Logic.OR, of(0.9, 0.8), 0.98},
                {Logic.OR, of(0.01, 0.01), 0.0199},
                {Logic.OR, of(0.2, 0.3, 0.4), 0.664},
                {Logic.OR, of(0.2, 0.3, 0.2), 0.552},
                {Logic.OR, of(0.2, 0.7, 0.2), 0.808},

                {Logic.NOT, of(0.5), 0.5},
                {Logic.NOT, of(0.8), 0.2},
                {Logic.NOT, of(0.3), 0.7},
                {Logic.NOT, of(0.1), 0.9},

                {Logic.AND_WITH_ORDER, of(0.5), 0.5},
                {Logic.AND_WITH_ORDER, of(0.7, 0.8, 0.4), 0.07468},
                {Logic.AND_WITH_ORDER, of(0.7, 0.8, 0.6), 0.12},
                {Logic.AND_WITH_ORDER, of(0.4, 0.8, 0.7), 0.07468},
                {Logic.AND_WITH_ORDER, of(0.5, 0.5), 0.75},
                {Logic.AND_WITH_ORDER, of(0.5, 0.5), 0.75},
                {Logic.AND_WITH_ORDER, of(0.5, 0.5), 0.75},
        };
    }


    @DataProvider(name = "valuesWithNull")
    public Object[][] valuesWithNull() {
        return new Object[][]{
                {Logic.AND, of(0.2, null, 0.2)},
                {Logic.AND, of(null, 0.2)},
                {Logic.AND_WITH_ORDER, of(0.1, null, 0.2)},
                {Logic.AND_WITH_ORDER, of(null, 0.2)},
                {Logic.NOT, of(null)},
                {Logic.OR, of(null, 0.2)},
                {Logic.OR, of(0.1, null, 0.2)},
                {Logic.OR, of(0.3, 0.1, null)}
        };
    }

    @DataProvider(name = "logics")
    public Object[][] logics() {
        return new Object[][]{
                {Logic.AND},
                {Logic.OR},
                {Logic.NOT},
                {Logic.AND_WITH_ORDER}
        };
    }


    private static List<Probability> of(Double first, Double... rest) {
        List<Probability> output = newArrayList();
        if (first == null) {
            output.add(null);
        } else {
            output.add(Probability.scientificForm(first));
        }
        if (rest != null) {
            for (Double d : rest) {
                if (d == null) {
                    output.add(null);
                } else {
                    output.add(Probability.scientificForm(d));
                }
            }
        }
        return output;
    }*/
}
