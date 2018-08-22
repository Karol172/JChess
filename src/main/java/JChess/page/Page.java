package JChess.page;

import JChess.page.listeners.ResizedWindow;

import javax.swing.*;

public abstract class Page extends JPanel {

    protected PageManager pageManager;

    public Page (PageManager pageManager) {
        this.pageManager = pageManager;
        this.addComponentListener(new ResizedWindow());
    }

    public PageManager getPageManager () { return this.pageManager; }

    public boolean exit () {
        return true;
    }
}
