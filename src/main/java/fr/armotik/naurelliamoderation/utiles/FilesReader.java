package fr.armotik.naurelliamoderation.utiles;

import fr.armotik.naurelliamoderation.listerners.ChatFilter;
import fr.armotik.naurelliamoderation.utilsclasses.Report;
import fr.armotik.naurelliamoderation.tools.SanctionsManager;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class FilesReader {

    private static final Logger logger = Logger.getLogger(FilesReader.class.getName());

    private FilesReader() {

        throw new IllegalStateException("Utility Class");
    }

    /**
     * READ STRING FROM A GIVEN FILE IN RESOURCES FOLDER
     *
     * @param is File
     * @return STREAM ARRAY OF STRINGS
     */
    public static Stream<String> readStrings(InputStream is) {

        InputStreamReader inputStreamReader = new InputStreamReader(is);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        return bufferedReader.lines();
    }

    /**
     * WRITE STRING TO A GIVEN FILE IN THE RESOURCES FOLDER OF THE PLUGIN (WHERE THE CONFIG IS)
     */
    public static void writeStrings() {

        File file = new File("blacklistedWords.txt");

        if (file.length() != 0L) {
            file.delete();
        }

        if (ChatFilter.getBlackListedWords().isEmpty()) {
            logger.log(Level.INFO, "[NaurelliaModeration] -> FilesReader : Unable to write Blacklisted Words - map is empty");
            return;
        }

        try {
            file.createNewFile();

            PrintWriter writer = new PrintWriter(file);
            writer.flush();

            ChatFilter.getBlackListedWords().forEach(writer::println);

            assert (file.length() > 0L);

            logger.log(Level.INFO, "[NaurelliaModeration] -> FilesReader : Successfully wrote Blacklisted Words");

            writer.close();

        } catch (IOException e) {
            ExceptionsManager.ioExceptionLog(e);
        }
    }


    /**
     * Read Infractions saved on the offlineInfractions file
     * @return Map of UUID and String of infractions
     */
    public static Map<UUID, String> readOfflineInfractions() {

        Map<UUID, String> res = new HashMap<>();
        BufferedReader bufferedReader = null;

        try {
            bufferedReader = new BufferedReader(new FileReader("./offlineInfractions.txt"));

            if (bufferedReader.readLine() == null) return new HashMap<>();

            String[] lines = bufferedReader.readLine().split("\n");

            for (String line : lines) {

                String[] data = line.trim().split(":");

                res.put(UUID.fromString(data[0]), data[1]);
            }

            bufferedReader.close();

        } catch (FileNotFoundException e) {
            ExceptionsManager.fileNotFoundExceptionLog(e);
        } catch (IOException e) {
            ExceptionsManager.ioExceptionLog(e);
        }

        return res;
    }

    /**
     * Write infraction to the offlineInfraction file
     */
    public static void writeOfflineInfractions() {

        File infractionFile = new File("offlineInfractions.txt");

        if (infractionFile.length() != 0L) {
            infractionFile.delete();
        }

        if (SanctionsManager.getOfflineInfractionMessages().isEmpty()) {
            logger.log(Level.INFO, "[NaurelliaModeration] -> FilesReader : Unable to write Offline Infractions - map is empty");
            return;
        }

        try {
            infractionFile.createNewFile();

            PrintWriter infractionWriter = new PrintWriter(infractionFile);
            infractionWriter.flush();

            SanctionsManager.getOfflineInfractionMessages().forEach((uuid, s) -> {

                infractionWriter.append(uuid.toString()).append(" : ").append(s).append("\n");
            });

            assert (infractionFile.length() > 0L);

            logger.log(Level.INFO, "[NaurelliaModeration] -> FilesReader : Successfully wrote Offline Infractions");

            infractionWriter.close();

        } catch (IOException e) {
            ExceptionsManager.ioExceptionLog(e);
        }
    }

    public static Map<UUID, InetAddress> readConnections() {

        Map<UUID, InetAddress> connections = new HashMap<>();

        try (Connection conn = Database.getConnection()) {

            assert conn != null;
            try (Statement statement = conn.createStatement();
                 ResultSet res = statement.executeQuery("SELECT uuid, address FROM Connections");
            ) {

                if (res == null) {
                    logger.log(Level.WARNING, "[NaurelliaModeration] -> FilesReader : readConnections ERROR - res == null");
                    return null;
                }

                while (res.next()) {

                    connections.put(UUID.fromString(res.getString("uuid")), InetAddress.getByName(res.getString("address")));
                }
            }
        } catch (SQLException e) {
            ExceptionsManager.sqlExceptionLog(e);
            return null;
        } catch (UnknownHostException e) {
            ExceptionsManager.unknownHostExceptionLog(e);
            return null;
        }

        return connections;
    }

    public static void readReports() {

        try (Connection conn = Database.getConnection()) {

            assert conn != null;
            try (Statement statement = conn.createStatement();
                 ResultSet res = statement.executeQuery("SELECT * FROM Reports");
            ) {

                if (res == null) {
                    logger.log(Level.WARNING, "[NaurelliaModeration] -> FilesReader : readReports ERROR - res == null");
                    return;
                }

                while (res.next()) {

                    new Report(UUID.fromString(res.getString("reporter_uuid")), UUID.fromString(res.getString("target_uuid")), res.getString("reason"), res.getString("report_date"), res.getBoolean("isTreated"));
                }
            }
        } catch (SQLException e) {
            ExceptionsManager.sqlExceptionLog(e);
        }
    }
}
