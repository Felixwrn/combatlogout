package de.wrn.combatlogout;

import org.bukkit.plugin.java.JavaPlugin;

public final class Combatlogout extends JavaPlugin {

    private CombatManager combatManager;

    @Override
    public void onEnable() {

        saveDefaultConfig();

        combatManager = new CombatManager(this);

        getServer().getPluginManager().registerEvents(
                new CombatListener(combatManager),
                this
        );

        getServer().getPluginManager().registerEvents(
                new LogoutListener(combatManager),
                this
        );

        getServer().getPluginManager().registerEvents(
                new CommandListener(combatManager),
                this
        );

        getServer().getPluginManager().registerEvents(
                new TeleportListener(combatManager),
                this
        );

        getServer().getPluginManager().registerEvents(
                new PearlListener(combatManager),
                this
        );

        getServer().getPluginManager().registerEvents(
                new TridentListener(combatManager),
                this
        );

        if (getCommand("combatlog") != null) {
            getCommand("combatlog").setExecutor(
                    new CombatCommand(this, combatManager)
            );
        }

        getLogger().info("WRN BattleCore has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("WRN BattleCore has been disabled!");
    }

    public CombatManager getCombatManager() {
        return combatManager;
    }
}