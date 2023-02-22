package fr.armotik.naurelliamoderation.listerners;

import fr.armotik.naurelliamoderation.Louise;
import fr.armotik.naurelliamoderation.commands.BanCommand;
import fr.armotik.naurelliamoderation.tools.SanctionsManager;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.event.server.TabCompleteEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Locale;
import java.util.logging.Logger;

public class EventManager implements Listener {

    private final Logger logger = Logger.getLogger(EventManager.class.getName());
    private final World world = Bukkit.getWorld("world");


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        PermissionManager.setupPermissions(player);

        if (player.hasPlayedBefore()) PermissionManager.readPermissions(player);

        SanctionsManager.checkInfractions(player);
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
    public void onTabComplete(TabCompleteEvent event) {

        if (!event.getSender().isOp()) {

            if (event.getBuffer().startsWith("naurelliamoderation:")) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {

        String cmd = event.getMessage().toLowerCase(Locale.ENGLISH);
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
            return;
        }

        if (cmd.startsWith("/ban ")) {
            event.setCancelled(true);

            if (!event.getPlayer().hasPermission("naurellia.admin")) {
                event.getPlayer().sendMessage(Louise.permissionMissing());
            } else {

                String[] args = event.getMessage().split(" ");
                Player staff = event.getPlayer();

                new BanCommand(staff, args, false);
                return;
            }
        }

        if (cmd.startsWith("/ban-ip ")) {
            event.setCancelled(true);

            if (!event.getPlayer().hasPermission("naurellia.admin")) {
                event.getPlayer().sendMessage(Louise.permissionMissing());
            } else {

                String[] args = event.getMessage().split(" ");
                Player staff = event.getPlayer();

                new BanCommand(staff, args, true);
                return;
            }
        }

        if (cmd.startsWith("/kick ")) {
            event.setCancelled(true);

            if (!event.getPlayer().hasPermission("naurellia.staff")) {
                event.getPlayer().sendMessage(Louise.permissionMissing());
            } else {

                String[] args = event.getMessage().split(" ");
                Player staff = event.getPlayer();

                new KickCommand(staff, args);
                return;
            }
        }
    }
}
