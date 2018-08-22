package JChess.game;

import JChess.board.Board;
import JChess.chessman.*;
import JChess.element.Place;
import JChess.element.Position;
import JChess.enums.Result;
import JChess.enums.Team;
import JChess.rules.Castle;
import JChess.rules.CheckMoves;
import JChess.rules.NormalMoves;
import JChess.rules.Rules;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Game {

    private List<Player> players;
    private Board board;
    private Player currentPlayer;
    private Configuration configuration;

    public Game() { initializeGame(); }

    public Configuration getConfiguration() { return configuration; }

    public Board getBoard() { return board; }

    public Player getCurrentPlayer() { return this.currentPlayer; }

    public Set<Position> getAvailableMoves(Position position){
        Set<Position> set = new TreeSet<>();
        if (position != null) {
            Chessman chessman = this.board.getMap().get(position).getChessman();
            if (chessman != null)
                if (chessman.getTeam() == currentPlayer.getTeam()) {
                    set.addAll(chessman.getAttackingField());
                    currentPlayer.getChessmanSet().forEach(k -> set.remove(k.getPosition()));
                    if (chessman.getClass() == King.class)
                        set.addAll(new Castle().doCastle(board.getMap(), getRival().getAttackingFields(), currentPlayer));
                }
        }
        return set;
    }

    public void move (Position sourcePosition, Position destinationPosition) {
        Chessman chessman = this.board.getMap().get(sourcePosition).getChessman();
        Chessman destination = this.board.getMap().get(destinationPosition).getChessman();
        if (chessman != null) {
            if (isEmptyOrRival(destination)) {
                this.getRival().removeChessman(destination);
                this.board.replaceChessman(sourcePosition, destinationPosition);
            }
            else if (isMakingCastle(chessman, destination))
                if (currentPlayer.isMayDoCastleShort() && destinationPosition.getFile() == 8)
                    makeCastle(chessman, destination, 2, -2);
                else if (currentPlayer.isMayDoCastleLong() && destinationPosition.getFile() == 1)
                    makeCastle(chessman, destination, -2, 3);
            checkFlagForKingAndRook(chessman);
        }
    }

    public void finishMove () {
        findAttackingFields();
        switchPlayer();
        playerIsChecking();
        findAttackingFields();
    }

    public Position checkPawnPromotion () {
        int rank = currentPlayer.getTeam() == Team.WHITE ? 8 : 1;
        for (int i = 1; i <= 8; i++) {
            Position position = new Position(i, rank);
            Chessman chessman = this.board.getMap().get(position).getChessman();
            if (chessman != null && chessman.getClass() == Pawn.class)
                return position;
        }
        return null;
    }

    public void makePawnPromotion(Position position, Class chessmanClass) {
        Place place = this.board.getMap().get(position);
        currentPlayer.removeChessman(place != null ? place.getChessman() : null);
        currentPlayer.addChessman(changeChessman(chessmanClass, position, currentPlayer.getTeam()));
    }

    public Result getResult() {
        if (currentPlayer.getAttackingFields().isEmpty()) {
            if (currentPlayer.isChecking())
                if (currentPlayer.getTeam() == Team.WHITE)
                    return Result.WHITE_WIN;
                else
                    return Result.BLACK_WIN;
            else
                return Result.DRAW;
        }
        else
            if (currentPlayer.isChecking())
                if (currentPlayer.getTeam() == Team.WHITE)
                    return Result.WHITE_CHECK;
                else
                    return Result.BLACK_CHECK;
        return Result.NO_END;
    }

    public void initializeGame() {
        configuration = new Configuration();
        players = new ArrayList<>();
        players.add(new Player(Team.WHITE));
        players.add(new Player(Team.BLACK));
        board = new Board();
        currentPlayer = players.get(0);
        Class[] tab = {Rook.class, KNight.class, Bishop.class, Queen.class,
                King.class, Bishop.class, KNight.class, Rook.class};
        for (int i = 0; i < tab.length; i++) {
            players.get(0).addChessman(insertChessman(tab[i], new Position(i+1, 1), Team.WHITE));
            players.get(0).addChessman(insertChessman(Pawn.class, new Position(i+1, 2), Team.WHITE));
            players.get(1).addChessman(insertChessman(tab[i], new Position(i+1, 8), Team.BLACK));
            players.get(1).addChessman(insertChessman(Pawn.class, new Position(i+1, 7), Team.BLACK));
        }
        findAttackingFields();
    }

    private boolean playerIsChecking() {
        if (getRival().getAttackingFields().contains(currentPlayer.getKing().getPosition()))
            currentPlayer.setChecking(true);
        else
            currentPlayer.setChecking(false);
        return currentPlayer.isChecking();
    }

    private void checkFlagForKingAndRook(Chessman chessman) {
        if (chessman.getClass() == King.class)
            currentPlayer.moveKing();
        else if (chessman.getClass() == Rook.class)
            if (chessman.getPosition().getFile() == 8)
                currentPlayer.moveCloserRook();
            else if (chessman.getPosition().getFile() == 1)
                currentPlayer.moveFurtherRook();
    }

    private boolean isMakingCastle(Chessman chessman, Chessman destination) {
        return chessman.getClass() == King.class && destination.getClass() == Rook.class;
    }

    private boolean isEmptyOrRival(Chessman destination) {
        return destination == null || destination.getTeam() == getRival().getTeam();
    }

    private void makeCastle(Chessman chessman, Chessman destination, int i, int i2) {
        this.board.replaceChessman(new Position(chessman.getPosition().getFile() + i,
                chessman.getPosition().getRank()), chessman);
        this.board.replaceChessman(new Position(destination.getPosition().getFile() + i2,
                destination.getPosition().getRank()), destination);
    }

    private void findAttackingFields () {
        getRival().clearProtectedFields();
        currentPlayer.clearAttackingFields();
        currentPlayer.getChessmanSet().forEach(chessman ->  {
            Rules rules = currentPlayer.isChecking() ?
                    new CheckMoves(board.getMap(), chessman.getPosition(),
                            getRival().getAttackingFields(), currentPlayer) :
                    new NormalMoves(board.getMap(), chessman.getPosition(),
                            getRival().getAttackingFields(), currentPlayer);
            chessman.searchAvailableMove(rules);
            currentPlayer.addAttackingFields(chessman.getAttackingField()); });
    }

    private void switchPlayer() { this.currentPlayer = getRival(); }

    private Player getRival() {
        if (players.indexOf(currentPlayer) == 0)
            return players.get(1);
        return players.get(0);
    }

    private Chessman insertChessman (Class chessmanClass, Position position, Team team) {
        Chessman chessman = ChessmanFactory.createChessman(chessmanClass, team, position);
        if (board.insertChessman(chessman, position))
            return chessman;
        return null;
    }

    private Chessman changeChessman(Class chessmanClass, Position position, Team team) {
        Chessman chessman = ChessmanFactory.createChessman(chessmanClass, team, position);
        board.replaceChessman(position, chessman);
        return chessman;
    }
}