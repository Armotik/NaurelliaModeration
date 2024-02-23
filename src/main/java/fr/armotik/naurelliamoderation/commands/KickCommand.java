package fr.armotik.naurelliamoderation.commands;

import fr.armotik.louise.louise.LouiseModeration;
import fr.armotik.naurelliamoderation.tools.SanctionsManager;
import fr.armotik.naurelliamoderation.utiles.Database;
import fr.armotik.louise.utiles.ExceptionsManager;

import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KickCommand {
    public KickCommand(Player player, String[] args) {

        Logger logger = Logger.getLogger(KickCommand.class.getName());

        TextComponent msg = new TextComponent(LouiseModeration.wrongCommand());
        msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§cCommand §7: §c/kick <player> <reason>")));

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

        try (Connection conn = Database.getConnection()) {

            assert conn != null;
            try (Statement statement = conn.createStatement();
                 ResultSet res = statement.executeQuery("SELECT uuid FROM Players WHERE ign = '" + args[0] + "'");
            ) {

                if (res == null) {

                    logger.log(Level.WARNING, "[NaurelliaModeration] -> KickCommand : onCommand ERROR - res == null");
                    return;
                }

                if (!res.next()) {

                    player.spigot().sendMessage(msg);
                    return;
                }

                SanctionsManager.kick(player, Objects.requireNonNull(Bukkit.getPlayer(UUID.fromString(res.getString("uuid")))), reason);
                Database.close();
            }
        } catch (SQLException e) {
            ExceptionsManager.sqlExceptionLog(e);
        }
    }
}
