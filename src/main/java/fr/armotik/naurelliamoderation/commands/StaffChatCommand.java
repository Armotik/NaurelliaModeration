package fr.armotik.naurelliamoderation.commands;

import fr.armotik.naurelliamoderation.Louise;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class StaffChatCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (strings.length == 0) {

            TextComponent msg = new TextComponent(Louise.wrongCommand());
            msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§cCommand §7: §c/staffchat <message>")));

            commandSender.spigot().sendMessage(msg);
            return false;
        }

        StringBuilder message = new StringBuilder();

        for (String string : strings) {
            message.append(string).append(" ");
        }

        Bukkit.getServer().getOnlinePlayers().forEach(player -> {
            if (player.hasPermission("naurellia.staff.helper")) {
                player.sendMessage("§c[StaffChat] §7" + commandSender.getName() + " §8» §f" + message);
            }
        });

        return true;
    }
}
