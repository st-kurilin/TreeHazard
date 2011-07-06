package ua.kharkov.kture.ot.service.serialization.load.beans;

import ua.kharkov.kture.ot.shared.VariantVO;

import java.util.Collection;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 17.04.11
 */
public class ComponentBean extends FigureBean {
    private String brokenEventTitle;

    private VariantVO base;
    private Collection<VariantVO> variants;

    public VariantVO getBase() {
        return base;
    }

    public void setBase(VariantVO base) {
        this.base = base;
    }

    public void setBrokenEventTitle(String brokenEventTitle) {
        this.brokenEventTitle = brokenEventTitle;
    }

    public String getBrokenEventTitle() {
        return brokenEventTitle;
    }

    public Collection<VariantVO> getVariants() {
        return variants;
    }

    public void setVariants(Collection<VariantVO> variants) {
        this.variants = variants;
    }
}
