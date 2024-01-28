package fr.armotik.naurelliamoderation.guis;

import fr.armotik.naurelliamoderation.Louise;
import fr.armotik.naurelliamoderation.listerners.GuiManager;
import fr.armotik.naurelliamoderation.utiles.Database;
import fr.armotik.naurelliamoderation.utiles.ExceptionsManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class ItemsInfractionsGui {

    private final Inventory inventory;
    private final OfflinePlayer target;
    private final Player player;
    private int page = 1;
    private final Logger logger = Logger.getLogger(ItemsInfractionsGui.class.getName());

    public ItemsInfractionsGui(Inventory inv, OfflinePlayer target, Player player, int page) {
        this.inventory = inv;
        this.target = target;
        this.player = player;

        if (page <= 0) {
            this.page = page;
        }
    }

    public void infractionsGui() {

        List<ItemStack> items = new ArrayList<>();

        try (Connection connection = Database.getConnection()) {

            assert connection != null;
            try (Statement statement = connection.createStatement();
                 ResultSet res = statement.executeQuery("SELECT * FROM `Infractions` WHERE `targetUUID` = '" + target.getUniqueId() + "'")) {

                if (res == null) {

                    player.getOpenInventory().close();
                    player.sendMessage(Louise.commandError());

                    logger.warning("[NaurelliaModeration] -> ItemsInfractionsGui : infractionsGui ERROR - req <= 0");
                    return;
                }

                while (res.next()) {

                    String infractionsType = res.getString("infractionType");
                    String reason = res.getString("reason");
                    String date = res.getString("infractionDate");

                    UUID staffUUID;
                    OfflinePlayer staff = null;

                    if (res.getString("staffUUID") != null) {


                        staffUUID = UUID.fromString(res.getString("staffUUID"));
                        staff = Bukkit.getOfflinePlayer(staffUUID);
                    }

                    ItemStack item;
                    ItemMeta itemMeta = null;

                    if (infractionsType.equalsIgnoreCase("kick")) {
                        item = GuiManager.headFactory("http://textures.minecraft.net/texture/3b511cb4fa5f4ba66c9229575190df34a93b232141b69d3effaf2b7e512cd");

                        itemMeta = item.getItemMeta();
                        assert itemMeta != null;

                        itemMeta.setDisplayName("§6" + infractionsType);
                    } else if (infractionsType.equalsIgnoreCase("warn")) {
                        item = GuiManager.headFactory("http://textures.minecraft.net/texture/8decf6eacc73734e1dc6b21573595ea0d9559146e29913a10552da5956acf7d");

                        itemMeta = item.getItemMeta();
                        assert itemMeta != null;

                        itemMeta.setDisplayName("§7" + infractionsType);
                    } else if (infractionsType.equalsIgnoreCase("mute") || infractionsType.equalsIgnoreCase("tempmute")) {
                        item = GuiManager.headFactory("http://textures.minecraft.net/texture/819b26d993b1931c931ed2d57e8bab8a278429c2402b56d564c24db422eef885");

                        itemMeta = item.getItemMeta();
                        assert itemMeta != null;

                        itemMeta.setDisplayName("§e" + infractionsType);
                    } else if (infractionsType.equalsIgnoreCase("ban") || infractionsType.equalsIgnoreCase("tempban")) {
                        item = GuiManager.headFactory("http://textures.minecraft.net/texture/d4c4a254bec46e274757c07979f0ddae36c7b365487c8f35ffead4cb2e311b9");

                        itemMeta = item.getItemMeta();
                        assert itemMeta != null;

                        itemMeta.setDisplayName("§c" + infractionsType);
                    } else {
                        item = GuiManager.getPlayerHead(target);
                        itemMeta = item.getItemMeta();

                        assert itemMeta != null;
                        itemMeta.setDisplayName("§cERROR");
                    }

                    List<String> itemLore = new ArrayList<>();
                    itemLore.add("§6Reason : §c" + reason);
                    itemLore.add("§6Date : §c" + date);

                    if (staff != null) {

                        itemLore.add("§6Staff : §c" + staff.getName());
                    } else {

                        itemLore.add("§6Staff : §cLouise");
                    }

                    itemMeta.setLore(itemLore);
                    itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                    item.setItemMeta(itemMeta);

                    items.add(item);
                }
            }
        } catch (SQLException e) {

            ExceptionsManager.sqlExceptionLog(e);
        }

        if (items.isEmpty()) return;

        List<ItemStack> subList;

        if (items.size() < 10 && page == 1) {

            subList = items;

        } else if (page > 1 && items.size() < (page * 10)) {

            subList = items.subList((page * 10) - 10, items.size());

        } else {

            // get infractions between page * 10 - 10 and (page * 10) -1
            subList = items.subList((page * 10) - 10, (page * 10));
        }

        int[] inventoryPositions = {0, 2, 4, 6, 8, 18, 20, 22, 24, 26};

        for (int i = 0; i < subList.size(); i++) {

            if (subList.get(i) != null) {

                inventory.setItem(inventoryPositions[i], subList.get(i));
            }
        }

        // PAGE BLOCK
        ItemStack pageBlock = GuiManager.headFactory("http://textures.minecraft.net/texture/a0a7989b5d6e621a121eedae6f476d35193c97c1a7cb8ecd43622a485dc2e912");

        ItemMeta pageBlockMeta = pageBlock.getItemMeta();
        assert pageBlockMeta != null;
        pageBlockMeta.setDisplayName("§cPage : §6" + page);
        pageBlockMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // NEXT BLOCK
        ItemStack nextBlock = GuiManager.headFactory("http://textures.minecraft.net/texture/19bf3292e126a105b54eba713aa1b152d541a1d8938829c56364d178ed22bf");

        ItemMeta nextBlockMeta = nextBlock.getItemMeta();
        assert nextBlockMeta != null;
        nextBlockMeta.setDisplayName("§cNext Page");
        nextBlockMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // PREVIOUS BLOCK
        ItemStack previousBlock = GuiManager.headFactory("http://textures.minecraft.net/texture/bd69e06e5dadfd84e5f3d1c21063f2553b2fa945ee1d4d7152fdc5425bc12a9");

        ItemMeta previousBlockItemMeta = previousBlock.getItemMeta();
        assert previousBlockItemMeta != null;
        previousBlockItemMeta.setDisplayName("§cPrevious Page");
        previousBlockItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        nextBlock.setItemMeta(nextBlockMeta);
        previousBlock.setItemMeta(previousBlockItemMeta);
        pageBlock.setItemMeta(pageBlockMeta);

        if (page == 1) {
            inventory.setItem(53, nextBlock);
        } else if (page > 1 && subList.size() < (page * 10)) {
            inventory.setItem(45, previousBlock);
        } else {
            inventory.setItem(45, previousBlock);
            inventory.setItem(53, nextBlock);
        }

        inventory.setItem(49, pageBlock);
    }
}
