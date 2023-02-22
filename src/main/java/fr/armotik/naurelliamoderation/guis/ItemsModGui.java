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
        ItemStack warnBlock = new ItemStack(Material.PLAYER_HEAD, 1);
        warnBlock = Bukkit.getUnsafe().modifyItemStack(warnBlock, "{display:{Name:\"{\\\"text\\\":\\\"Oak Wood W\\\"}\"},SkullOwner:{Id:[I;545352292,1464026660,-1923219372,-1704716389],Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjY5YWQxYTg4ZWQyYjA3NGUxMzAzYTEyOWY5NGU0YjcxMGNmM2U1YjRkOTk1MTYzNTY3ZjY4NzE5YzNkOTc5MiJ9fX0=\"}]}}}");

        ItemMeta warnBlockMeta = warnBlock.getItemMeta();
        assert warnBlockMeta != null;
        warnBlockMeta.setDisplayName("§cWARNS");
        warnBlockMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // SPAM BLOCK
        ItemStack spamBlock = new ItemStack(Material.PLAYER_HEAD, 1);
        spamBlock = Bukkit.getUnsafe().modifyItemStack(spamBlock, "{display:{Name:\"{\\\"text\\\":\\\"Light Blue S\\\"}\"},SkullOwner:{Id:[I;1612401613,-1836167881,-2106188292,-1143428142],Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjc5NWI2NjE1N2I1ZDFlMDc1NzMzMWQ4ODk3NmZjYjYzMDlkZThkMDFiNTFlNDc5YjM5MGI2NGU2ZmU1NTIifX19\"}]}}}");

        ItemMeta spamBlockMeta = spamBlock.getItemMeta();
        assert spamBlockMeta != null;
        spamBlockMeta.setDisplayName("§bSpam");
        spamBlockMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // SPAM BLOCK RELAPSE
        ItemStack spamBlockRelapse = new ItemStack(Material.PLAYER_HEAD, 1);
        spamBlockRelapse = Bukkit.getUnsafe().modifyItemStack(spamBlockRelapse, "{display:{Name:\"{\\\"text\\\":\\\"Purple S\\\"}\"},SkullOwner:{Id:[I;1669355414,1558463566,-1433400613,-1389725156],Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOThmNzkxNGU5OGQ5OGU5NGE3ZTNjZjk3MmNiNjZjNTNhNjZjZmEyNDMwNWQzNGIzMzNjYWU4MmRmZDRiZTQ1NSJ9fX0=\"}]}}}");

        ItemMeta spamBlockRelapseMeta = spamBlockRelapse.getItemMeta();
        assert spamBlockRelapseMeta != null;
        spamBlockRelapseMeta.setDisplayName("§dSpam (Relapse)");
        spamBlockRelapseMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // CHAT FILTER BYPASS BLOCK
        ItemStack filterBypassBlock = new ItemStack(Material.PLAYER_HEAD, 1);
        filterBypassBlock = Bukkit.getUnsafe().modifyItemStack(filterBypassBlock, "{display:{Name:\"{\\\"text\\\":\\\"Purple B\\\"}\"},SkullOwner:{Id:[I;-499931585,1996180783,-1143786723,650283318],Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDU5M2Y5YjliZmMxYjIxMTA0MTFjN2FkN2RlMzYyY2JiZjM0OTY4MmMyNDQ5YmI1NDQ1MmFkOGM1ZjU1YzQyIn19fQ==\"}]}}}");

        ItemMeta filerBypassBlockMeta = filterBypassBlock.getItemMeta();
        assert filerBypassBlockMeta != null;
        filerBypassBlockMeta.setDisplayName("§dChat Filter Bypass");
        filerBypassBlockMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // DISCRIMINATION BLOCK
        ItemStack discriminationBlock = new ItemStack(Material.PLAYER_HEAD, 1);
        discriminationBlock = Bukkit.getUnsafe().modifyItemStack(discriminationBlock, "{display:{Name:\"{\\\"text\\\":\\\"Red D\\\"}\"},SkullOwner:{Id:[I;2046800594,-1864417256,-2081226056,1986986655],Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWQ2OGFlMzE1MGQ4MWU0YzBhOWQxNzJiZDg0YzRmZjczY2RjMGI4N2ZlZThlYzY2MTIxMzQ2OGQ1NDQ0ODMifX19\"}]}}}");

        ItemMeta discriminationBlockMeta = discriminationBlock.getItemMeta();
        assert discriminationBlockMeta != null;
        discriminationBlockMeta.setDisplayName("§cDiscrimination");
        discriminationBlockMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // TROLL BLOCK
        ItemStack trollBlock = new ItemStack(Material.PLAYER_HEAD, 1);
        trollBlock = Bukkit.getUnsafe().modifyItemStack(trollBlock, "{display:{Name:\"{\\\"text\\\":\\\"Red T\\\"}\"},SkullOwner:{Id:[I;-1268621102,1821526746,-1241549044,1805574280],Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTJjM2IyZTYyMjcxMmIxNDk1ZmI5NWRmY2IwNWViMTQzYjJmMjY5N2U2ZjNjNGU0YTNkNjg1Y2YzMWQ0In19fQ==\"}]}}}");

        ItemMeta trollBlockMeta = trollBlock.getItemMeta();
        assert trollBlockMeta != null;
        trollBlockMeta.setDisplayName("§cTroll");
        trollBlockMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // GLITCH BLOCK
        ItemStack glitchBlock = new ItemStack(Material.PLAYER_HEAD, 1);
        glitchBlock = Bukkit.getUnsafe().modifyItemStack(glitchBlock, "{display:{Name:\"{\\\"text\\\":\\\"Red G\\\"}\"},SkullOwner:{Id:[I;-1978450331,420564577,-1621175725,-1109891785],Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTg1MzYxMzc3MjE1ZTIxNGEzNWM5NmNiYWY5ZGI2NzIxMDVhZmFmYzk0OTRiYmY0YWU3MmFhNjczYzVmZTdjIn19fQ==\"}]}}}");

        ItemMeta glitchBlockMeta = glitchBlock.getItemMeta();
        assert glitchBlockMeta != null;
        glitchBlockMeta.setDisplayName("§cGlitch");
        glitchBlockMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // FLOOD BLOCK
        ItemStack floodBlock = new ItemStack(Material.PLAYER_HEAD, 1);
        floodBlock = Bukkit.getUnsafe().modifyItemStack(floodBlock, "{display:{Name:\"{\\\"text\\\":\\\"Light Blue F\\\"}\"},SkullOwner:{Id:[I;-681112180,344804692,-2017358826,-1921645000],Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzAxYzFlMjliYWIyNzI0NzljNTI2ZjIxOGYwNTE1ODZjMjJmMjlkYzA5MjhiNmNhOThhZTE0YTNlMDdhZDkifX19\"}]}}}");

        ItemMeta floodBlockMeta = floodBlock.getItemMeta();
        assert floodBlockMeta != null;
        floodBlockMeta.setDisplayName("§bFlood");
        floodBlockMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // PUB BLOCK
        ItemStack pubBlock = new ItemStack(Material.PLAYER_HEAD, 1);
        pubBlock = Bukkit.getUnsafe().modifyItemStack(pubBlock, "{display:{Name:\"{\\\"text\\\":\\\"Red P\\\"}\"},SkullOwner:{Id:[I;-1939754343,363938933,-1623279894,2118337978],Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTY1ZjIzZGRjNmU4M2FiN2QwYzRhYTljNTc0NGFmN2I5NmJjNzM5YmM4M2E5NmNiMWYyYjE4ZDY3MWYifX19\"}]}}}");

        ItemMeta pubBlockMeta = pubBlock.getItemMeta();
        assert pubBlockMeta != null;
        pubBlockMeta.setDisplayName("§cPub");
        pubBlockMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // TOXICITY BLOCK
        ItemStack toxicityBlock = new ItemStack(Material.PLAYER_HEAD, 1);
        toxicityBlock = Bukkit.getUnsafe().modifyItemStack(toxicityBlock, "{display:{Name:\"{\\\"text\\\":\\\"Purple T\\\"}\"},SkullOwner:{Id:[I;1549873150,1147226913,-1156694067,285988518],Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzQ1MTg5N2Q3Zjc0N2E5MDFjMTgzYmZlZTJlZDE3NGYzNTY1NWM5NjZmOWFkZjZlMmM3NjMwYTAzYTgxNTViZCJ9fX0=\"}]}}}");

        ItemMeta toxicityBlockMeta = toxicityBlock.getItemMeta();
        assert toxicityBlockMeta != null;
        toxicityBlockMeta.setDisplayName("§dToxicity");
        toxicityBlockMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // HARASSMENT BLOCK
        ItemStack harassmentBlock = new ItemStack(Material.PLAYER_HEAD, 1);
        harassmentBlock = Bukkit.getUnsafe().modifyItemStack(harassmentBlock, "{display:{Name:\"{\\\"text\\\":\\\"Purple H\\\"}\"},SkullOwner:{Id:[I;1722196696,-2031465023,-1840909396,896803579],Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjkyZGJjYjA0YTZkMzE1ODEzOGM5ZDYyZjIyODJhZTI4NTg0MTdjYmZlZmJmNjQ0NzY5NjVhN2Q3NjdkMzgifX19\"}]}}}");

        ItemMeta harassmentBlockMeta = harassmentBlock.getItemMeta();
        assert harassmentBlockMeta != null;
        harassmentBlockMeta.setDisplayName("§dHarassment");
        harassmentBlockMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // HARASSMENT BLOCK RELAPSE
        ItemStack harassmentBlockRelapse = new ItemStack(Material.PLAYER_HEAD, 1);
        harassmentBlockRelapse = Bukkit.getUnsafe().modifyItemStack(harassmentBlockRelapse, "{display:{Name:\"{\\\"text\\\":\\\"Red H\\\"}\"},SkullOwner:{Id:[I;-140119467,-2034742481,-2001567507,-2104486308],Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGQ5ZTMzZDc0M2VlMzQyMjQzMzEyMjkxY2NkY2ZmZDdmY2NhNWJkYzhhNmE4NDU5ZWU4ZTYyY2U1N2FkZDcifX19\"}]}}}");

        ItemMeta harassmentBlockRelapseMeta = harassmentBlockRelapse.getItemMeta();
        assert harassmentBlockRelapseMeta != null;
        harassmentBlockRelapseMeta.setDisplayName("§cHarassment (Relapse)");
        harassmentBlockRelapseMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // TOXICITY BLOCK RELAPSE
        ItemStack toxicityBlockRelapse = new ItemStack(Material.PLAYER_HEAD, 1);
        toxicityBlockRelapse = Bukkit.getUnsafe().modifyItemStack(toxicityBlockRelapse, "{display:{Name:\"{\\\"text\\\":\\\"Red T\\\"}\"},SkullOwner:{Id:[I;-1268621102,1821526746,-1241549044,1805574280],Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTJjM2IyZTYyMjcxMmIxNDk1ZmI5NWRmY2IwNWViMTQzYjJmMjY5N2U2ZjNjNGU0YTNkNjg1Y2YzMWQ0In19fQ==\"}]}}}");

        ItemMeta toxicityBlockRelapseMeta = toxicityBlockRelapse.getItemMeta();
        assert toxicityBlockRelapseMeta != null;
        toxicityBlockRelapseMeta.setDisplayName("§cToxicity (Relapse)");
        toxicityBlockRelapseMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // ALT ACCOUNT BLOCK
        ItemStack altBlock = new ItemStack(Material.PLAYER_HEAD, 1);
        altBlock = Bukkit.getUnsafe().modifyItemStack(altBlock, "{display:{Name:\"{\\\"text\\\":\\\"Red A\\\"}\"},SkullOwner:{Id:[I;518065356,351159546,-1709551866,533950267],Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTFkMzgzNDAxZjc3YmVmZmNiOTk4YzJjZjc5YjdhZmVlMjNmMThjNDFkOGE1NmFmZmVkNzliYjU2ZTIyNjdhMyJ9fX0=\"}]}}}");

        ItemMeta altBlockMeta = altBlock.getItemMeta();
        assert altBlockMeta != null;
        altBlockMeta.setDisplayName("§cAlt Account");
        altBlockMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // ALT USAGE BLOCK
        ItemStack altUsageBlock = new ItemStack(Material.PLAYER_HEAD, 1);
        altUsageBlock = Bukkit.getUnsafe().modifyItemStack(altUsageBlock, "{display:{Name:\"{\\\"text\\\":\\\"Red A\\\"}\"},SkullOwner:{Id:[I;518065356,351159546,-1709551866,533950267],Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTFkMzgzNDAxZjc3YmVmZmNiOTk4YzJjZjc5YjdhZmVlMjNmMThjNDFkOGE1NmFmZmVkNzliYjU2ZTIyNjdhMyJ9fX0=\"}]}}}");

        ItemMeta altUsageBlockMeta = altUsageBlock.getItemMeta();
        assert altUsageBlockMeta != null;
        altUsageBlockMeta.setDisplayName("§cAlt Usage");
        altUsageBlockMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // GRIEFING BLOCK
        ItemStack griefingBlock = new ItemStack(Material.PLAYER_HEAD, 1);
        griefingBlock = Bukkit.getUnsafe().modifyItemStack(griefingBlock, "{display:{Name:\"{\\\"text\\\":\\\"Light Blue G\\\"}\"},SkullOwner:{Id:[I;209366737,311443912,-1118687833,-2137559363],Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzhhMzMyNGM3N2ZkY2NkNTk3Nzc0MzA0YmJkYTE1NTE3ZWEyMzU5ZGU1Mzg5N2FlZDA5NTI4ZDFjNmVjOSJ9fX0=\"}]}}}");

        ItemMeta griefingBlockMeta = griefingBlock.getItemMeta();
        assert griefingBlockMeta != null;
        griefingBlockMeta.setDisplayName("§bGriefing (Low)");
        griefingBlockMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // INAPPROPRIATE DISCUSSION BLOCK
        ItemStack inapDiscussionBlock = new ItemStack(Material.PLAYER_HEAD, 1);
        inapDiscussionBlock = Bukkit.getUnsafe().modifyItemStack(inapDiscussionBlock, "{display:{Name:\"{\\\"text\\\":\\\"Purple I\\\"}\"},SkullOwner:{Id:[I;1090715590,-1173402846,-1883301067,2065111730],Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmNlZWUyMmQ3MzE5MTRmNjcwOTZiNzRlNWVhZDRkZDg0YzVlOGViOTJiNTQzOThlNjFlOGIyN2JmYjM3In19fQ==\"}]}}}");

        ItemMeta inapDiscussionBlockMeta = inapDiscussionBlock.getItemMeta();
        assert inapDiscussionBlockMeta != null;
        inapDiscussionBlockMeta.setDisplayName("§dInappropriate Discussion");
        inapDiscussionBlockMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // NON ENGLISH CHATTING BLOCK
        ItemStack nonEnglishBlock = new ItemStack(Material.PLAYER_HEAD, 1);
        nonEnglishBlock = Bukkit.getUnsafe().modifyItemStack(nonEnglishBlock, "{display:{Name:\"{\\\"text\\\":\\\"Purple N\\\"}\"},SkullOwner:{Id:[I;-2034322649,1377979515,-1403384221,-195163621],Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmQ0NWY1YzJjZWZkZTQzNjdiNzU4ZmQzYjE5ZDc0NmI5YzRhOGNhZjE0ZjljZjc3YzU0MGMyMjNlNWQ5YTEifX19\"}]}}}");

        ItemMeta nonEnglishBlockMeta = nonEnglishBlock.getItemMeta();
        assert nonEnglishBlockMeta != null;
        nonEnglishBlockMeta.setDisplayName("§dNon English Chatting");
        nonEnglishBlockMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // NEXT BLOCK
        ItemStack nextBlock = new ItemStack(Material.PLAYER_HEAD, 1);
        nextBlock = Bukkit.getUnsafe().modifyItemStack(nextBlock, "{display:{Name:\"{\\\"text\\\":\\\"Oak Wood Arrow Right\\\"}\"},SkullOwner:{Id:[I;-720120218,160580295,-1700338408,-1472328904],Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTliZjMyOTJlMTI2YTEwNWI1NGViYTcxM2FhMWIxNTJkNTQxYTFkODkzODgyOWM1NjM2NGQxNzhlZDIyYmYifX19\"}]}}}");
        ItemMeta nextBlockMeta = nextBlock.getItemMeta();
        assert nextBlockMeta != null;
        nextBlockMeta.setDisplayName("§cNext Page");
        nextBlockMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        //

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
