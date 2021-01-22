package me.mob.basicimprovements;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ImprovementCommands implements CommandExecutor {

    private final BasicImprovements plugin = BasicImprovements.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("basicreload")) {
            Messages.reloadLanguage();
            sender.sendMessage(Messages.PLUGIN_RELOAD.get());
            return true;
        }

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (command.getName().equalsIgnoreCase("ping")) {
                Object entityPlayer = null;
                String entityName = null;
                int ping = 0;
                try {
                    if (args.length == 0) {
                        entityPlayer = player.getClass().getMethod("getHandle").invoke(player);
                        entityName = "Your";
                    } else if (args.length == 1) {
                        Player target = Bukkit.getPlayer(args[0]);
                        entityPlayer = target.getClass().getMethod("getHandle").invoke(target);
                        entityName = target.getDisplayName();
                    } else {
                        return false;
                    }
                    if (entityPlayer == null) {
                        return false;
                    }
                    ping = (int) entityPlayer.getClass().getField("ping").get(entityPlayer);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                sender.sendMessage(Messages.PLUGIN_PING.get(entityName, String.valueOf(ping)));
                return true;
            }


        }
        return false;
    }
}
