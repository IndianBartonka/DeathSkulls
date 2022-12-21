package me.indian.deathskulls.utils;

import cn.nukkit.Player;
import me.indian.deathskulls.DeathSkulls;

import static me.indian.deathskulls.listeners.PlayerDeathListener.skullPlayer;

public class SkullInfoUtil {

    private static DeathSkulls plugin = DeathSkulls.getInstance();

    public static String getSkull(Player p) {
        String skull = "";
        if (skullPlayer.contains(p.getUniqueId())) {
            skull = ChatColor.replaceColorCode(plugin.getConfig().getString("Skull-icon"));
        }
        return ChatColor.replaceColorCode(skull);
    }
    
}
