package fr.armotik.naurelliamoderation.commands;

import fr.armotik.naurelliamoderation.Louise;
import fr.armotik.naurelliamoderation.utiles.Database;
import fr.armotik.naurelliamoderation.utiles.ExceptionsManager;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class InfractionsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        Logger logger = Logger.getLogger(InfractionsCommand.class.getName());

        if (strings.length == 0) {
            TextComponent msg = new TextComponent(Louise.wrongCommand());
            msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§cCommand §7: §c/infractions <player> <page>")));

            commandSender.spigot().sendMessage(msg);
            return false;
        }

        String page;
        if (strings.length == 1) {
            page = "1";
        } else {
            page = strings[1];
        }

        int pageInt;

        try {
            pageInt = Integer.parseInt(page);
        } catch (NumberFormatException e) {
            TextComponent msg = new TextComponent(Louise.wrongCommand());
            msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§cInvalid page number §7: §c" + page)));

            commandSender.spigot().sendMessage(msg);
            return false;
        }

        if (pageInt < 1) {
            TextComponent msg = new TextComponent(Louise.wrongCommand());
            msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§cInvalid page number §7: §c" + page)));

            commandSender.spigot().sendMessage(msg);
            return false;
        }

        try (Connection connection = Database.getConnection()) {

            assert connection != null;
            String sqlQuery =
                    "SELECT Players.uuid, Infractions.staffUUID, Infractions.infractionType, Infractions.reason, Infractions.duration, Infractions.infractionDate, Infractions.endInfraction, Infractions.infractionStatus " +
                            "FROM `Infractions` INNER JOIN `Players` ON `Infractions`.`targetUUID` = `Players`.`uuid` " +
                            "WHERE `Players`.`ign` = '" + strings[0] + "' " +
                            "ORDER BY `Infractions`.`infractionDate` " +
                            "DESC LIMIT 10 OFFSET " + ((pageInt - 1) * 10) + ";";

            try (Statement statement = connection.createStatement();
                 ResultSet res = statement.executeQuery(sqlQuery)) {

                if (res == null) {

                    logger.warning("[NaurelliaModeration] -> InfractionsCommand : onCommand ERROR - res == null");
                    return false;
                }

                if (!res.next()) {

                    TextComponent msg = new TextComponent(Louise.playerNotFound());
                    msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§cCommand §7: §c/infractions <player> <page>")));

                    commandSender.spigot().sendMessage(msg);
                    return false;
                }

                while (res.next()) {

                    String infractionType = res.getString("infractionType");
                    String reason = res.getString("reason");
                    String duration = res.getString("duration");
                    String infractionDate = res.getString("infractionDate");
                    String endInfraction = res.getString("endInfraction");
                    String infractionStatus = res.getString("infractionStatus");
                    String staffUUID = res.getString("staffUUID");

                    String infraction = "§c" + infractionType + " §7: §c" + reason + " §7| §c" + duration + " §7| §c" + infractionDate + " §7| §c" + endInfraction + " §7| §c" + infractionStatus + " §7| §c" + staffUUID;

                }

                return true;
            }

        } catch (SQLException e) {

            ExceptionsManager.sqlExceptionLog(e);
            return false;
        }
    }
}
