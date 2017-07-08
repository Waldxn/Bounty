package me.waldxn.bounties.cmds;

import me.waldxn.bounties.Bounties;
import me.waldxn.bounties.objects.Bounty;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BountyCmd implements CommandExecutor {

    public static final Map<UUID, Bounty> bounties = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.BLACK + "[" + ChatColor.GOLD + "Bounties" + ChatColor.BLACK + "] " + ChatColor.WHITE +
                    "You must be a player to run this command!");
            return true;
        }

        Player player = (Player) sender;
        UUID playerUUID = player.getUniqueId();

        if (!player.hasPermission("bounties.use")) {
            player.sendMessage(ChatColor.BLACK + "[" + ChatColor.GOLD + "Bounties" + ChatColor.BLACK + "] " + ChatColor.WHITE +
                    "You don't have permission to do that!");
            return true;
        }

        if (args[0].equalsIgnoreCase("cancel")) {

            if (args.length != 1) {
                return false;
            }

            if (!bounties.containsKey(playerUUID)) {
                player.sendMessage("You don't have any bounties to cancel!");
                return true;
            }

            Bounties.getEconomy().depositPlayer(player, bounties.get(playerUUID).getPrice());
            player.sendMessage(ChatColor.BLACK + "[" + ChatColor.GOLD + "Bounties" + ChatColor.BLACK + "] " + ChatColor.WHITE +
                    "Your bounty has been cancelled!");
            bounties.remove(playerUUID);
            return true;
        }

        if (args.length != 2) {
            return false;
        }

        if (Bukkit.getPlayer(args[0]) == null) {
            player.sendMessage(ChatColor.BLACK + "[" + ChatColor.GOLD + "Bounties" + ChatColor.BLACK + "] " + ChatColor.WHITE +
                    "Player is not online!");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        double price;

        try {
            price = Double.parseDouble(args[1]);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            player.sendMessage(ChatColor.BLACK + "[" + ChatColor.GOLD + "Bounties" + ChatColor.BLACK + "] " + ChatColor.WHITE +
                    "Please enter a valid price!");
            return true;
        }

        if (Bounties.getEconomy().getBalance(player) < price) {
            player.sendMessage(ChatColor.BLACK + "[" + ChatColor.GOLD + "Bounties" + ChatColor.BLACK + "] " + ChatColor.WHITE +
                    "You don't have enough money to do that!");
            return true;
        }

        if (bounties.containsKey(playerUUID)) {
            player.sendMessage(ChatColor.BLACK + "[" + ChatColor.GOLD + "Bounties" + ChatColor.BLACK + "] " + ChatColor.WHITE +
                    "You already have a bounty placed on " + bounties.get(playerUUID).getTarget().getDisplayName());
            return true;
        }

        if (bounties.size() >= 54) {
            player.sendMessage(ChatColor.BLACK + "[" + ChatColor.GOLD + "Bounties" + ChatColor.BLACK + "] " + ChatColor.WHITE +
                    "The max amount of bounties are currently active! Please wait until slots open up.");
            return true;
        }

        Bounties.getEconomy().withdrawPlayer(player, price);
        Bounty bounty = new Bounty(player, target, price);
        bounties.put(playerUUID, bounty);
        Bukkit.broadcastMessage(ChatColor.BLACK + "[" + ChatColor.GOLD + "Bounties" + ChatColor.BLACK + "] " + ChatColor.WHITE + "A new bounty of" + ChatColor.GREEN +
                " $" + bounty.getPrice() + ChatColor.WHITE + " has been placed on " + ChatColor.GOLD + bounty.getTarget().getDisplayName() + "!");
        return true;
    }
}
