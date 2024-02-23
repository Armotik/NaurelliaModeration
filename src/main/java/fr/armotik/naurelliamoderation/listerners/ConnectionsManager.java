package fr.armotik.naurelliamoderation.listerners;


import fr.armotik.naurelliamoderation.utiles.FilesReader;
import fr.armotik.naurelliamoderation.utilsclasses.ResultCodeType;
import fr.armotik.louise.utiles.ExceptionsManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
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
    private static Map<UUID, List<InetAddress>> connections = new HashMap<>();

    /**
     * Constructor
     * <p>
     * Initialize the VPN IPs list
     * Initialize the connections map
     * Read the connections data from the database
     * Read the VPN IPs from the internet
     * </p>
     *
     * @see FilesReader#readConnections()
     */
    public ConnectionsManager() {
        vpnIPs = new HashSet<>();

        try {
            URL url = new URL("https://raw.githubusercontent.com/stamparm/ipsum/master/ipsum.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                vpnIPs.add(line.trim());
            }

            setConnections(FilesReader.readConnections());
        } catch (IOException e) {

            ExceptionsManager.ioExceptionLog(e);
        }
    }

    /**
     * Check if the given IP is from a VPN
     *
     * @param inetAddress IP to check
     * @return true if the IP is from a VPN
     */
    private static boolean isIPFromVPB(InetAddress inetAddress) {

        String ipAddress = inetAddress.getHostAddress();

        if (inetAddress.isSiteLocalAddress()) return false;

        if (inetAddress.isAnyLocalAddress() || inetAddress.isLinkLocalAddress() || inetAddress.isLoopbackAddress()) {
            return false;
        }

        return vpnIPs.contains(ipAddress);
    }

    /**
     * Add a connection to the connections map
     *
     * @param uuid    UUID of the player
     * @param address IP of the player
     */
    public static void addConnection(UUID uuid, InetAddress address) {

        if (!connections.containsKey(uuid)) {

            List<InetAddress> addresses = new ArrayList<>();
            addresses.add(address);

            connections.put(uuid, addresses);
            return;
        }

        if (!connections.get(uuid).contains(address)) {

            connections.get(uuid).add(address);
        }
    }

    /**
     * Get every player with the given IP
     * <p>
     * If the player is online, his name will be green
     * If the player is banned, his name will be red
     * If the player is offline and not banned, his name will be gray
     * </p>
     *
     * @param address IP to check
     * @return List of players with the given IP
     */
    public static List<String> getPlayersFromIP(InetAddress address) {

        List<String> players = new ArrayList<>();

        for (Map.Entry<UUID, List<InetAddress>> entry : connections.entrySet()) {

            if (entry.getValue().contains(address)) {

                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(entry.getKey());

                if (offlinePlayer.isOnline()) players.add("§a" + offlinePlayer.getName());
                else if (Bukkit.getServer().getBannedPlayers().contains(offlinePlayer))
                    players.add("§c" + offlinePlayer.getName());
                else players.add("§8" + offlinePlayer.getName());
            }
        }

        return players;
    }

    /**
     * Check if the given player is connected with a VPN
     *
     * @param target Player to check
     * @return ResultCodeType of the check
     */
    public static ResultCodeType ConnectionChecker(Player target) {

        InetAddress address = Objects.requireNonNull(target.getAddress()).getAddress();

        int nb = 0;

        /*
        Count the number of players with the given IP

        If the number is <= 5, the player is the only one with the given IP and the connection is accepted
         */
        for (Map.Entry<UUID, List<InetAddress>> entry : connections.entrySet()) {

            if (entry.getValue().contains(address)) {

                nb++;
            }
        }

        if (nb > 5) return ResultCodeType.TOO_MANY_ACCOUNTS;

        /*
        Player is connected with a VPN
        -> Connection refused
         */
        if (isIPFromVPB(address)) return ResultCodeType.VPN;

        return ResultCodeType.SUCCESS;
    }

    /**
     * Display every player with the given IP to every staff member
     *
     * @param address IP to check
     */
    public static void displayPlayers(InetAddress address) {

        List<String> players = getPlayersFromIP(address);

        StringBuilder stringBuilder = new StringBuilder();

        for (String str : players) {
            stringBuilder.append(str).append(" ");
        }

        for (Player player : Bukkit.getOnlinePlayers()) {

            if (player.hasPermission("naurellia.staff.helper")) {

                player.sendMessage(stringBuilder.toString());
            }
        }
    }

    /**
     * Display every player with the given IP to every staff member when a player join
     * Display nothing if the player is the only one with the given IP
     *
     * @param address IP to check
     */
    public static void displayPlayersOnJoin(InetAddress address) {

        if (getPlayersFromIP(address).size() <= 1) return;

        displayPlayers(address);
    }

    /**
     * Get the connections map
     *
     * @return connections map
     */
    public static Map<UUID, List<InetAddress>> getConnections() {
        return connections;
    }

    /**
     * Set the connections map
     *
     * @param connections connections map
     */
    public static void setConnections(Map<UUID, List<InetAddress>> connections) {
        ConnectionsManager.connections = connections;
    }
}
