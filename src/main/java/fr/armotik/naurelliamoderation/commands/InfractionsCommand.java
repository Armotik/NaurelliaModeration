package fr.armotik.naurelliamoderation.commands;

import fr.armotik.naurelliamoderation.Louise;
import fr.armotik.naurelliamoderation.listerners.GuiManager;
import fr.armotik.naurelliamoderation.utiles.Database;
import fr.armotik.naurelliamoderation.utiles.ExceptionsManager;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class InfractionsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        Logger logger = Logger.getLogger(InfractionsCommand.class.getName());

        if (!(commandSender instanceof Player)) return false;

        Player player = ((Player) commandSender).getPlayer();

        if (strings.length == 0) {
            TextComponent msg = new TextComponent(Louise.wrongCommand());
            msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§cCommand §7: §c/infractions <player>")));

            commandSender.spigot().sendMessage(msg);
            return false;
        }

        String target = strings[0];

        try (Connection connection = Database.getConnection()) {

            assert connection != null;
            try (Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("SELECT uuid FROM `Players` WHERE ign='" + target + "'")) {

                if (res == null) {

                    assert player != null;
                    player.sendMessage(Louise.commandError());
                    logger.warning("[NaurelliaModeration] -> InfractionsCommand : onCommand ERROR - res == null");
                    return false;
                }

                if (!res.next()) {

                    assert player != null;
                    player.sendMessage(Louise.playerNotFound());
                    return false;
                }

                OfflinePlayer targetPlayer = Bukkit.getOfflinePlayer(UUID.fromString(res.getString("uuid")));

                GuiManager.infractionsGui(player, targetPlayer, 1);
                return true;
            }
        } catch (SQLException e) {
            ExceptionsManager.sqlExceptionLog(e);
            return false;
        }
    }
}
