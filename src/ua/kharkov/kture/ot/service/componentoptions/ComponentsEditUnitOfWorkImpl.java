package ua.kharkov.kture.ot.service.componentoptions;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import org.apache.log4j.Logger;
import ua.kharkov.kture.ot.common.Money;
import ua.kharkov.kture.ot.common.math.Probability;
import ua.kharkov.kture.ot.shared.simpleobjects.ComponentDTO;
import ua.kharkov.kture.ot.shared.simpleobjects.VariantDTO;
import ua.kharkov.kture.ot.view.declaration.Transactional;
import ua.kharkov.kture.ot.view.declaration.additionalwindows.ComponentsEditUnitOfWork;
import ua.kharkov.kture.ot.view.declaration.viewers.ComponentView;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Collections2.transform;
import static java.util.Collections.synchronizedList;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 23.04.11
 */
public final class ComponentsEditUnitOfWorkImpl implements Transactional, ComponentsEditUnitOfWork {
    private static Logger LOG = Logger.getLogger(ComponentsEditUnitOfWorkImpl.class);

    private final Map<ComponentDTO, ComponentView.Presenter> presenters;
    private final List<Command> commands = synchronizedList(Lists.<Command>newLinkedList());
    private final Multimap<ComponentDTO, VariantDTO> dirtyDtos = HashMultimap.create();

    @Inject
    public ComponentsEditUnitOfWorkImpl(Map<ComponentDTO, ComponentView.Presenter> presenters) {
        this.presenters = presenters;
    }

    @Override
    public void remove(VariantDTO variant) {
        for (ComponentDTO dto : presenters.keySet()) {
            if (dto.getVariants().contains(variant)) {
                remove(dto, variant);
                return;
            }
        }
        throw new RuntimeException("component not found");
    }

    @Override
    public void remove(ComponentDTO component, VariantDTO variant) {
        commands.add(new RemoveCommand(component, variant));
    }

    @Override
    public void newVariantName(ComponentDTO component, VariantDTO variant, String newName) {
        commands.add(new RenameVariantCommand(component, variant, individualise(component, newName)));
    }


    @Override
    public void newCost(ComponentDTO component, VariantDTO variant, Money money) {
        commands.add(new ChangeCostCommand(component, variant, money));
    }

    @Override
    public void newCrashProbability(ComponentDTO component, VariantDTO variant, Probability probability) {
        commands.add(new ChangeCrashProbabilityCommand(component, variant, probability));
    }

    @Override
    public void add(ComponentDTO component, VariantDTO variant) {
        variant.setName(individualise(component, variant.getName()));
        commands.add(new AddCommand(component, variant));
    }


    @Override
    public void markAsDirty(ComponentDTO component, VariantDTO variant) {
        if (!component.getVariants().contains(variant)) {
            throw new RuntimeException("component " + component.hashCode() + "doesn't contain" + variant);
        }
        dirtyDtos.put(component, variant);
    }

    @Override
    public void renameComponent(ComponentDTO dto, String newName) {
        commands.add(new RenameCommand(dto, individualise(dto, newName)));
    }

    @Override
    public void changeBrokenTitle(ComponentDTO dto, String newBrokenTitle) {
        commands.add(new BrokenRenameCommand(dto, newBrokenTitle));
    }

    @Override
    public void commit() {
        for (Command command : commands) {
            command.execute();
        }
        commands.clear();
    }

    @Override
    public void rollback() {
        for (Command command : Lists.reverse(commands)) {
            command.cancel();
        }
    }

    private String individualise(ComponentDTO component, String wish) {
        if (!alreadyContainsVariantWithName(component, wish)) {
            return wish;
        }
        int postfix = 2;
        while (alreadyContainsVariantWithName(component, wish + postfix)) {
            postfix++;
        }
        return wish + postfix;
    }

    private boolean alreadyContainsVariantWithName(ComponentDTO component, String name) {
        return transform(component.getVariants(), new Function<VariantDTO, String>() {
            @Override
            public String apply(VariantDTO input) {
                return input.getName();
            }
        }).contains(name);
    }

    private class AddCommand implements Command {
        ComponentDTO component;
        VariantDTO variant;
        String variantName;

        private AddCommand(ComponentDTO component, VariantDTO variant) {
            LOG.debug("created" + this.getClass().getSimpleName() + " for " + variant.getName());
            this.component = component;
            this.variant = variant;
            component.getVariants().add(variant);
            variantName = variant.getName();
        }

