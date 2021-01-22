package me.mob.basicimprovements.statistics;

import me.mob.basicecon.basicecon.BasicEcon;
import me.mob.basicimprovements.BasicImprovements;
import me.mob.basicimprovements.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class StatisticCommand implements CommandExecutor {

    private final BasicImprovements plugin = BasicImprovements.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("statistic")) {
            Player player;
            Player invoker = null;
            UUID target = null;
            if (sender instanceof Player) {
                invoker = (Player) sender;
            }
            if (args.length == 1) {
                if (invoker == null) {
                    sender.sendMessage(Messages.PLUGIN_NOTPLAYER.get());
                    return true;
                } else {
                    sender.sendMessage(Statistics.valueOf(args[0]).get(invoker.getUniqueId(), invoker));
                    return true;
                }

            } else if (args.length == 2) {
                try {
                    if (plugin.Econ) {
                        BasicEcon basicEcon = BasicEcon.getInstance;
                        target = basicEcon.playerInfo.getPlayerUUID(args[0]);
                    } else {
                        player = Bukkit.getPlayer(args[0]);
                        if (player != null) {
                            target = player.getUniqueId();
                        }
                    }
                    if (target == null) {
                        sender.sendMessage(Messages.PLUGIN_INVALID_PLAYER.get());
                        return true;
                    }
                    sender.sendMessage(Statistics.valueOf(args[1]).get(target, invoker));
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return false;
        }
        return false;
    }
}
