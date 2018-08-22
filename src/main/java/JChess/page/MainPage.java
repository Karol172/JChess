package JChess.page;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPage extends Page implements ActionListener {

    private JButton gameButton;
    private JButton settingsButton;
    private JButton exitButton;

    public MainPage(PageManager pageManager) {
        super(pageManager);

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.gameButton = new JButton("Start");
        this.settingsButton = new JButton("Settings");
        this.exitButton = new JButton("Exit");

        this.gameButton.addActionListener(this);
        this.settingsButton.addActionListener(this);
        this.exitButton.addActionListener(this);

        this.gameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.settingsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        createButtonFiller(this.gameButton, pageManager.getWidth(), pageManager.getHeight());
        createButtonFiller(this.settingsButton, pageManager.getWidth(), pageManager.getHeight());
        createButtonFiller(this.exitButton, pageManager.getWidth(), pageManager.getHeight());

        add(createFiller(pageManager.getHeight()));
        add(gameButton);
        add(createFiller(pageManager.getHeight()));
        add(settingsButton);
        add(createFiller(pageManager.getHeight()));
        add(exitButton);
    }

    public JButton createButtonFiller (JButton button, int width, int height) {
        if (button != null)
            if (button.getComponents().length == 1) {
                Box.Filler filler = (Box.Filler) button.getComponents()[0];
                button.remove(filler);
            }
            button.add(Box.createRigidArea(new Dimension((int) (0.75 * width), (int) (0.085 * height))));
        return button;
    }

    public Component createFiller (int height) { return Box.createVerticalStrut((int)(0.125*height)); }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == gameButton)
            pageManager.display(new GamePage(pageManager));
        else if (source == settingsButton)
            pageManager.display(new SettingsPage(pageManager));
        else if (source == exitButton)
            java.awt.Toolkit.getDefaultToolkit().getSystemEventQueue()
                    .postEvent(new java.awt.event.WindowEvent(pageManager, java.awt.event.WindowEvent.WINDOW_CLOSING));
    }
}