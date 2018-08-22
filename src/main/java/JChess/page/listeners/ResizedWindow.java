package JChess.page.listeners;

import JChess.page.GamePage;
import JChess.page.MainPage;
import JChess.page.Page;
import JChess.page.SettingsPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.LinkedList;

public class ResizedWindow implements ComponentListener {

    private void resizedPage (Page page) {
        if (page.getPageManager().getHeight() < 400)
            page.getPageManager().setSize(page.getPageManager().getWidth(), 400);

        if (page.getPageManager().getWidth() < 500)
            page.getPageManager().setSize(500, page.getPageManager().getHeight());

        page.getPageManager().revalidate();
        page.getPageManager().repaint();
    }

    private void resizedMainPage (MainPage page) {
        LinkedList<JButton> buttonQueue = new LinkedList<>();
        for (Component component: page.getComponents()) {
            if (component.getClass() == JButton.class)
                buttonQueue.addLast((JButton)component);
            page.remove(component);
        }
        for (int i = 0; i < buttonQueue.size(); i++) {
            page.add(page.createFiller(page.getPageManager().getHeight()));
            page.add(page.createButtonFiller(buttonQueue.get(i),
                    page.getPageManager().getWidth(), page.getPageManager().getHeight()));
        }
        resizedPage(page);
    }

    private void resizedGamePage (GamePage page) {
        page.getGraphicsBoard().setMarginRight(page.getPageManager().getWidth() / 8);
        page.getGraphicsBoard().setSize(page.getPageManager().getWidth(), page.getPageManager().getHeight());
        resizedPage(page);
    }

    private void resizedSettingsPage (SettingsPage page) { resizedPage(page); }

    @Override
    public void componentResized(ComponentEvent e) {
        Object source = e.getSource();
        if (source.getClass() == MainPage.class)
            resizedMainPage((MainPage) source);
        else if (source.getClass() == GamePage.class)
            resizedGamePage((GamePage) source);
        else if (source.getClass() == SettingsPage.class)
            resizedSettingsPage((SettingsPage) source);
    }

    @Override
    public void componentMoved(ComponentEvent e) { }

    @Override
    public void componentShown(ComponentEvent e) { }

    @Override
    public void componentHidden(ComponentEvent e) { }
}