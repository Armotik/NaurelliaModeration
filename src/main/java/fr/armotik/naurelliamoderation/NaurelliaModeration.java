package fr.armotik.naurelliamoderation;

import fr.armotik.naurelliamoderation.commands.WarnCommand;
import fr.armotik.naurelliamoderation.completers.StaffCommandCompleter;
import fr.armotik.naurelliamoderation.listerners.EventManager;
import fr.armotik.naurelliamoderation.listerners.PermissionManager;
import fr.armotik.naurelliamoderation.tools.ChatFilter;
import fr.armotik.naurelliamoderation.tools.SanctionsManager;
import fr.armotik.naurelliamoderation.utiles.Database;
import fr.armotik.naurelliamoderation.utiles.FilesReader;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class NaurelliaModeration extends JavaPlugin {

    private static NaurelliaModeration plugin;
    private static final Logger logger = Logger.getLogger(NaurelliaModeration.class.getName());

    /**
     * @return plugin
     */
    public static NaurelliaModeration getPlugin() {return plugin;}

    /**
     * Plugin starts
     */
    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        logger.log(Level.INFO, "[NaurelliaModeration] -> NaurelliaModeration is loading ...");
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();

        /*
        Commands
         */
        Objects.requireNonNull(getCommand("warn")).setExecutor(new WarnCommand());

        /*
        Completer
         */


        /*
        Tests
         */
        Database.databaseTest();
        Database.close();

        /*
        Event Listeners
         */
        this.getServer().getPluginManager().registerEvents(new EventManager(), this);
        this.getServer().getPluginManager().registerEvents(new PermissionManager(), this);
        this.getServer().getPluginManager().registerEvents(new ChatFilter(), this);
        this.getServer().getPluginManager().registerEvents(new SanctionsManager(), this);

        logger.log(Level.INFO, "[NaurelliaCore] -> Successfully loaded NaurelliaCore");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        FilesReader.writeOfflineInfractions();
        Database.closeAll();
    }

    public static void moderationLoop() {

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SanctionsManager.unMuteDetector();
                SanctionsManager.unBanDetector();
            }
        }, 0, TimeUnit.MINUTES.toMillis(5)); //5m
    }
}
