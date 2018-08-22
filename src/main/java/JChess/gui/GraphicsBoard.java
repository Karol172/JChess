package JChess.gui;

import JChess.element.Place;
import JChess.element.Position;
import JChess.enums.Team;

import java.awt.*;
import java.util.Map;
import java.util.Set;

public class GraphicsBoard {

    private int widthField;
    private int heightField;
    private int labelSize1;
    private int labelSize2;
    private int marginLeft;
    private int marginRight;
    private int marginUp;
    private IconHelper iconHelper;
    private Position selectedField;
    private Team currentTeam;
    private Map<Position, Place> board;
    private Set<Position> adviceList;

    public GraphicsBoard(Map<Position, Place> board, int widthMap, int heightMap, int rightMargin) {
        this.iconHelper = new IconHelper();
        this.board = board;
        this.currentTeam = Team.WHITE;
        this.marginRight = rightMargin;
        this.labelSize1 = 26;
        this.labelSize2 = 26;
        int x = widthMap - rightMargin - 2 * labelSize1 <= heightMap - 4 * labelSize2 ?
                widthMap - rightMargin - 2 * labelSize1 : heightMap - 4 * labelSize2;
        x /= 8;
        this.widthField = x;
        this.heightField = x;
        this.marginLeft = (widthMap - 2 * labelSize1 - 8 * widthField - rightMargin) / 2;
        this.marginUp = (heightMap - 3 * labelSize2 - 8 * heightField) / 2;
    }

    public void setCurrentTeam (Team team) { this.currentTeam = team; }

    public void setSize (int x, int y) {
        int temp = x - marginRight - 2* labelSize1 <= y - 4 * labelSize2 ?
                x - marginRight - 2 * labelSize1 : y - 4 * labelSize2;
        temp /= 8;
        this.widthField = temp;
        this.heightField = temp;
        this.marginLeft = (x - 2 * labelSize1 - 8 * widthField - marginRight) / 2;
        this.marginUp = (y - 3 * labelSize2 - 8 * heightField) / 2;
    }

    public int getWidthField() { return widthField; }

    public int getHeightField() { return heightField; }

    public int getMarginLeft() { return labelSize1 + marginLeft; }

    public int getMarginUp() { return labelSize2 + marginUp; }

    public void setMarginRight (int marginRight) { this.marginRight = marginRight; }

    public void setSelectedField (Position position) { this.selectedField = position; }

    public Position getSelectedField () { return this.selectedField; }

    public void setAdviceList(Set<Position> adviceList) { this.adviceList = adviceList; }

    public Set<Position> getAdviceList() { return this.adviceList; }

    public void drawChessmen (Graphics2D graphics) {
        for (int j = 0; j < 8; j++)
            for (int i = 0; i < 8; i++) {
            Position position = new Position(i+1, j+1);
            if (board.get(position).getChessman() != null)
                graphics.drawImage(iconHelper.selectIcon(board.get(position).getChessman()),
                        marginLeft + labelSize1 + i * widthField, currentTeam == Team.WHITE ?
                                marginUp + labelSize2 + (7 - j) * heightField :
                                marginUp + labelSize2 + j * heightField, widthField, heightField, null);
            }
    }

    public void selectField (Graphics2D graphics) {
        if (selectedField != null)
            graphics.drawOval(marginLeft + labelSize1 + (selectedField.getFile() - 1) * widthField,
                    currentTeam == Team.WHITE ? marginUp + labelSize2 + (8 - selectedField.getRank() ) * heightField
                            : marginUp + labelSize2 + (selectedField.getRank() - 1 ) * heightField,
                    widthField, heightField);
    }

    public void drawAdvice(Graphics2D graphics) {
        if (adviceList != null)
            for (Position position: adviceList)
                graphics.drawImage(iconHelper.downloadImage("advice.png"),
                        marginLeft + labelSize1 + (position.getFile() - 1) * widthField,
                        currentTeam == Team.WHITE ? marginUp + labelSize2 + (8 - position.getRank() ) * heightField :
                                marginUp + labelSize2 + (position.getRank() - 1 ) * heightField,
                        widthField, heightField, null);
    }

    public void drawBoard(Graphics2D graphics) {
        Color[] colors = getColor();
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                if (j % 2 == i % 2)
                    graphics.setColor(colors[0]);
                else
                    graphics.setColor(colors[1]);
                graphics.fillRect(marginLeft + labelSize1 + j * widthField,
                        marginUp + labelSize2 + i * heightField, widthField, heightField);
            }
        graphics.setColor(Color.BLACK);
        graphics.drawRect(marginLeft,marginUp, 2 * labelSize1 + 8 * widthField,
                2 * labelSize2 + 8 * heightField);
        for (int i = 0; i < 9; i++) {
            graphics.drawLine(marginLeft + labelSize1 + i * widthField, marginUp,
                    marginLeft + labelSize1 + i * widthField, marginUp + 2 * labelSize2 + 8 * heightField);
            graphics.drawLine(marginLeft, marginUp + labelSize1 + i * widthField,
                    marginLeft + 2 * labelSize1 + 8 * widthField, marginUp + labelSize2 + i * widthField);
        }
        for (int i = 0; i < 8; i++) {
            graphics.drawString(getText('A', i),
                    marginLeft + labelSize1 + i * widthField + widthField / 2, marginUp + labelSize2 / 2 + 5);
            graphics.drawString(getText('A', i), marginLeft + labelSize1 + i * widthField + widthField / 2,
                    marginUp + labelSize2 + 8 * heightField + labelSize2 / 2 + 5);
            graphics.drawString(getText('1', i),
                    marginLeft + labelSize1 / 2 - 5, marginUp + labelSize2 + i * heightField + heightField / 2);
            graphics.drawString(getText('1', i), marginLeft + labelSize1 + 8 * widthField + labelSize1 / 2 - 5,
                    marginUp + labelSize2 + i * heightField + heightField / 2);
        }
    }

    private String getText(char symbol, int count) {
        if (symbol == 'A')
            return Character.valueOf((char) ('A' + count)).toString();
        else if (symbol == '1')
            return currentTeam == Team.WHITE ? Character.valueOf((char) ('8' - count)).toString() :
                    Character.valueOf((char) ('1' + count)).toString();
        return "";
    }

    private Color[] getColor() {
        Color[] colors = {Color.GRAY, Color.WHITE};
        if (this.currentTeam == Team.BLACK) {
            Color temp = colors[0];
            colors[0] = colors[1];
            colors[1] = temp;
        }
        return colors;
    }
}