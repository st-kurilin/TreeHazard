package ua.kharkov.kture.ot.view.swing.innerrepo;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import ua.kharkov.kture.ot.shared.simpleobjects.ComponentDTO;
import ua.kharkov.kture.ot.shared.simpleobjects.FigureDTO;
import ua.kharkov.kture.ot.shared.simpleobjects.HazardDTO;
import ua.kharkov.kture.ot.view.declaration.viewers.CalculatedEventView;
import ua.kharkov.kture.ot.view.declaration.viewers.ComponentView;
import ua.kharkov.kture.ot.view.declaration.viewers.FigureView;

import javax.inject.Singleton;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 07.05.11
 */
@Singleton
public class ViewInnerRepository {
    private final BiMap<FigureView<?>, FigureView.Presenter> presenters = HashBiMap.create();
    private final BiMap<ComponentView, ComponentDTO> componentsDtos = HashBiMap.create();
    private final BiMap<CalculatedEventView, HazardDTO> hazardDtos = HashBiMap.create();

    public void addPresenter(FigureView view, FigureView.Presenter presenter) {
        presenters.put(view, presenter);
    }

    public void dispose(FigureView view) {
        presenters.remove(view);
        componentsDtos.remove(view);
    }

    public void addComponentDto(ComponentView view, ComponentDTO dto) {
        componentsDtos.put(view, dto);
    }

    public void addHazardDto(CalculatedEventView view, HazardDTO dto) {
        hazardDtos.put(view, dto);
    }

    public FigureDTO getDto(FigureView<?> view) {
        if (componentsDtos.containsKey(view)) {
            return componentsDtos.get(view);
        } else {
            return hazardDtos.get(view);
        }

    }

    public BiMap<FigureView<?>, FigureView.Presenter> getPresenters() {
        return Maps.unmodifiableBiMap(presenters);
    }

    public BiMap<ComponentView, ComponentDTO> getComponentsDtos() {
        return Maps.unmodifiableBiMap(componentsDtos);
    }
}
