package fr.armotik.naurelliamoderation.completers;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class StaffCommandCompleter implements TabCompleter {
    private final List<String> staffCommands = new ArrayList<>();
    private List<String> res = new ArrayList<>();

    /**
     * Requests a list of possible completions for a command argument.
     *
     * @param sender  Source of the command.  For players tab-completing a
     *                command inside a command block, this will be the player, not
     *                the command block.
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    The staffCommands passed to the command, including final
     *                partial argument to be completed
     * @return A List of possible completions for the final argument, or null
     * to default to the command executor
     */
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("naurellia.staff")) {
                List<String> completions = new ArrayList<>();
                if (args.length == 1) {
                    completions.add("/warn");
                    completions.add("/infraction");
                } else if (args[0].equalsIgnoreCase("/warn")) {

                    return null;
                }
                return completions;
            } else {
                return new ArrayList<>();
            }
        } else {
            return new ArrayList<>();
        }
    }
}
