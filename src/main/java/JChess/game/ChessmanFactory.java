package JChess.game;

import JChess.chessman.*;
import JChess.element.Position;
import JChess.enums.Team;

public class ChessmanFactory {
    public static Chessman createChessman(Class chessmanClass, Team team, Position position) {
        if (chessmanClass == Bishop.class)
            return new Bishop(team, position);
        else if (chessmanClass == King.class)
            return new King(team, position);
        else if (chessmanClass == KNight.class)
            return new KNight(team, position);
        else if (chessmanClass == Pawn.class)
            return new Pawn(team, position);
        else if (chessmanClass == Queen.class)
            return new Queen(team, position);
        else if (chessmanClass == Rook.class)
            return new Rook(team, position);
        return null;
    }
}
