package ua.kharkov.kture.ot.service.optimization;

import ua.kharkov.kture.ot.common.Money;
import ua.kharkov.kture.ot.common.connector.Observer;
import ua.kharkov.kture.ot.common.math.Logic;
import ua.kharkov.kture.ot.common.math.Point;
import ua.kharkov.kture.ot.common.math.Probability;
import ua.kharkov.kture.ot.model.CalculatedHazard;
import ua.kharkov.kture.ot.model.Component;
import ua.kharkov.kture.ot.model.Hazard;
import ua.kharkov.kture.ot.model.RootHazard;
import ua.kharkov.kture.ot.shared.VariantVO;

import java.util.Collection;

import static com.google.common.collect.Sets.newHashSet;
import static ua.kharkov.kture.ot.common.SimpleMocker.mock;

/**
 * Utility class for testing.
 * Build your tree faster!
 *
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 29.05.11
 */
public class TreeBuilder {
    public static class HazardDescriber {
        private Collection<HazardDescriber> hazardChildren = newHashSet();
        private Collection<ComponentDescriber> componentChildren = newHashSet();
        private Logic logic;
        private Point location;
        private HazardDescriber parent;

        public HazardDescriber() {
        }

        public HazardDescriber(HazardDescriber parent) {
            this.parent = parent;
        }

        public HazardDescriber logic(Logic logic) {
            this.logic = logic;
            return this;
        }

        public HazardDescriber location(Point location) {
            this.location = location;
            return this;
        }

        public HazardDescriber supHazard() {
            HazardDescriber toRet = new HazardDescriber(this);
            hazardChildren.add(toRet);
            return toRet;
        }

        public ComponentDescriber component() {
            ComponentDescriber toRet = new ComponentDescriber(this);
            componentChildren.add(toRet);
            return toRet;
        }

        public Hazard build() {
            if (parent != null) {
                return parent.build();
            }
            Hazard hazard = new RootHazard(logic, Point.HIGHEST, "a", null);
            hazard.setListener(mock(Observer.class));
            for (HazardDescriber hazardDescriber : hazardChildren) {
                hazard.addChild(hazardDescriber.build(hazard));
            }
            for (ComponentDescriber componentDescriber : componentChildren) {
                hazard.addChild(componentDescriber.build(hazard));
            }
            return hazard;
        }

        private Hazard build(Hazard hazard) {
            Hazard toRet = new CalculatedHazard(logic, hazard, Point.HIGHEST, "a", null);
            toRet.setListener(mock(Observer.class));
            for (HazardDescriber hazardDescriber : hazardChildren) {
                toRet.addChild(hazardDescriber.build(hazard));
            }
            for (ComponentDescriber componentDescriber : componentChildren) {
                toRet.addChild(componentDescriber.build(hazard));
            }
            return toRet;
        }

        public HazardDescriber end() {
            return parent;
        }
    }

    public static class ComponentDescriber {
        private Point location;
        private HazardDescriber parent;
        private Collection<VariantVO> variants = newHashSet();

        public ComponentDescriber(HazardDescriber parent) {
            this.parent = parent;
        }

        public ComponentDescriber location(Point location) {
            this.location = location;
            return this;
        }

        public ComponentDescriber variant(double probability, int money) {
            return variant(Probability.commonForm(probability), new Money(money));
        }

        public ComponentDescriber variant(Probability probability, Money money) {
            variants.add(new VariantVO("some variant", probability, money));
            return this;
        }


        public HazardDescriber end() {
            return parent;
        }

        private Component build(Hazard parent) {
            Component component = new Component("c", "b", location, parent, null);
            component.setListener(mock(Observer.class));
            for (VariantVO variant : variants) {
                component.addVariant(variant);
            }
            return component;
        }
    }

    public static HazardDescriber modelTree() {
        return new HazardDescriber();
    }
}
