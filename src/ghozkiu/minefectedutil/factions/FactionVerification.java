package ghozkiu.minefectedutil.factions;

import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.util.IdUtil;
import org.bukkit.entity.Player;

public class FactionVerification {
    public static boolean getFaction(Player p) {
        MPlayer fpName = MPlayer.get(IdUtil.getId(p));
        String faction = String.valueOf(fpName.getFaction());
        String susurradores = String.valueOf(FactionColl.get().getByName("Susurradores"));
        return faction.equals(susurradores);
    }
}
