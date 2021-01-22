package me.mob.basicimprovements.statistics;

import me.mob.basicecon.basicecon.BasicEcon;
import me.mob.basicimprovements.BasicImprovements;
import me.mob.basicimprovements.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlaytimeCommand implements CommandExecutor {

    private final BasicImprovements plugin = BasicImprovements.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (command.getName().equalsIgnoreCase("playtime")) {
                UUID acc = null;
                String pname = null;
                if (args.length == 0) {
                    acc = player.getUniqueId();
                    pname = "Your";
                }
                if (args.length == 1) {
                    try {
                        if (plugin.Econ) {
                            BasicEcon becon = BasicEcon.getInstance;
                            acc = becon.playerInfo.getPlayerUUID(args[0]);
                            pname = Bukkit.getPlayer(acc).getDisplayName();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (acc == null) {
                    player.sendMessage(Messages.PLUGIN_INVALID_PLAYER.get());
                    return true;
                }
                long playtime = StatisticData.getPlayerStatistic(acc, "play_one_minute");
                double playtimemin = (double) playtime / 20 / 60;
                playtimemin = Math.round(playtimemin);
                double playtimehrs;
                double playtimefinal;
                int unit = 1;
                if (playtimemin >= 600) {
                    playtimehrs = StatisticData.round(playtimemin / 60, 1);
                    unit = 2;
                    if (playtimehrs >= 250) {
                        playtimefinal = StatisticData.round(playtimehrs / 24, 2);
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
                return true;
            }
        }

        return false;
    }
}
