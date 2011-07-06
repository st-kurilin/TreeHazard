package ua.kharkov.kture.ot.service.serialization.save;

import ua.kharkov.kture.ot.model.Component;
import ua.kharkov.kture.ot.model.Figure;
import ua.kharkov.kture.ot.model.Hazard;
import ua.kharkov.kture.ot.model.Visitor;
import ua.kharkov.kture.ot.shared.VariantVO;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import static ua.kharkov.kture.ot.common.StreamCloser.close;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 20.04.11
 */
//TODO: [stas] extract interface
public class Saver {
    public void save(String fileName, Hazard root) throws IOException {
        PrintWriter printWriter = null;
        try {
            StringBuffer data = new StringBuffer(1000);
            serializeItem(root, data);

            printWriter = new PrintWriter(new File(fileName), "UTF-8");
            printWriter.append(data.toString());
        } finally {
            close(printWriter);
        }
    }

    private void serializeItem(Figure figure, final StringBuffer stringBuffer) {
        figure.accept(new Visitor<Void>() {
            @Override
            public Void visitComponent(Component component) {
                stringBuffer.append("<component")
                        .append(propertyInfo("title", component.getTitle()))
                        .append(propertyInfo("brokenEventTitle", component.getBrokenEventTitle()))
                        .append(">")
                        .append("<location")
                        .append(propertyInfo("x", component.getLocation().getX()))
                        .append(propertyInfo("y", component.getLocation().getY()))
                        .append("/>");
                stringBuffer.append("<proposal>");
                for (VariantVO variant : component.getVariants()) {
                    stringBuffer.append("<variant")
                            .append(propertyInfo("title", variant.getName()));
                    if (variant.equals(component.getBaseVariant())) {
                        stringBuffer.append(propertyInfo("base", true));
                    }
                    stringBuffer.append(propertyInfo("cost", variant.getCost().getDollars()))
                            .append(propertyInfo("crashProbability", variant.getCrashProbability().inCommonForm()))
                            .append("/>");
                }
                stringBuffer.append("</proposal>").append("</component>");
                return null;
            }

            @Override
            public Void visitCalculatedEvent(Hazard hazard) {
                stringBuffer.append("<hazard")
                        .append(propertyInfo("title", hazard.getTitle()))
                        .append(propertyInfo("logic", hazard.getLogic()));
                if (hazard.getTwins() != 1) {
                    stringBuffer.append(propertyInfo("twins", hazard.getTwins()));
                }
                stringBuffer.append(">");
                stringBuffer.append("<location")
                        .append(propertyInfo("x", hazard.getLocation().getX()))
                        .append(propertyInfo("y", hazard.getLocation().getY()))
                        .append("/>");
                for (Figure child : hazard.getChilds()) {
                    serializeItem(child, stringBuffer);
                }
                stringBuffer.append("</hazard>");
                return null;
            }

        });
    }

    private CharSequence propertyInfo(CharSequence title, Object value) {
        StringBuilder stringBuilder = new StringBuilder(10);
        stringBuilder.append(" ").append(title).append("=\"").append(value).append("\"");
        return stringBuilder;
    }

    private CharSequence fieldInfo(CharSequence tagName, Object value) {
        StringBuilder stringBuilder = new StringBuilder(20);
        stringBuilder.append("<").append(tagName).append(">")
                .append(value)
                .append("</").append(tagName).append(">");
        return stringBuilder;
    }


}
