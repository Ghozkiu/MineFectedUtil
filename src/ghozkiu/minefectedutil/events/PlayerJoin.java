package ghozkiu.minefectedutil.events;

import ghozkiu.minefectedutil.MineFectedUtil;
import ghozkiu.minefectedutil.diseases.Diseases;
import ghozkiu.minefectedutil.thirst.PlayerThirst;
import ghozkiu.minefectedutil.thirst.Thirst;
import ghozkiu.minefectedutil.utilities.MFile;
import ghozkiu.minefectedutil.utilities.paths.Paths;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.joda.time.DateTime;

import java.sql.SQLException;
import java.util.List;

public class PlayerJoin implements Listener {
    private final MFile ipDataFile;
    private final MineFectedUtil plugin;

    public PlayerJoin(MineFectedUtil plugin) {
        this.ipDataFile = new MFile(plugin, "ipData.yml");
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e) throws SQLException {
        thirstInitializer(e);
        Thirst.applyThirst(e.getPlayer());
        collectData(e);
        Diseases.applyDisease(e);
    }

    public void thirstInitializer(PlayerJoinEvent e) {
      if(Thirst.isThirstEnabled(plugin)){
          Player player = e.getPlayer();
          String name = player.getName();
          if(!Thirst.playersT.containsKey(name)){
              Thirst.playersT.put(name, new PlayerThirst(100, DateTime.now()));
          }
          Thirst thirst = new Thirst();
          thirst.initialize(player);
      }
    }



    private void collectData(PlayerJoinEvent e) {
        if (plugin.getConfig().getBoolean(Paths.PLAYER_DATA_COLLECTOR.get())) {
            Player player = e.getPlayer();
            String data = player.getAddress() + " " +
                    player.getUniqueId();
            List<String> dataLoaded = ipDataFile.getStringList("Data." + player.getName());
            if (dataLoaded != null) {
                if (!dataLoaded.contains(data)) {
                    dataLoaded.add(data);
                    ipDataFile.set("Data." + player.getName(), dataLoaded);
                    ipDataFile.save();
                }
            } else {
                assert false;
                dataLoaded.add(data);
                ipDataFile.set("Data." + player.getName(), dataLoaded);
                ipDataFile.save();
            }
        }
    }

}
