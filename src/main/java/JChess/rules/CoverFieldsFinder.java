package JChess.rules;

import JChess.chessman.Chessman;
import JChess.element.CoverFields;
import JChess.element.Place;
import JChess.element.Position;
import JChess.enums.Move;

import java.util.Map;
import java.util.Set;

public class CoverFieldsFinder {

    private Map<Position, Place> board;
    private Chessman me;

    public CoverFieldsFinder(Map<Position, Place> board, Chessman me) {
        this.board = board;
        this.me = me;
    }

    public void findDiagonallyToLeftDownCorner (int i, int j, int k, int maxMoves) {
        CoverFields protectedFields = new CoverFields(Move.DiagonallyLeftDown);
        Chessman chessman = this.board.get(new Position(i, j)).getChessman();

        if (this.me.getTeam() != chessman.getTeam())
            for (--i, --j, ++k; i >= 1 && j >= 1 && k < maxMoves; i--, j--, k++)
                if (!check(protectedFields.getProtectedFields(), new Position(i,j)))
                    break;
        chessman.addProtectedFields(protectedFields);
    }

    public void findDiagonallyToLeftUpCorner (int i, int j, int k, int maxMoves) {
        CoverFields protectedFields = new CoverFields(Move.DiagonallyLeftUp);
        Chessman chessman = this.board.get(new Position(i, j)).getChessman();

        if (this.me.getTeam() != chessman.getTeam())
            for (--i, ++j, ++k; i >= 1 && j <= 8 && k < maxMoves; i--, j++, k++)
            if (!check(protectedFields.getProtectedFields(), new Position(i,j)))
                break;
        chessman.addProtectedFields(protectedFields);
    }

    public void findDiagonallyToRightUpCorner (int i, int j, int k, int maxMoves) {
        CoverFields protectedFields = new CoverFields(Move.DiagonallyRightUp);
        Chessman chessman = this.board.get(new Position(i, j)).getChessman();

        if (this.me.getTeam() != chessman.getTeam())
            for (++i, ++j, ++k; i <= 8 && j <= 8 && k < maxMoves; i++, j++, k++)
            if (!check(protectedFields.getProtectedFields(), new Position(i,j)))
                break;
        chessman.addProtectedFields(protectedFields);
    }

    public void findDiagonallyToRightDownCorner (int i, int j, int k, int maxMoves) {
        CoverFields protectedFields = new CoverFields(Move.DiagonallyRightDown);
        Chessman chessman = this.board.get(new Position(i, j)).getChessman();

        if (this.me.getTeam() != chessman.getTeam())
            for (++i, --j, ++k; i <= 8 && j >= 1 && k < maxMoves; i++, j--, k++)
            if (!check(protectedFields.getProtectedFields(), new Position(i,j)))
                break;
        chessman.addProtectedFields(protectedFields);
    }

    public void findStraightUp (int i, int j, int k, int maxMoves) {
        CoverFields protectedFields = new CoverFields(Move.StraightUp);
        Chessman chessman = this.board.get(new Position(i, j)).getChessman();

        if (this.me.getTeam() != chessman.getTeam())
            for (++j, ++k; j <= 8 && k < maxMoves; j++, k++)
            if (!check(protectedFields.getProtectedFields(), new Position(i, j)))
                break;
        chessman.addProtectedFields(protectedFields);
    }

    public void findStraightDown (int i, int j, int k, int maxMoves) {
        CoverFields protectedFields = new CoverFields(Move.StraightDown);
        Chessman chessman = this.board.get(new Position(i, j)).getChessman();

        if (this.me.getTeam() != chessman.getTeam())
            for (--j, ++k; j >= 1 && k < maxMoves; j--, k++)
            if (!check(protectedFields.getProtectedFields(), new Position(i, j)))
                break;
        chessman.addProtectedFields(protectedFields);
    }

    public void findStraightLeft (int i, int j, int k, int maxMoves) {
        CoverFields protectedFields = new CoverFields(Move.StraightLeft);
        Chessman chessman = this.board.get(new Position(i, j)).getChessman();

        if (this.me.getTeam() != chessman.getTeam())
            for (--i, ++k; i >= 1 && k < maxMoves; i--, k++)
            if (!check(protectedFields.getProtectedFields(), new Position(i, j)))
                break;
        chessman.addProtectedFields(protectedFields);
    }

    public void findStraightRight (int i, int j, int k, int maxMoves) {
        CoverFields protectedFields = new CoverFields(Move.StraightRight);
        Chessman chessman = this.board.get(new Position(i, j)).getChessman();

        if (this.me.getTeam() != chessman.getTeam())
            for (++i, ++k; i <= 8 && k < maxMoves; i++, k++)
            if (!check(protectedFields.getProtectedFields(), new Position(i, j)))
                break;
        chessman.addProtectedFields(protectedFields);
    }

    private boolean check (Set<Position> fields, Position position) {
        if (board.get(position).getChessman() == null) {
            fields.add(position);
            return true;
        }
        else if (board.get(position).getChessman() != null
                && board.get(position).getChessman().getTeam() != this.me.getTeam())
            fields.add(position);
        return false;
    }
}