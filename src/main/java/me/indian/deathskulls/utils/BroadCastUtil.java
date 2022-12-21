package me.indian.deathskulls.utils;

import cn.nukkit.Player;
import cn.nukkit.Server;
import me.indian.deathskulls.DeathSkulls;


public class BroadCastUtil {

    public static void broadCast(DeathSkulls plugin, Player p1 , Player p2 , String  msg){

     plugin.getServer().getConsoleSender().sendMessage(msg
             .replace("<player1>", p1.getDisplayName())
             .replace("<player2>", p2.getDisplayName()));

        for(Player all : Server.getInstance().getOnlinePlayers().values()){
            all.sendMessage(msg
                    .replace("<player1>", p1.getDisplayName())
                    .replace("<player2>", p2.getDisplayName()));
        }
    }

}
