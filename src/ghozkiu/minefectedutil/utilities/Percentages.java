package ghozkiu.minefectedutil.utilities;

import ghozkiu.minefectedutil.diseases.Diseases;
import ghozkiu.minefectedutil.utilities.armor.ArmorValues;
import ghozkiu.minefectedutil.utilities.metadata.OtherMetaData;
import ghozkiu.minefectedutil.utilities.paths.Paths;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.ThreadLocalRandom;

public class Percentages implements Listener {
    ArmorValues armor;

    public Percentages(Plugin plugin) {
        armor = new ArmorValues(plugin);
    }

    private boolean infectionPercentage(int percentage, Player p) {
        int finalPercentage = percentage - (this.armor.helmet(p) + this.armor.chestplate(p) + this.armor.leggings(p) + this.armor.boots(p)) * percentage / 100;
        int randomNum = ThreadLocalRandom.current().nextInt(1, 101);
        return (randomNum < finalPercentage);
    }

    public void infectionType(int percentage, Player p) {
        if (infectionPercentage(percentage, p)) {
            int randomNum = ThreadLocalRandom.current().nextInt(1, 101);
            if (randomNum <= Diseases.plugin.getConfig().getInt("Config.other_infection")) {
                randomNum = ThreadLocalRandom.current().nextInt(1, 101);
                if (randomNum < Diseases.plugin.getConfig().getInt("Config.leg_infection_prob")) {
                    OtherMetaData.setLegInfection(p, p.getUniqueId());
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', Diseases.plugin
                            .getConfig().getString(Paths.LEG_INFECTED.get())));
                } else {
                    OtherMetaData.setArmInfection(p, p.getUniqueId());
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', Diseases.plugin
                            .getConfig().getString(Paths.ARM_INFECTED.get())));
                }
            } else {
                OtherMetaData.setBodyInfection(p, p.getUniqueId());
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Diseases.plugin
                        .getConfig().getString(Paths.BODY_INFECTED.get())));
            }
        }
    }
}
