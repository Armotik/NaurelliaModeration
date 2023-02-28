package fr.armotik.naurelliamoderation.listerners;


import fr.armotik.naurelliamoderation.utiles.ExceptionsManager;
import fr.armotik.naurelliamoderation.utiles.FilesReader;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.util.*;

public class ConnectionsManager implements Listener {

    private static Set<String> vpnIPs;
    private static final Map<UUID, InetAddress> connections = FilesReader.readConnections();

    public ConnectionsManager() {
        vpnIPs = new HashSet<>();

        try {
            URL url = new URL("https://raw.githubusercontent.com/stamparm/ipsum/master/ipsum.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                vpnIPs.add(line.trim());
            }
        } catch (IOException e) {

            ExceptionsManager.ioExceptionLog(e);
        }
    }

    private static boolean isIPFromVPB(InetAddress inetAddress) {

        String ipAddress = inetAddress.getHostAddress();

        if (inetAddress.isSiteLocalAddress()) return false;

        if (inetAddress.isAnyLocalAddress() || inetAddress.isLinkLocalAddress() || inetAddress.isLoopbackAddress()) {
            return false;
        }

        return vpnIPs.contains(ipAddress);
    }

    private static List<String> getPlayersFromIP(InetAddress address) {

        List<String> players = new ArrayList<>();

        assert connections != null;
        for (UUID uuid : connections.keySet()) {

            if (connections.get(uuid) == address) {

                if (Bukkit.getOfflinePlayer(uuid).isOnline()) {

                    players.add("§a" + Bukkit.getOfflinePlayer(uuid).getName());
                } else {

                    players.add("§c" + Bukkit.getOfflinePlayer(uuid).getName());
                }
            }
        }

        return players;
    }

    public static boolean ConnectionChecker(Player target) {

        InetAddress address = Objects.requireNonNull(target.getAddress()).getAddress();

        /*
        Player is connected with a VPN
        -> Connection refused
         */
        if (isIPFromVPB(address)) {

            return false;
        }

        assert connections != null;

        /*
        Information incorrect
        -> add new information
         */
        if (!connections.containsKey(target.getUniqueId()) || !connections.containsValue(target.getAddress().getAddress()) || connections.get(target.getUniqueId()) != target.getAddress().getAddress()) {

            connections.put(target.getUniqueId(), target.getAddress().getAddress());
        }

        List<String> playersFromIP = getPlayersFromIP(target.getAddress().getAddress());

        if (playersFromIP.size() > 1) {

            StringBuilder stringBuilder = new StringBuilder();

            for (String str : playersFromIP) {
                stringBuilder.append(str).append(" ");
            }

            for (UUID uuid : PermissionManager.getStaffList()) {

                if (Bukkit.getOfflinePlayer(uuid).isOnline()) {

                    Objects.requireNonNull(Bukkit.getPlayer(uuid)).sendMessage(stringBuilder.toString());
                }
            }
        }

        return true;
    }
}
