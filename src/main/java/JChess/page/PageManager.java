package JChess.page;

import JChess.game.Game;
import JChess.page.listeners.ClosingWindowListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Stack;

public class PageManager extends JFrame implements KeyListener {
    private Page displayingPage;
    protected Game game;
    private Stack<Page> stack;

    public PageManager() {
        super("JChess");
        pack();
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setVisible(true);
        setSize(new Dimension(800, 600));
        setLocation(50, 50);
        setFocusable(true);
        this.game = new Game();
        this.stack = new Stack<>();
        this.stack.add(new MainPage(this));
        displayingPage = this.stack.pop();
        addKeyListener(this);
        addWindowListener(new ClosingWindowListener());
        add(displayingPage);
    }

    public Game getGame() { return this.game; }

    public Page getDisplayingPage() { return this.displayingPage; }

    public boolean back() {
        if (!stack.empty() && this.displayingPage.exit()) {
            remove(this.displayingPage);
            displayingPage = stack.pop();
            add(displayingPage);
            repaint();
            revalidate();
            return true;
        }
        return false;
    }

    public boolean display(Page page) {
        if (page == null)
            return false;
        remove(displayingPage);
        this.stack.push(displayingPage);
        this.displayingPage = page;
        add(page);
        page.validate();
        page.repaint();
        validate();
        repaint();
        return true;
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) { }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            back();
    }
}