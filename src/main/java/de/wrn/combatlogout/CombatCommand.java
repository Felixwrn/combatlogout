package de.wrn.combatlogout;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CombatCommand implements CommandExecutor {

    private final Combatlogout plugin;
    private final CombatManager manager;

    public CombatCommand(
            Combatlogout plugin,
            CombatManager manager
    ) {
        this.plugin = plugin;
        this.manager = manager;
    }

    @Override
    public boolean onCommand(
            CommandSender sender,
            Command command,
            String label,
            String[] args
    ) {

        if (args.length == 1 &&
                args[0].equalsIgnoreCase("reload")) {

            plugin.reloadConfig();

            manager.setCombatTime(
                    plugin.getConfig()
                            .getInt("combat-time", 15)
            );

            sender.sendMessage(
                    "§aCombatLogout neu geladen."
            );

            return true;
        }

        sender.sendMessage(
                "§c/combatlog reload"
        );

        return true;
    }
}