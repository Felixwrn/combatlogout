package de.wrn.combatlogout;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CombatCommand implements CommandExecutor {

    private final CombatManager manager;

    public CombatCommand(CombatManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender,
                             Command command,
                             String label,
                             String[] args) {

        if (args.length != 1) {
            sender.sendMessage("§c/combatlog <sekunden>");
            return true;
        }

        try {
            int seconds = Integer.parseInt(args[0]);

            manager.setCombatTime(seconds);
            sender.sendMessage(
                    "§aCombat-Zeit auf §e" + seconds + " Sekunden §agesetzt."
            );

        } catch (NumberFormatException e) {
            sender.sendMessage("§cBitte eine Zahl eingeben.");
        }

        return true;
    }
}