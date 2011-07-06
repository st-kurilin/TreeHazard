package ua.kharkov.kture.ot.creation;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.grapher.GrapherModule;
import com.google.inject.grapher.InjectorGrapher;
import com.google.inject.grapher.graphviz.GraphvizModule;
import com.google.inject.grapher.graphviz.GraphvizRenderer;
import ua.kharkov.kture.ot.creation.mvp.ModelRelatedModule;
import ua.kharkov.kture.ot.view.swing.SwingViewModule;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 16.04.11
 */

//It draw injection diagram. You need Graphviz to view it.
public class Grapher {
    private void graph(String filename, Injector demoInjector) throws IOException {
        PrintWriter out = new PrintWriter(new File(filename), "UTF-8");

        Injector injector = Guice.createInjector(new GrapherModule(), new GraphvizModule());
        GraphvizRenderer renderer = injector.getInstance(GraphvizRenderer.class);
        renderer.setOut(out).setRankdir("TB");

        injector.getInstance(InjectorGrapher.class)
                .of(demoInjector)
                .graph();
    }


    public static void main(String[] args) throws IOException {
        new Grapher().graph("diagram.dot", Guice.createInjector(new ModelRelatedModule(), new SwingViewModule(null)));
    }
}