package me.mob.basicimprovements.basicimprovements.basicimprovements;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class ImprovementSetTP implements CommandExecutor {

    private final BasicImprovements plugin = BasicImprovements.getInstance;

    public HashMap<String, Location> backLoc = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (command.getName().equalsIgnoreCase("warp")) {
                if (args.length == 1) {
                    String loc = args[0];
                }
                // player.teleport(plugin.pwarps.get("spawn"));
            }
        }
        return false;
    }
}
