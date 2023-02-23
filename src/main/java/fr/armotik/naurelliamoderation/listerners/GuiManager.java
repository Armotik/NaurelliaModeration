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

import java.lang.reflect.Field;
import java.util.UUID;

public class GuiManager implements Listener {

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

        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", textureURL));
        Field profileField;
        try {
            assert meta != null;
            profileField = meta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(meta, profile);
        } catch (NoSuchFieldException e) {
            ExceptionsManager.noSuchFieldExceptionLog(e);
        } catch (IllegalAccessException e) {
            ExceptionsManager.illegalAccessExceptionLog(e);
        }
        item.setItemMeta(meta);

        return item;
    }
}
