package fr.armotik.naurelliamoderation.listerners;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import fr.armotik.naurelliamoderation.guis.ItemsModGui;
import fr.armotik.naurelliamoderation.guis.ItemsModGui2;
import fr.armotik.naurelliamoderation.utiles.ExceptionsManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.UUID;

public class GuiManager implements Listener {

    private static final UUID RANDOM_UUID = UUID.randomUUID();

    /**
     * Init mod GUI and open it
     * @param player who opened the inventory
     * @param targetUUID for who the inventory is open
     */
    public static void modGui(Player player, UUID targetUUID) {

        Inventory inv = Bukkit.createInventory(null, 54, ChatColor.GOLD + "Mod Menu : " + Bukkit.getOfflinePlayer(targetUUID).getName());
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

        Inventory inv = Bukkit.createInventory(null, 54, "§6Mod Menu : " + Bukkit.getOfflinePlayer(targetUUID).getName());
        ItemsModGui2 itemsGUIS = new ItemsModGui2(inv);

        itemsGUIS.itemsModGui2();

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
    public static PlayerProfile getProfile(String url) {
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
}
