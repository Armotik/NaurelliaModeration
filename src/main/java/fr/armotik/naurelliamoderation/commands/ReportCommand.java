package fr.armotik.naurelliamoderation.commands;

import fr.armotik.naurelliamoderation.Louise;
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
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

public class ReportCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (!(commandSender instanceof Player)) return false;

        if (strings.length < 2) {

            TextComponent msg = new TextComponent(Louise.wrongCommand());
            msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§cCommand §7: §c/report <player> <reason>")));

            commandSender.spigot().sendMessage(msg);
            return false;
        }

        Player player = (Player) commandSender;
        Player target = Bukkit.getServer().getPlayer(strings[0]);

        if (target == null) {

            TextComponent msg = new TextComponent(Louise.playerNotFound());
            msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§cCommand §7: §c/report <player> <reason>")));

            commandSender.spigot().sendMessage(msg);
            return false;
        }

        if (target == player && !player.hasPermission("naurellia.staff.tester")) {

            TextComponent msg = new TextComponent(Louise.wrongCommand());
            msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§cYou can't report yourself")));

            commandSender.spigot().sendMessage(msg);
            return false;
        }

        String reason;

        if (strings.length == 2) {

            reason = strings[1];
        } else {

            reason = SanctionsManager.reasonDefBuilder(strings, 0);
        }

        SanctionsManager.report(player, target, reason);

        return true;
    }
}
