package ghozkiu.minefectedutil.events;

import ghozkiu.minefectedutil.MineFectedUtil;
import ghozkiu.minefectedutil.thirst.PlayerThirst;
import ghozkiu.minefectedutil.thirst.Thirst;
import ghozkiu.minefectedutil.utilities.MFile;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class WorldChange implements Listener {
    private static MFile lastLocFile;

    public WorldChange(MineFectedUtil plugin) {
        lastLocFile = new MFile(plugin, "lastLoc.yml");
    }

    public static MFile getLastLocFile() {
        return lastLocFile;
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        Location playerFromLoc = event.getFrom();
        Location playerToLoc = event.getTo();
        Player player = event.getPlayer();
        World lastWorld = playerFromLoc.getWorld();
        World currentWorld = playerToLoc.getWorld();


        switch (lastWorld.getName()) {
            case "TWD2":
                lastLocFile.set("Data." + player.getName(), lastWorld.getName() + " " +
                        playerFromLoc.getX() + " " + playerFromLoc.getY() + " " + playerFromLoc.getZ());
                lastLocFile.save();
                break;
            case "construccion":
                if (currentWorld.getName().equals("TWD2")) {
                    player.getInventory().clear();
                }
                break;
        }

        thirstPlayerTeleport(event);
    }

    public void thirstPlayerTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        PlayerThirst playerT = Thirst.playersT.get(player.getName());
        if(playerT != null){
            if (Thirst.isInEnabledWorld(event.getTo().getWorld().getName())) {
                playerT.getBossBar().addPlayer(player);
                Thirst.setBossBarProgress(player, playerT.getThirstLevel());
            } else {
                playerT.getBossBar().removePlayer(player);
            }
        }
    }

}
