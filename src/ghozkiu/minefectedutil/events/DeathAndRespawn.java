package ghozkiu.minefectedutil.events;

import ghozkiu.minefectedutil.thirst.PlayerThirst;
import ghozkiu.minefectedutil.thirst.Thirst;
import ghozkiu.minefectedutil.utilities.MessageManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DeathAndRespawn implements Listener {
    private final Map<Player, List<ItemStack>> inventories = new HashMap<>();
    private final List<String> notDroppableItems;

    public DeathAndRespawn(Plugin plugin) {
        this.notDroppableItems = plugin.getConfig().getStringList("Config.notDroppableItems");
    }

    //    @EventHandler(priority = EventPriority.HIGHEST)
    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        thirstOnDeath(player);
        dropsOnDeath(event, player);
    }

//    @EventHandler(priority = EventPriority.HIGHEST)
    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        thirstOnRespawn(player);
        dropsOnRespawn(player);
    }

    public void dropsOnDeath(PlayerDeathEvent event, Player player){

        List<ItemStack> drops = event.getDrops();
        List<ItemStack> items = new LinkedList<>();

        player.getInventory().forEach(item -> {
            if (item != null) {
                if (item.getItemMeta().getDisplayName() != null) {
                    for (String noDItem : notDroppableItems) {
                        if (ChatColor.stripColor(item.getItemMeta().getDisplayName()).equals(noDItem)) {
                            items.add(item);
                        }
                    }
                }
            }
        });
        inventories.put(player, items);
        for (ItemStack item : items) {
            drops.remove(item);
            MessageManager.ConsoleInfo("Se ha eliminado " + item.getType().name() + " del drop");
        }
    }

    public void dropsOnRespawn(Player player){
        List<ItemStack> items = inventories.get(player);
        if (items != null) {
            for (ItemStack item : items) {
                player.getInventory().addItem(item);
            }
            items.clear();
        }
    }

    public void thirstOnDeath(Player player){
        PlayerThirst playerT = Thirst.playersT.get(player.getName());
        playerT.setThirstLevel(100);
        playerT.getBossBar().removePlayer(player);
    }

    public void thirstOnRespawn(Player player){
        PlayerThirst playerT = Thirst.playersT.get(player.getName());
        if(Thirst.isInEnabledWorld(player)){
            Thirst.setBossBarProgress(player, playerT.getThirstLevel());
        }
    }
}
