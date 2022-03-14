package lunar.apple.igc.discord.format;

public enum FormatEvent {
    ENABLE_SERVER(0),
    DISABLE_SERVER(0),
    CHAT(0),
    PLAYER_JOIN(0),
    PLAYER_LEAVE(-1),
    PLAYER_DEATH(0);

    private final int playerOnlineChange;

    FormatEvent(int playerOnlineChange) {
        this.playerOnlineChange = playerOnlineChange;
    }

    public int getPlayersOnlineChange() {
        return playerOnlineChange;
    }
}
