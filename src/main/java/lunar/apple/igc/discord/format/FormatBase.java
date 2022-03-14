package lunar.apple.igc.discord.format;

import ycm.yml.manager.fields.YcmField;

public class FormatBase {
    private static final String defaultPlayerAvatar = "https://crafatar.com/renders/head/%playerUUID%";
    private static FormatBase instance;

    @YcmField
    private final Chat chat = new Chat();
    @YcmField
    private final GameEvent gameEvent = new GameEvent();
    @YcmField
    private final Status status = new Status();

    public FormatBase() {
        instance = this;
    }


    public static FormattedMessage format(FormattedMessageContext context) {
        return (switch (context.event) {
            case ENABLE_SERVER -> instance.status.start;
            case DISABLE_SERVER -> instance.status.stop;
            case CHAT -> instance.chat;
            case PLAYER_JOIN -> instance.gameEvent.login;
            case PLAYER_LEAVE -> instance.gameEvent.logout;
            case PLAYER_DEATH -> instance.gameEvent.death;
        }).copy().format(context);
    }

    public static class Chat extends FormattedMessage {
        public Chat() {
            super(defaultPlayerAvatar, "%username%", "%content%");
        }
    }

    public static class GameEvent {
        @YcmField
        public FormattedMessage login =
                new FormattedMessage(defaultPlayerAvatar, "[Login] %username%", "%content%");
        @YcmField
        public FormattedMessage logout =
                new FormattedMessage(defaultPlayerAvatar, "[Logout] %username%", "%content%");
        @YcmField
        public FormattedMessage death =
                new FormattedMessage(defaultPlayerAvatar, "[Player Death] %username%", "%content%");
    }

    public static class Status {
        @YcmField
        public FormattedMessage start =
                new FormattedMessage("", "Server Status", "**SERVER STARTED** :white_check_mark:");
        @YcmField
        public FormattedMessage stop =
                new FormattedMessage("", "Server Status", "**SERVER STOPPED** :x:");
        @YcmField
        public FormattedMessage reloadingStart =
                new FormattedMessage("", "Server Status", "**SERVER RELOADING** :arrows_counterclockwise:");
        @YcmField
        public FormattedMessage reloadingStop =
                new FormattedMessage("", "Server Status", "**RELOAD COMPLETE** :white_check_mark:");
    }
}
