package me.indian.deathskulls.listeners;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.player.PlayerDeathEvent;
import cn.nukkit.item.Item;
import cn.nukkit.lang.TranslationContainer;
import cn.nukkit.utils.Config;
import me.indian.deathskulls.DeathSkulls;
import me.indian.deathskulls.utils.BroadCastUtil;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;


public class PlayerDeathListener implements Listener {

    private final DeathSkulls plugin;
    public static List<UUID> skullPlayer;
    public PlayerDeathListener(DeathSkulls plugin) {
        this.plugin = plugin;
        this.skullPlayer = plugin.getConfig().getList("SkullPlayers");
    }


    @EventHandler(priority = EventPriority.HIGH)
    public void deathEvent(final PlayerDeathEvent event) {
        final Config conf = this.plugin.getConfig();
        final Player player = event.getEntity();
        if (!PlayerDeathListener.skullPlayer.contains(player.getUniqueId())) {
            event.setKeepInventory(true);
            ArrayList<Item> dropItems = this.getList(player);
            dropItems = this.random(dropItems);
            this.removeItem(dropItems, player);
            this.dropItem(dropItems, player);
        }
        if (event.getEntity() instanceof Player && event.getEntity().getKiller() instanceof Player) {
            final Player ent = event.getEntity();
            final Player dmg = (Player)event.getEntity().getKiller();
            if (PlayerDeathListener.skullPlayer.contains(ent.getUniqueId()) && PlayerDeathListener.skullPlayer.contains(dmg.getUniqueId())) {
                event.setDeathMessage("");

                return;
            }
            if (PlayerDeathListener.skullPlayer.contains(ent.getUniqueId())) {
                PlayerDeathListener.skullPlayer.remove(ent.getUniqueId());
                event.setDeathMessage("");
            }
            if (!PlayerDeathListener.skullPlayer.contains(dmg.getUniqueId())) {
                PlayerDeathListener.skullPlayer.add(dmg.getUniqueId());
                event.setDeathMessage("");
                return;
            }
            if (PlayerDeathListener.skullPlayer.contains(dmg.getUniqueId()) && !PlayerDeathListener.skullPlayer.contains(ent.getUniqueId())) {
                event.setDeathMessage("");
            }
            this.plugin.getConfig().save();
        }
    }

    private ArrayList<Item> random(ArrayList<Item> dropItems){

        int lenght = (dropItems.size() -1) / 2;

        for (int i = lenght; i > 0; i--) {
            Random random = new Random();
            int radnomNumber = random.nextInt(lenght);

            dropItems.remove(radnomNumber);
        }


        return dropItems;
    }

    private void removeItem(ArrayList<Item> dropitem, Player player){

        for (Item itemStack : dropitem) {

            if((itemStack == null))
                continue;

            player.getInventory().removeItem(itemStack);
        }


    }

    private void dropItem(ArrayList<Item> dropitem, Player player){
        for (Item itemStack : dropitem) {

            if((itemStack == null))
                continue;

            player.getLevel().dropItem(player.getLocation(), itemStack);
        }

    }

    private ArrayList<Item> getList(Player p){

        ArrayList<Item> list = new ArrayList<>();

        for (Item content : p.getInventory().getContents().values() )
            list.add(content);

        return list;
    }



}
