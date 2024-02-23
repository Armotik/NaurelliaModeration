package fr.armotik.naurelliamoderation.commands;

import fr.armotik.louise.louise.LouiseModeration;
import fr.armotik.naurelliamoderation.listerners.EventManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class RaidModeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (EventManager.isRaidMode()) {

            EventManager.setRaidMode(false);
            commandSender.sendMessage(LouiseModeration.getName() + "§cRaid mode deactivated !");

            Bukkit.broadcastMessage(LouiseModeration.getName() + "§cRaid mode deactivated !");
        } else {

            EventManager.setRaidMode(true);
            commandSender.sendMessage(LouiseModeration.getName() + "§aRaid mode activated !");

            Bukkit.broadcastMessage(LouiseModeration.getName() + "§aRaid mode activated !");
        }

        return true;
    }
}
