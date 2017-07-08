package me.waldxn.bounties.events;

import me.waldxn.bounties.Bounties;
import me.waldxn.bounties.cmds.BountyCmd;
import me.waldxn.bounties.objects.Bounty;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class BountyListener implements Listener {

    @EventHandler
    public void onPlayerKill(PlayerDeathEvent event) {
        Player target = event.getEntity();
        if (event.getEntity().getKiller() != null) {
            Player killer = event.getEntity().getKiller();
            for (Bounty b : BountyCmd.bounties.values()) {
                if (b.getTarget().equals(target)) {
                    Bounties.getEconomy().depositPlayer(killer, b.getPrice());
                    Bukkit.broadcastMessage(ChatColor.BLACK + "[" + ChatColor.GOLD + "Bounties" + ChatColor.BLACK + "] " + ChatColor.GOLD + killer.getDisplayName() + ChatColor.WHITE + " has claimed the" + ChatColor.GREEN +
                            " $" + b.getPrice() + ChatColor.WHITE + " bounty on " + ChatColor.GOLD + b.getTarget().getDisplayName() + ChatColor.WHITE + "!");
                    BountyCmd.bounties.remove(b.getPlayer());
                    event.setDeathMessage(null);
                }
            }
        }
    }
}
