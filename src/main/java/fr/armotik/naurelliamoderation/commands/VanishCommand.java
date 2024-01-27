package fr.armotik.naurelliamoderation.commands;

import fr.armotik.naurelliamoderation.Louise;
import fr.armotik.naurelliamoderation.NaurelliaModeration;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VanishCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (!(commandSender instanceof Player)) return false;

        Player player = (Player) commandSender;

        if (player.isInvisible()) {
            player.setInvisible(false);
            player.setFlying(false);
            player.setAllowFlight(false);
            player.setGameMode(GameMode.SURVIVAL);
            player.setInvulnerable(false);

            for (Player p : NaurelliaModeration.getPlugin().getServer().getOnlinePlayers()) {
                p.showPlayer(NaurelliaModeration.getPlugin(), player);
            }

            player.sendMessage(Louise.getName() + "§cYou are now visible !");

        } else {
            /*
            -> set invisible
            -> set invisible to all players (except staff) on the tablist
            -> set invulnerable
            -> set flying
            -> set gamemode spectator
            -> set allow flight
            -> disable potion effects
             */
            player.setInvisible(true);
            player.setInvulnerable(true);
            player.setAllowFlight(true);
            player.setFlying(true);
            player.setGameMode(GameMode.SPECTATOR);
            player.getActivePotionEffects().clear();

            for (Player p : NaurelliaModeration.getPlugin().getServer().getOnlinePlayers()) {
                if (!p.hasPermission("naurellia.staff.helper")) {
                    p.hidePlayer(NaurelliaModeration.getPlugin(), player);
                }
            }

            player.sendMessage(Louise.getName() + "§aYou are now invisible !");
        }
        return true;
    }
}
