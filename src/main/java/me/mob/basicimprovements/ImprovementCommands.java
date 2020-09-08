package me.mob.basicimprovements;

import me.mob.basicimprovements.data.ImprovementData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
                        Object entityPlayer = target.getClass().getMethod("getHandle").invoke(target);
                        int ping = (int) entityPlayer.getClass().getField("ping").get(entityPlayer);
                        sender.sendMessage(ChatColor.GRAY + "§a" + target.getName() + "§7's ping is §a" + ping + "§7 milliseconds.");
                        return true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            if (command.getName().equalsIgnoreCase("playtime")) {
                if (args.length == 0) {
                    try {
                        long playtime = ImprovementData.getPlayerStatistic(player.getUniqueId(), "play_one_minute");
                        long playtimemin = playtime/20/60;
                        double playtimehrs = 0;
                        double playtimefinal = 0;
                        int unit = 1;
                        if (playtimemin >= 600) {
                            playtimehrs = ImprovementData.round(playtimemin / 60, 0);
                            unit = 2;
                            if (playtimehrs >= 250) {
                                playtimefinal = ImprovementData.round(playtimehrs / 24, 1);
                                unit = 3;
                            } else {
                                playtimefinal = playtimehrs;
                            }
                        } else {
                            playtimefinal = playtimemin;
                        }
                        switch (unit) {
                            case 1:
                                sender.sendMessage(ChatColor.GRAY + "Your playtime is §a" + playtimefinal + "§7 minutes.");
                                break;
                            case 2:
                                sender.sendMessage(ChatColor.GRAY + "Your playtime is §a" + playtimefinal + "§7 hours.");
                                break;
                            case 3:
                                sender.sendMessage(ChatColor.GRAY + "Your playtime is §a" + playtimefinal + "§7 days.");
                                break;
                        }
                        return true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                /*if (args.length == 1) {
                    try {
                        Player target = Bukkit.getPlayer(args[0]);
                        Object entityPlayer = target.getClass().getMethod("getHandle").invoke(target);
                        int ping = (int) entityPlayer.getClass().getField("ping").get(entityPlayer);
                        sender.sendMessage(ChatColor.GRAY + "§a" + target.getName() + "§7's playtime is §a" + ping + "§7 milliseconds.");
                        return true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } */
            }
        }
        return false;
    }
}
