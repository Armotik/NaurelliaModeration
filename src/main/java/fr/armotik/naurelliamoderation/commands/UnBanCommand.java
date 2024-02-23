package fr.armotik.naurelliamoderation.commands;

import fr.armotik.louise.louise.LouiseModeration;
import fr.armotik.louise.utiles.ExceptionsManager;
import fr.armotik.naurelliamoderation.tools.SanctionsManager;
import fr.armotik.naurelliamoderation.utiles.Database;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UnBanCommand implements CommandExecutor {
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

        Logger logger = Logger.getLogger(UnBanCommand.class.getName());

        if (!(sender instanceof Player)) return false;

        /*
        IF NO ARGUMENTS
         */
        if (args.length < 1) {

            TextComponent msg = new TextComponent(LouiseModeration.wrongCommand());
            msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§cCommand §7: §c/unban <player>")));

            sender.spigot().sendMessage(msg);
            return false;
        } else {

            try (Connection conn = Database.getConnection()){
                /*
                IF THE DATABASE DO NOT FIND THE PLAYER
                 */
                assert conn != null;

                try (Statement statement = conn.createStatement();
                     ResultSet res = statement.executeQuery("SELECT uuid FROM Players WHERE ign = '" + args[0] + "'");
                ) {

                    if (res == null) {
                        logger.log(Level.WARNING, "unMuteCommand ERROR : res == null");
                        Database.close();
                        return false;
                    }

                    if (!res.next()) {

                        TextComponent msg = new TextComponent(LouiseModeration.playerNotFound());
                        msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§cCommand §7: §c/unban <player>")));

                        sender.spigot().sendMessage(msg);
                        return false;

                    }

                    Player player = ((Player) sender).getPlayer();
                    UUID targetUUID = UUID.fromString(res.getString("uuid"));

                    if (!Bukkit.getOfflinePlayer(targetUUID).isBanned()) {

                            TextComponent msg = new TextComponent(LouiseModeration.getName() + " §7» §c" + args[0] + " §cis not banned.");
                            msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§cCommand §7: §c/unban <player>")));

                            sender.spigot().sendMessage(msg);
                            return false;
                    }

                    SanctionsManager.unBan(player, Bukkit.getOfflinePlayer(targetUUID));

                    return true;
                }

            } catch (SQLException e) {
                ExceptionsManager.sqlExceptionLog(e);
                return false;
            }
        }
    }
}
