package JChess.game;

import JChess.chessman.King;
import JChess.element.Position;
import JChess.enums.Team;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class PlayerTest {

    private Player player;

    @BeforeEach
    void setUp () {
        this.player = new Player(Team.WHITE);
    }

    @Test
    void testAddChessmanAddNullChessman () {
        assertFalse(this.player.addChessman(null));
    }

    @Test
    void testAddChessman () {
        assertTrue(this.player.addChessman(ChessmanFactory.createChessman(
                King.class, Team.WHITE, new Position(5,5))));
    }
}