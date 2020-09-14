package me.mob.basicimprovements;

import me.mob.basicecon.basicecon.BasicEcon;
import me.mob.basicimprovements.data.ImprovementData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ImprovementCommands implements CommandExecutor {

    private final BasicImprovements plugin = BasicImprovements.getInstance;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (command.getName().equalsIgnoreCase("ping")) {
                if (args.length == 0) {
                    try {
                        Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);
                        int ping = (int) entityPlayer.getClass().getField("ping").get(entityPlayer);
                        sender.sendMessage(ChatColor.GRAY + "Your ping is §a" + ping + "§7 milliseconds.");
                        return true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } if (args.length == 1) {
                    try {
                        Player target = Bukkit.getPlayer(args[0]);
                        if (target != null) {
                            Object entityPlayer = target.getClass().getMethod("getHandle").invoke(target);
                            int ping = (int) entityPlayer.getClass().getField("ping").get(entityPlayer);
                            sender.sendMessage(ChatColor.GRAY + "§a" + target.getName() + "§7's ping is §a" + ping + "§7 milliseconds.");
                        } else {
                            player.sendMessage(ChatColor.RED + "§lHEY! §r§7That player doesn't exist!");
                        }
                        return true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            if (command.getName().equalsIgnoreCase("playtime")) {
                UUID acc = null;
                String pname = null;
                try {
                    if (plugin.Econ) {
                        BasicEcon becon = BasicEcon.getInstance;
                        if (args.length == 0) {
                            acc = becon.playerInfo.getPlayerUUID(player.getName());
                            pname = "§7Your";
                        }
                        if (args.length == 1) {
                            acc = becon.playerInfo.getPlayerUUID(args[0]);
                            if (acc == null) {
                                player.sendMessage(ChatColor.RED + "§lHEY! §r§7That player doesn't exist!");
                                return true;
                            }
                            pname = "§a" + Bukkit.getOfflinePlayer(acc).getName() + "§7's";
                        }
                    } else {
                        acc = player.getUniqueId();
                        pname = "§7Your";
                    }
                    if (acc != null) {
                        long playtime = ImprovementData.getPlayerStatistic(acc, "play_one_minute");
                        double playtimemin = (double) playtime/20/60;
                        playtimemin = Math.round(playtimemin);
                        double playtimehrs;
                        double playtimefinal;
                        int unit = 1;
                        if (playtimemin >= 600) {
                            playtimehrs = ImprovementData.round(playtimemin / 60, 1);
                            unit = 2;
                            if (playtimehrs >= 250) {
                                playtimefinal = ImprovementData.round(playtimehrs / 24, 2);
                                unit = 3;
                            } else {
                                playtimefinal = playtimehrs;
                            }
                        } else {
                            playtimefinal = playtimemin;
                        }
                        switch (unit) {
                            case 1:
                                sender.sendMessage(ChatColor.GRAY + pname + "§7 playtime is §a" + playtimefinal + "§7 minutes.");
                                break;
                            case 2:
                                sender.sendMessage(ChatColor.GRAY + pname + "§7 playtime is §a" + playtimefinal + "§7 hours.");
                                break;
                            case 3:
                                sender.sendMessage(ChatColor.GRAY + pname + "§7 playtime is §a" + playtimefinal + "§7 days.");
                                break;
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "§lHEY! §r§7That player doesn't exist!");
                    }
                    return true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        }
        return false;
    }
}
