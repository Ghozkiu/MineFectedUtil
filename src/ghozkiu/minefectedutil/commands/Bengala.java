package ghozkiu.minefectedutil.commands;

import ghozkiu.minefectedutil.MineFectedUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Bengala implements CommandExecutor {
    private MineFectedUtil plugin;


    public Bengala(MineFectedUtil plugin) {               //Reemplazar por MinefectedUtil
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
        if (!(sender instanceof Player)) {
            String player_name = args[0];
            Player p = Bukkit.getPlayer(player_name);
            p.sendMessage(ChatColor.RED + "Has atraido a una horda!");

            spawnHorde(p);
        } else {
            sender.sendMessage(ChatColor.RED + "Permisos insuficientes!");

        }
        return true;

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


