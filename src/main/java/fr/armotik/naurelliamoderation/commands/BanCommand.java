package fr.armotik.naurelliamoderation.commands;

import fr.armotik.louise.louise.LouiseModeration;
import fr.armotik.naurelliamoderation.tools.SanctionsManager;
import fr.armotik.naurelliamoderation.utiles.Database;
import fr.armotik.louise.utiles.ExceptionsManager;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BanCommand {

    /**
     * Ban command
     *
     * @param player player
     * @param args   args
     * @param ip     ip (true if ip ban)
     */
    public BanCommand(Player player, String[] args, boolean ip) {

        Logger logger = Logger.getLogger(BanCommand.class.getName());

        TextComponent msg = new TextComponent(LouiseModeration.wrongCommand());
        msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§cCommand §7: §c/ban<ip> <player> <reason>")));

        assert player != null;

        if (args.length == 0) {

            player.spigot().sendMessage(msg);
            return;
        }

        String reason;
        if (args.length == 2) {
            reason = null;
        } else {
            reason = SanctionsManager.reasonDefBuilder(args, 1);
        }

        try (Connection conn = Database.getConnection();
        ) {
            assert conn != null;
            try (Statement statement = conn.createStatement();
                 ResultSet result = statement.executeQuery("SELECT uuid FROM Players WHERE ign = '" + args[0] + "'");
            ) {

                if (result == null) {

                    logger.log(Level.WARNING, "[NaurelliaModeration] -> BanCommand : onCommand ERROR - res == null");
                    return;
                }

                if (!result.next()) {

                    player.spigot().sendMessage(msg);
                    return;
                }

                if (ip) {

                    SanctionsManager.banip(player, UUID.fromString(result.getString("uuid")), reason);
                } else {

                    SanctionsManager.ban(player, UUID.fromString(result.getString("uuid")), reason);
                }

            }
        } catch (SQLException e) {
            ExceptionsManager.sqlExceptionLog(e);
        }
    }
}
