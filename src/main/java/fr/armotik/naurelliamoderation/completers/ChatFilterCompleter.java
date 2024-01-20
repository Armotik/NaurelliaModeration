package fr.armotik.naurelliamoderation.completers;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public class ChatFilterCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {

        if (strings.length == 1) {
            return List.of("enable", "disable", "edit");
        }

        if (strings.length == 2) {
            return List.of("blacklist", "whitelist");
        }

        if (strings.length == 3) {
            return List.of("add", "remove");
        }

        return null;
    }
}
