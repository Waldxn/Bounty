package me.waldxn.bounties.handlers;

import me.waldxn.bounties.Bounties;
import me.waldxn.bounties.cmds.BountyCmd;
import me.waldxn.bounties.objects.Bounty;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class GuiHandler implements Listener {

    private final Bounties plugin;

    public GuiHandler(Bounties plugin) {
        this.plugin = plugin;
    }

    public void openGui(Player player) {
        Inventory inv = Bukkit.createInventory(player, 54, ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Bounties.Gui.Title")));
        player.openInventory(inv);
        for (Bounty b : BountyCmd.bounties.values()) {
            ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
            SkullMeta meta = (SkullMeta) skull.getItemMeta();
            meta.setOwner(b.getTarget().getName());
            meta.setDisplayName(b.getTarget().getDisplayName() + " | $" + b.getPrice());
            skull.setItemMeta(meta);
            inv.addItem(skull);
            player.updateInventory();
        }
    }

    @EventHandler
    public void onListClick(InventoryClickEvent event){
        if (event.getClickedInventory().getTitle().contains(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Bounties.Gui.Title")))) {
            event.setCancelled(true);
        }
    }
}
