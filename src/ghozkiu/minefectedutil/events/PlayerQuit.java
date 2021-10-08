package ghozkiu.minefectedutil.events;

import ghozkiu.minefectedutil.thirst.PlayerThirst;
import ghozkiu.minefectedutil.thirst.Thirst;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        PlayerThirst playerT = Thirst.playersT.get(player.getName());
        playerT.setQuitTime(System.currentTimeMillis());
    }
}
