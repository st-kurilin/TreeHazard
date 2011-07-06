package ua.kharkov.kture.ot.service.serialization;

import ua.kharkov.kture.ot.model.Hazard;
import ua.kharkov.kture.ot.presenters.HazardPresenter;
import ua.kharkov.kture.ot.service.serialization.load.Parser;
import ua.kharkov.kture.ot.service.serialization.load.TreeBuilder;
import ua.kharkov.kture.ot.service.serialization.load.beans.HazardBean;
import ua.kharkov.kture.ot.service.serialization.save.Saver;

import javax.inject.Inject;
import java.io.IOException;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 17.04.11
 */
public class XmlSerializationService implements SerializationService {
    private final Parser parser;
    private final TreeBuilder treeBuilder;
    private final Saver saver;

    @Inject
    public XmlSerializationService(Parser parser, TreeBuilder treeBuilder, Saver saver) {
        this.parser = parser;
        this.treeBuilder = treeBuilder;
        this.saver = saver;
    }

    @Override
    public void save(String fileName, Hazard root) throws IOException {
        saver.save(fileName, root);

    }

    @Override
    public HazardPresenter load(String fileName) throws IOException {
        HazardBean root = parser.parseFile(fileName);
        return treeBuilder.build(root);
    }
}
