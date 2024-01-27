package fr.armotik.naurelliamoderation.commands;

import fr.armotik.naurelliamoderation.Louise;
import fr.armotik.naurelliamoderation.listerners.ConnectionsManager;
import fr.armotik.naurelliamoderation.tools.SanctionsManager;
import fr.armotik.naurelliamoderation.utiles.Database;
import fr.armotik.naurelliamoderation.utiles.ExceptionsManager;
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
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        TextComponent msg = new TextComponent(Louise.wrongCommand());
        msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§cCommand §7: §c/connections <player>")));

        Logger logger = Logger.getLogger(ConnectionsCommand.class.getName());

        if (strings.length < 1) {

            commandSender.spigot().sendMessage(msg);
            return false;
        }

        try (Connection conn = Database.getConnection();
        ) {
            assert conn != null;
            try (Statement statement = conn.createStatement();
                 ResultSet result = statement.executeQuery("SELECT uuid FROM Players WHERE ign = '" + strings[0] + "'");
            ){

                if (result == null) {

                    logger.log(Level.WARNING, "[NaurelliaModeration] -> ConnectionsCommand : onCommand ERROR - res == null");
                    return false;
                }

                if (!result.next()) {

                    commandSender.spigot().sendMessage(msg);
                    return false;
                }

                UUID uuid = UUID.fromString(result.getString("uuid"));

                Player target = Bukkit.getServer().getPlayer(uuid);

                if (target == null) {

                    msg = new TextComponent(Louise.getName() + "§cPlayer not found or not connected");
                    commandSender.spigot().sendMessage(msg);
                    return false;
                }

                ConnectionsManager.displayPlayers(Objects.requireNonNull(target.getAddress()).getAddress());
            }
        } catch (SQLException e) {
            ExceptionsManager.sqlExceptionLog(e);
            return false;
        }

        return true;
    }
}
