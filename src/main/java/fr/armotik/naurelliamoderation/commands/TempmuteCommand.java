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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TempmuteCommand implements CommandExecutor {
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

        Logger logger = Logger.getLogger(TempmuteCommand.class.getName());

        if (!(sender instanceof Player)) return false;

        TextComponent msg = new TextComponent(Louise.wrongCommand());
        msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§cCommand §7: §c/tempmute <player> <duration in day> <reason>")));

        Player player = ((Player) sender).getPlayer();


        if (args.length == 0) {

            assert player != null;
            player.spigot().sendMessage(msg);
            return false;
        }

        try {
            Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            ExceptionsManager.numberFormatExceptionLog(e);
            assert player != null;
            player.spigot().sendMessage(msg);
            return false;
        }

        String reason;
        if (args.length == 2) {
            reason = null;
        } else {
            reason = SanctionsManager.reasonDefBuilder(args, 2);
        }

        try (Connection conn = Database.getConnection()) {

            assert conn != null;
            try (Statement statement = conn.createStatement();
                 ResultSet res = statement.executeQuery("SELECT uuid FROM Players WHERE ign = '" + args[0] + "'");
            ) {

                if (res == null) {

                    logger.log(Level.WARNING, "[NaurelliaModeration] -> TempmuteCommand : onCommand ERROR - res == null");
                    return false;
                }

                if (!res.next()) {

                    assert player != null;
                    player.spigot().sendMessage(msg);
                    return false;
                }

                SanctionsManager.tempmute(player, UUID.fromString(res.getString("uuid")), reason, TimeUnit.DAYS.toMillis(Integer.parseInt(args[2])));
                return true;
            }

        } catch (SQLException e) {
            ExceptionsManager.sqlExceptionLog(e);
            return false;
        }
    }
}
