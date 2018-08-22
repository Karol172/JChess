package JChess.element;

import JChess.enums.Move;

import java.util.Set;
import java.util.TreeSet;

public class CoverFields {
    private Set<Position> protectedFields;
    private Move availableMove;

    public CoverFields(Move availableMove) {
        this.protectedFields = new TreeSet<>();
        this.availableMove = availableMove;
    }

    public Set<Position> getProtectedFields() { return protectedFields; }

    public Move getAvailableMove() { return availableMove; }
}
