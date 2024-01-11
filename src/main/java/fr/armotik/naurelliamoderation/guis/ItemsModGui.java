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

        // SPAM BLOCK
        ItemStack spamBlock = GuiManager.headFactory("http://textures.minecraft.net/texture/6795b66157b5d1e0757331d88976fcb6309de8d01b51e479b390b64e6fe552");

        ItemMeta spamBlockMeta = spamBlock.getItemMeta();
        assert spamBlockMeta != null;
        spamBlockMeta.setDisplayName("§bSpam");
        spamBlockMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // SPAM BLOCK RELAPSE
        ItemStack spamBlockRelapse = GuiManager.headFactory("http://textures.minecraft.net/texture/98f7914e98d98e94a7e3cf972cb66c53a66cfa24305d34b333cae82dfd4be455");

        ItemMeta spamBlockRelapseMeta = spamBlockRelapse.getItemMeta();
        assert spamBlockRelapseMeta != null;
        spamBlockRelapseMeta.setDisplayName("§dSpam (Relapse)");
        spamBlockRelapseMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // CHAT FILTER BYPASS BLOCK
        ItemStack filterBypassBlock = GuiManager.headFactory("http://textures.minecraft.net/texture/4593f9b9bfc1b2110411c7ad7de362cbbf349682c2449bb54452ad8c5f55c42");

        ItemMeta filerBypassBlockMeta = filterBypassBlock.getItemMeta();
        assert filerBypassBlockMeta != null;
        filerBypassBlockMeta.setDisplayName("§dChat Filter Bypass");
        filerBypassBlockMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // DISCRIMINATION BLOCK
        ItemStack discriminationBlock = GuiManager.headFactory("http://textures.minecraft.net/texture/ed68ae3150d81e4c0a9d172bd84c4ff73cdc0b87fee8ec661213468d544483");

        ItemMeta discriminationBlockMeta = discriminationBlock.getItemMeta();
        assert discriminationBlockMeta != null;
        discriminationBlockMeta.setDisplayName("§cDiscrimination");
        discriminationBlockMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // TROLL BLOCK
        ItemStack trollBlock = GuiManager.headFactory("http://textures.minecraft.net/texture/a2c3b2e622712b1495fb95dfcb05eb143b2f2697e6f3c4e4a3d685cf31d4");

        ItemMeta trollBlockMeta = trollBlock.getItemMeta();
        assert trollBlockMeta != null;
        trollBlockMeta.setDisplayName("§cTroll");
        trollBlockMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // GLITCH BLOCK
        ItemStack glitchBlock = GuiManager.headFactory("http://textures.minecraft.net/texture/185361377215e214a35c96cbaf9db672105afafc9494bbf4ae72aa673c5fe7c");

        ItemMeta glitchBlockMeta = glitchBlock.getItemMeta();
        assert glitchBlockMeta != null;
        glitchBlockMeta.setDisplayName("§cGlitch");
        glitchBlockMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // FLOOD BLOCK
        ItemStack floodBlock = GuiManager.headFactory("http://textures.minecraft.net/texture/301c1e29bab272479c526f218f051586c22f29dc0928b6ca98ae14a3e07ad9");

        ItemMeta floodBlockMeta = floodBlock.getItemMeta();
        assert floodBlockMeta != null;
        floodBlockMeta.setDisplayName("§bFlood");
        floodBlockMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // PUB BLOCK
        ItemStack pubBlock = GuiManager.headFactory("http://textures.minecraft.net/texture/565f23ddc6e83ab7d0c4aa9c5744af7b96bc739bc83a96cb1f2b18d671f");

        ItemMeta pubBlockMeta = pubBlock.getItemMeta();
        assert pubBlockMeta != null;
        pubBlockMeta.setDisplayName("§cPub");
        pubBlockMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // TOXICITY BLOCK
        ItemStack toxicityBlock = GuiManager.headFactory("http://textures.minecraft.net/texture/c451897d7f747a901c183bfee2ed174f35655c966f9adf6e2c7630a03a8155bd");

        ItemMeta toxicityBlockMeta = toxicityBlock.getItemMeta();
        assert toxicityBlockMeta != null;
        toxicityBlockMeta.setDisplayName("§dToxicity");
        toxicityBlockMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // HARASSMENT BLOCK
        ItemStack harassmentBlock = GuiManager.headFactory("http://textures.minecraft.net/texture/f92dbcb04a6d3158138c9d62f2282ae2858417cbfefbf64476965a7d767d38");

        ItemMeta harassmentBlockMeta = harassmentBlock.getItemMeta();
        assert harassmentBlockMeta != null;
        harassmentBlockMeta.setDisplayName("§dHarassment");
        harassmentBlockMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // HARASSMENT BLOCK RELAPSE
        ItemStack harassmentBlockRelapse = GuiManager.headFactory("http://textures.minecraft.net/texture/4d9e33d743ee342243312291ccdcffd7fcca5bdc8a6a8459ee8e62ce57add7");

        ItemMeta harassmentBlockRelapseMeta = harassmentBlockRelapse.getItemMeta();
        assert harassmentBlockRelapseMeta != null;
        harassmentBlockRelapseMeta.setDisplayName("§cHarassment (Relapse)");
        harassmentBlockRelapseMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // TOXICITY BLOCK RELAPSE
        ItemStack toxicityBlockRelapse = GuiManager.headFactory("http://textures.minecraft.net/texture/a2c3b2e622712b1495fb95dfcb05eb143b2f2697e6f3c4e4a3d685cf31d4");

        ItemMeta toxicityBlockRelapseMeta = toxicityBlockRelapse.getItemMeta();
        assert toxicityBlockRelapseMeta != null;
        toxicityBlockRelapseMeta.setDisplayName("§cToxicity (Relapse)");
        toxicityBlockRelapseMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // ALT ACCOUNT BLOCK
        ItemStack altBlock = GuiManager.headFactory("http://textures.minecraft.net/texture/51d383401f77beffcb998c2cf79b7afee23f18c41d8a56affed79bb56e2267a3");

        ItemMeta altBlockMeta = altBlock.getItemMeta();
        assert altBlockMeta != null;
        altBlockMeta.setDisplayName("§cAlt Account");
        altBlockMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // ALT USAGE BLOCK
        ItemStack altUsageBlock = GuiManager.headFactory("http://textures.minecraft.net/texture/51d383401f77beffcb998c2cf79b7afee23f18c41d8a56affed79bb56e2267a3");

        ItemMeta altUsageBlockMeta = altUsageBlock.getItemMeta();
        assert altUsageBlockMeta != null;
        altUsageBlockMeta.setDisplayName("§cAlt Usage");
        altUsageBlockMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // GRIEFING BLOCK
        ItemStack griefingBlock = GuiManager.headFactory("http://textures.minecraft.net/texture/78a3324c77fdccd597774304bbda15517ea2359de53897aed09528d1c6ec9");

        ItemMeta griefingBlockMeta = griefingBlock.getItemMeta();
        assert griefingBlockMeta != null;
        griefingBlockMeta.setDisplayName("§bGriefing (Low)");
        griefingBlockMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // INAPPROPRIATE DISCUSSION BLOCK
        ItemStack inapDiscussionBlock = GuiManager.headFactory("http://textures.minecraft.net/texture/bceee22d731914f67096b74e5ead4dd84c5e8eb92b54398e61e8b27bfb37");

        ItemMeta inapDiscussionBlockMeta = inapDiscussionBlock.getItemMeta();
        assert inapDiscussionBlockMeta != null;
        inapDiscussionBlockMeta.setDisplayName("§dInappropriate Discussion");
        inapDiscussionBlockMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // NON ENGLISH CHATTING BLOCK
        ItemStack nonEnglishBlock = GuiManager.headFactory("http://textures.minecraft.net/texture/fd45f5c2cefde4367b758fd3b19d746b9c4a8caf14f9cf77c540c223e5d9a1");

        ItemMeta nonEnglishBlockMeta = nonEnglishBlock.getItemMeta();
        assert nonEnglishBlockMeta != null;
        nonEnglishBlockMeta.setDisplayName("§dNon English Chatting");
        nonEnglishBlockMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // NEXT BLOCK
        ItemStack nextBlock = GuiManager.headFactory("http://textures.minecraft.net/texture/19bf3292e126a105b54eba713aa1b152d541a1d8938829c56364d178ed22bf");

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
