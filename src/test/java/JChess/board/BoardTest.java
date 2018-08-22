package JChess.board;

import JChess.chessman.Pawn;
import JChess.element.Position;
import JChess.enums.Team;
import JChess.game.ChessmanFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
public class BoardTest {

    private Board board;

    @BeforeEach
    void setUp () {
        this.board = new Board();
    }

    @Test
    void testInsertChessman () {
        assertTrue(this.board.insertChessman(ChessmanFactory.createChessman(
                Pawn.class, Team.WHITE, new Position(1,1)), new Position(1,1)));
    }

    @Test
    void testInsertChessmanInOccupiedField () {
        this.board.insertChessman(ChessmanFactory.createChessman(
                Pawn.class, Team.WHITE, new Position(1,1)), new Position(1,1));
        assertFalse(this.board.insertChessman(ChessmanFactory.createChessman(
                Pawn.class, Team.WHITE, new Position(2,2)), new Position(1,1)));
    }

    @Test
    void testReplaceChessmanNullChessman () {
        assertFalse(this.board.replaceChessman(new Position(1,1), new Position(8,8)));
    }

    @Test
    void testReplaceChessmanToOccupiedField () {
        this.board.insertChessman(ChessmanFactory.createChessman(
                Pawn.class, Team.WHITE, new Position(1,2)), new Position(1,2));
        this.board.insertChessman(ChessmanFactory.createChessman(
                Pawn.class, Team.BLACK, new Position(2,3)), new Position(2,3));
        assertTrue(this.board.replaceChessman(new Position(2,3), new Position(1,2)));
    }
}