        @Override
        public void execute() {
            LOG.debug("executed" + this.getClass().getSimpleName() + " for " + variant.getName());
            Preconditions.checkNotNull(variant.getName());
            String currentName = variant.getName();
            variant.setName(variantName);
            presenters.get(component).addVariant(variant);
            variant.setName(currentName);
        }

        @Override
        public void cancel() {
            component.getVariants().remove(variant);
        }
    }

    private class RemoveCommand implements Command {
        VariantDTO variant;
        ComponentDTO component;

        private RemoveCommand(ComponentDTO component, VariantDTO variant) {
            LOG.debug("created" + this.getClass().getSimpleName() + " for " + variant.getName());
            this.variant = variant;
            this.component = component;
            component.getVariants().remove(variant);
        }

        @Override
        public void execute() {
            LOG.debug("executed" + this.getClass().getSimpleName() + " for " + variant.getName());
            presenters.get(component).removeVariant(variant.getName());
        }

        @Override
        public void cancel() {
            component.getVariants().add(variant);
        }
    }

    private class RenameCommand implements Command {
        ComponentDTO component;
        String oldName;

        private RenameCommand(ComponentDTO component, String newName) {
            this.component = component;
            this.oldName = component.getTitle();
            component.setTitle(newName);
        }

        @Override
        public void execute() {
            presenters.get(component).rename(component.getTitle());
        }

        @Override
        public void cancel() {
            component.setTitle(oldName);
        }
    }

    private class BrokenRenameCommand implements Command {
        ComponentDTO component;
        String oldName;

        private BrokenRenameCommand(ComponentDTO component, String newBrokenName) {
            this.component = component;
            this.oldName = component.getBrokenEventTitle();
            component.setBrokenEventTitle(newBrokenName);
        }

        @Override
        public void execute() {
            presenters.get(component).changeBrokenEventTitle(component.getBrokenEventTitle());
        }

        @Override
        public void cancel() {
            component.setBrokenEventTitle(oldName);
        }
    }


    private class MarkAsBaseCommand implements Command {
        ComponentDTO component;
        VariantDTO oldValue;

        private MarkAsBaseCommand(ComponentDTO component, VariantDTO variant) {
            this.component = component;
            this.oldValue = component.getBaseVariant();
            component.setBaseVariant(variant);
        }

        @Override
        public void execute() {
            presenters.get(component).markVariantAsBase(component.getBaseVariant().getName());
        }

        @Override
        public void cancel() {
            component.setBaseVariant(oldValue);
        }
    }

    private class RenameVariantCommand implements Command {
        ComponentDTO component;
        VariantDTO variant;
        String oldValue;

        private RenameVariantCommand(ComponentDTO component, VariantDTO variant, String newValue) {
            LOG.debug("created" + this.getClass().getSimpleName() + " for " + variant.getName());
            this.component = component;
            oldValue = variant.getName();
            variant.setName(newValue);
            this.variant = variant.clone();
        }

        @Override
        public void execute() {
            LOG.debug("executed" + this.getClass().getSimpleName() + " for " + variant.getName());
            presenters.get(component).removeVariant(oldValue);
            presenters.get(component).addVariant(variant);
        }

        @Override
        public void cancel() {
            variant.setName(oldValue);
        }
    }

    private class ChangeCrashProbabilityCommand implements Command {
        ComponentDTO component;
        VariantDTO variant;
        Probability oldValue;

        private ChangeCrashProbabilityCommand(ComponentDTO component, VariantDTO variant, Probability newValue) {
            LOG.debug("created" + this.getClass().getSimpleName() + " for " + variant.getName());
            this.component = component;
            this.oldValue = variant.getCrashProbability();
            variant.setCrashProbability(newValue);
            this.variant = variant.clone();
        }

        @Override
        public void execute() {
            LOG.debug("executed" + this.getClass().getSimpleName() + " for " + variant.getName());
            presenters.get(component).removeVariant(variant.getName());
            presenters.get(component).addVariant(variant);
        }

        @Override
        public void cancel() {
            variant.setCrashProbability(oldValue);
        }
    }

    private class ChangeCostCommand implements Command {
        ComponentDTO component;
        Money oldValue;
        VariantDTO variant;

        private ChangeCostCommand(ComponentDTO component, VariantDTO variant, Money newValue) {
            this.component = component;
            this.oldValue = variant.getCost();
            variant.setCost(newValue);
            this.variant = variant.clone();
        }

        @Override
        public void execute() {
            presenters.get(component).removeVariant(variant.getName());
            presenters.get(component).addVariant(variant);
        }

        @Override
        public void cancel() {
            variant.setCost(oldValue);
        }
    }
}
