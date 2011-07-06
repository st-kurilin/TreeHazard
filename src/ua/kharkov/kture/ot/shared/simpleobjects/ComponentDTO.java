package ua.kharkov.kture.ot.shared.simpleobjects;

import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.List;

import static com.google.common.collect.Lists.newLinkedList;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 23.04.11
 */
public class ComponentDTO extends FigureDTO implements Comparable<ComponentDTO> {
    private String brokenEventTitle;
    private List<VariantDTO> variants = newLinkedList();
    private VariantDTO baseVariant;

    public String getBrokenEventTitle() {
        return brokenEventTitle;
    }

    public void setBrokenEventTitle(String brokenEventTitle) {
        this.brokenEventTitle = brokenEventTitle;
    }

    public List<VariantDTO> getVariants() {
        return variants;
    }

    public void setVariants(Collection<VariantDTO> variants) {
        this.variants = Lists.newArrayList(variants);
    }

    @Override
    @Deprecated
    //TODO: [stas] WTF? why getBrokenEventTitle? don't put view representation in dto objects
    public String toString() {
        return getTitle();
    }

    public VariantDTO getBaseVariant() {
        return baseVariant;
    }

    public void setBaseVariant(VariantDTO baseVariant) {
        this.baseVariant = baseVariant;
    }

    @Override
    public int compareTo(ComponentDTO other) {
        return this.getTitle().compareTo(other.getTitle());
    }
}
