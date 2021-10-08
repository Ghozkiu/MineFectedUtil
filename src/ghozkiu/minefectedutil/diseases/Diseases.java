package ghozkiu.minefectedutil.diseases;

import ghozkiu.minefectedutil.MineFectedUtil;
import ghozkiu.minefectedutil.utilities.Percentages;
import ghozkiu.minefectedutil.utilities.metadata.OtherMetaData;
import ghozkiu.minefectedutil.utilities.paths.Paths;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public class Diseases implements Listener {
    public static MineFectedUtil plugin;
    private Percentages percentages;

    public Diseases(MineFectedUtil plugin) {
        Diseases.plugin = plugin;
        percentages = new Percentages(plugin);
    }

    public static void applyDisease(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();
        long cooldown = plugin.getConfig().getInt("Config.bleeding_timer_cooldown") * 20L;
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            if (OtherMetaData.hasBleeding(p, uuid)) {
                p.damage(plugin.getConfig().getInt("Config.bleeding_damage"));
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString(Paths.BLEEDING.get())));
            }
        }, 0, cooldown);

        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            if (OtherMetaData.hasLegInfection(p, uuid) || OtherMetaData.hasBodyInfection(p, uuid) || OtherMetaData.hasArmInfection(p, uuid))
                p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, plugin.getConfig().getInt("Config.infection_effect_cooldown") * 20, plugin.getConfig().getInt("Config.infection_effect_level")));
        }, plugin.getConfig().getInt("infection_delay_effect_first_run"), (plugin.getConfig().getInt("Config.infection_timer_cooldown") * 20L));

        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            if (OtherMetaData.hasAmputatedLeg(p, uuid) && !OtherMetaData.hasLegProsthesis(p, uuid))
                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, plugin.getConfig().getInt("Config.amputation_slowness_duration") * 20, plugin.getConfig().getInt("Config.amputation_slowness_level")));
        }, plugin.getConfig().getInt("slowness_delay_effect_first_run"), (plugin.getConfig().getInt("Config.slowness_effect_timer_cooldown") * 20L));
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent e) {
        if (e.getDamager() == null || e.getEntity() == null)
            return;
        if (e.getDamager() instanceof Zombie && e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            UUID uuid = p.getUniqueId();
            if (plugin.getConfig().getString(Paths.INFECTION.get()).equals("true")) {
                if ((!OtherMetaData.hasBleeding(p, uuid)) && (!OtherMetaData.hasLegInfection(p, uuid)) && (!OtherMetaData.hasArmInfection(p, uuid)) && (!OtherMetaData.hasBodyInfection(p, uuid))) {
                    this.percentages.infectionType(plugin.getConfig().getInt("Config.percentage_value"), p);
                }
            }
        }
        Whisperers.rally(e);
    }
}
