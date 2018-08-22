package JChess.game;

import JChess.chessman.Chessman;
import JChess.chessman.King;
import JChess.element.Position;
import JChess.enums.Team;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

public class Player {
    private final Team team;
    private Chessman king;
    private boolean mayDoCastleShort;
    private boolean mayDoCastleLong;
    private boolean isChecking;
    private Set<Position> attackingFields;
    private Set<Chessman> chessmen;

    public Player(Team team) {
        this.team = team;
        this.attackingFields = new TreeSet<>();
        this.chessmen = new TreeSet<>();
        this.mayDoCastleShort = true;
        this.mayDoCastleLong = true;
        this.isChecking = false;
        this.king = null;
    }

    public Team getTeam() {
        return team;
    }

    public Chessman getKing() { return this.king; }

    public Set<Position> getAttackingFields() {
        return attackingFields;
    }

    public void addAttackingFields(Collection<Position> moveList) {
        this.attackingFields.addAll(moveList);
    }

    public void clearAttackingFields() {
        this.attackingFields.clear();
    }

    public Set<Chessman> getChessmanSet() { return chessmen; }

    public boolean addChessman(Chessman chessman) {
        if (chessman != null) {
            if (chessman.getClass() == King.class)
                if (this.king == null)
                    this.king = chessman;
            this.chessmen.add(chessman);
            return true;
        }
        return false;
    }

    public void removeChessman(Chessman chessman) {
        if (chessman != null)
            this.chessmen.remove(chessman);
    }

    public void clearProtectedFields () { this.chessmen.forEach(k->k.clearProtectedFields()); }

    public boolean isMayDoCastleShort() { return mayDoCastleShort; }

    public boolean isMayDoCastleLong() { return mayDoCastleLong; }

    public void moveCloserRook() { this.mayDoCastleShort = false; }

    public void moveFurtherRook() { this.mayDoCastleLong = false; }

    public void moveKing() {
        this.moveCloserRook();
        this.moveFurtherRook();
    }

    public boolean isChecking() { return isChecking; }

    public void setChecking(boolean checking) { isChecking = checking; }

}