package fr.armotik.naurelliamoderation.listerners;

import fr.armotik.naurelliamoderation.Louise;
import fr.armotik.naurelliamoderation.commands.BanCommand;
import fr.armotik.naurelliamoderation.tools.SanctionsManager;
import fr.armotik.naurelliamoderation.utiles.Database;
import fr.armotik.naurelliamoderation.utiles.ExceptionsManager;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KickCommand {
    public KickCommand(Player player, String[] args) {

        Logger logger = Logger.getLogger(KickCommand.class.getName());

        TextComponent msg = new TextComponent(Louise.wrongCommand());
        msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§cCommand §7: §c/kick <player> <reason>")));

        assert player != null;
        if (!player.hasPermission("naurellia.staff")) {
            player.sendMessage(Louise.permissionMissing());
            return;
        }

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

        ResultSet res = Database.executeQuery("SELECT uuid FROM usersIG WHERE username = '" + args[0] + "'");

        if (res == null) {

            logger.log(Level.WARNING, "[NaurelliaModeration] -> KickCommand : onCommand ERROR - res == null");
            Database.close();
            return;
        }

        try {

            if (!res.next()) {

                player.spigot().sendMessage(msg);
                return;
            }

            SanctionsManager.kick(player, Objects.requireNonNull(Bukkit.getPlayer(UUID.fromString(res.getString("uuid")))), reason);
            Database.close();
        } catch (SQLException e) {
            ExceptionsManager.sqlExceptionLog(e);
            Database.close();
        }
    }
}
