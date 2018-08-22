package JChess.chessman;

import JChess.element.Position;
import JChess.enums.Team;
import JChess.rules.Rules;

public class King extends Chessman {

    public King(Team team, Position position) {
        super(team, position);
    }

    @Override
    public void searchAvailableMove(Rules rules) {
        super.searchAvailableMove(rules);

        this.attackingFields.addAll(rules.moveDiagonallyToLeftDownCorner(1));
        this.attackingFields.addAll(rules.moveDiagonallyToLeftUpCorner(1));
        this.attackingFields.addAll(rules.moveDiagonallyToRightUpCorner(1));
        this.attackingFields.addAll(rules.moveDiagonallyToRightDownCorner(1));
        this.attackingFields.addAll(rules.moveStraightUp(1));
        this.attackingFields.addAll(rules.moveStraightDown(1));
        this.attackingFields.addAll(rules.moveStraightLeft(1));
        this.attackingFields.addAll(rules.moveStraightRight(1));
        this.attackingFields.removeAll(rules.getEndengeredPositions());
        this.protectedFields.forEach(k->this.attackingFields.removeAll(k.getProtectedFields()));
        //this.attackingFields.removeAll(rules.getPlayer().getChessmanPosition());
    }
}