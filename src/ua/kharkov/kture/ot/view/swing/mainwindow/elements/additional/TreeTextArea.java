package ua.kharkov.kture.ot.view.swing.mainwindow.elements.additional;

import ua.kharkov.kture.ot.view.swing.utils.content.ConstantKeeper;

import javax.swing.*;
import java.awt.*;

public class TreeTextArea extends JTextArea {

    public TreeTextArea() {
        super(3, 10);
        setFont(new Font("Serif", 0, 10));
        setLineWrap(true);
        setWrapStyleWord(true);
        getBounds().setSize(ConstantKeeper.TEXT_SIZE.getWidth(), ConstantKeeper.TEXT_SIZE.getHeight());
    }

}
