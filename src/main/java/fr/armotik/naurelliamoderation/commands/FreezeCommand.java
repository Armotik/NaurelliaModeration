package fr.armotik.naurelliamoderation.commands;

import fr.armotik.louise.louise.LouiseModeration;
import fr.armotik.naurelliamoderation.listerners.EventManager;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FreezeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (!(commandSender instanceof Player)) return false;

        if (strings.length == 0) {
            TextComponent msg = new TextComponent(LouiseModeration.wrongCommand());
            msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§cCommand §7: §c/freeze <player>")));

            commandSender.spigot().sendMessage(msg);
            return false;
        }

        Player target = Bukkit.getServer().getPlayer(strings[0]);

        if (target == null) {
            TextComponent msg = new TextComponent(LouiseModeration.playerNotFound());
            msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§cPlayer §7: §c" + strings[0] + " §cnot found")));

            commandSender.spigot().sendMessage(msg);
            return false;
        }

        if (target.hasPermission("naurellia.staff.helper")) {
            commandSender.sendMessage(LouiseModeration.getName() + "§cYou can't freeze a staff member");
            return false;
        }

        if (!target.isOnline()) {
            commandSender.sendMessage(LouiseModeration.getName() + "§cThis player is not online");
            return false;
        }

        EventManager.getFrozen().put(target.getUniqueId(), true);

        commandSender.sendMessage(LouiseModeration.getName() + "§aYou have frozen §e" + target.getName());

        return true;
    }
}
