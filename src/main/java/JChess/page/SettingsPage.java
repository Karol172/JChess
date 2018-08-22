package JChess.page;

import JChess.enums.PlayerSource;
import com.sun.java.swing.plaf.windows.WindowsBorders;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

public class SettingsPage extends Page implements ActionListener {
    private Set<String> messages;
    private JButton button;
    private JPanel messagesPanel;
    private JPanel rivalPanel;

    public SettingsPage(PageManager pageManager) {
        super(pageManager);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.button = new JButton("Set");
        this.button.addActionListener(this);
        this.messages = new HashSet<>();
        prepareRivalConfigurationPanel();
        addPanels();
    }

    private void addPanels () {
        add(rivalPanel);
        add(this.button);
        revalidate();
        repaint();
    }

    private void removePanels () {
        if (this.rivalPanel.getComponentCount() > 0)
            for (Component component: getComponents())
                remove(component);
        revalidate();
        repaint();
    }

    private void addMessages () {
        prepareMessagesPanel();
        removePanels();
        if (!messages.isEmpty())
            add(messagesPanel);
        addPanels();
    }

    private void prepareMessagesPanel () {
        this.messagesPanel = new JPanel();
        messagesPanel.setLayout(new GridLayout(messages.size(),1));
        messagesPanel.setVisible(true);
        messagesPanel.setMaximumSize(new Dimension(500,40 * messages.size()));
        messagesPanel.setBorder(BorderFactory.createTitledBorder(
                new WindowsBorders.DashedBorder(Color.BLACK), "Messages"));
        for (String message : messages)
            messagesPanel.add(new JLabel(message));
    }

    private void prepareRivalConfigurationPanel () {
        this.rivalPanel = new JPanel();
        rivalPanel.setLayout(new GridLayout(1,1));
        rivalPanel.setVisible(true);
        rivalPanel.setMaximumSize(new Dimension(500,50));
        rivalPanel.setBorder(BorderFactory.createTitledBorder(
                new WindowsBorders.DashedBorder(Color.BLACK), "Rival's Settings"));

        JComboBox<PlayerSource> playerComboBox = new JComboBox<>();
        playerComboBox.setName("playerComboBox");
        playerComboBox.addActionListener(this);
        playerComboBox.addItem(PlayerSource.USER);
        playerComboBox.addItem(PlayerSource.COMPUTER);
        playerComboBox.addItem(PlayerSource.NETWORK);
        playerComboBox.setSelectedItem(this.pageManager.getGame().getConfiguration().getPlayerSource());

        JLabel playerComboBoxLabel = new JLabel("Select rival's source: ");
        playerComboBoxLabel.setName("playerComboBoxLabel");

        rivalPanel.add(playerComboBoxLabel);
        rivalPanel.add(playerComboBox);
    }

    private void validateSettings () {
        this.messages.clear();
    }

    private void changeConfiguration () {
        setRival();
        this.pageManager.back();
    }

    private void setRival() {
        JComboBox playerComboBox = (JComboBox)getComponent(rivalPanel.getComponents(), "playerComboBox");
        this.pageManager.getGame().getConfiguration().setPlayerSource((PlayerSource) playerComboBox.getSelectedItem());
    }

    private Component getComponent (Component[] components, String name) {
        if (components != null && name != null)
            for (Component component : components) {
                if (component.getName() != null && component.getName().equals(name))
                    return component;
            }
        return null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == this.button) {
            validateSettings();
            if (this.messages.isEmpty())
                changeConfiguration();
            else
                addMessages();
        }
    }
}