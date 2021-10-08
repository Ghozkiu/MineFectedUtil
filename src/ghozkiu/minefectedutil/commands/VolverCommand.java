package ghozkiu.minefectedutil.commands;

import ghozkiu.minefectedutil.events.WorldChange;
import ghozkiu.minefectedutil.utilities.MFile;
import ghozkiu.minefectedutil.utilities.MessageManager;
import ghozkiu.minefectedutil.utilities.paths.Permissions;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class VolverCommand implements CommandExecutor {
    private Plugin plugin;

    public VolverCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        MFile lastLocFile = WorldChange.getLastLocFile();

        if (sender instanceof Player) {
            Player player = (Player) sender;
            //World currentWorld = player.getWorld();
            String lastPlayerLoc = lastLocFile.getString("Data." + player.getName());

            if (player.hasPermission(Permissions.VOLVER.get())) {
                if ((lastPlayerLoc != null)) {
                    String[] locationSplit = lastPlayerLoc.split(" ", 4);

                    World lastWorld = plugin.getServer().getWorld(locationSplit[0]);
                    double x = Double.parseDouble(locationSplit[1]);
                    double y = Double.parseDouble(locationSplit[2]);
                    double z = Double.parseDouble(locationSplit[3]);


                    if (lastWorld != null) {
                        Location location = new Location(lastWorld, x, y, z);
                        MessageManager.ConsoleInfo(location.getWorld().getName());
                        player.teleport(location);
                        return true;

                    } else {
                        MessageManager.ConsoleBad("EL MUNDO NO EXISTE");
                    }
                } else {
                    MessageManager.ConsoleBad("NO EXISTEN ULTIMAS LOCALIZACIONES");
                    player.sendMessage(ChatColor.RED + "No cuentas con un checkpoint, utiliza el comando /volverBackUp o contacta con un administrador");
                }
            }
        }
        return false;
    }
}
