package fr.armotik.naurelliamoderation.commands;

import fr.armotik.louise.louise.LouiseModeration;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InvseeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (!(commandSender instanceof Player)) return false;

        Player player = ((Player) commandSender).getPlayer();

        assert player != null;

        if (strings.length == 0) {

            TextComponent msg = new TextComponent(LouiseModeration.wrongCommand());
            msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§cCommand §7: §c/openinv <player>")));

            player.spigot().sendMessage(msg);
            return false;
        }

        Player target = Bukkit.getServer().getPlayer(strings[0]);

        if (target == null) {
            player.sendMessage(LouiseModeration.getName() + "§cPlayer not found.");
            return false;
        }

        if (!target.isOnline()) {
            player.sendMessage(LouiseModeration.getName() + "§cPlayer not online.");
            return false;
        }

        if (target == player) {
            player.sendMessage(LouiseModeration.getName() + "§cYou can't see your own inventory.");
            return false;
        }

        player.openInventory(target.getInventory());

        return false;
    }
}
