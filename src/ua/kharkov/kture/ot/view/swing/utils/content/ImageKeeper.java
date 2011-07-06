package ua.kharkov.kture.ot.view.swing.utils.content;

import ua.kharkov.kture.ot.common.math.Logic;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.io.Resources.getResource;


//TODO: [stas] don't make it static
//TODO: refactring needed
public class ImageKeeper {

    private final String imgPath = "";

    private int id = 1;

    private Map<Logic, Map<Boolean, Image>> images = new HashMap<Logic, Map<Boolean, Image>>();

    public final static ImageKeeper INSTANCE = new ImageKeeper();

    private ImageKeeper() {
        images.put(Logic.AND, new HashMap<Boolean, Image>() {
            /**
             *
             */
            private static final long serialVersionUID = 1L;

            {
                this.put(Boolean.FALSE, loadImage(imgPath + "AND.png"));
                this.put(Boolean.TRUE, loadImage(imgPath + "ANDM.png"));
            }
        });

        images.put(Logic.OR, new HashMap<Boolean, Image>() {
            /**
             *
             */
            private static final long serialVersionUID = 1L;

            {
                this.put(Boolean.FALSE, loadImage(imgPath + "OR.png"));
                this.put(Boolean.TRUE, loadImage(imgPath + "ORM.png"));
            }
        });

        images.put(Logic.NOT, new HashMap<Boolean, Image>() {
            /**
             *
             */
            private static final long serialVersionUID = 1L;

            {
                this.put(Boolean.FALSE, loadImage(imgPath + "NOT.png"));
                this.put(Boolean.TRUE, loadImage(imgPath + "NOTM.png"));
            }
        });

        images.put(Logic.AND_WITH_ORDER, new HashMap<Boolean, Image>() {
            /**
             *
             */
            private static final long serialVersionUID = 1L;

            {
                this.put(Boolean.FALSE, loadImage(imgPath + "ANDP.png"));
                this.put(Boolean.TRUE, loadImage(imgPath + "ANDPM.png"));
            }
        });
    }

    private Image loadImage(String path) {
        Toolkit tool = Toolkit.getDefaultToolkit();
        String r = "images/" + path;

        java.net.URL imageURL = getResource(r);
        Image img = tool.getImage(imageURL);
        try {
            MediaTracker mTracker = new MediaTracker(new JPanel());
            mTracker.addImage(img, id);
            mTracker.waitForID(id++);
            return img;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Image getLogicElementImage(Logic type, int multy) {
        return images.get(type).get(multy > 1);
    }

}
