package fr.armotik.naurelliamoderation.commands;

import fr.armotik.naurelliamoderation.Louise;
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
            commandSender.sendMessage(Louise.getName() + "§cRaid mode deactivated !");

            Bukkit.broadcastMessage(Louise.getName() + "§cRaid mode deactivated !");
        } else {

            EventManager.setRaidMode(true);
            commandSender.sendMessage(Louise.getName() + "§aRaid mode activated !");

            Bukkit.broadcastMessage(Louise.getName() + "§aRaid mode activated !");
        }

        return true;
    }
}
