package ghozkiu.minefectedutil.commands;

import ghozkiu.minefectedutil.MineFectedUtil;
import ghozkiu.minefectedutil.utilities.paths.Paths;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MapCommand implements CommandExecutor {
    private final MineFectedUtil plugin;

    public MapCommand(MineFectedUtil plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player)sender;
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString(Paths.MAP.get())));
            return true;
        }
        return false;
    }
}
