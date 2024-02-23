package fr.armotik.naurelliamoderation.guis;

import fr.armotik.naurelliamoderation.listerners.ConnectionsManager;
import fr.armotik.naurelliamoderation.listerners.GuiManager;
import fr.armotik.naurelliamoderation.utiles.Database;
import fr.armotik.louise.utiles.ExceptionsManager;
import org.bukkit.Material;
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
import java.util.Objects;

public class ItemsModMenuGui {

    private final Inventory inventory;
    private final OfflinePlayer target;
    private final Player player;

    public ItemsModMenuGui(Inventory inv, OfflinePlayer target, Player player) {
        this.inventory = inv;
        this.target = target;
        this.player = player;
    }

    public void mainMenuGui(){

        // Player Head
        ItemStack playerHead = GuiManager.getPlayerHead(target);

        ItemMeta playerHeadMeta = playerHead.getItemMeta();
        assert playerHeadMeta != null;
        playerHeadMeta.setDisplayName("§6" + target.getName());
        playerHeadMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // Sanctions
        ItemStack sanctions = GuiManager.headFactory("http://textures.minecraft.net/texture/fdd2cce04674c2c3d5a3a94ff219787a2b459de790a0c01ff29b96729072cd");

        ItemMeta sanctionsMeta = sanctions.getItemMeta();
        assert sanctionsMeta != null;
        sanctionsMeta.setDisplayName("§cSanctions");
        sanctionsMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // Infractions
        ItemStack infraction = GuiManager.headFactory("http://textures.minecraft.net/texture/135c1e44fa6a4ba45c2a34f519aca241d895c5d7883dde07df44fb987748");

        ItemMeta infractionMeta = infraction.getItemMeta();
        assert infractionMeta != null;
        infractionMeta.setDisplayName("§cInfractions");
        infractionMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // Vanish
        ItemStack vanish = GuiManager.headFactory("http://textures.minecraft.net/texture/a86c97159cd658858d12b833d1671b84529e1f116d8be379d43824cb17963bb");

        if (player.isInvisible()) {

            vanish = GuiManager.headFactory("http://textures.minecraft.net/texture/7ca6eabed620fd1c3579dbe6568f7427b32a09f731dc8b745504058c9853449");
        }

        ItemMeta vanishMeta = vanish.getItemMeta();
        assert vanishMeta != null;
        vanishMeta.setDisplayName("§aVanish");
        vanishMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // Open Inventory
        ItemStack openInventory = GuiManager.headFactory("http://textures.minecraft.net/texture/e8f47923861c275311ab69b03e2751224ff394e44e3b3c8db5225ff");

        ItemMeta openInventoryMeta = openInventory.getItemMeta();
        assert openInventoryMeta != null;
        openInventoryMeta.setDisplayName("§aOpen Inventory");
        openInventoryMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // Freeze
        ItemStack freeze = GuiManager.headFactory("http://textures.minecraft.net/texture/301c1e29bab272479c526f218f051586c22f29dc0928b6ca98ae14a3e07ad9");

        ItemMeta freezeMeta = freeze.getItemMeta();
        assert freezeMeta != null;
        freezeMeta.setDisplayName("§bFreeze");
        freezeMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // Glass Pane (Blank)
        ItemStack glassPane = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);

        ItemMeta glassPaneMeta = glassPane.getItemMeta();
        assert glassPaneMeta != null;
        glassPaneMeta.setDisplayName(" ");
        glassPaneMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // Infractions count
        ItemStack infractionCount = GuiManager.headFactory("http://textures.minecraft.net/texture/4e78708f252fbea16fd2539fe1ded81b865ff39124cdbb793ced3d99171b840");

        ItemMeta infractionCountMeta = infractionCount.getItemMeta();
        assert infractionCountMeta != null;
        String title = "§cInfractions Count : ";

        try (Connection connection = Database.getConnection()) {
            assert connection != null;

            try (Statement statement = connection.createStatement();
                 ResultSet res = statement.executeQuery("SELECT COUNT(*) FROM Infractions WHERE targetUUID = '" + target.getUniqueId() + "'")) {

                if (res == null) {
                    return;
                }

                if (res.next()) {

                    title += res.getInt(1);
                }
            }

        } catch (SQLException e) {
            ExceptionsManager.sqlExceptionLog(e);
        }

        infractionCountMeta.setDisplayName(title);
        infractionCountMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // Connections count
        ItemStack connections = GuiManager.headFactory("http://textures.minecraft.net/texture/0b7a67087da884a25fc547ee1ba3d9bdcbf726bda6deb7744c6c52c264a");

        ItemMeta connectionsMeta = connections.getItemMeta();
        assert connectionsMeta != null;

        if (target.isOnline()) {

            String connectionTitle = "§cConnections Count : " + ConnectionsManager.getPlayersFromIP(Objects.requireNonNull(Objects.requireNonNull(target.getPlayer()).getAddress()).getAddress()).size();

            connectionsMeta.setDisplayName(connectionTitle);
        } else {

                connectionsMeta.setDisplayName("§cConnections Count : §4Offline");
        }

        connectionsMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // TELEPORT
        ItemStack teleport = GuiManager.headFactory("http://textures.minecraft.net/texture/9fc32c914276f686141796a5a023c39efdfed14d3d7c9c42792513846f7fa4b3");

        ItemMeta teleportMeta = teleport.getItemMeta();
        assert teleportMeta != null;
        teleportMeta.setDisplayName("§aTeleport");
        teleportMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        // REPORTS
        ItemStack reports = GuiManager.headFactory("http://textures.minecraft.net/texture/adc3a4ea4b9dc41daeb83f142baae74836b1b970824c5bb0a738253ed870a23d");

        ItemMeta reportsMeta = reports.getItemMeta();
        assert reportsMeta != null;
        reportsMeta.setDisplayName("§6Reports");
        reportsMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        playerHead.setItemMeta(playerHeadMeta);
        sanctions.setItemMeta(sanctionsMeta);
        infraction.setItemMeta(infractionMeta);
        vanish.setItemMeta(vanishMeta);
        openInventory.setItemMeta(openInventoryMeta);
        freeze.setItemMeta(freezeMeta);
        glassPane.setItemMeta(glassPaneMeta);
        infractionCount.setItemMeta(infractionCountMeta);
        teleport.setItemMeta(teleportMeta);
        connections.setItemMeta(connectionsMeta);
        reports.setItemMeta(reportsMeta);

        for (int i = 9; i < 18; i++) {
            inventory.setItem(i, glassPane);
        }

        inventory.setItem(4, playerHead);
        inventory.setItem(28, sanctions);
        inventory.setItem(30, infraction);
        inventory.setItem(32, vanish);
        inventory.setItem(34, openInventory);
        inventory.setItem(0, freeze);
        inventory.setItem(2, teleport);
        inventory.setItem(6, connections);
        inventory.setItem(8, infractionCount);
        inventory.setItem(22, reports);
    }
}
