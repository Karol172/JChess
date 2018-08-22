package JChess.rules;

import JChess.chessman.Chessman;
import JChess.chessman.King;
import JChess.element.Place;
import JChess.element.Position;
import JChess.enums.Team;
import JChess.game.Player;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class NormalMoves implements Rules {
    protected Map<Position, Place> board;
    protected Set<Position> endengeredPositions;
    protected Chessman me;
    protected Player player;

    public NormalMoves(Map<Position, Place> board, Position currentPosition,
                       Set<Position> endengeredPositions, Player player) {
        this.board = board;
        this.endengeredPositions = endengeredPositions;
        this.me = board.get(currentPosition).getChessman();
        this.player = player;
    }

    @Override
    public Player getPlayer() { return this.player; }

    @Override
    public Set<Position> getEndengeredPositions () { return this.endengeredPositions; }

    @Override
    public Map<Position, Place> getBoard () { return this.board; }

    @Override
    public Set<Position> moveDiagonallyToLeftDownCorner (int maxMoves) {
        Set<Position> availableMove = new TreeSet<>();
        for (int i = this.me.getPosition().getFile() - 1, j = this.me.getPosition().getRank() - 1, k = 0;
             i >= 1 && j >= 1 && k < maxMoves; i--, j--, k++)
            if (isInRange(i,j) && !check(availableMove, new Position(i,j))) {
                new CoverFieldsFinder(this.board, this.me).findDiagonallyToLeftDownCorner(i, j, k, maxMoves);
                break;
            }
        return checkIfKing(availableMove, endengeredPositions);
    }

    @Override
    public Set<Position> moveDiagonallyToLeftUpCorner (int maxMoves) {
        Set<Position> availableMove = new TreeSet<>();
        for (int i = this.me.getPosition().getFile() - 1, j = this.me.getPosition().getRank() + 1, k = 0;
             i >= 1 && j <= 8 && k < maxMoves; i--, j++, k++)
            if (isInRange(i,j) && !check(availableMove, new Position(i,j))) {
                new CoverFieldsFinder(this.board, this.me).findDiagonallyToLeftUpCorner(i, j, k, maxMoves);
                break;
            }
        return checkIfKing(availableMove, endengeredPositions);
    }

    @Override
    public Set<Position> moveDiagonallyToRightUpCorner (int maxMoves) {
        Set<Position> availableMove = new TreeSet<>();
        for (int i = this.me.getPosition().getFile() + 1, j = this.me.getPosition().getRank() + 1, k = 0;
             i <= 8 && j <= 8 && k < maxMoves; i++, j++, k++)
            if (isInRange(i,j) && !check(availableMove, new Position(i,j))){
                new CoverFieldsFinder(this.board, this.me).findDiagonallyToRightUpCorner(i, j, k, maxMoves);
                break;
            }
        return checkIfKing(availableMove, endengeredPositions);
    }

    @Override
    public Set<Position> moveDiagonallyToRightDownCorner (int maxMoves) {
        Set<Position> availableMove = new TreeSet<>();
        for (int i = this.me.getPosition().getFile() + 1, j = this.me.getPosition().getRank() - 1, k = 0;
             i <= 8 && j >= 1 && k < maxMoves; i++, j--, k++)
            if (isInRange(i,j) && !check(availableMove, new Position(i,j))){
                new CoverFieldsFinder(this.board, this.me).findDiagonallyToRightDownCorner(i, j, k, maxMoves);
                break;
            }
        return checkIfKing(availableMove, endengeredPositions);
    }

    @Override
    public Set<Position> moveStraightUp (int maxMoves) {
        Set<Position> availableMove = new TreeSet<>();
        for (int j = this.me.getPosition().getRank() + 1, k = 0, i = this.me.getPosition().getFile();
             j <= 8 && k < maxMoves; j++, k++)
            if (isInRange(i,j) && !check(availableMove, new Position(i, j))){
                new CoverFieldsFinder(this.board, this.me).findStraightUp(i, j, k, maxMoves);
                break;
            }
        return checkIfKing(availableMove, endengeredPositions);
    }

    @Override
    public Set<Position> moveStraightDown (int maxMoves) {
        Set<Position> availableMove = new TreeSet<>();
        for (int j = this.me.getPosition().getRank() - 1, k = 0, i = this.me.getPosition().getFile();
             j >= 1 && k < maxMoves; j--, k++)
            if (isInRange(i,j) && !check(availableMove, new Position(i,j))){
                new CoverFieldsFinder(this.board, this.me).findStraightDown(i, j, k, maxMoves);
                break;
            }
        return checkIfKing(availableMove, endengeredPositions);
    }

    @Override
    public Set<Position> moveStraightLeft (int maxMoves) {
        Set<Position> availableMove = new TreeSet<>();
        for (int i = this.me.getPosition().getFile() - 1, k = 0, j = this.me.getPosition().getRank();
             i >= 1 && k < maxMoves; i--, k++)
            if (isInRange(i,j) && !check(availableMove, new Position(i,j))){
                new CoverFieldsFinder(this.board, this.me).findStraightLeft(i, j, k, maxMoves);
                break;
            }
        return checkIfKing(availableMove, endengeredPositions);
    }

    @Override
    public Set<Position> moveStraightRight (int maxMoves) {
        Set<Position> availableMove = new TreeSet<>();
        for (int i = this.me.getPosition().getFile() + 1, k = 0, j = this.me.getPosition().getRank();
             i <= 8 && k < maxMoves; i++, k++)
            if (isInRange(i,j) && !check(availableMove, new Position(i, j))){
                new CoverFieldsFinder(this.board, this.me).findStraightRight(i, j, k, maxMoves);
                break;
            }
        return checkIfKing(availableMove, endengeredPositions);
    }

    @Override
    public Set<Position> moveLikeKNight () {
        Set<Position> availableMove = new TreeSet<>();
        int[][] multipliers = {{-1, -1}, {-1, 1}, {1, 1}, {1, -1}};

        for (int[] tab: multipliers) {
            int file1 = this.me.getPosition().getFile() + 1*tab[0], rank1 = this.me.getPosition().getRank() + 2*tab[1];
            int file2 = this.me.getPosition().getFile() + 2*tab[0], rank2 = this.me.getPosition().getRank() + 1*tab[1];
            if (isInRange(file1, rank1))
                check(availableMove, new Position(file1, rank1));
            if (isInRange(file2, rank2))
                check(availableMove, new Position(file2, rank2));
        }
        return checkIfKing(availableMove, endengeredPositions);
    }

    @Override
    public Set<Position> captureLikePawnOnLeft (Team team) {
        int file = this.me.getPosition().getFile();
        int rank = this.me.getPosition().getRank();

        if (team == Team.WHITE && isInRange(file - 1, rank + 1))
            return this.findChessmanToCapture(team, new Position(file - 1, rank + 1));
        else if (team == Team.BLACK && isInRange(file + 1, rank - 1))
            return this.findChessmanToCapture(team, new Position(file + 1, rank - 1));
        return new TreeSet<>();
    }

    @Override
    public Set<Position> captureLikePawnOnRight (Team team) {
        int file = this.me.getPosition().getFile();
        int rank = this.me.getPosition().getRank();

        if (team == Team.WHITE && isInRange(file + 1, rank + 1))
            return this.findChessmanToCapture(team, new Position(file + 1, rank + 1));
        else if (team == Team.BLACK && isInRange(file - 1, rank - 1))
            return this.findChessmanToCapture(team, new Position(file - 1, rank - 1));
        return new TreeSet<>();
    }

    protected Set<Position> findChessmanToCapture(Team team, Position position) {
        Set<Position> availableMove = new TreeSet<>();
        if (this.board.get(position).getChessman() != null && this.board.get(position).getChessman().getTeam() != team)
            availableMove.add(position);
        return availableMove;
    }

    protected boolean check (Set<Position> moves, Position position) {
        moves.add(position);
        if (board.get(position).getChessman() == null)
            return true;
        else
            return false;
    }

    protected Set<Position> checkIfKing (Set<Position> availableMove, Set<Position> endengeredPositions) {
        if (me.getClass() == King.class)
            availableMove.removeAll(endengeredPositions);
        return availableMove;
    }

    protected boolean isInRange (int file, int rank) {
        return file >= 1 && file <= 8 && rank >= 1 && rank <= 8 ? true : false;
    }
}