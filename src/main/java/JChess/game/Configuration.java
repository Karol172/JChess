package JChess.game;

import JChess.enums.PlayerSource;

public class Configuration {
    private PlayerSource playerSource;

    public Configuration() { this.playerSource = PlayerSource.USER; }

    public PlayerSource getPlayerSource() {
        return playerSource;
    }

    public void setPlayerSource(PlayerSource playerSource) {
        this.playerSource = playerSource;
    }
}