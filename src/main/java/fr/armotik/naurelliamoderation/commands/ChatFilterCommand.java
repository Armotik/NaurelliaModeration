package fr.armotik.naurelliamoderation.commands;

import fr.armotik.naurelliamoderation.Louise;
import fr.armotik.naurelliamoderation.listerners.ChatFilter;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.logging.Logger;

public class ChatFilterCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        TextComponent msg = new TextComponent(Louise.wrongCommand());
        msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§cCommand §7: §c/chatfilter <enable|disable|edit>")));

        Logger logger = Logger.getLogger(ChatFilterCommand.class.getName());

        if (strings.length < 1) {
            commandSender.spigot().sendMessage(msg);
            return false;
        }

        if (strings[0].equalsIgnoreCase("enable")) {
            if (ChatFilter.getChatFilterEnabled()) {

                commandSender.sendMessage(Louise.getName() + "§cChatFilter is already enabled");
                return false;
            }

            ChatFilter.setChatFilterEnabled(true);

            logger.info("ChatFilter enabled by " + commandSender.getName());
        }

        if (strings[0].equalsIgnoreCase("disable")) {
            if (!ChatFilter.getChatFilterEnabled()) {

                commandSender.sendMessage(Louise.getName() + "§cChatFilter is already disabled");
                return false;
            }

            ChatFilter.setChatFilterEnabled(false);

            logger.info("ChatFilter disabled by " + commandSender.getName());
        }

        if (strings[0].equalsIgnoreCase("edit")) {

            if (strings.length < 4) {

                msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§cCommand §7: §c/chatfilter edit <blacklist|whitelist> <add|remove> <word>")));

                commandSender.spigot().sendMessage(msg);
                return false;
            }

            if (strings[1].equalsIgnoreCase("blacklist")) {

                if (strings[2].equalsIgnoreCase("add")) {

                    if (ChatFilter.getBlackListedWords().contains(strings[3])) {

                        commandSender.sendMessage(Louise.getName() + "§cThis word is already blacklisted");
                        return false;
                    }

                    ChatFilter.getBlackListedWords().add(strings[3]);
                    commandSender.sendMessage(Louise.getName() + "§aThe word §e" + strings[3] + " §ahas been added to the blacklist");

                    logger.info("Word " + strings[3] + " added to the blacklist by " + commandSender.getName());
                }

                if (strings[2].equalsIgnoreCase("remove")) {

                    if (!ChatFilter.getBlackListedWords().contains(strings[3])) {

                        commandSender.sendMessage(Louise.getName() + "§cThis word is not blacklisted");
                        return false;
                    }

                    ChatFilter.getBlackListedWords().remove(strings[3]);
                    commandSender.sendMessage(Louise.getName() + "§aThe word §e" + strings[3] + " §ahas been removed from the blacklist");

                    logger.info("Word " + strings[3] + " removed from the blacklist by " + commandSender.getName());
                }
            }

            if (strings[1].equalsIgnoreCase("whitelist")) {

                commandSender.sendMessage(Louise.getName() + "§cThis feature is not available yet");
            }
        }

        return true;
    }
}
