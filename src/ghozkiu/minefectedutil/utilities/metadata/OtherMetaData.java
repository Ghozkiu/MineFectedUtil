package ghozkiu.minefectedutil.utilities.metadata;

import ghozkiu.minefectedutil.MineFectedUtil;
import ghozkiu.minefectedutil.diseases.Diseases;
import ghozkiu.minefectedutil.utilities.paths.Paths;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.UUID;

public class OtherMetaData implements Listener {
    private static MineFectedUtil plugin;

    public OtherMetaData(MineFectedUtil plugin) {
        OtherMetaData.plugin = plugin;
    }

    public static void setBodyInfection(Player p, UUID uuid) {
        p.setMetadata(uuid + "_bodyInfected", new FixedMetadataValue(plugin, "MineFectedUtil"));
    }

    public static boolean hasBodyInfection(Player p, UUID uuid) {
        return p.hasMetadata(uuid + "_bodyInfected");
    }

    public static void removeBodyInfection(Player p, UUID uuid) {
        p.removeMetadata(uuid + "_bodyInfected", plugin);
    }

    public static void setLegInfection(Player p, UUID uuid) {
        p.setMetadata(uuid + "_legInfected", new FixedMetadataValue(plugin, "MineFectedUtil"));
    }

    public static boolean hasLegInfection(Player p, UUID uuid) {
        return p.hasMetadata(uuid + "_legInfected");
    }

    public static void removeLegInfection(Player p, UUID uuid) {
        p.removeMetadata(uuid + "_legInfected", plugin);
    }

    public static void setArmInfection(Player p, UUID uuid) {
        p.setMetadata(uuid + "_armInfected", new FixedMetadataValue(plugin, "MineFectedUtil"));
    }

    public static boolean hasArmInfection(Player p, UUID uuid) {
        return p.hasMetadata(uuid + "_armInfected");
    }

    public static void removeArmInfection(Player p, UUID uuid) {
        p.removeMetadata(uuid + "_armInfected", plugin);
    }

    public static void setBleeding(Player p, UUID uuid) {
        p.setMetadata(uuid + "_bleeding", new FixedMetadataValue(plugin, "MineFectedUtil"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', Diseases.plugin.getConfig().getString(Paths.AMPUTATED.get())));
    }

    public static boolean hasBleeding(Player p, UUID uuid) {
        return p.hasMetadata(uuid + "_bleeding");
    }

    public static void removeBleeding(Player p, UUID uuid) {
        p.removeMetadata(uuid + "_bleeding", plugin);
    }

    public static void setAmputatedLeg(Player p, UUID uuid) {
        p.setMetadata(uuid + "_amLeg", new FixedMetadataValue(plugin, "MineFectedUtil"));
    }

    public static boolean hasAmputatedLeg(Player p, UUID uuid) {
        return p.hasMetadata(uuid + "_amLeg");
    }

    public static void removeAmputatedLeg(Player p, UUID uuid) {
        p.removeMetadata(uuid + "_amLeg", plugin);
    }

    public static void setAmputatedArm(Player p, UUID uuid) {
        p.setMetadata(uuid + "_amArm", new FixedMetadataValue(plugin, "MineFectedUtil"));
    }

    public static boolean hasAmputatedArm(Player p, UUID uuid) {
        return p.hasMetadata(uuid + "_amArm");
    }

    public static void removeAmputatedArm(Player p, UUID uuid) {
        p.removeMetadata(uuid + "_amArm", plugin);
    }
    public static void setLegProsthesis(Player p, UUID uuid) {
        p.setMetadata(uuid + "_legProsthesis", new FixedMetadataValue(plugin, "MineFectedUtil"));
    }

    public static boolean hasLegProsthesis(Player p, UUID uuid) {
        return p.hasMetadata(uuid + "_legProsthesis");
    }

    public static void removeLegProsthesis(Player p, UUID uuid) {
        p.removeMetadata(uuid + "_legProsthesis", plugin);
    }
    public static void setArmProsthesis(Player p, UUID uuid) {
        p.setMetadata(uuid + "_armProsthesis", new FixedMetadataValue(plugin, "MineFectedUtil"));
    }

    public static boolean hasArmProsthesis(Player p, UUID uuid) {
        return p.hasMetadata(uuid + "_armProsthesis");
    }

    public static void removeArmProsthesis(Player p, UUID uuid) {
        p.removeMetadata(uuid + "_armProsthesis", plugin);
    }

    @EventHandler
    public void RemoveMetaOnDeath(EntityDeathEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player)e.getEntity();
            UUID uuid = p.getUniqueId();
            if (hasBodyInfection(p, uuid))
                removeBodyInfection(p, uuid);
            if (hasBleeding(p, uuid))
                removeBleeding(p, uuid);
            if (hasAmputatedLeg(p, uuid))
                removeAmputatedLeg(p, uuid);
            if (hasLegInfection(p, uuid))
                removeLegInfection(p, uuid);
            if (hasAmputatedArm(p, uuid))
                removeAmputatedArm(p, uuid);
            if (hasArmInfection(p, uuid))
                removeArmInfection(p, uuid);
            if(hasLegProsthesis(p, uuid))
                removeLegProsthesis(p, uuid);
            if(hasArmProsthesis(p, uuid))
                removeArmProsthesis(p, uuid);
        }
    }
}
