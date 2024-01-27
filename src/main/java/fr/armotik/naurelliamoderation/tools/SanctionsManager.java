package fr.armotik.naurelliamoderation.tools;

import fr.armotik.naurelliamoderation.Louise;
import fr.armotik.naurelliamoderation.utilsclasses.Report;
import fr.armotik.naurelliamoderation.utiles.Database;
import fr.armotik.naurelliamoderation.utiles.ExceptionsManager;
import fr.armotik.naurelliamoderation.utiles.FilesReader;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.ban.ProfileBanList;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.profile.PlayerProfile;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SanctionsManager implements Listener {

    private static final Logger logger = Logger.getLogger(SanctionsManager.class.getName());
    private static final String DEFAULT_REASON = "§cNo reason given";
    private static final LocalDate LOCAL_DATE = LocalDate.now();
    private static final Date DATE = new Date();
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("YYYY-MM-DD hh:mm:ss");
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final Map<UUID, Date> bannedFromChat = readMuteInfractions();
    private static final Map<UUID, String> offlineInfractionMessages = FilesReader.readOfflineInfractions();

    /**
     * Get bannedFromChat Map
     *
     * @return BannedFromChat Map
     */
    public static Map<UUID, Date> getBannedFromChat() {
        return bannedFromChat;
    }

    /**
     * Get offlineInfraction Map
     *
     * @return OfflineInfraction Map
     */
    public static Map<UUID, String> getOfflineInfractionMessages() {
        return offlineInfractionMessages;
    }

    /**
     * Convert a long to double
     *
     * @param duration long duration
     * @return double duration
     */
    private static Double longToDouble(long duration) {

        long n = TimeUnit.MILLISECONDS.toSeconds(duration);
        double nDay = ((double) n) / 86400;

        return (double) Math.round(nDay * 100) / 100;
    }

    /**
     * Warn a player
     * Send a private message to the player
     * Add the infraction in the database
     *
     * @param staff      who executed the command
     * @param targetUUID who will get the infraction
     * @param reason     reason
     */
    public static void warn(Player staff, UUID targetUUID, String reason) {

        /*
        If no reason then add a default reason
         */
        if (reason == null || reason.trim().equalsIgnoreCase("")) reason = DEFAULT_REASON;

        int req = 0;

        if (staff == null) {

            req = Database.executeUpdate("INSERT INTO Infractions (targetUUID, infractionType, reason, staffUUID, infractionDate) " +
                    "VALUES ('" + targetUUID + "','WARN','" + reason + "'," + null + ",'" + LOCAL_DATE.format(FORMATTER) + "');");
        } else {

            req = Database.executeUpdate("INSERT INTO Infractions (targetUUID, infractionType, reason, staffUUID, infractionDate) " +
                    "VALUES ('" + targetUUID + "','WARN','" + reason + "','" + staff.getUniqueId() + "','" + LOCAL_DATE.format(FORMATTER) + "');");
        }

        if (req <= 0) {

            if (staff != null) {
                staff.sendMessage(Louise.commandError());
            }

            logger.log(Level.WARNING, "[NaurelliaModeration] -> SanctionManager : warn ERROR - req <= 0");
            Database.close();
            return;
        }

        TextComponent msg = new TextComponent("§6|-------------------+====+-------------------| \n" + Louise.getName() + "§cYou have been warned for : §a" + reason + "\n§6|-------------------+====+-------------------|");
        msg.setHoverEvent(new HoverEvent(
                HoverEvent.Action.SHOW_TEXT, new Text("§cPlease refer to our rules")
        ));
        msg.setClickEvent(new ClickEvent(
                ClickEvent.Action.OPEN_URL, "https://www.naurelliacraft.com/rules/"
        ));

        /*
        IF THE PLAYER IS NOT ONLINE
         */
        if (!Bukkit.getOfflinePlayer(targetUUID).isOnline()) {

            offlineInfractionMessages.put(targetUUID, "§6|-------------------+====+-------------------| \n" + Louise.getName() + "§cYou have been warned for : §a" + reason + "\n§6|-------------------+====+-------------------|");

        } else {

            Objects.requireNonNull(Bukkit.getPlayer(targetUUID)).spigot().sendMessage(msg);
        }

        if (staff != null) {

            staff.sendMessage(Louise.getName() + "§aPlayer successfully warned");
        }

        logger.log(Level.INFO, "[NaurelliaModeration] -> SanctionManager : " + Objects.requireNonNull(Bukkit.getPlayer(targetUUID)).getName() + " has been warned by : " + staff.getName() + " for : " + reason);

        Database.close();
    }

    /**
     * Tempmute a player
     * Send a private message to the player
     * Add the infraction in the database
     *
     * @param staff      who executed the command
     * @param targetUUID who will get the infraction
     * @param reason     reason
     * @param duration   mute duration
     */
    public static void tempmute(Player staff, UUID targetUUID, String reason, long duration) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DATE);

        calendar.setTimeInMillis(DATE.getTime() + duration);
        Date unMuteDate = calendar.getTime();


        double muteDays = longToDouble(duration);

        /*
        If no reason then add a default reason
         */
        if (reason == null || reason.trim().equalsIgnoreCase("")) reason = DEFAULT_REASON;

        int req = 0;

        if (staff == null) {

            req = Database.executeUpdate("INSERT INTO Infractions (targetUUID, infractionType, reason, staffUUID, duration, infractionDate, endInfraction) " +
                    "VALUES ('" + targetUUID + "','TEMPMUTE','" + reason + "'," + null + "," + muteDays + ",'" + LOCAL_DATE.format(FORMATTER) + "','" + DATE_FORMAT.format(unMuteDate) + "');");
        } else {


            req = Database.executeUpdate("INSERT INTO Infractions (targetUUID, infractionType, reason, staffUUID, duration, infractionDate, endInfraction) " +
                    "VALUES ('" + targetUUID + "','TEMPMUTE','" + reason + "','" + staff.getUniqueId() + "'," + muteDays + ",'" + LOCAL_DATE.format(FORMATTER) + "','" + DATE_FORMAT.format(unMuteDate) + "');");
        }

        if (req <= 0) {

            if (staff != null) {
                staff.sendMessage(Louise.commandError());
            }

            logger.log(Level.WARNING, "[NaurelliaModeration] -> SanctionManager : " + "[NaurelliaModeration] -> SanctionManager : tempmute ERROR - req <= 0");
            Database.close();
            return;
        }

        assert bannedFromChat != null;

        try {
            bannedFromChat.put(targetUUID, DATE_FORMAT.parse(DATE_FORMAT.format(unMuteDate)));
        } catch (ParseException e) {
            ExceptionsManager.parseExceptionLog(e);

            if (staff != null) {
                staff.sendMessage(Louise.commandError());
            }

            Database.close();
            return;
        }

        TextComponent msg = new TextComponent("§6|-------------------+====+-------------------| \n" + Louise.getName() + "§cYou have been muted for : §a" + reason + "\n§cUNMUTE DATE : §f§l" + DATE_FORMAT.format(unMuteDate) + "\n§6|-------------------+====+-------------------|");
        msg.setHoverEvent(new HoverEvent(
                HoverEvent.Action.SHOW_TEXT, new Text("§cPlease refer to our rules")
        ));
        msg.setClickEvent(new ClickEvent(
                ClickEvent.Action.OPEN_URL, "https://www.naurelliacraft.com/rules/"
        ));

        /*
         IF THE PLAYER IS NOT ONLINE SEND HIM A MESSAGE
         */
        if (!Bukkit.getOfflinePlayer(targetUUID).isOnline()) {

            offlineInfractionMessages.put(targetUUID, "§6|-------------------+====+-------------------| \n" + Louise.getName() + "§cYou have been muted for : §a" + reason + "\n§cUNMUTE DATE : §f§l" + DATE_FORMAT.format(unMuteDate) + "\n§6|-------------------+====+-------------------|");
        } else {

            Objects.requireNonNull(Bukkit.getPlayer(targetUUID)).spigot().sendMessage(msg);
        }


        if (staff != null) {

            staff.sendMessage(Louise.getName() + "§aPlayer successfully muted");
        }

        logger.log(Level.INFO, "[NaurelliaModeration] -> SanctionManager : " + Objects.requireNonNull(Bukkit.getPlayer(targetUUID)).getName() + " has been muted by : " + staff.getName() + " until " + DATE_FORMAT.format(unMuteDate) + " for : " + reason);

        Database.close();
    }

    /**
     * Mute a player
     * Send a private message to the player
     * Add the infraction in the database
     *
     * @param staff      who executed the command
     * @param targetUUID who will get the infraction
     * @param reason     reason
     */
    public static void mute(Player staff, UUID targetUUID, String reason) {

        /*
        If no reason then add a default reason
         */
        if (reason == null || reason.trim().equalsIgnoreCase("")) reason = DEFAULT_REASON;

        int req = Database.executeUpdate("INSERT INTO Infractions (targetUUID, infractionType, reason, staffUUID, duration, infractionDate) " +
                "VALUES ('" + targetUUID + "','MUTE','" + reason + "','" + staff.getUniqueId() + "'," + null + ",'" + LOCAL_DATE.format(FORMATTER) + "');");

        if (req <= 0) {

            staff.sendMessage(Louise.commandError());
            logger.log(Level.WARNING, "[NaurelliaModeration] -> SanctionManager : mute ERROR - req <= 0");
            Database.close();
            return;
        }

        assert bannedFromChat != null;

        bannedFromChat.put(targetUUID, null);

        TextComponent msg = new TextComponent("§6|-------------------+====+-------------------| \n" + Louise.getName() + "§cYou have been muted for : §a" + reason + "\n§6|-------------------+====+-------------------|");
        msg.setHoverEvent(new HoverEvent(
                HoverEvent.Action.SHOW_TEXT, new Text("§cPlease refer to our rules")
        ));
        msg.setClickEvent(new ClickEvent(
                ClickEvent.Action.OPEN_URL, "https://www.naurelliacraft.com/rules/"
        ));

        /*
         IF THE PLAYER IS NOT ONLINE SEND HIM A MESSAGE
         */
        if (!Bukkit.getOfflinePlayer(targetUUID).isOnline()) {

            offlineInfractionMessages.put(targetUUID, "§6|-------------------+====+-------------------| \n" + Louise.getName() + "§cYou have been muted for : §a" + reason + "\n§6|-------------------+====+-------------------|");
            staff.sendMessage(Louise.getName() + "§aPlayer successfully muted");
            logger.log(Level.INFO, "[NaurelliaModeration] -> SanctionManager : " + Bukkit.getOfflinePlayer(targetUUID).getName() + " has been muted by : " + staff.getName() + " for : " + reason);

            Database.close();
            return;
        }

        Objects.requireNonNull(Bukkit.getPlayer(targetUUID)).spigot().sendMessage(msg);

        staff.sendMessage(Louise.getName() + "§aPlayer successfully muted");

        logger.log(Level.INFO, "[NaurelliaModeration] -> SanctionManager : " + Objects.requireNonNull(Bukkit.getPlayer(targetUUID)).getName() + " has been muted by : " + staff.getName() + " for : " + reason);

        Database.close();
    }

    /**
     * Kick a player
     * Add the infraction in the database
     *
     * @param staff  who executed the command
     * @param target who will get the infraction
     * @param reason reason
     */
    public static void kick(Player staff, Player target, String reason) {

        /*
        If the target is not online
         */
        if (!target.isOnline()) {
            staff.sendMessage(Louise.playerNotFound());
            return;
        }

        /*
        If no reason then add a default reason
         */
        if (reason == null || reason.trim().equalsIgnoreCase("")) reason = DEFAULT_REASON;

        int req = 0;

        if (staff == null) {

            req = Database.executeUpdate("INSERT INTO Infractions (targetUUID, infractionType, reason, staffUUID, infractionDate) " +
                    "VALUES ('" + target.getUniqueId() + "','KICK','" + reason + "'," + null + ",'" + LOCAL_DATE.format(FORMATTER) + "');");
        } else {


            req = Database.executeUpdate("INSERT INTO Infractions (targetUUID, infractionType, reason, staffUUID, infractionDate) " +
                    "VALUES ('" + target.getUniqueId() + "','KICK','" + reason + "','" + staff.getUniqueId() + "','" + LOCAL_DATE.format(FORMATTER) + "');");
        }

        if (req <= 0) {

            if (staff != null) {
                staff.sendMessage(Louise.commandError());
            }

            logger.log(Level.WARNING, "[NaurelliaModeration] -> SanctionManager : kick ERROR - req <= 0");
            Database.close();
            return;
        }

        target.kickPlayer("§cYou have been kicked \n\n§cREASON : §f§l" + reason);

        if (staff != null) {

            staff.sendMessage(Louise.getName() + "§aPlayer successfully kicked");
            logger.log(Level.INFO, "[NaurelliaModeration] -> SanctionManager : " + target.getName() + " has been kicked by " + staff.getName() + " for " + reason);
        }

        Database.close();
    }

    /**
     * Tempban a player
     * Kick the player if the player is online
     * Add the infraction in the database
     *
     * @param staff      who executed the command
     * @param targetUUID who will get the infraction
     * @param reason     reason
     * @param duration   ban duration
     */
    public static void tempban(Player staff, UUID targetUUID, String reason, long duration) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DATE);

        calendar.setTimeInMillis(DATE.getTime() + duration);
        Date unBanDays = calendar.getTime();

        double banDays = longToDouble(duration);

        /*
        If no reason then add a default reason
         */
        if (reason == null || reason.trim().equalsIgnoreCase("")) reason = DEFAULT_REASON;

        int req = Database.executeUpdate("INSERT INTO Infractions (targetUUID, infractionType, reason, staffUUID, duration, infractionDate, endInfraction) " +
                "VALUES ('" + targetUUID + "','TEMPBAN','" + reason + "','" + staff.getUniqueId() + "'," + banDays + ",'" + LOCAL_DATE.format(FORMATTER) + "','" + DATE_FORMAT.format(unBanDays) + "');");

        if (req <= 0) {

            staff.sendMessage(Louise.commandError());
            logger.log(Level.WARNING, "[NaurelliaModeration] -> SanctionManager : tempban ERROR - req <= 0");
            Database.close();
            return;
        }

        /*
         * IF THE PLAYER IS ONLINE KICK HIM
         */
        if (Bukkit.getOfflinePlayer(targetUUID).isOnline()) {
            Player target = Bukkit.getPlayer(targetUUID);
            assert target != null;
            target.kickPlayer("§cYou have been banned \n\n§cREASON : §f§l" + reason + "\n§cUNBAN DATE : §f§l" + LOCAL_DATE.format(FORMATTER));
        }

        ProfileBanList profileBanList = Bukkit.getBanList(BanList.Type.PROFILE);
        PlayerProfile playerProfile = Bukkit.getOfflinePlayer(targetUUID).getPlayerProfile();
        profileBanList.addBan(playerProfile, "\n\n§cBAN REASON : §f§l" + reason + "\n§cUNBAN DATE : §f§l" + LOCAL_DATE.format(FORMATTER) + "\n\n§cYou can appeal your ban in our Discord", unBanDays, staff.getName());
        staff.sendMessage(Louise.getName() + "§aPlayer successfully banned for : " + banDays + " days");

        logger.log(Level.INFO, "[NaurelliaModeration] -> SanctionManager : " + Bukkit.getOfflinePlayer(targetUUID).getName() + " has been banned until " + LOCAL_DATE.format(FORMATTER) + " by : " + staff.getName() + " for : " + reason);

        Database.close();
    }

    /**
     * Ban a player
     * Kick the player if the player is online
     * Add the infraction in the database
     *
     * @param staff      who executed the command
     * @param targetUUID who will get the infraction
     * @param reason     reason
     */
    public static void ban(Player staff, UUID targetUUID, String reason) {

        /*
        If no reason then add a default reason
         */
        if (reason == null || reason.trim().equalsIgnoreCase("")) reason = DEFAULT_REASON;

        int req = Database.executeUpdate("INSERT INTO Infractions (targetUUID, infractionType, reason, staffUUID, infractionDate) " +
                "VALUES ('" + targetUUID + "','BAN','" + reason + "','" + staff.getUniqueId() + "','" + LOCAL_DATE.format(FORMATTER) + "');");

        if (req <= 0) {

            staff.sendMessage(Louise.commandError());
            logger.log(Level.WARNING, "[NaurelliaModeration] -> SanctionManager : ban ERROR - req <= 0");
            Database.close();
            return;
        }

        /*
         * IF THE PLAYER IS ONLINE KICK HIM
         */
        if (Bukkit.getOfflinePlayer(targetUUID).isOnline()) {
            Player target = Bukkit.getPlayer(targetUUID);
            assert target != null;
            target.kickPlayer("§cYou have been banned \n\n§cREASON : §f§l" + reason);
        }

        ProfileBanList profileBanList = Bukkit.getBanList(BanList.Type.PROFILE);
        PlayerProfile playerProfile = Bukkit.getOfflinePlayer(targetUUID).getPlayerProfile();
        profileBanList.addBan(playerProfile, "\n\n§cBAN REASON : §f§l" + reason + "\n\n§cYou can appeal your ban in our Discord", (Date) null, staff.getName());

        staff.sendMessage(Louise.getName() + "§aPlayer successfully banned");

        logger.log(Level.INFO, "[NaurelliaModeration] -> SanctionManager : " + Bukkit.getOfflinePlayer(targetUUID).getName() + " has been banned " + " by : " + staff.getName() + " for : " + reason);

        Database.close();
    }

    /**
     * Ban-ip a player
     * Kick the player if the player is online
     * Add the infraction in the database
     *
     * @param staff      who executed the command
     * @param targetUUID who will get the infraction
     * @param reason     reason
     */
    public static void banip(Player staff, UUID targetUUID, String reason) {

        /*
        If no reason then add a default reason
         */
        if (reason == null || reason.trim().equalsIgnoreCase("")) reason = DEFAULT_REASON;

        int req = Database.executeUpdate("INSERT INTO Infractions (targetUUID, infractionType, reason, staffUUID, infractionDate) " +
                "VALUES ('" + targetUUID + "','BANIP','" + reason + "','" + staff.getUniqueId() + "','" + LOCAL_DATE.format(FORMATTER) + "');");

        if (req <= 0) {

            staff.sendMessage(Louise.commandError());
            logger.log(Level.WARNING, "[NaurelliaModeration] -> SanctionManager : banip ERROR - req <= 0");
            Database.close();
            return;
        }

        /*
         * IF THE PLAYER IS ONLINE KICK HIM
         */
        if (Bukkit.getOfflinePlayer(targetUUID).isOnline()) {
            Player target = Bukkit.getPlayer(targetUUID);
            assert target != null;
            target.kickPlayer("§cYou have been banned-ip \n\n§cREASON : §f§l" + reason);
        }

        ProfileBanList profileBanList = Bukkit.getBanList(BanList.Type.IP);
        PlayerProfile playerProfile = Bukkit.getOfflinePlayer(targetUUID).getPlayerProfile();
        profileBanList.addBan(playerProfile, "\n\n§cBAN REASON : §f§l" + reason + "\n\n§cYou can appeal your ban in our Discord", (Date) null, staff.getName());

        staff.sendMessage(Louise.getName() + "§aPlayer successfully banned-ip");

        logger.log(Level.INFO, "[NaurelliaModeration] -> SanctionManager : " + Bukkit.getOfflinePlayer(targetUUID).getName() + " has been banned-ip " + " by : " + staff.getName() + " for : " + reason);

        Database.close();
    }

    /**
     * Unmute a player
     * Update the infractions
     *
     * @param staff      who executed the command
     * @param targetUUID who will be unmuted
     */
    public static void unMute(Player staff, UUID targetUUID) {

        int req = Database.executeUpdate("UPDATE Infractions SET infractionStatus=false WHERE targetUUID='" + targetUUID + "' AND (infractiontype='MUTE' OR infractiontype='TEMPMUTE') AND infractionstatus=true;");

        if (req <= 0) {

            staff.sendMessage(Louise.commandError());
            logger.log(Level.WARNING, "[NaurelliaModeration] -> SanctionManager : unmute ERROR - req <= 0");
            Database.close();
            return;
        }

        bannedFromChat.remove(targetUUID);

        TextComponent msg = new TextComponent("§6|-------------------+====+-------------------| \n" + Louise.getName() + "§cYou're now unmuted\n§6|-------------------+====+-------------------|");
        msg.setHoverEvent(new HoverEvent(
                HoverEvent.Action.SHOW_TEXT, new Text("§cPlease refer to our rules")
        ));
        msg.setClickEvent(new ClickEvent(
                ClickEvent.Action.OPEN_URL, "https://www.naurelliacraft.com/rules/"
        ));

        /*
         IF THE PLAYER IS NOT ONLINE SEND HIM A MESSAGE
         */
        if (!Bukkit.getOfflinePlayer(targetUUID).isOnline()) {

            offlineInfractionMessages.put(targetUUID, "§6|-------------------+====+-------------------| \n" + Louise.getName() + "§cYou are now unmuted" + "\n§6|-------------------+====+-------------------|");
            staff.sendMessage(Louise.getName() + "§aPlayer successfully muted");
            logger.log(Level.INFO, "[NaurelliaModeration] -> SanctionManager : " + Bukkit.getOfflinePlayer(targetUUID).getName() + " has been unmuted");

            Database.close();
            return;
        }

        if (staff != null) {

            staff.sendMessage(Louise.getName() + "§aPlayer successfully unmuted");
        }

        Objects.requireNonNull(Bukkit.getPlayer(targetUUID)).spigot().sendMessage(msg);

        logger.log(Level.INFO, "[NaurelliaModeration] -> SanctionManager : " + Bukkit.getOfflinePlayer(targetUUID).getName() + " is now unmuted");
        Database.close();
    }

    /**
     * UnBan a player
     * Update the infractions
     *
     * @param staff  who executed the command
     * @param target who will be unbanned
     */
    public static void unBan(Player staff, OfflinePlayer target) {

        int req = Database.executeUpdate("UPDATE Infractions SET infractionStatus=false WHERE targetUUID='" + target.getUniqueId() + "' AND (infractiontype='BAN' OR infractiontype='TEMPBAN') AND infractionstatus=true;");

        if (req <= 0) {

            staff.sendMessage(Louise.commandError());
            logger.log(Level.WARNING, "[NaurelliaModeration] -> SanctionManager : unban ERROR - req <= 0");
            Database.close();
            return;
        }

        ProfileBanList profileBanList = Bukkit.getBanList(BanList.Type.PROFILE);
        PlayerProfile playerProfile = target.getPlayerProfile();
        profileBanList.pardon(playerProfile);

        if (staff != null) {

            staff.sendMessage(Louise.getName() + "§aPlayer successfully unbanned");
        }

        logger.log(Level.INFO, "[NaurelliaModeration] -> SanctionManager : " + target.getName() + " is now unbanned");
        Database.close();
    }

    public static void report(Player reporter, Player target, String reason) {

        if (Report.getReports() != null) {

            for (Report report : Report.getReports()) {

                // transform string date to date
                Date date = null;
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    date = dateFormat.parse(report.getDate());
                } catch (ParseException e) {
                    ExceptionsManager.parseExceptionLog(e);
                }

            /*
            If the player has already reported this player within the last hour
             */
                if (report.getReporter_uuid().equals(reporter.getUniqueId()) &&
                        report.getTarget_uuid().equals(target.getUniqueId()) &&
                        date != null &&
                        date.after(new Date(System.currentTimeMillis() - TimeUnit.HOURS.toMillis(1))) &&
                        !report.isTreated() &&
                        !reporter.hasPermission("naurellia.staff.helper")) {

                    reporter.sendMessage(Louise.getName() + "§cPlease wait 1h before reporting this player again");
                    return;
                }
            }
        }

        int req = Database.executeUpdate("INSERT INTO Reports (reporter_uuid, target_uuid, reason, report_date) VALUES ('" + reporter.getUniqueId() + "', '" + target.getUniqueId() + "', '" + reason + "', '" + LOCAL_DATE.format(FORMATTER) + "');");

        if (req <= 0) {

            reporter.sendMessage(Louise.commandError());
            logger.log(Level.WARNING, "[NaurelliaModeration] -> SanctionManager : report ERROR - req <= 0");
            Database.close();
            return;
        }

        new Report(reporter.getUniqueId(), target.getUniqueId(), reason, LOCAL_DATE.format(FORMATTER), false);

        reporter.sendMessage(Louise.getName() + "§aPlayer successfully reported");

        for (Player staff : Bukkit.getOnlinePlayers()) {

            if (staff.hasPermission("naurellia.staff.helper")) {

                staff.sendMessage(Louise.getName() + " §7» §c" + reporter.getName() + " §7reported §c" + target.getName() + " §7for §c" + reason + "§7.");
            }
        }

        logger.log(Level.INFO, "[NaurelliaModeration] -> SanctionManager : " + reporter.getName() + " has reported " + target.getName() + " for " + reason);
        Database.close();
    }

    /**
     * Check offlineInfractions Map to see if the player got an infractions
     *
     * @param target target
     */
    public static void checkInfractions(Player target) {

        if (!target.isOnline()) return;

        if (!offlineInfractionMessages.isEmpty() && offlineInfractionMessages.containsKey(target.getUniqueId())) {

            TextComponent msg = new TextComponent(offlineInfractionMessages.get(target.getUniqueId()));
            msg.setHoverEvent(new HoverEvent(
                    HoverEvent.Action.SHOW_TEXT, new Text("§cPlease refer to our rules")
            ));
            msg.setClickEvent(new ClickEvent(
                    ClickEvent.Action.OPEN_URL, "https://www.naurelliacraft.com/rules/"
            ));

            target.spigot().sendMessage(msg);

            offlineInfractionMessages.remove(target.getUniqueId());
        }
    }

    /**
     * Read mutes from the database
     *
     * @return Mute map
     */
    public static Map<UUID, Date> readMuteInfractions() {

        Map<UUID, Date> mutes = new HashMap<>();

        try (Connection conn = Database.getConnection()) {

            assert conn != null;
            try (Statement statement = conn.createStatement();
                 ResultSet res = statement.executeQuery("SELECT * FROM Infractions WHERE infractionType='MUTE' OR infractionType='TEMPMUTE'");
            ) {

                if (res == null) {

                    logger.log(Level.WARNING, "[NaurelliaModeration] -> SanctionManager : readMuteInfractions ERROR - res == null");
                    return mutes;
                }

                while (res.next()) {

                    if (res.getBoolean("infractionStatus")) {

                        UUID targetUUID = UUID.fromString(res.getString("targetUUID"));

                        if (res.getString("endInfraction") == null) {

                            mutes.put(targetUUID, null);
                        } else {

                            String endInfraction = res.getString("endInfraction");

                            Date endInfractionDate = DATE_FORMAT.parse(endInfraction);

                            mutes.put(targetUUID, endInfractionDate);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            ExceptionsManager.sqlExceptionLog(e);
            return mutes;
        } catch (ParseException e) {
            ExceptionsManager.parseExceptionLog(e);
            return mutes;
        }

        return mutes;
    }

    /**
     * Detect when a player is unmuted
     */
    public static void unMuteDetector() {

        if (bannedFromChat.isEmpty()) return;

        bannedFromChat.forEach((uuid, date) -> {

            if (date != null && date.before(DATE)) {

                unMute(null, uuid);
            }
        });
    }

    /**
     * Detect when a player is unbanned
     */
    public static void unBanDetector() {

        try (Connection connection = Database.getConnection()) {

            assert connection != null;
            try (Statement statement = connection.createStatement();
                 ResultSet res = statement.executeQuery("SELECT targetuuid, endinfraction FROM Infractions\n" +
                         "WHERE infractionStatus=true AND (infractionType='BAN' OR infractionType='TEMPBAN')\n" +
                         "AND endInfraction IS NOT NULL;")) {

                if (res == null) {

                    logger.log(Level.WARNING, "[NaurelliaModeration] -> SanctionManager : unBanDetector ERROR - res == null");
                    return;
                }

                while (res.next()) {

                    String endInfraction = res.getString("endinfraction");

                    Date endInfractionDate = DATE_FORMAT.parse(endInfraction);

                    if (endInfractionDate.before(DATE)) {

                        UUID targetUUID = UUID.fromString(res.getString("targetuuid"));

                        int req = Database.executeUpdate("UPDATE Infractions SET infractionStatus=false WHERE targetUUID='" + targetUUID + "' AND (infractiontype='BAN' OR infractiontype='TEMPBAN') AND infractionstatus=true;");

                        ProfileBanList profileBanList = Bukkit.getBanList(BanList.Type.PROFILE);
                        PlayerProfile playerProfile = Bukkit.getOfflinePlayer(targetUUID).getPlayerProfile();
                        profileBanList.pardon(playerProfile);

                        if (req <= 0) {

                            logger.log(Level.WARNING, "[NaurelliaModeration] -> SanctionManager : unBanDetector ERROR - req <= 0");
                            return;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            ExceptionsManager.sqlExceptionLog(e);
        } catch (ParseException e) {
            ExceptionsManager.parseExceptionLog(e);
        }
    }

    /**
     * Build the reason for infraction's commands
     *
     * @param args    args
     * @param rmIndex index to remove
     * @return reason
     */
    public static String reasonDefBuilder(String[] args, int rmIndex) {

        StringBuilder bc = new StringBuilder();

        ArrayList<String> resList = new ArrayList<>(Arrays.asList(args));

        while (rmIndex >= 0) {
            rmIndex--;
            resList.remove(0);
        }

        for (String part : resList) {
            bc.append(part).append(" ");
        }

        return bc.toString();
    }
}
