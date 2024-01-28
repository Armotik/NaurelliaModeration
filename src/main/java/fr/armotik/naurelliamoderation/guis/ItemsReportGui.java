package fr.armotik.naurelliamoderation.guis;

import fr.armotik.naurelliamoderation.listerners.GuiManager;
import fr.armotik.naurelliamoderation.utilsclasses.Report;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ItemsReportGui {

    private final Inventory inventory;
    private int page = 1;
    Player target;

    public ItemsReportGui(Inventory inv, int page, Player target) {
        this.inventory = inv;
        if (page != 0) {
            this.page = page;
        }

        this.target = target;
    }

    public void reportGui() {

        List<Report> subList = new ArrayList<>();

        for (Report report : Report.getReports()) {
            if (Bukkit.getOfflinePlayer(report.getTarget_uuid()).isOnline() && !report.isTreated()) {
                subList.add(report);
            }
        }

        if (subList.isEmpty()) {
            return;
        }

        if (page < 1) {
            page = 1;
        }

        List<Report> reportList;

        if (subList.size() < 10 && page == 1) {

            reportList = subList;

        } else if (page > 1 && subList.size() < (page * 10)) {

            reportList = subList.subList((page * 10) - 10, subList.size());

        }  else {

            // get reports between page * 10 - 10 and (page * 10) -1
            reportList = subList.subList((page * 10) - 10, (page * 10));
        }

        int[] inventoryPositions = {0, 2, 4, 6, 8, 18, 20, 22, 24, 26};

        for (int i = 0; i < reportList.size(); i++) {

            if (reportList.get(i) != null) {

                ItemStack itemStack = GuiManager.getPlayerHead(Bukkit.getOfflinePlayer(reportList.get(i).getTarget_uuid()));

                ItemMeta itemMeta = itemStack.getItemMeta();
                assert itemMeta != null;
                itemMeta.setDisplayName("§6" + Bukkit.getOfflinePlayer(reportList.get(i).getTarget_uuid()).getName());

                List<String> lore = new ArrayList<>();
                lore.add("§cID : §6" + reportList.get(i).getId());
                lore.add("§cReporter : §6" + Bukkit.getOfflinePlayer(reportList.get(i).getReporter_uuid()).getName());
                lore.add("§cReason : §6" + reportList.get(i).getReason());
                lore.add("§cDate : §6" + reportList.get(i).getDate());
                itemMeta.setLore(lore);
                itemStack.setItemMeta(itemMeta);

                inventory.setItem(inventoryPositions[i], itemStack);
            }
        }

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

        // PAGE BLOCK
        ItemStack pageBlock = GuiManager.headFactory("http://textures.minecraft.net/texture/a0a7989b5d6e621a121eedae6f476d35193c97c1a7cb8ecd43622a485dc2e912");

        ItemMeta pageBlockMeta = pageBlock.getItemMeta();
        assert pageBlockMeta != null;
        pageBlockMeta.setDisplayName("§cPage : §6" + page);
        pageBlockMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

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
