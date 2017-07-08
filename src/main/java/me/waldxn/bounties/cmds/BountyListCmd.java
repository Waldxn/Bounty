package me.waldxn.bounties.cmds;

import me.waldxn.bounties.Bounties;
import me.waldxn.bounties.handlers.GuiHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BountyListCmd implements CommandExecutor {

    @SuppressWarnings("FieldCanBeLocal")
    private final Bounties plugin;
    private final GuiHandler gui;

    public BountyListCmd(Bounties plugin){
        this.plugin = plugin;
        this.gui = new GuiHandler(this.plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.BLACK + "[" + ChatColor.GOLD + "Bounties" + ChatColor.BLACK + "] " + ChatColor.WHITE + "You must be a player to list all bounties");
            return true;
        }

        Player player = (Player) sender;

        if (args.length != 0){
            return false;
        }

        if (!player.hasPermission("bounties.use")) {
            player.sendMessage(ChatColor.BLACK + "[" + ChatColor.GOLD + "Bounties" + ChatColor.BLACK + "] " + ChatColor.WHITE + "You don't have permission to do that!");
            return true;
        }

        gui.openGui(player);
        return true;
    }
}
