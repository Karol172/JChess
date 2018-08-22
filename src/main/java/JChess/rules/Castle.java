package JChess.rules;

import JChess.element.Place;
import JChess.element.Position;
import JChess.enums.Team;
import JChess.game.Player;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Castle {

    public Set<Position> doCastle (Map<Position, Place> board, Set<Position> endengeredPositions, Player player) {
        Set<Position> availableMove = new TreeSet<>();
        int rank = player.getTeam() == Team.WHITE ? 1 : 8;

        if (!player.isChecking() && player.isMayDoCastleLong()  && isEmpty(new Position(2, rank), board, 3))
            if (!isEndengered(new Position(1, rank), endengeredPositions, 5))
                availableMove.add(new Position(1, rank));
        if (!player.isChecking() && player.isMayDoCastleShort() && isEmpty(new Position(6, rank), board, 2))
            if (!isEndengered(new Position(5, rank), endengeredPositions, 4))
                availableMove.add(new Position(8, rank));
        return availableMove;
    }

    private boolean isEndengered(Position position, Set<Position> endengeredPosition, int count) {
        for (int i = 0; i < count; i++) {
            if (endengeredPosition.contains(position))
                return true;
            if (isInRange(position.getFile() + 1, position.getRank()))
                position = new Position(position.getFile() + 1, position.getRank());
        }
        return false;
    }

    private boolean isEmpty(Position position, Map<Position, Place> board, int count) {
        for (int i = 0; i < count; i++) {
            if (board.get(position).getChessman() != null)
                return false;
            if (isInRange(position.getFile() + 1, position.getRank()))
                position = new Position(position.getFile() + 1, position.getRank());
        }
        return true;
    }

    private boolean isInRange (int file, int rank) {
        return file >= 1 && file <= 8 && rank >= 1 && rank <= 8;
    }
}