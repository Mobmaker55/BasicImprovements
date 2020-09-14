package me.mob.basicimprovements.teleports;

import me.mob.basicimprovements.BasicImprovements;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitScheduler;

import static org.bukkit.Bukkit.getPlayer;
import static org.bukkit.Bukkit.getServer;

public class TPEvents implements Listener {

    private final BasicImprovements plugin = BasicImprovements.getInstance;
    private final BukkitScheduler scheduler = getServer().getScheduler();
    private int count = 0;

    @EventHandler
    public void playerMove(PlayerMoveEvent event) {
        if (plugin.warpTasks.containsKey(event.getPlayer())) {

            if (event.getTo().getX() != event.getFrom().getX() || event.getTo().getZ() != event.getFrom().getZ() || event.getTo().getY() != event.getFrom().getY()) {
                count++;
            }
            if (count >= 2) {
                scheduler.cancelTask(plugin.warpTasks.get(event.getPlayer()));
                event.getPlayer().sendMessage(ChatColor.RED + "§lHEY! §r§7You moved! Warp cancelled.");
                plugin.warpTasks.remove(event.getPlayer());
                count = 0;
            }
        }
    }

    @EventHandler
    public void playerDamage(EntityDamageEvent event) {
        if (event.getEntity().getType() == EntityType.PLAYER) {
            Player player = getPlayer(event.getEntity().getUniqueId());
            if (plugin.warpTasks.containsKey(player)) {
                scheduler.cancelTask(plugin.warpTasks.get(player));
                player.sendMessage(ChatColor.RED + "§lHEY! §r§7You took damage! Warp cancelled.");
                plugin.warpTasks.remove(player);
            }
        }
    }
}
