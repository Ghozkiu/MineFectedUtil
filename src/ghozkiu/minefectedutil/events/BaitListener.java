package ghozkiu.minefectedutil.events;

import ghozkiu.minefectedutil.MineFectedUtil;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

@SuppressWarnings("deprecation")
public class BaitListener implements Listener {
    private MineFectedUtil plugin;

    public BaitListener(MineFectedUtil plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        ItemStack it = e.getItemDrop().getItemStack();
        Location locitem = e.getItemDrop().getLocation();
        this.plugin.getServer().getLogger().info("Check 0");
        if (it.getType() == Material.RAW_BEEF || it.getType() == Material.RAW_FISH || it.getType() == Material.COOKED_BEEF || it.getType() == Material.COOKED_RABBIT) {
            int randomNumb = (int) (Math.random() * 5);
            this.plugin.getServer().getLogger().info("Check 1");
            boolean checker = isOnBiome(p);
            if (checker) {
                if (randomNumb == 1) {
                    it.getItemMeta().setDisplayName("Carnada");
                    this.plugin.getServer().getLogger().info("Check 2");
                    p.sendMessage(ChatColor.GREEN + "Un animal ha sido atraido por la carnada!");
                    new BukkitRunnable() {

                        @Override
                        public void run() {
                            biomeChecker(p, locitem);
                            e.getItemDrop().remove();
                        }

                    }.runTaskLater(this.plugin, 100);


                } else {
                    e.getItemDrop().remove();
                }
            }


        }
    }

    @EventHandler
    public void onPickUp(PlayerPickupItemEvent e) {
        ItemStack item = e.getItem().getItemStack();
        if (item != null) {
            if ((item.hasItemMeta()) && (item.getItemMeta().getDisplayName() != null)) {
                ItemMeta meta = item.getItemMeta();
                if(ChatColor.stripColor(meta.getDisplayName()).equals("Carnada")){
                    e.setCancelled(true);
                }
            }
        }
    }


    public void spawnMountain(Location ploc) {
        int px = ploc.getBlockX();
        int py = ploc.getBlockY();
        int pz = ploc.getBlockZ();

        int x = ploc.getBlockX();
        int y = ploc.getBlockY();
        int z = ploc.getBlockZ();

        double distance = Math.sqrt(Math.pow(px + 4007, 2) + Math.pow(py - 150, 2) + Math.pow(pz - 1507, 2));
        int random = (int) (Math.random() * 2);
        if (distance <= 1850) {
            switch (random) {
                case 1:
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mm m s Puma 1 TWD2," + x + "," + y + "," + z);
                    this.plugin.getServer().getLogger().info("[Bait] Comando ejecutado");
                    break;
                case 2:
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mm m s PolarBear 1 TWD2," + x + "," + y + "," + z);
                    this.plugin.getServer().getLogger().info("[Bait] Comando ejecutado, Oso");
            }
        }
    }


    public void biomeChecker(Player pl, Location locitem) {
        this.plugin.getServer().getLogger().info("Check 3");
        Location loc = pl.getLocation();
        World world = pl.getWorld();
        int x = loc.getBlockX();
        int z = loc.getBlockZ();
        Biome biome = world.getBiome(x, z);
        if (biome.equals(Biome.TAIGA_COLD))
            spawnTundra(locitem);
        else if (biome.equals(Biome.JUNGLE))
            spawnSwamp(locitem);
        else
            spawnMountain(loc);
    }

    public void spawnTundra(Location locitem) {
        int x = locitem.getBlockX();
        int y = locitem.getBlockY();
        int z = locitem.getBlockZ();
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mm m s Manada 1 TWD2," + x + "," + y + "," + z);
        this.plugin.getServer().getLogger().info("[Bait] Comando ejecutado");
    }

    public void spawnSwamp(Location locitem) {
        int x = locitem.getBlockX();
        int y = locitem.getBlockY();
        int z = locitem.getBlockZ();
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mm m s Cocodrilo 1 TWD2," + x + "," + y + "," + z);
        this.plugin.getServer().getLogger().info("[Bait] Comando ejecutado");
    }

    public boolean isOnBiome(Player pl) {
        Location loc = pl.getLocation();
        World world = pl.getWorld();
        int x = loc.getBlockX();
        int z = loc.getBlockZ();
        Biome biome = world.getBiome(x, z);
        if (biome.equals(Biome.TAIGA_COLD))
            return true;
        else if (biome.equals(Biome.JUNGLE))
            return true;
        else return loc.getBlockY() > 100;
    }


}
