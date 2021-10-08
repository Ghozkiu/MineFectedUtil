package ghozkiu.minefectedutil.thirst;

import ghozkiu.minefectedutil.database.ThirstDatabase;
import ghozkiu.minefectedutil.diseases.Diseases;
import ghozkiu.minefectedutil.events.ItemConsumption;
import ghozkiu.minefectedutil.utilities.paths.Paths;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Thirst extends ThirstDatabase implements Listener {

    public static HashMap<String, PlayerThirst> playersT = new HashMap<>();


    public static boolean isThirstEnabled(Plugin plugin) {
        return plugin.getConfig().getBoolean(Paths.IS_THIRST_ENABLED.get());
    }

    public static void setBossBarProgress(Player player, int thirstLevel) {
        PlayerThirst playerT = playersT.get(player.getName());
        BossBar bossBar = playerT.getBossBar();
        int newThirst = limitCalc(thirstLevel);
        if (newThirst == 0) {
            player.setHealth(0);
            setBossBarTittle(newThirst, bossBar);
        } else {
            if (newThirst <= 30) {
                bossBar.setColor(BarColor.RED);

            } else {
                bossBar.setColor(BarColor.BLUE);
            }

            setBossBarTittle(newThirst, bossBar);
            bossBar.setProgress((double) newThirst / 100);
            playerT.setThirstLevel(newThirst);
        }
    }

    //Just sets bossBar's thirstLevel tittle.
    private static void setBossBarTittle(int thirstLevel, BossBar bossBar) {
        String title =
                (ChatColor.translateAlternateColorCodes('&', Diseases.plugin.getConfig()
                        .getString(Paths.THIRST_TITTLE.get())
                        .replaceAll("<thirst>", thirstLevel + "")));
        bossBar.setTitle(title);
    }

    //Prevents overflow
    private static Integer limitCalc(int newThirstLevel) {
        if (newThirstLevel > 100) {
            return 100;
        } else if (newThirstLevel < 0) {
            return 0;
        }
        return newThirstLevel;
    }

    //Was player online?
    public static boolean wasOnline(long quitTime) {
        long finalTime = System.currentTimeMillis() - quitTime;
        finalTime /= 1000;
        return finalTime <= Diseases.plugin.getConfig().getInt(Paths.WAS_ONLINE.get());
    }

    //Is player in an enabled world?
    public static boolean isInEnabledWorld(Player player) {
        List<String> enabledWorlds = Diseases.plugin.getConfig().getStringList(Paths.THIRST_ENABLED_WORLDS.get());
        return enabledWorlds.contains(player.getLocation().getWorld().getName());
    }

    //Is player in an enabled world? but using a string
    public static boolean isInEnabledWorld(String worldName) {
        List<String> enabledWorlds = Diseases.plugin.getConfig().getStringList(Paths.THIRST_ENABLED_WORLDS.get());
        return enabledWorlds.contains(worldName);
    }

    //Thirst timer
    public static void applyThirst(Player player) {
        PlayerThirst playerT = playersT.get(player.getName());
        FileConfiguration config = Diseases.plugin.getConfig();
        int minRandomInterval = Diseases.plugin.getConfig().getInt(Paths.MIN_THIRST_RANDOM_INTERVAL.get());
        int maxRandomInterval = Diseases.plugin.getConfig().getInt(Paths.MAX_THIRST_RANDOM_INTERVAL.get()) + 1;

        Bukkit.getScheduler().runTaskTimer(Diseases.plugin, () -> {
            int randomNum = ThreadLocalRandom.current().nextInt(minRandomInterval, maxRandomInterval);
            if (player.isOnline() && isInEnabledWorld(player)) {
                if (!player.isDead()) {
                    setBossBarProgress(player, playerT.getThirstLevel() - randomNum);
                }
            } else if (wasOnline(playerT.getQuitTime()) && (playerT.getThirstLevel() > 50)) {
                int thirstLevel = playerT.getThirstLevel() - randomNum;
                playerT.setThirstLevel(thirstLevel);
            }
        }, config.getInt(Paths.JOIN_WARMUP.get()) * 20L, config.getInt(Paths.THIRST_TIMER.get()) * 20L);
    }

    //Initialize thirst on server join
    public void initialize(Player player) {
        if (isInEnabledWorld(player)) {
            PlayerThirst playerT = playersT.get(player.getName());
            BossBar bossBar = playerT.getBossBar();
            bossBar.addPlayer(player);
            setBossBarProgress(player, playerT.getThirstLevel());
            thirstRemainder(player);
        }
    }

    private void thirstRemainder(Player player){
        List<String> messages = Diseases.plugin.getConfig().getStringList(Paths.THIRST_REMAINDER.get());
        List<String> effects = Diseases.plugin.getConfig().getStringList(Paths.THIRST_RANDOM_EFFECT_ONTIMER.get());
        Bukkit.getScheduler().runTaskTimer(Diseases.plugin, () -> {
            PlayerThirst playerT = Thirst.playersT.get(player.getName());
            if(player.isOnline() && isInEnabledWorld(player) && playerT.getThirstLevel() <= 50){
                if(ThreadLocalRandom.current().nextInt(0, 2) == 0){
                    //Message
                    int randomMessageIndex = ThreadLocalRandom.current().nextInt(0, messages.size());
                    player.sendTitle("", ChatColor.translateAlternateColorCodes('&', messages.get(randomMessageIndex)), 10,
                            40, 10);
                }else{
                    //Effect
                    if(playerT.getThirstLevel() <= 30){
                        int randomEffectIndex = ThreadLocalRandom.current().nextInt(0, effects.size());
                        String[] splitEffectData = effects.get(randomEffectIndex).split(",", 3);
                        ItemConsumption.applyEffect(player, splitEffectData);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',messages.get(0)));
                    }
                }
            }
        }, Diseases.plugin.getConfig().getInt(Paths.JOIN_WARMUP.get()) * 20L,Diseases.plugin.getConfig().getInt(Paths.THIRST_REMAINDER_TIMER.get()) * 20L);
    }
}