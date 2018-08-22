package JChess.chessman;

import JChess.element.Place;
import JChess.element.Position;
import JChess.enums.Move;
import JChess.enums.Team;
import JChess.rules.Rules;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Pawn extends Chessman {
    public Pawn(Team team, Position position) {
        super(team, position);
    }

    @Override
    public void searchAvailableMove(Rules rules) {
        super.searchAvailableMove(rules);
        Chessman king = rules.getPlayer().getKing();
        Set<Move> availableDirection = this.getAvailableDirectionMove(king.getPosition());

        if (this.getTeam() == Team.WHITE) {
            if (this.getPosition().getRank() == 2) {
                if (availableDirection.contains(Move.StraightUp))
                    this.attackingFields.addAll(rules.moveStraightUp(2));
            }
            else
                if (availableDirection.contains(Move.StraightUp))
                    this.attackingFields.addAll(rules.moveStraightUp(1));
            removeIncorrectMoves(rules.getBoard());

            if (availableDirection.contains(Move.DiagonallyLeftUp))
                this.attackingFields.addAll(rules.captureLikePawnOnLeft(Team.WHITE));
            if (availableDirection.contains(Move.DiagonallyRightUp))
                this.attackingFields.addAll(rules.captureLikePawnOnRight(Team.WHITE));
        }
        else {
            if (this.getPosition().getRank() == 7) {
                if (availableDirection.contains(Move.StraightDown))
                    this.attackingFields.addAll(rules.moveStraightDown(2));
            }
            else
                if (availableDirection.contains(Move.StraightDown))
                    this.attackingFields.addAll(rules.moveStraightDown(1));
            removeIncorrectMoves(rules.getBoard());

            if (availableDirection.contains(Move.DiagonallyRightDown))
                this.attackingFields.addAll(rules.captureLikePawnOnLeft(Team.BLACK));
            if (availableDirection.contains(Move.DiagonallyLeftDown))
                this.attackingFields.addAll(rules.captureLikePawnOnRight(Team.BLACK));
        }
        //this.attackingFields.removeAll(rules.getPlayer().getChessmanPosition());
    }

    private void removeIncorrectMoves(Map<Position, Place> board) {
        this.attackingFields = this.attackingFields.stream()
                .filter(position -> board.get(position).getChessman() == null)
                .collect(Collectors.toSet());
    }
}
