package ua.kharkov.kture.ot.model;

import com.google.common.base.Function;
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.name.Named;
import ua.kharkov.kture.ot.common.math.Point;
import ua.kharkov.kture.ot.service.locations.LocationValidator;
import ua.kharkov.kture.ot.shared.VariantVO;

import javax.inject.Provider;
import java.util.Collections;
import java.util.Set;

import static com.google.common.collect.Collections2.transform;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 12.02.11
 */
public class Component extends Figure {
    private String brokenEventTitle;
    private final Hazard parent;
    private final Provider<LocationValidator> locationValidator;
    private Set<VariantVO> variants;
    private VariantVO base;

    {
        variants = Sets.newHashSet();
    }

    @Inject
    public Component(@Named("component title") String name,
                     @Named("broken event title") String brokenEventTitle, @Assisted Point location, @Assisted Hazard parent, Provider<LocationValidator> locationValidator) {
        this.parent = parent;
        this.locationValidator = locationValidator;
        this.brokenEventTitle = brokenEventTitle;
        this.location = location;
        this.title = name;
    }

    @Override
    public void setLocation(Point wish) {
        super.setLocation(locationValidator.get()
                .highest(parent.getLocation())
                .wish(wish).newLocation());
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitComponent(this);
    }

    public String getBrokenEventTitle() {
        return brokenEventTitle;
    }

    public void setBrokenEventTitle(String brokenEventTitle) {
        this.brokenEventTitle = brokenEventTitle;
        listener.update(this, "BrokenEventTitle");
    }

    /*Base variant used at crash probability calculation*/
    /*baseVariant should be contained in variants*/
    public void markAsBase(String baseVariantName) {
        base = getVariantByName(baseVariantName);
        listener.update(this, "variants");
    }

    public VariantVO getBaseVariant() {
        if (base == null) {
            if (variants.isEmpty()) {
                throw new IllegalStateException("there is no any variants");
            } else {
                return variants.iterator().next();
            }
        } else {
            return base;
        }
    }

    public void addVariant(VariantVO variant) {
        variants.add(variant);
        listener.update(this, "variants");
    }

    public void removeVariant(String variantName) {
        VariantVO variant = getVariantByName(variantName);
        variants.remove(variant);
        listener.update(this, "variants");
    }

    public Set<VariantVO> getVariants() {
        return Collections.unmodifiableSet(variants);
    }

    @Override
    public String toString() {
        return this.getTitle();
    }

    public void delete() {
        parent.removeChild(this);
        super.delete();
    }

    private VariantVO getVariantByName(String variantName) {
        for (VariantVO variant : variants) {
            if (variant.getName().equals(variantName)) {
                return variant;
            }
        }

        throw new RuntimeException("can't find variant with name: " + variantName + "available:" + transform(variants, new Function<VariantVO, String>() {
            @Override
            public String apply(VariantVO input) {
                return input.getName();
            }
        }));
    }
}
