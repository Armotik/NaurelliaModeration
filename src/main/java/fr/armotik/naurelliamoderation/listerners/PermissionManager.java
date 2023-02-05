package fr.armotik.naurelliamoderation.listerners;

import fr.armotik.naurelliamoderation.NaurelliaModeration;
import fr.armotik.naurelliamoderation.utiles.Database;
import fr.armotik.naurelliamoderation.utiles.ExceptionsManager;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.permissions.PermissionAttachment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PermissionManager implements Listener {

    private static final Map<UUID, PermissionAttachment> playerPermissions = new HashMap<>();
    private static final Map<UUID, PermissionAttachment> staffPermissions = new HashMap<>();
    private static final Logger logger = Logger.getLogger(PermissionManager.class.getName());

    public static Map<UUID, PermissionAttachment> getPlayerPermissions() {
        return playerPermissions;
    }

    public static Map<UUID, PermissionAttachment> getStaffPermissions() {
        return staffPermissions;
    }

    /**
     * Setting up default permissions
     * @param player player
     */
    public static void setupPermissions(Player player) {
        PermissionAttachment attachment = player.addAttachment(NaurelliaModeration.getPlugin());
        playerPermissions.put(player.getUniqueId(), attachment);
        staffPermissions.put(player.getUniqueId(), attachment);
    }

    /**
     * Read player's permission when the player join the server
     * @param player player
     */
    public static void readPermissions(Player player) {

        ResultSet res = Database.executeQuery("SELECT playerPermission, staffPermission FROM igPermissions WHERE uuid='" + player.getUniqueId() + "'");
        PermissionAttachment playerAttachment = playerPermissions.get(player.getUniqueId());
        PermissionAttachment staffAttachment = staffPermissions.get(player.getUniqueId());

        if (res == null) {
            logger.log(Level.WARNING, "[NaurelliaCore] -> PermissionManager : readPermissions ERROR - res == null");
            return;
        }

        try {

            if (!res.next()) {
                logger.log(Level.WARNING, "[NaurelliaCore] -> PermissionManager : readPermissions ERROR - res is empty");
            } else {

                String playerRank = res.getString("playerpermission");
                String staffRank = res.getString("staffpermission");

                if (playerRank != null) {

                    for (String permission : NaurelliaModeration.getPlugin().getConfig().getStringList("PlayerRanks." + playerRank + ".permissions")) {

                        playerAttachment.setPermission(permission, true);
                    }
                }

                if (staffRank != null) {

                    for (String permission : NaurelliaModeration.getPlugin().getConfig().getStringList("StaffRanks." + staffRank + ".permissions")) {

                        staffAttachment.setPermission(permission, true);
                    }
                }
            }
        } catch (SQLException e) {
            ExceptionsManager.sqlExceptionLog(e);
        }

        Database.close();
    }
}
