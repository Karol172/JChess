package JChess.board;

import JChess.chessman.Chessman;
import JChess.element.Place;
import JChess.element.Position;

import java.util.Map;
import java.util.TreeMap;

public class Board {

    private Map<Position, Place> board;

    public Board () {
        this.board = new TreeMap<>();
        createBoard();
    }

    public Map<Position, Place> getMap () {
        return board;
    }

    public boolean insertChessman (Chessman chessman, Position position){
        if (this.board.get(position).getChessman() != null)
            return false;
        this.board.get(position).setChessman(chessman);
        return true;
    }

    public boolean replaceChessman (Position sourcePosition, Position destinationPosition){
        Chessman chessman = this.board.get(sourcePosition).getChessman();
        if (chessman == null)
            return false;
        this.board.get(sourcePosition).removeChessman();
        this.board.get(destinationPosition).removeChessman();
        this.board.get(destinationPosition).setChessman(chessman);
        return true;
    }

    public void replaceChessman (Position position, Chessman chessman){
        this.board.get(chessman.getPosition()).removeChessman();
        this.board.get(position).removeChessman();
        this.board.get(position).setChessman(chessman);
    }

    private void createBoard () {
        for (int j = 1; j <= 8 ; j++)
            for (int i = 1; i <= 8; i++) {
                Position position = new Position(i, j);
                this.board.put(position, new Place(null, position));
            }
    }
}