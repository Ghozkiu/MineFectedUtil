package ghozkiu.minefectedutil.utilities.metadata;

import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Zombie;
import org.bukkit.event.Listener;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class WhisperersMetaData implements Listener {
    private static Plugin plugin;

    public WhisperersMetaData(Plugin plugin) {
        WhisperersMetaData.plugin = plugin;
    }

    public static void applyZombie(Zombie z, UUID uuid){
        z.setMetadata(uuid + "_attacked", new FixedMetadataValue(plugin, "SusurradoresMeta"));
    }
    public static void applyPigZombie(PigZombie z, UUID uuid){
        z.setMetadata(uuid + "_attacked", new FixedMetadataValue(plugin, "SusurradoresMeta"));
    }
    public static boolean hasZombieMetadata(Zombie z, UUID uuid){
        return z.hasMetadata(uuid + "_attacked");
    }
    public static boolean hasPigZombieMetadata(PigZombie pz, UUID uuid){
        return pz.hasMetadata(uuid + "_attacked");
    }
}
