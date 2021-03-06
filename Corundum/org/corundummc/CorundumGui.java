package org.corundummc;

import javax.swing.*;
import java.awt.*;

/** The GUI of Corundum. */
public class CorundumGui {
    private JFrame frame;
    private CorundumServer corundumServer;

    public CorundumGui() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // I know it's bad practice to just catch Exception e, but there's a LOT of possibly thrown exceptions.
            e.printStackTrace();
        }

        corundumServer = CorundumServer.getInstance();
        frame = new JFrame("Corundum Server");
        addComponents();
        frame.pack();
        frame.setLocationRelativeTo((Component) null);
        frame.setVisible(true);
        addWindowListener();
    }

    public void addComponents() {
        initLogComponent();
        // frame.add(...)
        // Some components will have listeners.
    }

    /** Initialises the log. */
    public void initLogComponent() {
        // TODO
    }

    /** Adds a {@link java.awt.event.WindowAdapter} to frame via {@link JFrame#addWindowListener(java.awt.event.WindowListener)} */
    public void addWindowListener() {
        // TODO
    }

    /** Logs something to the GUI's log. */
    public void log() {
        // TODO
    }
}
