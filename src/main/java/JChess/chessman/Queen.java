package JChess.chessman;

import JChess.element.Position;
import JChess.enums.Move;
import JChess.enums.Team;
import JChess.rules.Rules;

import java.util.Set;

public class Queen extends Chessman {

    public Queen(Team team, Position position) {
        super(team, position);
    }

    @Override
    public void searchAvailableMove(Rules rules) {
        super.searchAvailableMove(rules);
        Set<Move> availableDirection = this.getAvailableDirectionMove(rules.getPlayer().getKing().getPosition());

        if (availableDirection.contains(Move.DiagonallyLeftDown))
            this.attackingFields.addAll(rules.moveDiagonallyToLeftDownCorner(8));
        if (availableDirection.contains(Move.DiagonallyLeftUp))
            this.attackingFields.addAll(rules.moveDiagonallyToLeftUpCorner(8));
        if (availableDirection.contains(Move.DiagonallyRightUp))
            this.attackingFields.addAll(rules.moveDiagonallyToRightUpCorner(8));
        if (availableDirection.contains(Move.DiagonallyRightDown))
            this.attackingFields.addAll(rules.moveDiagonallyToRightDownCorner(8));
        if (availableDirection.contains(Move.StraightUp))
            this.attackingFields.addAll(rules.moveStraightUp(8));
        if (availableDirection.contains(Move.StraightDown))
            this.attackingFields.addAll(rules.moveStraightDown(8));
        if (availableDirection.contains(Move.StraightLeft))
            this.attackingFields.addAll(rules.moveStraightLeft(8));
        if (availableDirection.contains(Move.StraightRight))
            this.attackingFields.addAll(rules.moveStraightRight(8));
        //this.attackingFields.removeAll(rules.getPlayer().getChessmanPosition());
    }
}
