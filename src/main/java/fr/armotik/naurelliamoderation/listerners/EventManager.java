package fr.armotik.naurelliamoderation.listerners;

import fr.armotik.naurelliamoderation.Louise;
import fr.armotik.naurelliamoderation.tools.ChatFilter;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.TabCompleteEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

public class EventManager implements Listener {

    private final Logger logger = Logger.getLogger(EventManager.class.getName());
    private final World world = Bukkit.getWorld("world");
    private final List<String> commandList = new ArrayList<>();

    public EventManager() {

        commandList.add("/warn");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        PermissionManager.setupPermissions(player);

        if (player.hasPlayedBefore()) PermissionManager.readPermissions(player);
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();
        String message = event.getMessage();

        for (String word : ChatFilter.blackListedWords) {

            if (!player.hasPermission("naurellia.staff") && message.toLowerCase(Locale.ENGLISH).contains(word)) {

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
    }

    @EventHandler
    public void onPlayerCommandPreProcess(PlayerCommandPreprocessEvent event) {

        String message = event.getMessage();
        Player player = event.getPlayer();

        System.out.println(player.hasPermission("naurellia.staff"));

        if (!player.hasPermission("naurellia.staff")) {

            if (commandList.contains(message)) {

                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void test(TabCompleteEvent event) {

        if (!event.getSender().isOp()) {

            if(event.getBuffer().startsWith("naurelliamoderation:")) {
                event.setCancelled(true);
            }
        }
    }
}
