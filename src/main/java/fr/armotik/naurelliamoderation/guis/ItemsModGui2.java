package fr.armotik.naurelliamoderation.guis;

import fr.armotik.naurelliamoderation.listerners.GuiManager;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemsModGui2 {

    private final Inventory inventory;

    public ItemsModGui2(Inventory inv) {
        this.inventory = inv;
    }

    public void itemsModGui2() {

        // BANS BLOCK
        ItemStack banBlock = GuiManager.headFactory("http://textures.minecraft.net/texture/50c1b584f13987b466139285b2f3f28df6787123d0b32283d8794e3374e23");

        ItemMeta banBlockMeta = banBlock.getItemMeta();
        assert banBlockMeta != null;
        banBlockMeta.setDisplayName("§cBANS");
        banBlockMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // MUTES BLOCK
        ItemStack muteBlock = GuiManager.headFactory("http://textures.minecraft.net/texture/49c45a24aaabf49e217c15483204848a73582aba7fae10ee2c57bdb76482f");

        ItemMeta muteBlockMeta = muteBlock.getItemMeta();
        assert muteBlockMeta != null;
        muteBlockMeta.setDisplayName("§cMUTES");
        muteBlockMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // WARNS BLOCK
        ItemStack warnBlock = GuiManager.headFactory("http://textures.minecraft.net/texture/269ad1a88ed2b074e1303a129f94e4b710cf3e5b4d995163567f68719c3d9792");

        ItemMeta warnBlockMeta = warnBlock.getItemMeta();
        assert warnBlockMeta != null;
        warnBlockMeta.setDisplayName("§cWARNS");
        warnBlockMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // PREVIOUS BLOCK
        ItemStack previousBlock = GuiManager.headFactory("http://textures.minecraft.net/texture/bd69e06e5dadfd84e5f3d1c21063f2553b2fa945ee1d4d7152fdc5425bc12a9");

        ItemMeta previousBlockItemMeta = previousBlock.getItemMeta();
        assert previousBlockItemMeta != null;
        previousBlockItemMeta.setDisplayName("§cPrevious Page");
        previousBlockItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // TEST MUTE BLOCK
        ItemStack testMuteBlock = GuiManager.headFactory("http://textures.minecraft.net/texture/c451897d7f747a901c183bfee2ed174f35655c966f9adf6e2c7630a03a8155bd");

        ItemMeta testMuteBlockMeta = testMuteBlock.getItemMeta();
        assert testMuteBlockMeta != null;
        testMuteBlockMeta.setDisplayName("§cTEST MUTE");
        testMuteBlockMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // TEST BAN BLOCK
        ItemStack testBanBlock = GuiManager.headFactory("http://textures.minecraft.net/texture/d4c4a254bec46e274757c07979f0ddae36c7b365487c8f35ffead4cb2e311b9");

        ItemMeta testBanBlockMeta = testBanBlock.getItemMeta();
        assert testBanBlockMeta != null;
        testBanBlockMeta.setDisplayName("§cTEST BAN");
        testBanBlockMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        banBlock.setItemMeta(banBlockMeta);
        muteBlock.setItemMeta(muteBlockMeta);
        warnBlock.setItemMeta(warnBlockMeta);
        previousBlock.setItemMeta(previousBlockItemMeta);
        testMuteBlock.setItemMeta(testMuteBlockMeta);
        testBanBlock.setItemMeta(testBanBlockMeta);

        inventory.setItem(1, banBlock);
        inventory.setItem(4, muteBlock);
        inventory.setItem(7, warnBlock);
        inventory.setItem(45, previousBlock);
        inventory.setItem(13, testMuteBlock);
        inventory.setItem(10, testBanBlock);
    }
}
