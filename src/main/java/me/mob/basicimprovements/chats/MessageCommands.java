package me.mob.basicimprovements.chats;

import me.mob.basicimprovements.BasicImprovements;
import me.mob.basicimprovements.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MessageCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("broadcast")) {
            StringBuilder broadStream = new StringBuilder();
            for (String arg : args) {
                broadStream.append(" ").append(arg);
            }
            Bukkit.broadcastMessage(BasicImprovements.color(Messages.PLUGIN_PREFIX.get() + broadStream.toString()));
            return true;
        }
        return false;
    }
    //reload Messages.java command
}
