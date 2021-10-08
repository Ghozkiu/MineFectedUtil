package ghozkiu.minefectedutil.events;

import com.shampaggon.crackshot.events.WeaponShootEvent;
import ghozkiu.minefectedutil.MineFectedUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

public class BengalaListener implements Listener {
    private MineFectedUtil plugin;                //Reemplazar por MinefectedUtil


    public BengalaListener(MineFectedUtil plugin) {               //Reemplazar por MinefectedUtil
        this.plugin = plugin;
    }

    @EventHandler
    public void onShoot(WeaponShootEvent e) {
        String title = e.getWeaponTitle();
        Player p = e.getPlayer();
        if (title.equals("Bengala")) {
            p.sendMessage(ChatColor.RED + "Has atraido a una horda!");
            spawnHorde(p);
        }
    }


    public void spawnHorde(Player p) {
        Location loc = p.getLocation();
        int x = loc.getBlockX();
        int y = loc.getBlockY();
        int z = loc.getBlockZ();
        timedSpawn(x, y, z);

    }


    public void timedSpawn(int x, int y, int z) {
        int rad = 5; //Esto deberia ser editable en la config
        new BukkitRunnable() {

            @Override
            public void run() {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mm m s Caminante 15 TWD2," + (x + rad) + "," + y + "," + (z + rad));   //Spawn 1
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mm m s Caminante 15 TWD2," + (x + rad) + "," + y + "," + (z));  //Spawn 2
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mm m s Caminante 15 TWD2," + (x - rad) + "," + y + "," + (z - rad));  // SPawn 3
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mm m s Caminante 15 TWD2," + (x - rad) + "," + y + "," + (z)); //SPawn 4
            }

        }.runTaskLater(this.plugin, 200);       //El tiempo deberia ser configurable

    }
}
