package me.indian.deathskulls;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.plugin.PluginManager;
import com.creeperface.nukkit.placeholderapi.api.PlaceholderAPI;
import me.indian.deathskulls.listeners.PlayerDeathListener;
import me.indian.deathskulls.utils.ChatColor;
import me.indian.deathskulls.utils.SkullInfoUtil;

public class DeathSkulls extends PluginBase {

    private static DeathSkulls instance;

    public static DeathSkulls getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        PluginManager pm = getServer().getPluginManager();
        if (pm.getPlugin("PlaceholderAPI") == null && pm.getPlugin("KotlinLib") == null) {
            getLogger().info(ChatColor.replaceColorCode("&cYou don't have PlaceholderAPI or kotlin lib, these plugins are required!"));
            return;
        }

        saveDefaultConfig();
        new SkullInfoUtil();

        pm.registerEvents(new PlayerDeathListener(this), this);
        registerPlaceholders();
    }
    public void registerPlaceholders() {
        PlaceholderAPI api = PlaceholderAPI.getInstance();
        String prefix = "deathskulls_";
        api.builder(prefix +"skull", String.class)
                .visitorLoader(entry -> {
                    return SkullInfoUtil.getSkull(entry.getPlayer());
                })
                .build();

    }
}