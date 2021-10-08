package ghozkiu.minefectedutil.commands;

import ghozkiu.minefectedutil.database.ThirstDatabase;
import ghozkiu.minefectedutil.utilities.MessageManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DataSave implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try{
            ThirstDatabase.save();
            if (sender instanceof Player) {
                Player p = (Player)sender;
                p.sendMessage(ChatColor.GREEN + "Se ha subido la base de datos");
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            MessageManager.ConsoleBad("ERROR AL SUBIR LA BASE DE DATOS");
            return false;
        }
    }
}
