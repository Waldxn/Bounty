package me.waldxn.bounties;

import me.waldxn.bounties.cmds.BountyCmd;
import me.waldxn.bounties.cmds.BountyListCmd;
import me.waldxn.bounties.events.BountyListener;
import me.waldxn.bounties.handlers.GuiHandler;
import me.waldxn.bounties.objects.Bounty;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class Bounties extends JavaPlugin {

    private static Economy econ = null;
    private final BountyCmd bounty = new BountyCmd();

    @Override
    public void onEnable() {
        Logger logger = getLogger();
        setupEconomy();
        createConfig();
        registerCommands();
        registerEvents();
        logger.info("Bounties has been enabled!");
    }

    @Override
    public void onDisable() {
        Logger logger = getLogger();
        logger.info("Bounties has been disabled!");
        for (Bounty b : BountyCmd.bounties.values()) {
            getEconomy().depositPlayer(b.getPlayer(), b.getPrice());
        }
    }

    private void createConfig() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
    }

    private void registerCommands() {
        getCommand("bounty").setExecutor(bounty);
        getCommand("bountylist").setExecutor(new BountyListCmd(this));
    }

    private void registerEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new BountyListener(), this);
        pm.registerEvents(new GuiHandler(this), this);
    }

    private void setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return;
        }
        econ = rsp.getProvider();
    }

    public static Economy getEconomy() {
        return econ;
    }
}
