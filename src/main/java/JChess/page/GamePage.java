package JChess.page;

import JChess.element.Position;
import JChess.enums.Result;
import JChess.enums.Selection;
import JChess.enums.Team;
import JChess.gui.GraphicsBoard;
import JChess.gui.PawnPromotionPanel;
import JChess.tool.Response;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Set;

public class GamePage extends Page implements MouseListener {

    private GraphicsBoard graphicsBoard;
    private Selection selectionState;

    public GamePage(PageManager pageManager) {
        super(pageManager);
        this.pageManager.getGame().initializeGame();
        this.graphicsBoard = new GraphicsBoard(this.pageManager.getGame().getBoard().getMap(),
                pageManager.getWidth(), pageManager.getHeight(), 100);
        this.selectionState = Selection.NO_SELECTED;
        addMouseListener(this);
    }

    public GraphicsBoard getGraphicsBoard () { return this.graphicsBoard; }

    private Position selectPosition (int x, int y) {
        int marginLeft = this.graphicsBoard.getMarginLeft(), marginUp = this.graphicsBoard.getMarginUp();
        int widthBoard = this.graphicsBoard.getWidthField(), heightBoard = this.graphicsBoard.getHeightField();
        if (x >= marginLeft && x <= marginLeft + 8 * widthBoard && y >= marginUp && y <= marginUp + 8 * heightBoard) {
            int i = (x - marginLeft) / widthBoard + 1;
            int j = (y - marginUp) / heightBoard + 1;
            return new Position(i, this.pageManager.getGame().getCurrentPlayer().getTeam() == Team.WHITE ? 9 - j : j);
        }
        return null;
    }

    private void showWinDialogWindow (Team team) {
        JOptionPane.showConfirmDialog(this, "Congratulations! \n " + team.toString() + " won!",
                "JChess", JOptionPane.OK_OPTION);
    }

    private void showDrawDialogWindow () {
        JOptionPane.showConfirmDialog(this, "Draw",
                "JChess", JOptionPane.OK_OPTION);
    }

    private void showCheckDialogWindow() {
            JOptionPane.showConfirmDialog(this,"You are checking!",
                    "JChess", JOptionPane.OK_OPTION);
    }

    private Response selectChessmen() {
        Response response = new Response();
        while (response.isEmpty()) {
            JDialog jDialog = new JDialog(pageManager, "JChess", true);
            jDialog.add(new PawnPromotionPanel(jDialog, this.pageManager.getGame().getCurrentPlayer()
                    .getTeam(), response));
            jDialog.setSize(500, 150);
            jDialog.setLocation(350, 350);
            jDialog.setVisible(true);
        }
        return response;
    }

    @Override
    public boolean exit () {
        if (pageManager.getGame().getResult() == Result.BLACK_WIN ||
                pageManager.getGame().getResult() == Result.WHITE_WIN ||
                pageManager.getGame().getResult() == Result.DRAW)
            return true;
        return JOptionPane.showConfirmDialog(this, "Are you sure you want to give up?",
                "JChess", JOptionPane.YES_NO_OPTION) == 0 ? true : false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            Position position = selectPosition(e.getPoint().x, e.getPoint().y);
            if (this.selectionState == Selection.NO_SELECTED) {
                Set<Position> availableMove = this.pageManager.getGame().getAvailableMoves(position);
                if (!availableMove.isEmpty()) {
                    this.graphicsBoard.setSelectedField(position);
                    this.graphicsBoard.setAdviceList(availableMove);
                    this.selectionState = Selection.SELECTED_CHESSMAN;
                }
            }
            else if (this.selectionState == Selection.SELECTED_CHESSMAN)
                if (this.graphicsBoard.getAdviceList().contains(position)) {
                    this.pageManager.getGame().move(this.graphicsBoard.getSelectedField(), position);
                    this.graphicsBoard.setSelectedField(null);
                    this.graphicsBoard.setAdviceList(null);
                    this.selectionState = Selection.SELECTED_MOVE;
                }

            if (this.selectionState == Selection.SELECTED_MOVE) {
                Position pawnPromotion = this.pageManager.getGame().checkPawnPromotion();
                if (pawnPromotion != null)
                    this.pageManager.getGame().makePawnPromotion(pawnPromotion, (Class) selectChessmen().getObj());
                this.pageManager.getGame().finishMove();
                this.selectionState = Selection.NO_SELECTED;

                if (this.pageManager.getGame().getResult() == Result.WHITE_WIN ||
                        this.pageManager.getGame().getResult() == Result.BLACK_WIN)
                    showWinDialogWindow(this.pageManager.getGame().getCurrentPlayer().getTeam());
                else if (this.pageManager.getGame().getResult() == Result.DRAW)
                    showDrawDialogWindow();
                else if (this.pageManager.getGame().getResult() == Result.BLACK_CHECK ||
                        this.pageManager.getGame().getResult() == Result.WHITE_CHECK)
                    showCheckDialogWindow();
            }
        }
        else {
            this.graphicsBoard.setSelectedField(null);
            this.graphicsBoard.setAdviceList(null);
            this.selectionState = Selection.NO_SELECTED;
        }

        this.graphicsBoard.setCurrentTeam(this.pageManager.getGame().getCurrentPlayer().getTeam());
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) { }

    @Override
    public void mouseReleased(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }

    @Override
    protected void paintComponent (Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics;
        this.graphicsBoard.drawBoard(g);
        this.graphicsBoard.drawAdvice(g);
        this.graphicsBoard.drawChessmen(g);
        this.graphicsBoard.selectField(g);
    }
}