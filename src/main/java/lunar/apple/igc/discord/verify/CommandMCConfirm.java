package lunar.apple.igc.discord.verify;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Name;
import co.aikar.commands.annotation.Subcommand;
import lunar.apple.igc.LunarPlugin;
import lunar.apple.igc.discord.player.VerifiedPlayersDatabase;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@CommandAlias("discord")
public class CommandMCConfirm extends BaseCommand {
    public CommandMCConfirm() {
        LunarPlugin.get().registerCommand(this);
    }

    @Subcommand("confirm")
    public void confirm(CommandSender sender, @Name("id") String id) {
        UUID requestId = getUUID(sender, id);
        if (requestId == null) return;
        if (VerifiedPlayersDatabase.get().confirm(requestId)) {
            sender.sendMessage(ChatColor.GREEN + "Thank you for confirming your discord!");
        } else {
            fail(sender);
        }
    }

    private void fail(CommandSender sender) {
        sender.sendMessage(ChatColor.RED + "Please use a valid id.");
    }

    @Subcommand("deny")
    public void deny(CommandSender sender, @Name("id") String id) {
        UUID requestId = getUUID(sender, id);
        if (requestId == null) return;
        if (VerifiedPlayersDatabase.get().deny(requestId)) {
            sender.sendMessage("You denied that request.");
        } else {
            fail(sender);
        }
    }

    @Nullable
    private UUID getUUID(CommandSender sender, String id) {
        UUID requestId;
        try {
            requestId = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            fail(sender);
            return null;
        }
        return requestId;
    }
}
