package ghozkiu.minefectedutil.thirst;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.joda.time.DateTime;

public class PlayerThirst {
    private DateTime lastSeen;
    private int thirstLevel;
    private long quitTime;
    private final BossBar bossBar;


    public PlayerThirst(int thirstLevel, DateTime lastSeen) {
        this.lastSeen = lastSeen;
        this.thirstLevel = thirstLevel;
        this.quitTime = 0;
        this.bossBar = Bukkit.createBossBar(ChatColor.GRAY + " ", BarColor.BLUE, BarStyle.SOLID);
    }

    public BossBar getBossBar() {
        return bossBar;
    }

    public long getQuitTime() {
        return quitTime;
    }

    public void setQuitTime(long quitTime) {
        this.quitTime = quitTime;
    }

    public DateTime getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(DateTime lastSeen) {
        this.lastSeen = lastSeen;
    }

    public int getThirstLevel() {
        return thirstLevel;
    }

    public void setThirstLevel(int thirstLevel) {
        this.thirstLevel = thirstLevel;
    }
}
