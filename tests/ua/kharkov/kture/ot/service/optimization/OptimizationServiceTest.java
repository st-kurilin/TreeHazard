package ua.kharkov.kture.ot.service.optimization;

import org.junit.Test;
import ua.kharkov.kture.ot.common.math.Logic;
import ua.kharkov.kture.ot.model.Hazard;
import ua.kharkov.kture.ot.view.declaration.additionalwindows.optimization.OptimizerController;

import java.util.ArrayList;
import java.util.Collection;

import static ua.kharkov.kture.ot.service.optimization.ControllerTestUsage.*;
import static ua.kharkov.kture.ot.service.optimization.TreeBuilder.modelTree;
import static ua.kharkov.kture.ot.view.declaration.additionalwindows.optimization.Criteria.*;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 28.05.11
 */
//TODO: [stas] I think we should not test different stuff in one test case. Also, please read my comments by searching [stas] in this class
//public class OptimizationServiceTest {
//    @Test
//    public void foo() {
//        Hazard root = modelTree()
//                .logic(Logic.AND)
//                .supHazard().logic(Logic.AND_WITH_ORDER)
//                .component().variant(50, 100).variant(10, 200).end()
//                .end()
//                .build();
//        OptimizerController controller = optimizationController(root);
//        compareSystemVariantsValues(
//                controller,
//                controller.withoutWorst(),
//                systemVariant().put(COST, 100).put(CRASH_PROBABILITY, 50).build(),
//                systemVariant().put(COST, 200).build()
//        );
//    }
//
//
//
//    @Test
//    public void momontTest() {
//        Hazard root = modelTree()
//                .logic(Logic.AND)
//                .supHazard().logic(Logic.OR)
//                .component().variant(1, 10).variant(2, 5).variant(3, 3).variant(4, 2).end()
//                .component().variant(2, 25).variant(3, 20).variant(5, 10).end()
//                .end()
//                .component().variant(1.5, 5).variant(1, 2).end()
//                .build();
//        OptimizerController controller = optimizationController(root);
//        Object o = controller.bestBy(CRASH_PROBABILITY);
//        testVariant(controller, controller.bestBy(CRASH_PROBABILITY), systemVariant().put(COST, 2.98).build());
//
//    }
//
//    @Test
//    public void bar() {
//        Hazard root = modelTree()
//                .logic(Logic.AND)
//                .supHazard().logic(Logic.AND_WITH_ORDER)
//                .component().variant(50, 100).end()
//                .end()
//                .supHazard().logic(Logic.AND_WITH_ORDER)
//                .component().variant(50, 500).end()
//                .end()
//                .build();
//        OptimizerController controller = optimizationController(root);
//        compareSystemVariantsValues(
//                controller,
//                controller.withoutWorst(),
//                systemVariant().put(COST, 600).put(CRASH_PROBABILITY, 25).build()
//        );
//    }
//
//    @Test   //[stas] forgotten
//    public void this_test_should_not_work() {  //TODO: delete it when testing API will be fixed
//        Hazard root = modelTree()
//                .logic(Logic.NOT)
//                .component().variant(25, 100).variant(50, 200).end()
//                .build();
//        OptimizerController controller = optimizationController(root);
//        compareSystemVariantsValues(
//                controller,
//                controller.all(),
//                systemVariant().put(COST, 100).put(CRASH_PROBABILITY, 744444445).build(),
//                systemVariant().put(COST, 123).put(CRASH_PROBABILITY, 7332225).build(),
//                systemVariant().put(COST, 321235).put(CRASH_PROBABILITY, 75).build(),
//                systemVariant().put(COST, 3456).put(CRASH_PROBABILITY, 73333335).build(),
//                systemVariant().put(COST, 100).put(CRASH_PROBABILITY, 75).build(),
//                systemVariant().put(COST, 234).put(CRASH_PROBABILITY, 75).build(),
//                systemVariant().put(COST, 100).put(CRASH_PROBABILITY, 555555575).build(),
//                systemVariant().put(COST, 100222).put(CRASH_PROBABILITY, 75).build(),
//                systemVariant().put(COST, 105550).put(CRASH_PROBABILITY, 7333225).build(),
//                systemVariant().put(COST, 107770).put(CRASH_PROBABILITY, 75).build(),
//                systemVariant().put(COST, 10330).put(CRASH_PROBABILITY, 75).build(),
//                systemVariant().put(COST, 5).put(CRASH_PROBABILITY, 777776).build()
//        );
//    }
//
//    //TODO: tests may suck. I'm too sleepy now, so it can be wrong, sorry
//
//    @Test
//    public void not_and_simple_test() {
//        Hazard root = modelTree()
//                .logic(Logic.NOT)
//                .component().variant(25, 100).variant(50, 50).end()
//                .component().variant(15, 300).variant(35, 200).end()
//                .build();
//        OptimizerController controller = optimizationController(root);
//
//        //[stas] you can do smt like
//        testVariant(controller, controller.bestBy(COST), systemVariant().put(COST, 250).build());
//        //[stas] instead of
//        /*Collection<OptimizerController.SystemDescriber> bestByCost = new ArrayList<OptimizerController.SystemDescriber>();
//        bestByCost.add(controller.bestBy(COST));
//        compareSystemVariantsValues(
//                controller,
//                bestByCost,
//                systemVariant().put(COST, 250).build()
//        );*/
//
//        Collection<OptimizerController.SystemDescriber> bestByProbability = new ArrayList<OptimizerController.SystemDescriber>();
//        bestByProbability.add(controller.bestBy(CRASH_PROBABILITY));
//        compareSystemVariantsValues(
//                controller,
//                bestByProbability,
//                systemVariant().put(CRASH_PROBABILITY, 32.5).build()   //[stas] how did you get 32.5 ? I think this logic should be discussed.
//        );
//
//        Collection<OptimizerController.SystemDescriber> bestByCostMultiProb = new ArrayList<OptimizerController.SystemDescriber>();
//        bestByCostMultiProb.add(controller.bestBy(COST_MULTI_PROBABILITY));
//        compareSystemVariantsValues(
//                controller,
//                bestByCostMultiProb,
//                systemVariant().put(COST, 250).put(CRASH_PROBABILITY, 32.5).build()
//        );
//    }
//
//    @Test
//    public void or_simple_test() {
//        Hazard root = modelTree()
//                .logic(Logic.OR)
//                .component().variant(25, 100).variant(50, 50).end()
//                .component().variant(15, 300).variant(35, 200).end()
//                .build();
//        OptimizerController controller = optimizationController(root);
//
//        Collection<OptimizerController.SystemDescriber> bestByCost = new ArrayList<OptimizerController.SystemDescriber>();
//        bestByCost.add(controller.bestBy(COST));
//        compareSystemVariantsValues(
//                controller,
//                bestByCost,
//                systemVariant().put(COST, 250).build()
//        );
//
//        Collection<OptimizerController.SystemDescriber> bestByProbability = new ArrayList<OptimizerController.SystemDescriber>();
//        bestByProbability.add(controller.bestBy(CRASH_PROBABILITY));
//        compareSystemVariantsValues(
//                controller,
//                bestByProbability,
//                systemVariant().put(CRASH_PROBABILITY, 40).build()
//        );
//
//        Collection<OptimizerController.SystemDescriber> bestByCostMultiProb = new ArrayList<OptimizerController.SystemDescriber>();
//        bestByCostMultiProb.add(controller.bestBy(COST_MULTI_PROBABILITY));
//        compareSystemVariantsValues(
//                controller,
//                bestByCostMultiProb,
//                systemVariant().put(COST, 400).put(CRASH_PROBABILITY, 36.25).build()
//        );
//    }
//
//    @Test
//    public void and_simple_test() {
//        Hazard root = modelTree()
//                .logic(Logic.AND)
//                .component().variant(25, 100).variant(50, 50).end()
//                .component().variant(15, 300).variant(35, 200).end()
//                .build();
//        OptimizerController controller = optimizationController(root);
//
//        Collection<OptimizerController.SystemDescriber> bestByCost = new ArrayList<OptimizerController.SystemDescriber>();
//        bestByCost.add(controller.bestBy(COST));
//        compareSystemVariantsValues(
//                controller,
//                bestByCost,
//                systemVariant().put(COST, 250).build()
//        );
//
//        Collection<OptimizerController.SystemDescriber> bestByProbability = new ArrayList<OptimizerController.SystemDescriber>();
//        bestByProbability.add(controller.bestBy(CRASH_PROBABILITY));
//        compareSystemVariantsValues(
//                controller,
//                bestByProbability,
//                systemVariant().put(CRASH_PROBABILITY, 3.75).build()
//        );
//
//        //[stas] move this stuff in separate testcase.
//        /*Collection<OptimizerController.SystemDescriber> bestByCostMultiProb = new ArrayList<OptimizerController.SystemDescriber>();
//        bestByCostMultiProb.add(controller.bestBy(COST_MULTI_PROBABILITY));
//        compareSystemVariantsValues(
//                controller,
//                bestByCostMultiProb,
//                //in this test, both variant are equivalent
//                systemVariant().put(COST, 300).put(CRASH_PROBABILITY, 8.75).build(),
//                systemVariant().put(COST, 350).put(CRASH_PROBABILITY, 7.5).build()
//        );   */
//    }
//    */
//}
