package lunar.apple.igc.discord.format;

import ycm.yml.manager.fields.YcmField;

public class FormattedMessage {
    @YcmField
    protected String avatar;
    @YcmField
    protected String username;
    @YcmField
    protected String content;

    public FormattedMessage(String avatar, String username, String content) {
        this.avatar = avatar;
        this.username = username;
        this.content = content;
    }

    public FormattedMessage(FormattedMessage other) {
        this.avatar = other.avatar;
        this.username = other.username;
        this.content = other.content;
    }

    public FormattedMessage copy() {
        return new FormattedMessage(this);
    }

    public FormattedMessage format(FormattedMessageContext context) {
        this.avatar = FormatComponent.format(this.avatar, context);
        this.username = FormatComponent.format(this.username, context);
        this.content = FormatComponent.format(this.content, context);
        return this;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getUsername() {
        return username;
    }

    public String getContent() {
        return content;
    }
}
