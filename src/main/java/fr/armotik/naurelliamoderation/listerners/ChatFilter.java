package fr.armotik.naurelliamoderation.listerners;

import fr.armotik.naurelliamoderation.Louise;
import fr.armotik.naurelliamoderation.NaurelliaModeration;
import fr.armotik.naurelliamoderation.tools.SanctionsManager;
import fr.armotik.naurelliamoderation.utiles.ExceptionsManager;
import fr.armotik.naurelliamoderation.utiles.FilesReader;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class ChatFilter implements Listener {

    private static final long TIME_BETWEEN_MESSAGES = TimeUnit.SECONDS.toMillis(3);
    private static final int MAX_MESSAGES = 5;
    private static List<String> blackListedWords = new ArrayList<>();
    private static Boolean chatFilterEnabled = true;
    private static Map<Player, Long> lastMessageTime = new HashMap<>();
    private static Map<Player, Integer> messageCount = new HashMap<>();
    private static final List<String> authorizedLinks = new ArrayList<>();

    public ChatFilter() {

        try {

            InputStream inputStream = new FileInputStream("blacklistedWords.txt");
            Stream<String> data = FilesReader.readStrings(inputStream);
            data.forEach(blackListedWords::add);
        } catch (FileNotFoundException e) {

            ExceptionsManager.fileNotFoundExceptionLog(e);
        }

        Logger logger = Logger.getLogger(ChatFilter.class.getName());
        logger.log(Level.INFO, "[NaurelliaModeration] -> blacklistedWords successfully added to the memory");

        authorizedLinks.addAll(NaurelliaModeration.getPlugin().getConfig().getStringList("authorized-links"));

        lastMessageTime = new HashMap<>();
        messageCount = new HashMap<>();
    }

    public static Boolean getChatFilterEnabled() {
        return chatFilterEnabled;
    }

    public static void setChatFilterEnabled(Boolean chatFilterEnabled) {
        ChatFilter.chatFilterEnabled = chatFilterEnabled;
    }

    public static List<String> getBlackListedWords() {
        return blackListedWords;
    }

    public static void setBlackListedWords(List<String> blackListedWords) {
        ChatFilter.blackListedWords = blackListedWords;
    }

    @EventHandler
    public void onCommandPreProcess(PlayerCommandPreprocessEvent event) {

        Player player = event.getPlayer();

        if (!SanctionsManager.getBannedFromChat().isEmpty() && SanctionsManager.getBannedFromChat().containsKey(event.getPlayer().getUniqueId())) {

            event.setCancelled(true);
            TextComponent msg = new TextComponent(Louise.getName() + "§cYou're muted, you can't speak in the chat !");
            msg.setHoverEvent(new HoverEvent(
                    HoverEvent.Action.SHOW_TEXT, new Text("§cPlease refer to our rules")
            ));
            msg.setClickEvent(new ClickEvent(
                    ClickEvent.Action.OPEN_URL, "https://www.naurelliacraft.com/rules/#chat"
            ));

            player.spigot().sendMessage(msg);
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();
        String message = event.getMessage();

        if (SanctionsManager.getBannedFromChat().containsKey(player.getUniqueId())) {

            event.setCancelled(true);
            TextComponent msg = new TextComponent(Louise.getName() + "§cYou're muted, you can't speak in the chat !");
            msg.setHoverEvent(new HoverEvent(
                    HoverEvent.Action.SHOW_TEXT, new Text("§cPlease refer to our rules")
            ));
            msg.setClickEvent(new ClickEvent(
                    ClickEvent.Action.OPEN_URL, "https://www.naurelliacraft.com/rules/#chat"
            ));

            player.spigot().sendMessage(msg);
            return;
        }

        for (String word : blackListedWords) {

            if (!player.hasPermission("naurellia.staff.helper") && message.toLowerCase(Locale.ENGLISH).contains(word) && ChatFilter.chatFilterEnabled) {

                event.setCancelled(true);
                TextComponent msg = new TextComponent(Louise.chatFilter());
                msg.setHoverEvent(new HoverEvent(
                        HoverEvent.Action.SHOW_TEXT, new Text("§cPlease refer to our rules")
                ));
                msg.setClickEvent(new ClickEvent(
                        ClickEvent.Action.OPEN_URL, "https://www.naurelliacraft.com/rules/#chat"
                ));

                player.spigot().sendMessage(msg);
            }
        }

        if (lastMessageTime.containsKey(player)) {

            if (System.currentTimeMillis() - lastMessageTime.get(player) < TIME_BETWEEN_MESSAGES) {

                messageCount.put(player, messageCount.get(player) + 1);

                if (messageCount.get(player) > MAX_MESSAGES && !player.hasPermission("naurellia.staff.helper")) {

                    SanctionsManager.tempmute(null, player.getUniqueId(), "CHATFILTER - SPAM", TimeUnit.HOURS.toMillis(1));
                } else if (messageCount.get(player) == MAX_MESSAGES && player.hasPermission("naurellia.staff.helper")) {

                    SanctionsManager.warn(null, player.getUniqueId(), "CHATFILTER - SPAM");
                }

                messageCount.put(player, 0);
            }


            if (System.currentTimeMillis() - lastMessageTime.get(player) > TIME_BETWEEN_MESSAGES) {

                messageCount.put(player, 0);
            }
        }

        else {

            lastMessageTime.put(player, System.currentTimeMillis());
            messageCount.put(player, 1);
        }

        for (String link : authorizedLinks) {

            if ((message.startsWith("http://") ||
                    message.startsWith("https://") ||
                    message.startsWith("www.") ||
                    message.startsWith("ftp://") ||
                    message.startsWith("ftps://")) &&
                    message.contains(link)
            ) {

                event.setCancelled(true);
                TextComponent msg = new TextComponent(Louise.getName() + "§cYou can't send links in the chat !");
                msg.setHoverEvent(new HoverEvent(
                        HoverEvent.Action.SHOW_TEXT, new Text("§cPlease refer to our rules")
                ));
                msg.setClickEvent(new ClickEvent(
                        ClickEvent.Action.OPEN_URL, "https://www.naurelliacraft.com/rules/#chat"
                ));

                player.spigot().sendMessage(msg);
            }
        }
    }
}
