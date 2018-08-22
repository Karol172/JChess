package JChess.rules;

import JChess.board.Board;
import JChess.chessman.King;
import JChess.chessman.Rook;
import JChess.element.Position;
import JChess.enums.Team;
import JChess.game.ChessmanFactory;
import JChess.game.Player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class CastleTest {

    private List<Player> players;
    private Board board;

    @BeforeEach
    void setUp () {
        players = new ArrayList<>();
        players.add(new Player(Team.WHITE));
        players.add(new Player(Team.BLACK));
        this.board = new Board();
    }

    @Test
    void testDoCastleShort () {
        Castle castle = new Castle();
        this.board.getMap().get(new Position(5,1)).setChessman(
                ChessmanFactory.createChessman(King.class, Team.WHITE, new Position(5,1)));
        this.board.getMap().get(new Position(8,1)).setChessman(
                ChessmanFactory.createChessman(Rook.class, Team.WHITE, new Position(8,1)));
        assertTrue(castle.doCastle(board.getMap(), new TreeSet<>(),
                players.get(0)).contains(new Position(8,1)));
    }

    @Test
    void testDoCastleShortWithMovedKing () {
        Castle castle = new Castle();
        players.get(0).moveKing();
        this.board.getMap().get(new Position(5,1)).setChessman(
                ChessmanFactory.createChessman(King.class, Team.WHITE, new Position(5,1)));
        this.board.getMap().get(new Position(8,1)).setChessman(
                ChessmanFactory.createChessman(Rook.class, Team.WHITE, new Position(8,1)));
        assertFalse(castle.doCastle(board.getMap(), new TreeSet<>(),
                players.get(0)).contains(new Position(8,1)));
    }

    @Test
    void testDoCastleShortWithCheckingKing () {
        Castle castle = new Castle();
        players.get(0).setChecking(true);
        this.board.getMap().get(new Position(5,1)).setChessman(
                ChessmanFactory.createChessman(King.class, Team.WHITE, new Position(5,1)));
        this.board.getMap().get(new Position(8,1)).setChessman(
                ChessmanFactory.createChessman(Rook.class, Team.WHITE, new Position(8,1)));
        assertFalse(castle.doCastle(board.getMap(), new TreeSet<>(),
                players.get(0)).contains(new Position(8,1)));
    }

    @Test
    void testDoCastleShortEndengeredPosition () {
        Castle castle = new Castle();
        Set<Position> endengeredPosition = new TreeSet<>();
        endengeredPosition.add(new Position(7,1));
        this.board.getMap().get(new Position(5,1)).setChessman(
                ChessmanFactory.createChessman(King.class, Team.WHITE, new Position(5,1)));
        this.board.getMap().get(new Position(8,1)).setChessman(
                ChessmanFactory.createChessman(Rook.class, Team.WHITE, new Position(8,1)));
        assertFalse(castle.doCastle(board.getMap(), endengeredPosition,
                players.get(0)).contains(new Position(8,1)));
    }

    @Test
    void testDoCastleLong () {
        Castle castle = new Castle();
        this.board.getMap().get(new Position(5,1)).setChessman(
                ChessmanFactory.createChessman(King.class, Team.WHITE, new Position(5,1)));
        this.board.getMap().get(new Position(1,1)).setChessman(
                ChessmanFactory.createChessman(Rook.class, Team.WHITE, new Position(1,1)));
        assertTrue(castle.doCastle(board.getMap(), new TreeSet<>(),
                players.get(0)).contains(new Position(1,1)));
    }

    @Test
    void testDoCastleLongWithMovedKing () {
        Castle castle = new Castle();
        players.get(0).moveKing();
        this.board.getMap().get(new Position(5,1)).setChessman(
                ChessmanFactory.createChessman(King.class, Team.WHITE, new Position(5,1)));
        this.board.getMap().get(new Position(1,1)).setChessman(
                ChessmanFactory.createChessman(Rook.class, Team.WHITE, new Position(1,1)));
        assertFalse(castle.doCastle(board.getMap(), new TreeSet<>(),
                players.get(0)).contains(new Position(1,1)));
    }

    @Test
    void testDoCastleLongWithCheckingKing () {
        Castle castle = new Castle();
        players.get(0).setChecking(true);
        this.board.getMap().get(new Position(5,1)).setChessman(
                ChessmanFactory.createChessman(King.class, Team.WHITE, new Position(5,1)));
        this.board.getMap().get(new Position(1,1)).setChessman(
                ChessmanFactory.createChessman(Rook.class, Team.WHITE, new Position(1,1)));
        assertFalse(castle.doCastle(board.getMap(), new TreeSet<>(),
                players.get(0)).contains(new Position(1,1)));
    }

    @Test
    void testDoCastleLongEndengeredPosition () {
        Castle castle = new Castle();
        Set<Position> endengeredPosition = new TreeSet<>();
        endengeredPosition.add(new Position(2,1));
        this.board.getMap().get(new Position(5,1)).setChessman(
                ChessmanFactory.createChessman(King.class, Team.WHITE, new Position(5,1)));
        this.board.getMap().get(new Position(1,1)).setChessman(
                ChessmanFactory.createChessman(Rook.class, Team.WHITE, new Position(1,1)));
        assertFalse(castle.doCastle(board.getMap(), endengeredPosition,
                players.get(0)).contains(new Position(1,1)));
    }
}