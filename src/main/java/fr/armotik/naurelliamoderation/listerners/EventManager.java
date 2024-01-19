package fr.armotik.naurelliamoderation.listerners;

import fr.armotik.naurelliamoderation.Louise;
import fr.armotik.naurelliamoderation.commands.BanCommand;
import fr.armotik.naurelliamoderation.commands.KickCommand;
import fr.armotik.naurelliamoderation.reports.Report;
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
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.server.TabCompleteEvent;
import org.bukkit.inventory.ItemStack;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EventManager implements Listener {

    private final Logger logger = Logger.getLogger(EventManager.class.getName());
    private static Map<UUID, Boolean> frozen = new HashMap<>();

    public static Map<UUID, Boolean> getFrozen() {
        return frozen;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        if (player.hasPlayedBefore()) {
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

            if (!player.hasPermission("naurellia.staff.helper") && message.toLowerCase(Locale.ENGLISH).contains(word)) {

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

            if (!event.getPlayer().hasPermission("naurellia.staff.admin")) {
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

            if (!event.getPlayer().hasPermission("naurellia.staff.admin")) {
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

            if (!event.getPlayer().hasPermission("naurellia.staff.helper")) {
                event.getPlayer().sendMessage(Louise.permissionMissing());
            } else {

                String[] args = event.getMessage().split(" ");
                Player staff = event.getPlayer();

                new KickCommand(staff, args);
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {

        Player player = event.getPlayer();

        if (getFrozen().containsKey(player.getUniqueId())) {

            event.setCancelled(true);
            player.sendMessage(Louise.getName() + "§cYou're frozen, you can't move !");
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        if (!(event.getWhoClicked() instanceof Player)) return;

        if (event.getCurrentItem() == null) return;

        Player player = (Player) event.getWhoClicked();

        if (event.getClickedInventory() != null &&
                event.getClickedInventory().getHolder() instanceof Player &&
                (player.hasPermission("naurellia.staff.helper") && !player.hasPermission("naurellia.staff.srmod"))) {

            event.setCancelled(true);
            player.sendMessage(Louise.getName() + "§cYou can't interact with this inventory !");
            return;
        }

        if (player.hasPermission("naurellia.staff.helper")) {

            if (player.getOpenInventory().getTitle().startsWith("§6Moderation Menu : §c§l")) {

                event.setCancelled(true);

                ItemStack current = event.getCurrentItem();
                OfflinePlayer target = null;

                try (Connection conn = Database.getConnection()) {

                    assert conn != null;

                    try (Statement statement = conn.createStatement();
                         ResultSet res = statement.executeQuery("SELECT uuid FROM Players WHERE ign='" + player.getOpenInventory().getTitle().split("§c§l")[1] + "'");
                    ) {
                        if (res == null) {

                            logger.log(Level.WARNING, "[NaurelliaModeration] -> EventManager : onInventoryClick ERROR - res == null");
                            return;
                        }

                        if (res.next()) {

                            target = Bukkit.getOfflinePlayer(UUID.fromString(res.getString("uuid")));
                        }
                    }
                } catch (SQLException e) {

                    ExceptionsManager.sqlExceptionLog(e);
                    return;
                }

                if (target == null) {

                    player.sendMessage(Louise.playerNotFound());
                    return;
                }

                if (current.getType() == Material.PLAYER_HEAD) {

                    switch (Objects.requireNonNull(current.getItemMeta()).getDisplayName()) {

                        case "§aOpen Inventory" -> {
                            player.getOpenInventory().close();
                            player.openInventory(Objects.requireNonNull(target.getPlayer()).getInventory());
                            break;
                        }

                        case "§aVanish" -> {
                            // Todo
                        }

                        case "§bFreeze" -> {

                            if (!target.isOnline()) {

                                player.sendMessage(Louise.getName() + "§cThis player is not online !");
                                player.getOpenInventory().close();
                            }

                            if (getFrozen().containsKey(target.getUniqueId())) {

                                getFrozen().remove(target.getUniqueId());
                                player.sendMessage(Louise.getName() + "§aYou unfroze " + target.getName() + "§a !");
                                Objects.requireNonNull(target.getPlayer()).sendMessage(Louise.getName() + "§aYou've been unfrozen by " + player.getName() + "§a !");
                                player.getOpenInventory().close();
                            } else {

                                getFrozen().put(target.getUniqueId(), true);
                                player.sendMessage(Louise.getName() + "§aYou froze " + target.getName() + "§a !");
                                Objects.requireNonNull(target.getPlayer()).sendMessage(Louise.getName() + "§aYou've been frozen by " + player.getName() + "§a !");
                                player.getOpenInventory().close();
                            }
                            break;
                        }

                        case "§cSanctions" -> {
                            GuiManager.modGui(player, target.getUniqueId());
                        }

                        default -> {
                        }
                    }
                }
            }

            if (player.getOpenInventory().getTitle().equalsIgnoreCase("§cReports")) {

                event.setCancelled(true);

                ItemStack current = event.getCurrentItem();

                int page = Integer.parseInt(Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(event.getClickedInventory()).getItem(49)).getItemMeta()).getDisplayName().split("§6")[1]);

                if (current.getType() == Material.PLAYER_HEAD) {

                    switch (Objects.requireNonNull(current.getItemMeta()).getDisplayName()) {

                        case "§cNext Page" -> {
                            player.getOpenInventory().close();
                            GuiManager.reportGui(player, page + 1);
                        }

                        case "§cPrevious Page" -> {
                            player.getOpenInventory().close();
                            GuiManager.reportGui(player, page - 1);
                        }

                        default -> {
                        }
                    }

                    if (current.getItemMeta().hasLore() && player.hasPermission("naurellia.staff.mod")) {

                        List<String> lore = current.getItemMeta().getLore();

                        if (lore == null) return;

                        if (lore.size() > 4) return;

                        int id = Integer.parseInt(lore.get(0).split("§6")[1]);

                        Report report = Report.getReports().get(id);

                        if (report == null || report.isTreated()) return;

                        player.getOpenInventory().close();
                        GuiManager.modifyReportGui(player, report);
                    }
                }
            }

            if (player.getOpenInventory().getTitle().startsWith("§cReport : §6")) {

                event.setCancelled(true);

                ItemStack current = event.getCurrentItem();

                if (current.getType() == Material.PLAYER_HEAD && Objects.requireNonNull(current.getItemMeta()).getDisplayName().equalsIgnoreCase("§aResolved")) {

                    int id = Integer.parseInt(player.getOpenInventory().getTitle().split("§6")[1]);

                    Report report = Report.getReports().get(id);

                    if (report == null || report.isTreated()) return;

                    try (Connection conn = Database.getConnection()) {

                        assert conn != null;

                        try (Statement statement = conn.createStatement()) {

                            statement.executeUpdate("UPDATE Reports SET isTreated=1, wasTreatedBy='" + player.getUniqueId() + "' WHERE id=" + id);

                            report.setTreated(true);
                            player.getOpenInventory().close();
                            player.sendMessage(Louise.getName() + "§aYou resolved the report §6#" + id + "§a !");
                            GuiManager.reportGui(player, 1);
                        }
                    } catch (SQLException e) {

                        ExceptionsManager.sqlExceptionLog(e);

                        player.sendMessage(Louise.getName() + "§cAn error occurred while resolving the report §6#" + id + "§c !");
                        player.getOpenInventory().close();
                        GuiManager.reportGui(player, 1);
                    }
                }
            }

            if (player.getOpenInventory().getTitle().startsWith("§6Sanctions : §c§l")) {

                event.setCancelled(true);

                ItemStack current = event.getCurrentItem();
                OfflinePlayer target = null;

                try (Connection conn = Database.getConnection()) {

                    assert conn != null;
                    try (Statement statement = conn.createStatement();
                         ResultSet res = statement.executeQuery("SELECT uuid FROM Players WHERE ign='" + player.getOpenInventory().getTitle().split("§c§l")[1] + "'");
                    ) {
                        if (res == null) {

                            logger.log(Level.WARNING, "[NaurelliaModeration] -> EventManager : onInventoryClick ERROR - res == null");
                            return;
                        }

                        if (res.next()) {

                            target = Bukkit.getOfflinePlayer(UUID.fromString(res.getString("uuid")));
                        }
                    }
                } catch (SQLException e) {

                    ExceptionsManager.sqlExceptionLog(e);
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

                        case "§cTEST MUTE" -> {
                            SanctionsManager.tempmute(player, target.getUniqueId(), "TEST MUTE", TimeUnit.SECONDS.toMillis(10));
                            player.getOpenInventory().close();
                        }

                        case "§cTEST BAN" -> {
                            SanctionsManager.tempban(player, target.getUniqueId(), "TEST BAN", TimeUnit.SECONDS.toMillis(10));
                            player.getOpenInventory().close();
                        }
                        case "§cMenu" -> {
                            GuiManager.mainMenuModGui(player, target.getUniqueId());
                        }
                        case "§dReport Abuse" -> {
                            SanctionsManager.warn(player, target.getUniqueId(), "REPORT ABUSE");
                            player.getOpenInventory().close();
                        }
                        case "§dReport Abuse (Relapse)" -> {
                            SanctionsManager.tempmute(player, target.getUniqueId(), "REPORT ABUSE (RELAPSE)", TimeUnit.HOURS.toMillis(6));
                            player.getOpenInventory().close();
                        }
                        default -> {
                        }
                    }
                }
            }
        }
    }
}
