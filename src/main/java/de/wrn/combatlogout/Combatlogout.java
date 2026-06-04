package de.wrn.combatlogout;

import org.bukkit.plugin.java.JavaPlugin;

public final class Combatlogout extends JavaPlugin {

    private CombatManager combatManager;

    @Override
    public void onEnable() {

        combatManager = new CombatManager(this);

        getServer().getPluginManager().registerEvents(
                new CombatListener(combatManager),
                this
        );

        getServer().getPluginManager().registerEvents(
                new LogoutListener(combatManager),
                this
        );

        getCommand("combatlog").setExecutor(new CombatCommand(combatManager));
    }

    @Override
    public void onDisable() {

    }
}