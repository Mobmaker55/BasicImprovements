package me.mob.basicimprovements.teleports;

import me.mob.basicimprovements.BasicImprovements;
import me.mob.basicimprovements.Messages;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.bukkit.Bukkit.getServer;

public class TPCommands implements CommandExecutor, TabCompleter {

    private final BasicImprovements plugin = BasicImprovements.getInstance();
    private final BukkitScheduler scheduler = getServer().getScheduler();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            // Warp command
            if (command.getName().equalsIgnoreCase("warp")) {
                if (args.length == 1) {
                    String locName = args[0].toLowerCase();
                    if (plugin.publicWarps.containsKey(locName)) {
                        int taskid = scheduler.runTaskLater(plugin, new TPRunnable(locName, player, 1), 5 * 20).getTaskId();
                        plugin.warpTasks.put(player, taskid);
                        player.sendMessage(Messages.WARP_START.get());
                    } else {
                        sender.sendMessage(Messages.WARP_NOTFOUND.get());
                    }
                    return true;
                }
            }

            if (command.getName().equalsIgnoreCase("home")) {
                if (args.length == 0) {
                    String lc = player.getName().toLowerCase();
                    if (plugin.homeLocations.containsKey(lc)) {
                        if (!plugin.warpTasks.containsKey(player)) {
                            int taskid = scheduler.runTaskLater(plugin, new TPRunnable(lc, player, 2), 20 * 5).getTaskId();
                            plugin.warpTasks.put(player, taskid);
                            player.sendMessage(Messages.WARP_START.get());
                        }
                    } else {
                        player.sendMessage(Messages.HOME_NOTFOUND.get());
                    }
                    return true;
                }
            }

            if (command.getName().equalsIgnoreCase("back")) {
                if (args.length == 0) {
                    if (plugin.backLoc.containsKey(player.getName())) {
                        if (!plugin.warpTasks.containsKey(player)) {
                            int taskid = scheduler.runTaskLater(plugin, new TPRunnable(player.getName(), player, 3), 20 * 5).getTaskId();
                            plugin.warpTasks.put(player, taskid);
                            player.sendMessage(Messages.WARP_START.get());
                        }
                    } else {
                        sender.sendMessage(ChatColor.RED + "§lHEY! §r§7There's nowhere to go back to!");
                    }
                    return true;
                }
            }

            // Setwarp command
            if (command.getName().equalsIgnoreCase("setwarp")) {
                if (args.length == 1) {
                    String locName = args[0].toLowerCase();
                    plugin.publicWarps.put(locName, player.getLocation());
                    player.sendMessage(Messages.WARP_SET_SUCCESS.get(locName));
                } else {
                    sender.sendMessage(Messages.WARP_UNNAMED.get());
                }
                return true;
            }

            if (command.getName().equalsIgnoreCase("delwarp")) {
                if (args.length == 1) {
                    String locName = args[0].toLowerCase();
                    plugin.publicWarps.remove(locName);
                    player.sendMessage(Messages.WARP_DEL_SUCCESS.get(locName));
                    player.sendMessage(ChatColor.GRAY + "Well, temporarily, this doesn't work properly yet :)");
                } else {
                    sender.sendMessage(Messages.WARP_UNNAMED.get());
                }
                return true;
            }

            if (command.getName().equalsIgnoreCase("sethome")) {
                if (args.length == 0) {
                    String lc = player.getName().toLowerCase();
                    plugin.homeLocations.put(lc, player.getLocation());
                    player.sendMessage(Messages.HOME_SET_SUCCESS.get());
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
            if (command.getName().equalsIgnoreCase("randomtp")) {
                double timestamp = System.currentTimeMillis();
                if (plugin.rtpCooldown.containsKey(player)) {
                    double pTPC = plugin.rtpCooldown.get(player);
                    if (pTPC >= (timestamp - 3600000)) {
                        double remaining = (double) Math.round((60-((timestamp - pTPC)/60000)) * 10) / 10;
                        player.sendMessage(ChatColor.RED + "You can't run this command yet! You still have " + ChatColor.GOLD + remaining + ChatColor.RED + " minutes left!");
                        return true;
                    }
                }
                Random rand = new Random();
                World world = player.getWorld();
                if (args.length == 0) {
                    int safe = 0;
                    while (true) {
                        int rtpX = rand.nextInt(10000 + 10000) - 10000;
                        int rtpZ = rand.nextInt(10000 + 10000) - 10000;
                        Location loc = new Location(world, rtpX, 63, rtpZ, player.getLocation().getYaw(), player.getLocation().getPitch());
                        Chunk chunk = loc.getChunk();
                        chunk.load();
                        for (int i = 64; i <= 256; i++) {
                            loc.setY(i);
                            if (loc.getBlock().isEmpty()) {
                                safe ++;
                                if (safe == 6) {
                                    loc.setY(i-7);
                                    if (!loc.getBlock().isEmpty()) {
                                        if (!loc.getBlock().isLiquid()) {
                                            loc.setY(i-5);
                                            player.teleport(loc);
                                            plugin.rtpCooldown.put(player, timestamp);
                                            return true;
                                        } else {
                                            safe = 0;
                                        }
                                    } else {
                                        safe = 0;
                                    }
                                }
                            } else {
                                safe = 0;
                            }
                        }
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
