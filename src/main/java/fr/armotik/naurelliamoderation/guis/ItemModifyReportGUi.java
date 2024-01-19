package fr.armotik.naurelliamoderation.guis;

import fr.armotik.naurelliamoderation.listerners.GuiManager;
import fr.armotik.naurelliamoderation.reports.Report;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemModifyReportGUi {

    private final Inventory inventory;

    public ItemModifyReportGUi(Inventory inv, Report report) {
        this.inventory = inv;
    }

    public void modifyReportGui() {

        ItemStack resolveBlock = GuiManager.headFactory("http://textures.minecraft.net/texture/beb588b21a6f98ad1ff4e085c552dcb050efc9cab427f46048f18fc803475f7");

        ItemMeta resolveMeta = resolveBlock.getItemMeta();
        assert resolveMeta != null;
        resolveMeta.setDisplayName("§aResolved");
        resolveMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        resolveBlock.setItemMeta(resolveMeta);

        inventory.setItem(4, resolveBlock);
    }
}
