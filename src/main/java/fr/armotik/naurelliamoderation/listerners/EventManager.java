package fr.armotik.naurelliamoderation.listerners;

import fr.armotik.naurelliamoderation.Louise;
import fr.armotik.naurelliamoderation.commands.BanCommand;
import fr.armotik.naurelliamoderation.tools.SanctionsManager;
import fr.armotik.naurelliamoderation.utiles.Database;
import fr.armotik.naurelliamoderation.utiles.ExceptionsManager;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.server.TabCompleteEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EventManager implements Listener {

    private final Logger logger = Logger.getLogger(EventManager.class.getName());


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        PermissionManager.setupPermissions(player);

        if (player.hasPlayedBefore()) {
            PermissionManager.readPermissions(player);
            SanctionsManager.checkInfractions(player);
        }

        if (!ConnectionsManager.ConnectionChecker(player)) {

            player.kickPlayer("§cCONNECTION REFUSED - VPN");
            logger.log(Level.INFO, player.getName() + "[NaurelliaModeration] -> kicked (CONNECTION REFUSED - VPN) ");
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

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        if (!(event.getWhoClicked() instanceof Player)) return;

        if (event.getCurrentItem() == null) return;

        Player player = (Player) event.getWhoClicked();

        if (player.hasPermission("naurellia.staff")) {

            if (player.getOpenInventory().getTitle().startsWith("Mod Menu :")) {

                event.setCancelled(true);

                ItemStack current = event.getCurrentItem();
                OfflinePlayer target = null;

                ResultSet res = Database.executeQuery("SELECT uuid FROM usersIG WHERE username='" + player.getOpenInventory().getTitle().split(" : ")[1] + "'");

                if (res == null) {

                    Database.close();
                    logger.log(Level.WARNING, "[NaurelliaModeration] -> EventManager : onInventoryClick ERROR - res == null");
                    return;
                }

                try {
                    if (res.next()) {

                        target = Bukkit.getOfflinePlayer(UUID.fromString(res.getString("uuid")));
                        Database.close();
                    }
                } catch (SQLException e) {

                    ExceptionsManager.sqlExceptionLog(e);
                    Database.close();
                    return;
                }

                if (target == null) {

                    player.sendMessage(Louise.playerNotFound());
                    return;
                }

                if (current.getType() == Material.PLAYER_HEAD) {
                    switch (Objects.requireNonNull(current.getItemMeta()).getDisplayName()) {

                        case "§bSpam" -> {
                            SanctionsManager.warn(player, target.getUniqueId(), "SPAM");
                            player.getOpenInventory().close();
                        }

                        case "§bFlood" -> {
                            SanctionsManager.warn(player, target.getUniqueId(), "FLOOD");
                            player.getOpenInventory().close();
                        }

                        case "§dSpam (Relapse)" -> {
                            SanctionsManager.tempmute(player, target.getUniqueId(), "SPAM (relapse)", TimeUnit.HOURS.toMillis(1));
                            player.getOpenInventory().close();
                        }

                        case "§dChat Filter Bypass" -> {
                            SanctionsManager.tempmute(player, target.getUniqueId(), "CHAT FILTER BYPASS", TimeUnit.HOURS.toMillis(1));
                            player.getOpenInventory().close();
                        }

                        case "§cDiscrimination" -> {
                            SanctionsManager.tempban(player, target.getUniqueId(), "DISCRIMINATION", TimeUnit.DAYS.toMillis(14));
                            player.getOpenInventory().close();
                        }

                        case "§cTroll" -> {
                            SanctionsManager.ban(player, target.getUniqueId(), "TROLL");
                            player.getOpenInventory().close();
                        }

                        case "§cGlitch" -> {
                            SanctionsManager.tempban(player, target.getUniqueId(), "GLITCH", TimeUnit.DAYS.toMillis(7));
                            player.getOpenInventory().close();
                        }

                        case "§dToxicity" -> {
                            SanctionsManager.tempmute(player, target.getUniqueId(), "TOXICITY", TimeUnit.DAYS.toMillis(1));
                            player.getOpenInventory().close();
                        }

                        case "§dHarassment" -> {
                            SanctionsManager.tempmute(player, target.getUniqueId(), "HARASSMENT", TimeUnit.DAYS.toMillis(3));
                            player.getOpenInventory().close();
                        }

                        case "§cHarassment (Relapse)" -> {
                            SanctionsManager.tempban(player, target.getUniqueId(), "HARASSMENT (RELAPSE)", TimeUnit.DAYS.toMillis(14));
                            player.getOpenInventory().close();
                        }

                        case "§cToxicity (Relapse)" -> {
                            SanctionsManager.tempban(player, target.getUniqueId(), "TOXICITY (RELAPSE)", TimeUnit.DAYS.toMillis(7));
                            player.getOpenInventory().close();
                        }

                        case "§cAlt Account" -> {
                            SanctionsManager.ban(player, target.getUniqueId(), "ALT ACCOUNT");
                            player.getOpenInventory().close();
                        }

                        case "§cAlt Usage" -> {
                            SanctionsManager.tempban(player, target.getUniqueId(), "ALT USAGE", TimeUnit.DAYS.toMillis(14));
                            player.getOpenInventory().close();
                        }

                        case "§cAlt Usage (Relapse)" -> {
                            SanctionsManager.tempban(player, target.getUniqueId(), "ALT USAGE (RELAPSE", TimeUnit.DAYS.toMillis(30));
                            player.getOpenInventory().close();
                        }

                        case "§bGriefing (Low)" -> {
                            SanctionsManager.warn(player, target.getUniqueId(), "GRIEFING (LOW)");
                            player.getOpenInventory().close();
                        }

                        case "§cGriefing (High | Relapse)" -> {
                            SanctionsManager.tempban(player, target.getUniqueId(), "GRIEFING (HIGH | RELAPSE", TimeUnit.DAYS.toMillis(7));
                            player.getOpenInventory().close();
                        }

                        case "§cServer Griefing" -> {
                            SanctionsManager.banip(player, target.getUniqueId(), "SERVER GRIEFING");
                            player.getOpenInventory().close();
                        }

                        case "§cDuping" -> {
                            SanctionsManager.tempban(player, target.getUniqueId(), "DUPING", TimeUnit.DAYS.toDays(365));
                            player.getOpenInventory().close();
                        }

                        case "§cIRL Trading" -> {
                            SanctionsManager.ban(player, target.getUniqueId(), "IRL TRADING");
                            player.getOpenInventory().close();
                        }

                        case "§cDoxxing" -> {
                            SanctionsManager.ban(player, target.getUniqueId(), "DOXXING");
                            player.getOpenInventory().close();
                        }

                        case "§cImpersonating" -> {
                            SanctionsManager.tempban(player, target.getUniqueId(), "IMPERSONATING", TimeUnit.DAYS.toDays(3));
                            player.getOpenInventory().close();
                        }

                        case "§dInappropriate Discussion" -> {
                            SanctionsManager.tempmute(player, target.getUniqueId(), "INAPPROPRIATE DISCUSSION", TimeUnit.DAYS.toDays(1));
                            player.getOpenInventory().close();
                        }

                        case "§dNon English Chatting" -> {
                            SanctionsManager.tempmute(player, target.getUniqueId(), "NON ENGLISH CHATTING", TimeUnit.HOURS.toMillis(1));
                            player.getOpenInventory().close();
                        }

                        case "§cSpawn Killing" -> {
                            SanctionsManager.tempban(player, target.getUniqueId(), "SPAWN KILLING", TimeUnit.DAYS.toMillis(3));
                            player.getOpenInventory().close();
                        }

                        case "§cNext Page" -> {
                            GuiManager.modGui2(player, target.getUniqueId());
                        }

                        case "§cPrevious Page" -> {
                            GuiManager.modGui(player, target.getUniqueId());
                        }
                        default -> {}
                    }
                }
            }
        }
    }
}
