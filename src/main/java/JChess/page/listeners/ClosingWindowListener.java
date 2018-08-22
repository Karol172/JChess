package JChess.page.listeners;

import JChess.page.PageManager;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ClosingWindowListener extends WindowAdapter {
    @Override
    public void windowClosing(WindowEvent e) {
        Object source = e.getSource();

        if (source.getClass() == PageManager.class) {
            PageManager pageManager = (PageManager) source;
            if (pageManager.getDisplayingPage().exit())
                pageManager.dispose();
        }
    }
}