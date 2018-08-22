package JChess.rules;

import JChess.board.Board;
import JChess.chessman.King;
import JChess.chessman.Pawn;
import JChess.element.Position;
import JChess.enums.Team;
import JChess.game.Player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

public class NormalMovesTest {
    private Rules rules;

    @BeforeEach
    void setUp() {
        Board board = new Board();
        board.getMap().get(new Position(5, 5)).setChessman(new King(Team.WHITE, new Position(5, 5)));
        board.getMap().get(new Position(5, 6)).setChessman(new Pawn(Team.WHITE, new Position(5, 6)));
        board.getMap().get(new Position(8, 8)).setChessman(new Pawn(Team.WHITE, new Position(8, 8)));
        board.getMap().get(new Position(2, 2)).setChessman(new Pawn(Team.WHITE, new Position(2, 2)));

        Set<Position> endengeredPosition = new TreeSet<>();
        endengeredPosition.add(new Position(4, 4));

        this.rules = new NormalMoves(board.getMap(), new Position(5, 5),
                endengeredPosition, new Player(Team.WHITE));
    }

    @Test
    void testMoveDiagonallyToLeftDownCorrner () {
        assertTrue(equalSets(rules.moveDiagonallyToLeftDownCorner(8), getPositionArray("B2", "C3")));
    }

    @Test
    void testMoveDiagonallyToLeftUpCorrner () {
        assertTrue(equalSets(rules.moveDiagonallyToLeftUpCorner(8),
                getPositionArray("D6", "C7", "B8")));
    }

    @Test
    void testMoveDiagonallyToRightDownCorrner () {
        assertTrue(equalSets(rules.moveDiagonallyToRightDownCorner(8),
                getPositionArray("F4", "G3", "H2")));
    }

    @Test
    void testMoveDiagonallyToRigthUpCorrner () {
        assertTrue(equalSets(rules.moveDiagonallyToRightUpCorner(8),
                getPositionArray("F6", "G7", "H8")));
    }

    @Test
    void testMoveStraightUp () {
        assertTrue(equalSets(rules.moveStraightUp(8), getPositionArray("E6")));
    }

    @Test
    void testMoveStraightDown () {
        assertTrue(equalSets(rules.moveStraightDown(8), getPositionArray("E1", "E2", "E3", "E4")));
    }

    @Test
    void testMoveStraightLeft () {
        assertTrue(equalSets(rules.moveStraightLeft(8), getPositionArray("A5", "B5", "C5", "D5")));
    }

    @Test
    void testMoveStraightRight () {
        assertTrue(equalSets(rules.moveStraightRight(8), getPositionArray("F5", "G5", "H5")));
    }

    @Test
    void testMoveLikeKNight () {
        assertTrue(equalSets(rules.moveLikeKNight(),
                getPositionArray("C6", "D7", "F7", "G6", "G4", "F3", "D3", "C4")));
    }

    @Test
    void testCaptureLikePawnOnLeft () {
        assertTrue(equalSets(rules.captureLikePawnOnLeft(Team.BLACK), getPositionArray()));
    }

    @Test
    void testCaptureLikePawnOnRight () {
        rules.getBoard().get(new Position(4,4)).setChessman(new Pawn(Team.WHITE, new Position(4,4)));
        assertTrue(equalSets(rules.captureLikePawnOnRight(Team.BLACK), getPositionArray("D4")));
    }

    private boolean equalSets (Set<Position> set1, Set<Position> set2) {
        if (set1 == null || set2 == null)
            return false;
        if (set1.size() != set2.size())
            return false;
        return set1.containsAll(set2);
    }

    private Set<Position> getPositionArray (String...args) {
        Set<Position> positions = new TreeSet<>();
        Arrays.asList(args).stream().forEach(k->positions.add(new Position(k)));
        return positions;
    }
}