package fr.armotik.naurelliamoderation.listerners;

import fr.armotik.naurelliamoderation.guis.*;
import fr.armotik.naurelliamoderation.utilsclasses.Report;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

import java.net.URL;
import java.util.UUID;

public class GuiManager implements Listener {

    private static final UUID RANDOM_UUID = UUID.randomUUID();

    public static void mainMenuModGui(Player player, UUID targUUID) {

            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(targUUID);

            Inventory inv = Bukkit.createInventory(null, 45, "§6Moderation Menu : §c§l" + offlinePlayer.getName());
            ItemsModMenuGui itemsGUIS = new ItemsModMenuGui(inv, offlinePlayer, player);

            itemsGUIS.mainMenuGui();

            player.openInventory(inv);
    }

    /**
     * Init mod GUI and open it
     * @param player who opened the inventory
     * @param targetUUID for who the inventory is open
     */
    public static void modGui(Player player, UUID targetUUID) {

        Inventory inv = Bukkit.createInventory(null, 54, ChatColor.GOLD + "Sanctions : §c§l" + Bukkit.getOfflinePlayer(targetUUID).getName());
        ItemsModGui itemsGUIS = new ItemsModGui(inv);

        itemsGUIS.itemsModGui();

        player.openInventory(inv);
    }

    /**
     * Init the second page of mod GUI and open it
     * @param player who opened the inventory
     * @param targetUUID for who the inventory is open
     */
    public static void modGui2(Player player, UUID targetUUID) {

        Inventory inv = Bukkit.createInventory(null, 54, "§6Sanctions : §c§l" + Bukkit.getOfflinePlayer(targetUUID).getName());
        ItemsModGui2 itemsGUIS = new ItemsModGui2(inv);

        itemsGUIS.itemsModGui2();

        player.openInventory(inv);
    }

    public static void reportGui(Player player, int page) {

        Inventory inv = Bukkit.createInventory(null, 54, "§cReports");
        ItemsReportGui itemsGUIS = new ItemsReportGui(inv, page, null);

        itemsGUIS.reportGui();

        player.openInventory(inv);
    }

    public static void modifyReportGui(Player player, Report report) {

        Inventory inv = Bukkit.createInventory(null, 9, "§cReport : §6" + report.getId());
        ItemModifyReportGui itemModifyReportGUi = new ItemModifyReportGui(inv, report);

        itemModifyReportGUi.modifyReportGui();

        player.openInventory(inv);
    }

    public static void infractionsGui(Player player, OfflinePlayer target, int page) {

        Inventory inv = Bukkit.createInventory(null, 54, "§cInfractions : §6" + target.getName());
        ItemsInfractionsGui itemsGUIS = new ItemsInfractionsGui(inv, target, player, page);

        itemsGUIS.infractionsGui();

        player.openInventory(inv);
    }

    /**
     * Create custom head by using texture URL
     * @param textureURL texture URL
     * @return ItemStack - New Head
     */
    public static ItemStack headFactory(String textureURL) {

        PlayerProfile profile = getProfile(textureURL);
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        assert meta != null;
        meta.setOwnerProfile(profile);
        item.setItemMeta(meta);

        return item;
    }
    private static PlayerProfile getProfile(String url) {
        PlayerProfile profile = Bukkit.createPlayerProfile(RANDOM_UUID);
        PlayerTextures textures = profile.getTextures();
        URL urlObject;

        try {
            urlObject = new URL(url);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        textures.setSkin(urlObject);
        profile.setTextures(textures);
        return profile;
    }

    public static ItemStack getPlayerHead(OfflinePlayer player) {

        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        assert meta != null;
        meta.setOwningPlayer(player);
        head.setItemMeta(meta);

        return head;
    }
}
