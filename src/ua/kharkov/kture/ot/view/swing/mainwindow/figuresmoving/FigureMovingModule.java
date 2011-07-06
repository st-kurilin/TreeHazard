package ua.kharkov.kture.ot.view.swing.mainwindow.figuresmoving;

import com.google.inject.Provider;
import org.apache.log4j.Logger;
import ua.kharkov.kture.ot.common.MouseButtonType;
import ua.kharkov.kture.ot.common.eventbus.EventBus;
import ua.kharkov.kture.ot.common.guice.EventBusDependedModule;
import ua.kharkov.kture.ot.common.math.Point;
import ua.kharkov.kture.ot.shared.PostInitEvent;
import ua.kharkov.kture.ot.shared.simpleobjects.FigureDTO;
import ua.kharkov.kture.ot.view.declaration.viewers.FigureView;
import ua.kharkov.kture.ot.view.swing.events.redraw.MainWindowDirtyEvent;
import ua.kharkov.kture.ot.view.swing.innerrepo.ViewInnerRepository;
import ua.kharkov.kture.ot.view.swing.mainwindow.SwingWindow;
import ua.kharkov.kture.ot.view.swing.mainwindow.elements.SwingFigureViewer;
import ua.kharkov.kture.ot.view.swing.mainwindow.focus.FocusElementKeeper;
import ua.kharkov.kture.ot.view.swing.utils.SwingUtils;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 08.05.11
 */
public class FigureMovingModule extends EventBusDependedModule {
    private static Logger LOG = Logger.getLogger(FigureMovingModule.class);
    private Point lastPosition;
    Provider<ViewInnerRepository> repository;
    Provider<SwingWindow> window;
    Provider<FocusElementKeeper> focusElementKeeper;

    public FigureMovingModule(EventBus eventBus) {
        super(eventBus);

    }

    @Override
    protected void configure() {
        repository = getProvider(ViewInnerRepository.class);
        window = getProvider(SwingWindow.class);
        focusElementKeeper = getProvider(FocusElementKeeper.class);

        eventBus.addHandler(PostInitEvent.class, new EventBus.Handler<PostInitEvent>() {
            @Override
            public void handle(PostInitEvent event) {
                init();
            }
        });
    }

    private void init() {
        eventBus.addHandler(MouseActionEvent.class, new EventBus.Handler<MouseActionEvent>() {
            @Override
            public void handle(MouseActionEvent event) {
                LOG.debug("mouse event : " + event.getActionType());
                if (event.getButtonType() == MouseButtonType.LEFT) {
                    handleLeftButtonsEvents(event);
                }
                repaint();
            }
        });
    }

    private void handleLeftButtonsEvents(MouseActionEvent event) {
        switch (event.getActionType()) {
            case PRESSED: {
                onMousePressed(event.getLocation());
                break;
            }
            case RELEASED: {
                onMouseReleased(event.getLocation());
                break;
            }
            case DRAGGED: {
                onMouseDragged(event.getLocation());
                break;
            }
            default: {
                throw new AssertionError("Unexpected enum " + event.getActionType());
            }
        }
    }


    private void onMousePressed(Point location) {
        lastPosition = location;
    }

    private void onMouseReleased(Point location) {
        FigureView.Presenter inFocusPresenter = focusElementKeeper.get().getFocusedElement();

        if (inFocusPresenter != null) {
            FigureView inFocusView = repository.get().getPresenters().inverse().get(inFocusPresenter);
            FigureDTO inFocusDTO = repository.get().getDto(inFocusView);

            inFocusPresenter.move(inFocusDTO.getLocation());
        }
    }

    private void onMouseDragged(Point location) {
        if (lastPosition != null) {
            //TODO: [stas]  legacy code. deal with location offset
            moveFigure(location.getX() - lastPosition.getX(), location.getY() - lastPosition.getY());
        }
        lastPosition = location;
    }

    //TODO: [stas]  legacy code. refactoring needed
    private void moveFigure(int x, int y) {
        FigureView.Presenter inFocusPresenter = focusElementKeeper.get().getFocusedElement();
        if (inFocusPresenter != null) {
            FigureView inFocusView = repository.get().getPresenters().inverse().get(inFocusPresenter);
            FigureDTO inFocusDTO = repository.get().getDto(inFocusView);

            Point lastLocation = SwingUtils.convert(((SwingFigureViewer) inFocusView).getLocation());

            inFocusView.setLocation(new Point(lastLocation.getX() + x, lastLocation.getY() + y));
            inFocusDTO.setLocation(new Point(lastLocation.getX() + x, lastLocation.getY() + y));
        }
    }

    private void repaint() {
        eventBus.fireEvent(new MainWindowDirtyEvent());
    }
}
