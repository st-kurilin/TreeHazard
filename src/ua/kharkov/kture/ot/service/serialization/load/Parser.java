package ua.kharkov.kture.ot.service.serialization.load;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Sets;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ua.kharkov.kture.ot.common.Money;
import ua.kharkov.kture.ot.common.math.Logic;
import ua.kharkov.kture.ot.common.math.Point;
import ua.kharkov.kture.ot.common.math.Probability;
import ua.kharkov.kture.ot.service.serialization.load.beans.ComponentBean;
import ua.kharkov.kture.ot.service.serialization.load.beans.FigureBean;
import ua.kharkov.kture.ot.service.serialization.load.beans.HazardBean;
import ua.kharkov.kture.ot.shared.VariantVO;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.AbstractList;
import java.util.Set;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.Collections2.filter;
import static com.google.common.collect.Collections2.transform;
import static java.lang.Boolean.parseBoolean;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static ua.kharkov.kture.ot.common.Money.parseMoney;

/**
 * @author: Stanislav Kurilin
 * @mail: st dot kurilin at gmail dot com
 * Date: 20.04.11
 */
//TODO: [stas] extract interface
public class Parser {
    public HazardBean parseFile(String fileName) throws IOException {
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(new FileInputStream(fileName));
            return parseHazards(document.getFirstChild());
        } catch (Throwable e) {
            IOException exception = new IOException("error while file parsing");
            exception.initCause(e);
            throw exception;
        }
    }


    private HazardBean parseHazards(Node node) {
        HazardBean current = new HazardBean();
        current.setTitle(parseAttr(node, "title"));
        current.setLocation(retrieveLocation(node));
        current.setTwins(parseInt(safeParse(parseAttr(node, "twins"), "1")));
        current.setLogic(Logic.valueOf(parseAttr(node, "logic")));
        current.setChilds(transform(filter(new NodeListWrapper(node.getChildNodes()), new Predicate<Node>() {
            @Override
            public boolean apply(Node input) {
                return input.getNodeName().equals("component") || input.getNodeName().equals("hazard");
            }
        }), new Function<Node, FigureBean>() {
            @Override
            public FigureBean apply(Node input) {
                if (input.getNodeName().equals("hazard")) {
                    return parseHazards(input);
                }
                if (input.getNodeName().equals("component")) {
                    return parseComponent(input);
                }
                //TODO: [stas] change to AssertionError after xml validation provided
                throw new RuntimeException("file corrupted");
            }
        }));
        return current;
    }

    private ComponentBean parseComponent(Node node) {
        ComponentBean current = new ComponentBean();
        current.setTitle(parseAttr(node, "title"));
        current.setLocation(retrieveLocation(node));
        current.setBrokenEventTitle(parseAttr(node, "brokenEventTitle"));
        Node proposal = getChildNodeByName(node, "proposal");
        final Set<VariantVO> bases = Sets.newHashSet();
        final Set<VariantVO> variants = Sets.newHashSet();
        if (proposal != null) {
            NodeListWrapper nodeList = new NodeListWrapper(proposal.getChildNodes());
            for (Node input : nodeList) {
                String title = parseAttr(input, "title");
                Boolean base = parseBoolean(safeParse(parseAttr(input, "base"), "false"));
                Money money = parseMoney(safeParse(parseAttr(input, "cost"), "1"));
                Double probabilityS = parseDouble(safeParse(parseAttr(input, "crashProbability"), "1"));
                Probability probability = Probability.commonForm(probabilityS);
                VariantVO toRet = new VariantVO(title, probability, money);
                if (base) {
                    bases.add(toRet);
                }
                variants.add(toRet);
            }
        }
        current.setVariants(variants);
        checkState(bases.size() <= 1);
        if (!bases.isEmpty()) {
            current.setBase(bases.iterator().next());
        }
        if (!bases.isEmpty()) {
            current.setBase(bases.iterator().next());
        }
        return current;
    }

    private Node getChildNodeByName(Node node, String nodeName) {
        for (Node children : new NodeListWrapper(node.getChildNodes())) {
            if (children.getNodeName().endsWith(nodeName)) {
                return children;
            }
        }
        //TODO: [stas] change to AssertionError after xml validation provided
        throw new RuntimeException("file corrupted");
    }

    private Point retrieveLocation(Node node) {
        Node locationNode = getChildNodeByName(node, "location");
        int x = parseInt(parseAttr(locationNode, "x"));
        int y = parseInt(parseAttr(locationNode, "y"));
        return new Point(x, y);
    }

    private String safeParse(String val, String def) {
        return val == null ? def : val;
    }

    //TODO: [stas] I think there should be other way for this operation
    private String parseAttr(Node node, String key) {
        NamedNodeMap attr = node.getAttributes();
        if (node.hasAttributes()) {
            for (int i = 0; i < attr.getLength(); i++) {
                String currentNodeName = attr.item(i).getNodeName();
                if (currentNodeName.equals(key)) {
                    return attr.item(i).getNodeValue();
                }
            }
        }
        return null;
    }

    private static class NodeListWrapper extends AbstractList<Node> {
        private final NodeList target;

        public NodeListWrapper(NodeList target) {
            this.target = target;
        }

        @Override
        public Node get(int index) {
            return target.item(index);
        }

        @Override
        public int size() {
            return target.getLength();
        }
    }
}
