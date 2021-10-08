package ghozkiu.minefectedutil.diseases;

import com.massivecraft.factions.entity.MPlayer;
import ghozkiu.minefectedutil.factions.FactionVerification;
import ghozkiu.minefectedutil.utilities.armor.ArmorVerification;
import ghozkiu.minefectedutil.utilities.metadata.WhisperersMetaData;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.UUID;

public class Whisperers implements Listener {
    private Player p;
    private FileConfiguration config;
    private List<String> mobsName;

    public Whisperers(Plugin plugin) {
        this.config = plugin.getConfig();
        this.mobsName = config.getStringList("Config.mobsInvulnerability");
    }

    @EventHandler
    private void onEntityTarget(EntityTargetLivingEntityEvent e) {
        if (e.getTarget() instanceof Player) {

            this.p = (Player) e.getTarget();
            UUID uuid = p.getUniqueId();
            Entity mob = e.getEntity();


            for (String unSplitString : mobsName) {
                String[] splitString = unSplitString.split(":", 2);
                if (splitString[0].equals(ChatColor.stripColor(mob.getCustomName()))) {
                    MPlayer mPlayer = MPlayer.get(p);
                    if (mPlayer.getFaction().getName().equalsIgnoreCase(splitString[1])) {
                        e.setCancelled(true);
                    }
                }
            }

            if (mob instanceof PigZombie && !WhisperersMetaData.hasPigZombieMetadata((PigZombie) mob, uuid)) {
                if (ArmorVerification.hasMask(p)) {
                    e.setCancelled(true);
                    mask();
                } else if (FactionVerification.getFaction(p)) {
                    e.setCancelled(true);
                }
            }

            if (mob instanceof Zombie && !WhisperersMetaData.hasZombieMetadata((Zombie) mob, uuid)) {
                if (ArmorVerification.hasMask(p)) {
                    e.setCancelled(true);
                    mask();
                } else if (ArmorVerification.hasGuts(p)) {
                    e.setCancelled(true);
                    guts();
                }
            }

        }
    }

    private void mask() {
        ItemStack helmet = this.p.getInventory().getHelmet();
        if (helmet.getDurability() <= config.getInt("Config.mask_durability")) {
            helmet.setDurability((short) (helmet.getDurability() + 1));
        } else {
            this.p.getInventory().setHelmet(new ItemStack(Material.AIR));
            this.p.updateInventory();
            this.p.sendTitle(ChatColor.RED + "Maldicion!", ChatColor.YELLOW + "Se ha destruido mi mascara.", 10,
                    40, 10);
        }
    }

    private void guts() {
        ItemStack chestplate = this.p.getInventory().getChestplate();
        if (chestplate.getDurability() <= config.getInt("Config.guts_durability")) {
            chestplate.setDurability((short) (chestplate.getDurability() + 1));
        } else {
            this.p.getInventory().setChestplate(new ItemStack(Material.AIR));
            this.p.updateInventory();
            this.p.sendTitle(ChatColor.RED + "Maldicion!", ChatColor.YELLOW + "Se han destruido las tripas.",
                    10, 40, 10);
        }
    }

    public static void rally(EntityDamageByEntityEvent e) {
        Entity radius = e.getDamager();
        List<Entity> entities = radius.getNearbyEntities(15.0D, 15.0D, 15.0D);

        for (Entity ent : entities) {
            if (ent instanceof PigZombie &&
                    e.getDamager() instanceof Player) {
                UUID uuid = e.getDamager().getUniqueId();
                WhisperersMetaData.applyPigZombie((PigZombie) ent, uuid);
            }
            if (ent instanceof Zombie &&
                    e.getDamager() instanceof Player) {
                UUID uuid = e.getDamager().getUniqueId();
                WhisperersMetaData.applyZombie((Zombie) ent, uuid);
            }
        }
    }
}
