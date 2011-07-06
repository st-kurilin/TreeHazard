package ua.kharkov.kture.ot.view.swing.changemodel;

import com.google.inject.Inject;
import ua.kharkov.kture.ot.common.math.Point;
import ua.kharkov.kture.ot.shared.simpleobjects.HazardDTO;
import ua.kharkov.kture.ot.view.declaration.SizeRetriever;
import ua.kharkov.kture.ot.view.declaration.viewers.CalculatedEventView;
import ua.kharkov.kture.ot.view.swing.innerrepo.ViewInnerRepository;

import java.util.Random;

import static java.lang.Math.max;

/**
 * Just some strategy. That puts child somewhere near parent
 *
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 09.05.11
 */

public class SimpleChildPositionGenerator implements ChildPositionGenerator {
    private final SizeRetriever sizeRetriever;
    private final ViewInnerRepository repository;
    private final Random random;

    @Inject
    public SimpleChildPositionGenerator(SizeRetriever sizeRetriever, ViewInnerRepository repository) {
        this.sizeRetriever = sizeRetriever;
        this.repository = repository;
        random = new Random();
    }

    @Override
    public Point generatePosition(CalculatedEventView parent) {
        HazardDTO dto = (HazardDTO) repository.getDto(parent);
        int elementHeight = sizeRetriever.getCalculatedEventsSize(dto.getLogic(), dto.getTwins()).getHeight();
        int elementWidth = sizeRetriever.getCalculatedEventsSize(dto.getLogic(), dto.getTwins()).getWidth();
        int leftX = dto.getLocation().getX();
        int lowestY = dto.getLocation().getY() + elementHeight;
        int newX = max(0, leftX - elementWidth) + random.nextInt(2 * elementWidth);
        int newY = (int) (lowestY + 0.25 * elementHeight);
        return new Point(newX, newY);
    }
}
