package fr.armotik.naurelliamoderation.guis;

import fr.armotik.naurelliamoderation.listerners.GuiManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemsModGui {

    private final Inventory inventory;

    public ItemsModGui(Inventory inv) {
        this.inventory = inv;
    }

    public void itemsModGui() {
        // BANS BLOCK
        ItemStack banBlock = GuiManager.headFactory("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTBjMWI1ODRmMTM5ODdiNDY2MTM5Mjg1YjJmM2YyOGRmNjc4NzEyM2QwYjMyMjgzZDg3OTRlMzM3NGUyMyJ9fX0=");

        ItemMeta banBlockMeta = banBlock.getItemMeta();
        assert banBlockMeta != null;
        banBlockMeta.setDisplayName("§cBANS");
        banBlockMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // MUTES BLOCK
        ItemStack muteBlock = GuiManager.headFactory("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDljNDVhMjRhYWFiZjQ5ZTIxN2MxNTQ4MzIwNDg0OGE3MzU4MmFiYTdmYWUxMGVlMmM1N2JkYjc2NDgyZiJ9fX0=");

        ItemMeta muteBlockMeta = muteBlock.getItemMeta();
        assert muteBlockMeta != null;
        muteBlockMeta.setDisplayName("§cMUTES");
        muteBlockMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // WARNS BLOCK
        ItemStack warnBlock = GuiManager.headFactory("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjY5YWQxYTg4ZWQyYjA3NGUxMzAzYTEyOWY5NGU0YjcxMGNmM2U1YjRkOTk1MTYzNTY3ZjY4NzE5YzNkOTc5MiJ9fX0=");

        ItemMeta warnBlockMeta = warnBlock.getItemMeta();
        assert warnBlockMeta != null;
        warnBlockMeta.setDisplayName("§cWARNS");
        warnBlockMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // SPAM BLOCK
        ItemStack spamBlock = GuiManager.headFactory("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjc5NWI2NjE1N2I1ZDFlMDc1NzMzMWQ4ODk3NmZjYjYzMDlkZThkMDFiNTFlNDc5YjM5MGI2NGU2ZmU1NTIifX19");

        ItemMeta spamBlockMeta = spamBlock.getItemMeta();
        assert spamBlockMeta != null;
        spamBlockMeta.setDisplayName("§bSpam");
        spamBlockMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // SPAM BLOCK RELAPSE
        ItemStack spamBlockRelapse = GuiManager.headFactory("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOThmNzkxNGU5OGQ5OGU5NGE3ZTNjZjk3MmNiNjZjNTNhNjZjZmEyNDMwNWQzNGIzMzNjYWU4MmRmZDRiZTQ1NSJ9fX0=");

        ItemMeta spamBlockRelapseMeta = spamBlockRelapse.getItemMeta();
        assert spamBlockRelapseMeta != null;
        spamBlockRelapseMeta.setDisplayName("§dSpam (Relapse)");
        spamBlockRelapseMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // CHAT FILTER BYPASS BLOCK
        ItemStack filterBypassBlock = GuiManager.headFactory("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDU5M2Y5YjliZmMxYjIxMTA0MTFjN2FkN2RlMzYyY2JiZjM0OTY4MmMyNDQ5YmI1NDQ1MmFkOGM1ZjU1YzQyIn19fQ==");

        ItemMeta filerBypassBlockMeta = filterBypassBlock.getItemMeta();
        assert filerBypassBlockMeta != null;
        filerBypassBlockMeta.setDisplayName("§dChat Filter Bypass");
        filerBypassBlockMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // DISCRIMINATION BLOCK
        ItemStack discriminationBlock = GuiManager.headFactory("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWQ2OGFlMzE1MGQ4MWU0YzBhOWQxNzJiZDg0YzRmZjczY2RjMGI4N2ZlZThlYzY2MTIxMzQ2OGQ1NDQ0ODMifX19");

        ItemMeta discriminationBlockMeta = discriminationBlock.getItemMeta();
        assert discriminationBlockMeta != null;
        discriminationBlockMeta.setDisplayName("§cDiscrimination");
        discriminationBlockMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // TROLL BLOCK
        ItemStack trollBlock = GuiManager.headFactory("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTJjM2IyZTYyMjcxMmIxNDk1ZmI5NWRmY2IwNWViMTQzYjJmMjY5N2U2ZjNjNGU0YTNkNjg1Y2YzMWQ0In19fQ==");

        ItemMeta trollBlockMeta = trollBlock.getItemMeta();
        assert trollBlockMeta != null;
        trollBlockMeta.setDisplayName("§cTroll");
        trollBlockMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // GLITCH BLOCK
        ItemStack glitchBlock = GuiManager.headFactory("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTg1MzYxMzc3MjE1ZTIxNGEzNWM5NmNiYWY5ZGI2NzIxMDVhZmFmYzk0OTRiYmY0YWU3MmFhNjczYzVmZTdjIn19fQ==");

        ItemMeta glitchBlockMeta = glitchBlock.getItemMeta();
        assert glitchBlockMeta != null;
        glitchBlockMeta.setDisplayName("§cGlitch");
        glitchBlockMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // FLOOD BLOCK
        ItemStack floodBlock = GuiManager.headFactory("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzAxYzFlMjliYWIyNzI0NzljNTI2ZjIxOGYwNTE1ODZjMjJmMjlkYzA5MjhiNmNhOThhZTE0YTNlMDdhZDkifX19");

        ItemMeta floodBlockMeta = floodBlock.getItemMeta();
        assert floodBlockMeta != null;
        floodBlockMeta.setDisplayName("§bFlood");
        floodBlockMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // PUB BLOCK
        ItemStack pubBlock = GuiManager.headFactory("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTY1ZjIzZGRjNmU4M2FiN2QwYzRhYTljNTc0NGFmN2I5NmJjNzM5YmM4M2E5NmNiMWYyYjE4ZDY3MWYifX19");

        ItemMeta pubBlockMeta = pubBlock.getItemMeta();
        assert pubBlockMeta != null;
        pubBlockMeta.setDisplayName("§cPub");
        pubBlockMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // TOXICITY BLOCK
        ItemStack toxicityBlock = GuiManager.headFactory("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzQ1MTg5N2Q3Zjc0N2E5MDFjMTgzYmZlZTJlZDE3NGYzNTY1NWM5NjZmOWFkZjZlMmM3NjMwYTAzYTgxNTViZCJ9fX0=");

        ItemMeta toxicityBlockMeta = toxicityBlock.getItemMeta();
        assert toxicityBlockMeta != null;
        toxicityBlockMeta.setDisplayName("§dToxicity");
        toxicityBlockMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // HARASSMENT BLOCK
        ItemStack harassmentBlock = GuiManager.headFactory("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjkyZGJjYjA0YTZkMzE1ODEzOGM5ZDYyZjIyODJhZTI4NTg0MTdjYmZlZmJmNjQ0NzY5NjVhN2Q3NjdkMzgifX19");

        ItemMeta harassmentBlockMeta = harassmentBlock.getItemMeta();
        assert harassmentBlockMeta != null;
        harassmentBlockMeta.setDisplayName("§dHarassment");
        harassmentBlockMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // HARASSMENT BLOCK RELAPSE
        ItemStack harassmentBlockRelapse = GuiManager.headFactory("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGQ5ZTMzZDc0M2VlMzQyMjQzMzEyMjkxY2NkY2ZmZDdmY2NhNWJkYzhhNmE4NDU5ZWU4ZTYyY2U1N2FkZDcifX19");

        ItemMeta harassmentBlockRelapseMeta = harassmentBlockRelapse.getItemMeta();
        assert harassmentBlockRelapseMeta != null;
        harassmentBlockRelapseMeta.setDisplayName("§cHarassment (Relapse)");
        harassmentBlockRelapseMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // TOXICITY BLOCK RELAPSE
        ItemStack toxicityBlockRelapse = GuiManager.headFactory("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTJjM2IyZTYyMjcxMmIxNDk1ZmI5NWRmY2IwNWViMTQzYjJmMjY5N2U2ZjNjNGU0YTNkNjg1Y2YzMWQ0In19fQ==");

        ItemMeta toxicityBlockRelapseMeta = toxicityBlockRelapse.getItemMeta();
        assert toxicityBlockRelapseMeta != null;
        toxicityBlockRelapseMeta.setDisplayName("§cToxicity (Relapse)");
        toxicityBlockRelapseMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // ALT ACCOUNT BLOCK
        ItemStack altBlock = GuiManager.headFactory("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTFkMzgzNDAxZjc3YmVmZmNiOTk4YzJjZjc5YjdhZmVlMjNmMThjNDFkOGE1NmFmZmVkNzliYjU2ZTIyNjdhMyJ9fX0=");

        ItemMeta altBlockMeta = altBlock.getItemMeta();
        assert altBlockMeta != null;
        altBlockMeta.setDisplayName("§cAlt Account");
        altBlockMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // ALT USAGE BLOCK
        ItemStack altUsageBlock = GuiManager.headFactory("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTFkMzgzNDAxZjc3YmVmZmNiOTk4YzJjZjc5YjdhZmVlMjNmMThjNDFkOGE1NmFmZmVkNzliYjU2ZTIyNjdhMyJ9fX0=");

        ItemMeta altUsageBlockMeta = altUsageBlock.getItemMeta();
        assert altUsageBlockMeta != null;
        altUsageBlockMeta.setDisplayName("§cAlt Usage");
        altUsageBlockMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // GRIEFING BLOCK
        ItemStack griefingBlock = GuiManager.headFactory("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzhhMzMyNGM3N2ZkY2NkNTk3Nzc0MzA0YmJkYTE1NTE3ZWEyMzU5ZGU1Mzg5N2FlZDA5NTI4ZDFjNmVjOSJ9fX0=");

        ItemMeta griefingBlockMeta = griefingBlock.getItemMeta();
        assert griefingBlockMeta != null;
        griefingBlockMeta.setDisplayName("§bGriefing (Low)");
        griefingBlockMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // INAPPROPRIATE DISCUSSION BLOCK
        ItemStack inapDiscussionBlock = GuiManager.headFactory("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmNlZWUyMmQ3MzE5MTRmNjcwOTZiNzRlNWVhZDRkZDg0YzVlOGViOTJiNTQzOThlNjFlOGIyN2JmYjM3In19fQ==");

        ItemMeta inapDiscussionBlockMeta = inapDiscussionBlock.getItemMeta();
        assert inapDiscussionBlockMeta != null;
        inapDiscussionBlockMeta.setDisplayName("§dInappropriate Discussion");
        inapDiscussionBlockMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // NON ENGLISH CHATTING BLOCK
        ItemStack nonEnglishBlock = GuiManager.headFactory("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmQ0NWY1YzJjZWZkZTQzNjdiNzU4ZmQzYjE5ZDc0NmI5YzRhOGNhZjE0ZjljZjc3YzU0MGMyMjNlNWQ5YTEifX19");

        ItemMeta nonEnglishBlockMeta = nonEnglishBlock.getItemMeta();
        assert nonEnglishBlockMeta != null;
        nonEnglishBlockMeta.setDisplayName("§dNon English Chatting");
        nonEnglishBlockMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // NEXT BLOCK
        ItemStack nextBlock = GuiManager.headFactory("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTliZjMyOTJlMTI2YTEwNWI1NGViYTcxM2FhMWIxNTJkNTQxYTFkODkzODgyOWM1NjM2NGQxNzhlZDIyYmYifX19");

        ItemMeta nextBlockMeta = nextBlock.getItemMeta();
        assert nextBlockMeta != null;
        nextBlockMeta.setDisplayName("§cNext Page");
        nextBlockMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        banBlock.setItemMeta(banBlockMeta);
        muteBlock.setItemMeta(muteBlockMeta);
        warnBlock.setItemMeta(warnBlockMeta);
        spamBlock.setItemMeta(spamBlockMeta);
        spamBlockRelapse.setItemMeta(spamBlockRelapseMeta);
        filterBypassBlock.setItemMeta(filerBypassBlockMeta);
        discriminationBlock.setItemMeta(discriminationBlockMeta);
        trollBlock.setItemMeta(trollBlockMeta);
        glitchBlock.setItemMeta(glitchBlockMeta);
        floodBlock.setItemMeta(floodBlockMeta);
        pubBlock.setItemMeta(pubBlockMeta);
        toxicityBlock.setItemMeta(toxicityBlockMeta);
        harassmentBlock.setItemMeta(harassmentBlockMeta);
        harassmentBlockRelapse.setItemMeta(harassmentBlockRelapseMeta);
        toxicityBlockRelapse.setItemMeta(toxicityBlockRelapseMeta);
        altBlock.setItemMeta(altBlockMeta);
        altUsageBlock.setItemMeta(altUsageBlockMeta);
        griefingBlock.setItemMeta(griefingBlockMeta);
        inapDiscussionBlock.setItemMeta(inapDiscussionBlockMeta);
        nonEnglishBlock.setItemMeta(nonEnglishBlockMeta);
        nextBlock.setItemMeta(nextBlockMeta);

        inventory.setItem(1, banBlock);
        inventory.setItem(4, muteBlock);
        inventory.setItem(7, warnBlock);
        inventory.setItem(9, trollBlock);
        inventory.setItem(11, discriminationBlock);
        inventory.setItem(12, filterBypassBlock);
        inventory.setItem(14, spamBlockRelapse);
        inventory.setItem(15, floodBlock);
        inventory.setItem(17, spamBlock);
        inventory.setItem(18, glitchBlock);
        inventory.setItem(20, pubBlock);
        inventory.setItem(21, toxicityBlock);
        inventory.setItem(23, harassmentBlock);
        inventory.setItem(24, griefingBlock);
        inventory.setItem(27, harassmentBlockRelapse);
        inventory.setItem(29, toxicityBlockRelapse);
        inventory.setItem(30, inapDiscussionBlock);
        inventory.setItem(32, nonEnglishBlock);
        inventory.setItem(36, altBlock);
        inventory.setItem(38, altUsageBlock);
        inventory.setItem(53, nextBlock);
    }
}
