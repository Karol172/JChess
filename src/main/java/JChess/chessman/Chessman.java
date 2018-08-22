package JChess.chessman;

import JChess.element.CoverFields;
import JChess.element.Position;
import JChess.enums.Move;
import JChess.enums.Team;
import JChess.rules.Rules;

import java.util.*;

public abstract class Chessman implements Comparable<Chessman> {
    protected final Team team;
    private Position position;
    protected Set<Position> attackingFields;
    protected Set<CoverFields> protectedFields;

    public Chessman(Team team, Position position) {
        this.team = team;
        this.position = position;
        this.attackingFields = new TreeSet<>();
        this.protectedFields = new HashSet<>();
    }

    public Team getTeam() {
        return team;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Set<Position> getAttackingField() {
        return attackingFields;
    }

    public void addProtectedFields (CoverFields protectedFields) { this.protectedFields.add(protectedFields); }

    public void clearProtectedFields () { this.protectedFields.clear(); }

    public void searchAvailableMove(Rules rules) {
        this.attackingFields.clear();
    }

    protected Set<Move> getAvailableDirectionMove(Position kingPosition) {
        Set<Move> moves = new HashSet<>();
        if (isKing())
            for (CoverFields coverFields: this.protectedFields)
                if (isCoveringKing(kingPosition, coverFields)) {
                    moves.add(coverFields.getAvailableMove());
                    moves.add(Move.getReverseMove(coverFields.getAvailableMove()));
                    break;
                }
        if (moves.isEmpty())
            addAllMoveDirection(moves);
        return moves;
    }

    private void addAllMoveDirection(Set<Move> moves) {
        for (Move move : Move.values())
            moves.add(move);
    }

    private boolean isCoveringKing(Position kingPosition, CoverFields coverFields) {
        return coverFields.getProtectedFields().contains(kingPosition);
    }

    private boolean isKing() { return this.protectedFields != null && this.getClass() != King.class; }

    @Override
    public int compareTo(Chessman o) {
        if (this.hashCode() == o.hashCode())
            return 0;
        else if (this.hashCode() < o.hashCode())
            return -1;
        return 1;
    }
}