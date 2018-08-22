package JChess.rules;

import JChess.element.Place;
import JChess.element.Position;
import JChess.enums.Team;
import JChess.game.Player;

import java.util.Map;
import java.util.Set;

public interface Rules {

    Set<Position> getEndengeredPositions ();

    Map<Position, Place> getBoard ();

    Player getPlayer();

    Set<Position> moveDiagonallyToLeftDownCorner (int maxMoves);

    Set<Position> moveDiagonallyToLeftUpCorner (int maxMoves);

    Set<Position> moveDiagonallyToRightUpCorner (int maxMoves);

    Set<Position> moveDiagonallyToRightDownCorner (int maxMoves);

    Set<Position> moveStraightUp (int maxMoves);

    Set<Position> moveStraightDown (int maxMoves);

    Set<Position> moveStraightLeft (int maxMoves);

    Set<Position> moveStraightRight (int maxMoves);

    Set<Position> moveLikeKNight ();

    Set<Position> captureLikePawnOnLeft (Team team);

    Set<Position> captureLikePawnOnRight (Team team);
}