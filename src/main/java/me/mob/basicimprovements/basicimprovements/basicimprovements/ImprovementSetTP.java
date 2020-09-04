package me.mob.basicimprovements.basicimprovements.basicimprovements;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ImprovementSetTP implements CommandExecutor, TabCompleter {

    private final BasicImprovements plugin = BasicImprovements.getInstance;



    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            // Warp command
            if (command.getName().equalsIgnoreCase("warp")) {
                if (args.length == 1) {
                    String locName = args[0].toLowerCase();
                    //noinspection IfStatementWithIdenticalBranches
                    if (plugin.publicWarps.containsKey(locName)) {
                        String pgn = player.getName();
                        plugin.backLoc.put(pgn, player.getLocation());
                        player.teleport(plugin.publicWarps.get(locName));
                        return true;
                    } else {
                        sender.sendMessage(ChatColor.RED + "§lHEY! §r§7That warp doesn't exist. Make sure you spelled it right, or that it exists!");
                        return true;
                    }
                }
            }

            // Setwarp command
            if (command.getName().equalsIgnoreCase("setwarp")) {
                if (args.length == 1) {
                    String locName = args[0].toLowerCase();
                    //noinspection IfStatementWithIdenticalBranches
                    plugin.publicWarps.put(locName, player.getLocation());
                    return true;
                } else {
                    sender.sendMessage(ChatColor.RED + "§lHEY! §r§7Make sure to name the warp!");
                }
            }

            if (command.getName().equalsIgnoreCase("delwarp")) {
                if (args.length == 1) {
                    String locName = args[0].toLowerCase();
                    //noinspection IfStatementWithIdenticalBranches
                    plugin.publicWarps.remove(locName);
                    return true;
                } else {
                    sender.sendMessage(ChatColor.RED + "§lHEY! §r§7Make sure to name the warp!");
                }
            }

            if (command.getName().equalsIgnoreCase("home")) {
                if (args.length == 0) {
                    //noinspection IfStatementWithIdenticalBranches
                    String lc = player.getName().toLowerCase();
                    if (plugin.homeLocations.containsKey(lc)) {
                        plugin.backLoc.put(player.getName(), player.getLocation());
                        player.teleport(plugin.homeLocations.get(lc));
                        return true;
                    } else {
                        sender.sendMessage(ChatColor.RED + "§lHEY! §r§7Make sure to set your spawn first!");
                        return true;
                    }
                }
            }

            if (command.getName().equalsIgnoreCase("sethome")) {
                if (args.length == 0) {
                    String lc = player.getName().toLowerCase();
                    plugin.homeLocations.put(lc, player.getLocation());
                    return true;
                }
                /*if (args.length == 1) {
                    if (player.hasPermission("basicimprovements.admin")) {
                        String tg = args[0];
                        Player target = Bukkit.getPlayer(tg);
                        if (target != null) {
                            String lc = target.getName().toLowerCase();
                            plugin.homeLocations.put(lc, player.getLocation());
                            return true;
                        } else {
                            sender.sendMessage(ChatColor.RED + "§lHEY! §r§7Make sure you spelled that player's name right!");
                        }
                    }
                } */
            }

            if (command.getName().equalsIgnoreCase("back")) {
                if (args.length == 0) {
                    if (plugin.backLoc.containsKey(player.getName())) {
                        player.teleport(plugin.backLoc.get(player.getName()));
                        return true;
                    } else {
                        sender.sendMessage(ChatColor.RED + "§lHEY! §r§7There's nowhere to go back to!");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (command.getName().equalsIgnoreCase("warp")) {
            return new ArrayList<>(plugin.publicWarps.keySet());
        }
        if (command.getName().equalsIgnoreCase("setwarp")) {
            return new ArrayList<>(plugin.publicWarps.keySet());
        }
        if (command.getName().equalsIgnoreCase("delwarp")) {
            return new ArrayList<>(plugin.publicWarps.keySet());
        }
        return null;
    }
}
