package me.mob.basicimprovements.teleports;

import me.mob.basicimprovements.BasicImprovements;
import me.mob.basicimprovements.Messages;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TPRunnable implements Runnable {

    private final BasicImprovements plugin = BasicImprovements.getInstance();
    private final Player tptarg;
    private Location destination;
    private final String locname;

    public TPRunnable(String location, Player player, int loctype) {
        tptarg = player;
        locname = location;
        switch(loctype) {
            case 1:
                destination = plugin.publicWarps.get(location);
                break;
            case 2:
                destination = plugin.homeLocations.get(location);
                break;
            case 3:
                destination = plugin.backLoc.get(location);
                break;
        }
    }

    @Override
    public void run() {
        plugin.backLoc.put(tptarg.getName(), tptarg.getLocation());
        tptarg.teleport(destination);
        if (locname.contentEquals(tptarg.getName().toLowerCase())) {
            tptarg.sendMessage(Messages.HOME_SUCCESS.get());
        } else {
            tptarg.sendMessage(Messages.WARP_SUCCESS.get(locname));
        }
        plugin.warpTasks.remove(tptarg);

    }
}
