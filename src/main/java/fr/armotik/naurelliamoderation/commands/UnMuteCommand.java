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
import java.util.logging.Level;
import java.util.logging.Logger;

public class UnMuteCommand implements CommandExecutor {
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

        Logger logger = Logger.getLogger(UnMuteCommand.class.getName());

        if (!(sender instanceof Player)) return false;

        /*
          IF THE SENDER DO NOT HAVE THE PERMISSION
         */
        if (!sender.hasPermission("naurellia.unmute")) {
            sender.sendMessage(Louise.permissionMissing());
            return false;
        }

        /*
        IF NO ARGUMENTS
         */
        if (args.length < 1) {

            TextComponent msg = new TextComponent(Louise.wrongCommand());
            msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§cCommand §7: §c/unmute <player>")));

            sender.spigot().sendMessage(msg);
            return false;
        } else {

            ResultSet res = Database.executeQuery("SELECT uuid FROM usersIG WHERE username = '" + args[0] + "'");

            if (res == null) {
                logger.log(Level.WARNING, "unMuteCommand ERROR : res == null");
                Database.close();
                return false;
            }

            try {
                /*
                IF THE DATABASE DO NOT FIND THE PLAYER
                 */
                if (!res.next()) {

                    TextComponent msg = new TextComponent(Louise.playerNotFound());
                    msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§cCommand §7: §c/unmute <player>")));

                    sender.spigot().sendMessage(msg);
                    Database.close();
                    return false;

                    /*
                    IF THE DATABASE FIND THE PLAYER
                     */
                } else {

                    Player player = ((Player) sender).getPlayer();
                    UUID targetUUID = UUID.fromString(res.getString("uuid"));

                    if (SanctionsManager.getBannedFromChat().containsKey(targetUUID)) {

                        SanctionsManager.unMute(player, targetUUID);
                        Database.close();
                        return true;
                    } else {

                        assert player != null;
                        player.sendMessage(Louise.getName() + "§cPlayer not muted");
                        Database.close();
                        return false;
                    }
                }
            } catch (SQLException e) {
                ExceptionsManager.sqlExceptionLog(e);
                Database.close();
                return false;
            }
        }
    }
}
