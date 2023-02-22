package fr.armotik.naurelliamoderation.commands;

import fr.armotik.naurelliamoderation.Louise;
import fr.armotik.naurelliamoderation.tools.SanctionsManager;
import fr.armotik.naurelliamoderation.utiles.Database;
import fr.armotik.naurelliamoderation.utiles.ExceptionsManager;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MuteCommand implements CommandExecutor {
    /**
     * Executes the given command, returning its success.
     * <br>
     * If false is returned, then the "usage" plugin.yml entry for this command
     * (if defined) will be sent to the player.
     *
     * @param sender  Source of the command
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    Passed command arguments
     * @return true if a valid command, otherwise false
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Logger logger = Logger.getLogger(MuteCommand.class.getName());

        if (!(sender instanceof Player)) return false;

        TextComponent msg = new TextComponent(Louise.wrongCommand());
        msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§cCommand §7: §c/mute <player> <reason>")));

        Player player = ((Player) sender).getPlayer();

        assert player != null;
        if (!player.hasPermission("naurellia.staff")) {
            player.sendMessage(Louise.permissionMissing());
            return false;
        }

        if (args.length == 0) {

            player.spigot().sendMessage(msg);
            return false;
        }

        String reason;
        if (args.length == 2) {
            reason = null;
        } else {
            reason = SanctionsManager.reasonDefBuilder(args, 1);
        }

        ResultSet res = Database.executeQuery("SELECT uuid FROM usersIG WHERE username = '" + args[0] + "'");

        if (res == null) {

            logger.log(Level.WARNING, "[NaurelliaModeration] -> MuteCommand : onCommand ERROR - res == null");
            Database.close();
            return false;
        }

        try {

            if (!res.next()) {

                player.spigot().sendMessage(msg);
                return false;
            }

            SanctionsManager.mute(player, UUID.fromString(res.getString("uuid")), reason);
            Database.close();
            return true;
        } catch (SQLException e) {
            ExceptionsManager.sqlExceptionLog(e);
            Database.close();
            return false;
        }
    }
}
