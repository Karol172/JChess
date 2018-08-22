package JChess.rules;

import JChess.chessman.Chessman;
import JChess.chessman.King;
import JChess.element.Place;
import JChess.element.Position;
import JChess.game.Player;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class CheckMoves extends NormalMoves implements Rules  {

    private Set<Position> checkingPosition;

    public CheckMoves(Map<Position, Place> board, Position currentPosition,
                      Set<Position> endengeredPositions, Player player) {
        super(board, currentPosition, endengeredPositions, player);
        this.checkingPosition = getCheckingPosition();
    }

    @Override
    protected Set<Position> checkIfKing (Set<Position> availableMove, Set<Position> endengeredPositions) {
        if (me.getClass() == King.class)
            availableMove.removeAll(endengeredPositions);
        else
            removeIfNotContainsCheckingPosition(availableMove);
        return availableMove;
    }

    private void removeIfNotContainsCheckingPosition (Set<Position> movesSet) {
        Set<Position> temp = movesSet.stream().filter(k-> checkingPosition.contains(k)).collect(Collectors.toSet());
        movesSet.clear();
        movesSet.addAll(temp);
    }

    private Set<Position> getCheckingPosition () {
        Chessman king = this.player.getKing();
        Chessman atackingChessman = getAttackingChessman(king);

        if (atackingChessman != null) {
            int x = getDistanceX(king, atackingChessman);
            int y = getDistanceY(king, atackingChessman);

            return searchPosition(king.getPosition(),
                    atackingChessman.getPosition(), -1 * sign(x), -1 * sign(y));
        }
        return new TreeSet<>();
    }

    private int sign (int number) {
        return number != 0 ? number / Math.abs(number) : 0;
    }

    private int getDistanceX(Chessman king, Chessman atackingChessman) {
        Integer temp = king.getPosition().getDistanceX(atackingChessman.getPosition());
        return temp != null ? temp : 0;
    }

    private int getDistanceY(Chessman king, Chessman atackingChessman) {
        Integer temp = king.getPosition().getDistanceY(atackingChessman.getPosition());
        return temp != null ? temp : 0;
    }

    private Chessman getAttackingChessman(Chessman king) {
        for (Chessman chessman : getChessmen())
            if (chessman.getAttackingField().contains(king.getPosition()))
                return chessman;
        return null;
    }

    private Set<Position> searchPosition (Position kingPosition, Position attackingChessmanPosition,
                                          int deltaFile, int deltaRank) {
        Set<Position> result = new TreeSet<>();
        int fileMin = Math.min(kingPosition.getFile(), attackingChessmanPosition.getFile());
        int fileMax = Math.max(kingPosition.getFile(), attackingChessmanPosition.getFile());
        int rankMin = Math.min(kingPosition.getRank(), attackingChessmanPosition.getRank());
        int rankMax = Math.max(kingPosition.getRank(), attackingChessmanPosition.getRank());

        for (int i = kingPosition.getFile() + deltaFile, j = kingPosition.getRank() + deltaRank;
                i >= fileMin && i <= fileMax && j >= rankMin && j <= rankMax;
                i += deltaFile, j += deltaRank) {
            result.add(new Position(i, j));
        }
        return result;
    }

    private Set<Chessman> getChessmen () {
        Set<Chessman> chessmen = new TreeSet<>();
        board.forEach(((position, place) -> {
            if (place.getChessman() != null && place.getChessman().getTeam() != this.player.getTeam())
                chessmen.add(place.getChessman()); }));
        return chessmen;
    }
}