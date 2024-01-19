package fr.armotik.naurelliamoderation.listerners;

import fr.armotik.naurelliamoderation.utiles.FilesReader;
import org.bukkit.event.Listener;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class ChatFilter implements Listener {

    public static List<String> blackListedWords = new ArrayList<>();

    public ChatFilter() {

        InputStream is = getClass().getClassLoader().getResourceAsStream("blacklistedWords.txt");
        Stream<String> data = FilesReader.readStrings(is);

        data.forEach(blackListedWords::add);

        Logger logger = Logger.getLogger(ChatFilter.class.getName());
        logger.log(Level.INFO, "NaurelliaCore -> blacklistedWords successfully added to the memory");
    }
}
