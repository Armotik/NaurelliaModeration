package fr.armotik.naurelliamoderation;

import fr.armotik.naurelliamoderation.commands.*;
import fr.armotik.naurelliamoderation.completers.ChatFilterCompleter;
import fr.armotik.naurelliamoderation.listerners.*;
import fr.armotik.naurelliamoderation.tools.SanctionsManager;
import fr.armotik.naurelliamoderation.utiles.Database;
import fr.armotik.naurelliamoderation.utiles.FilesReader;
import org.bukkit.plugin.java.JavaPlugin;

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

        // TODO : Réaménager les Managers pour fluidifier le code
        // TODO : JavaDoc

        /*
        Commands
         */
        Objects.requireNonNull(getCommand("warn")).setExecutor(new WarnCommand());
        Objects.requireNonNull(getCommand("tempmute")).setExecutor(new TempmuteCommand());
        Objects.requireNonNull(getCommand("tempban")).setExecutor(new TempbanCommand());
        Objects.requireNonNull(getCommand("unmute")).setExecutor(new UnMuteCommand());
        Objects.requireNonNull(getCommand("unban")).setExecutor(new UnBanCommand());
        Objects.requireNonNull(getCommand("moderation")).setExecutor(new ModerationCommand());
        Objects.requireNonNull(getCommand("infractions")).setExecutor(new InfractionsCommand());
        Objects.requireNonNull(getCommand("report")).setExecutor(new ReportCommand());
        Objects.requireNonNull(getCommand("reports")).setExecutor(new ReportsCommand());
        Objects.requireNonNull(getCommand("freeze")).setExecutor(new FreezeCommand());
        Objects.requireNonNull(getCommand("unfreeze")).setExecutor(new UnFreezeCommand());
        Objects.requireNonNull(getCommand("staffchat")).setExecutor(new StaffChatCommand());
        Objects.requireNonNull(getCommand("openinv")).setExecutor(new OpenInvCommand());
        Objects.requireNonNull(getCommand("invsee")).setExecutor(new InvseeCommand());
        Objects.requireNonNull(getCommand("chatfilter")).setExecutor(new ChatFilterCommand());
        Objects.requireNonNull(getCommand("raidmode")).setExecutor(new RaidModeCommand());
        Objects.requireNonNull(getCommand("connections")).setExecutor(new ConnectionsCommand());
        Objects.requireNonNull(getCommand("vanish")).setExecutor(new VanishCommand());
        // TODO : anti flood
        // TODO : anti caps
        // TODO : infractions command
        // TODO : ip blacklist command
        // TODO : ip whitelist command

        /*
        Completer
         */
        Objects.requireNonNull(getCommand("chatfilter")).setTabCompleter(new ChatFilterCompleter());

        /*
        Tests
         */
        Database.databaseTest();
        Database.close();

        FilesReader.readReports();

        /*
        Event Listeners
         */
        this.getServer().getPluginManager().registerEvents(new EventManager(), this);
        this.getServer().getPluginManager().registerEvents(new ChatFilter(), this);
        this.getServer().getPluginManager().registerEvents(new SanctionsManager(), this);
        this.getServer().getPluginManager().registerEvents(new GuiManager(), this);
        this.getServer().getPluginManager().registerEvents(new ConnectionsManager(), this);

        logger.log(Level.INFO, "[NaurelliaModeration] -> Successfully loaded NaurelliaModeration");

        moderationLoop();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        FilesReader.writeOfflineInfractions();
        FilesReader.writeStrings();
        FilesReader.writeConnections();

        //TODO Save Connections
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
