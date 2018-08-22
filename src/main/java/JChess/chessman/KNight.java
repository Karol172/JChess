package JChess.chessman;

import JChess.element.Position;
import JChess.enums.Move;
import JChess.enums.Team;
import JChess.rules.Rules;

import java.util.Set;

public class KNight extends Chessman {
    public KNight(Team team, Position position) {
        super(team, position);
    }

    @Override
    public void searchAvailableMove(Rules rules) {
        super.searchAvailableMove(rules);

        if (canMove(rules.getPlayer().getKing()))
            this.attackingFields.addAll(rules.moveLikeKNight());
        //this.attackingFields.removeAll(rules.getPlayer().getChessmanPosition());
    }

    private boolean canMove (Chessman king) {
        Set<Move> availableDirection = this.getAvailableDirectionMove(king.getPosition());
        if (availableDirection.size() == 8)
            return true;
        return false;
    }
}
