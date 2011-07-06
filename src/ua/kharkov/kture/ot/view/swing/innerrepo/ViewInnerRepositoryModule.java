package ua.kharkov.kture.ot.view.swing.innerrepo;

import com.google.inject.matcher.Matchers;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import ua.kharkov.kture.ot.common.eventbus.EventBus;
import ua.kharkov.kture.ot.common.guice.EventBusDependedModule;
import ua.kharkov.kture.ot.creation.events.ComponentViewCreatedEvent;
import ua.kharkov.kture.ot.creation.events.HazardViewCreatedEvent;
import ua.kharkov.kture.ot.creation.events.RootViewCreatedEvent;
import ua.kharkov.kture.ot.view.declaration.viewers.FigureView;
import ua.kharkov.kture.ot.view.swing.utils.ChangeDtoField;

import java.lang.reflect.Method;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.inject.matcher.Matchers.subclassesOf;
import static ua.kharkov.kture.ot.common.guice.MethodMatchers.withName;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 07.05.11
 */
public class ViewInnerRepositoryModule extends EventBusDependedModule {
    public ViewInnerRepositoryModule(EventBus eventBus) {
        super(eventBus);
    }

    @Override
    protected void configure() {
        final ViewInnerRepository viewInnerRepository = new ViewInnerRepository();
        eventBus.addHandler(ComponentViewCreatedEvent.class, new EventBus.Handler<ComponentViewCreatedEvent>() {
            @Override
            public void handle(ComponentViewCreatedEvent event) {
                viewInnerRepository.addComponentDto(event.getView(), event.getDto());
                viewInnerRepository.addPresenter(event.getView(), event.getPresenter());
            }
        });
        eventBus.addHandler(HazardViewCreatedEvent.class, new EventBus.Handler<HazardViewCreatedEvent>() {
            @Override
            public void handle(HazardViewCreatedEvent event) {
                viewInnerRepository.addHazardDto(event.getView(), event.getDto());
                viewInnerRepository.addPresenter(event.getView(), event.getPresenter());
            }
        });

        eventBus.addHandler(RootViewCreatedEvent.class, new EventBus.Handler<RootViewCreatedEvent>() {
            @Override
            public void handle(RootViewCreatedEvent event) {
                viewInnerRepository.addHazardDto(event.getView(), event.getDto());
                viewInnerRepository.addPresenter(event.getView(), event.getPresenter());
            }
        });

        bindInterceptor(subclassesOf(FigureView.class), Matchers.annotatedWith(ChangeDtoField.class), new MethodInterceptor() {
            @Override
            public Object invoke(MethodInvocation methodInvocation) throws Throwable {

                String fieldName = methodInvocation.getStaticPart().getAnnotation(ChangeDtoField.class).value();
                FigureView<?> view = (FigureView) methodInvocation.getThis();
                Object dto = viewInnerRepository.getDto(view);
                checkNotNull(dto);
                String dtoSetterName = "set" + capitalize(fieldName);
                Method dtoSetter = dto.getClass().getMethod(dtoSetterName, methodInvocation.getMethod().getParameterTypes());
                dtoSetter.invoke(dto, methodInvocation.getArguments());

                return methodInvocation.proceed();
            }
        });
        bindInterceptor(subclassesOf(FigureView.class), withName("setPresenter"), new MethodInterceptor() {
            @Override
            public Object invoke(MethodInvocation methodInvocation) throws Throwable {
                checkArgument(methodInvocation.getMethod().getName().equals("setPresenter"));
                checkArgument(methodInvocation.getArguments().length == 1);
                checkArgument(methodInvocation.getArguments()[0] instanceof FigureView.Presenter);
                viewInnerRepository.addPresenter((FigureView) methodInvocation.getThis(), (FigureView.Presenter) methodInvocation.getArguments()[0]);
                return methodInvocation.proceed();
            }
        });

        bindInterceptor(subclassesOf(FigureView.class), withName("dispose"), new MethodInterceptor() {
            @Override
            public Object invoke(MethodInvocation methodInvocation) throws Throwable {
                checkArgument(methodInvocation.getArguments().length == 0);
                viewInnerRepository.dispose((FigureView) methodInvocation.getThis());
                return methodInvocation.proceed();
            }
        });
        bind(ViewInnerRepository.class).toInstance(viewInnerRepository);
    }

    //TODO: [stas] reorganize this stuff
    private String capitalize(String s) {
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }
}
