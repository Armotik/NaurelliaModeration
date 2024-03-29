package fr.armotik.naurelliamoderation.commands;

import fr.armotik.louise.louise.LouiseModeration;
import fr.armotik.naurelliamoderation.listerners.GuiManager;
import fr.armotik.naurelliamoderation.utilsclasses.Report;
import fr.armotik.louise.utiles.ExceptionsManager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReportsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (!(commandSender instanceof Player)) return false;

        Player player = (Player) commandSender;

        if (Report.getReports() == null || Report.getReports().isEmpty()) {
            player.sendMessage(LouiseModeration.getName() + "§cNo reports found");
            return false;
        }

        //check if all reports was treated
        boolean allTreated = true;

        for (Report report : Report.getReports()) {
            if (!report.isTreated()) {
                allTreated = false;
                break;
            }
        }

        if (allTreated) {
            player.sendMessage(LouiseModeration.getName() + "§cAll reports are treated");
            return false;
        }

        try {
            if (strings.length == 0) {
                GuiManager.reportGui(player, 1);
            } else {
                int page = Integer.parseInt(strings[0]);
                GuiManager.reportGui(player, page);
            }
        } catch (NumberFormatException e) {
            ExceptionsManager.numberFormatExceptionLog(e);
            player.sendMessage(LouiseModeration.getName() + "§cInvalid page number §7: §c" + strings[0]);
            return false;
        }

        return true;
    }
}
