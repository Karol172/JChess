package JChess.element;

import JChess.chessman.Chessman;

public class Place {
    private Chessman chessman;
    private Position position;

    public Place(Chessman chessman, Position position) {
        this.chessman = chessman;
        this.position = position;
    }

    public void setChessman(Chessman chessman) {
        if (chessman != null) {
            this.chessman = chessman;
            this.chessman.setPosition(this.position);
        }
    }

    public void removeChessman() {
        this.chessman = null;
    }

    public Chessman getChessman() { return this.chessman; }
}
