package ghozkiu.minefectedutil.factions;


import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.util.IdUtil;
import com.shampaggon.crackshot.events.WeaponDamageEntityEvent;
import ghozkiu.minefectedutil.MineFectedUtil;
import ghozkiu.minefectedutil.utilities.paths.Paths;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;


public class FriendlyFire implements Listener {
    private final MineFectedUtil plugin;

    public FriendlyFire(MineFectedUtil plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void BlockFriendlyFire(EntityDamageByEntityEvent e) {
        if (e.getDamager() == null || e.getEntity() == null)
            return;
        if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
            Player p1 = (Player) e.getDamager();
            Player p2 = (Player) e.getEntity();
            MPlayer fpname1 = MPlayer.get(IdUtil.getId(p1));
            MPlayer fpname2 = MPlayer.get(IdUtil.getId(p2));
            String Mapa = String.valueOf(FactionColl.get().getByName("Mapa"));
            String faction = String.valueOf(fpname1.getFaction());
            String faction2 = String.valueOf(fpname2.getFaction());


            if (!faction.equals(Mapa) && !faction2.equals(Mapa)) {
                if (faction.equals(faction2) && this.plugin.getConfig().getString(Paths.FRIENDLYF.get()).equals("false") && p2
                        .getLocation().getBlock().getBiome() != Biome.DESERT && p1
                        .getLocation().getBlock().getBiome() != Biome.DESERT)
                    e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void BlockCrackshot(WeaponDamageEntityEvent e) {
        if (e.getPlayer() == null || e.getVictim() == null)
            return;
        if (e.getVictim() instanceof Player) {
            Player p1 = e.getPlayer();
            Player p2 = (Player) e.getVictim();
            MPlayer fpname1 = MPlayer.get(IdUtil.getId(p1));
            MPlayer fpname2 = MPlayer.get(IdUtil.getId(p2));
            String Mapa = String.valueOf(FactionColl.get().getByName("Mapa"));
            String faction = String.valueOf(fpname1.getFaction());
            String faction2 = String.valueOf(fpname2.getFaction());
            if (!faction.equals(Mapa) && !faction2.equals(Mapa)) {
                if (faction.equals(faction2) && this.plugin.getConfig().getString(Paths.FRIENDLYF.get()).equals("false") && p2
                        .getLocation().getBlock().getBiome() != Biome.DESERT && p1
                        .getLocation().getBlock().getBiome() != Biome.DESERT)
                    e.setCancelled(true);
            }
        }
    }
}
