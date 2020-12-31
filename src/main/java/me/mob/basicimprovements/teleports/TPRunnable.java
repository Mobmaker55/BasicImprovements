package me.mob.basicimprovements.teleports;

import me.mob.basicimprovements.BasicImprovements;
import me.mob.basicimprovements.Messages;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Objects;

public class TPRunnable implements Runnable {

    private final BasicImprovements plugin = BasicImprovements.getInstance();
    private final Player tptarg;
    private final Location destination;
    private final Messages tpMessage;
    private Boolean isBack = false;

    public TPRunnable(Location location, Player player, Messages msgKey) {
        tptarg = player;
        destination = location;
        tpMessage = msgKey;
        if (msgKey.equals(Messages.BACK_SUCCESS)) {
            isBack = true;
        }
    }

    @Override
    public void run() {
        if (isBack) {
            plugin.backLoc.remove(tptarg.getName());
        } else {
            plugin.backLoc.put(tptarg.getName(), tptarg.getLocation());
        }
        if (destination.getWorld() == tptarg.getWorld()) {
            tptarg.teleport(destination);
            plugin.warpTasks.remove(tptarg);
            tptarg.sendMessage(tpMessage.get());
        } else {
            tptarg.sendMessage(Messages.WARP_WRONGWORLD.get(Objects.requireNonNull(destination.getWorld()).getName()));
        }

    }
}
