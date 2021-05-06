package me.wilux.spigot_seasons;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.logging.Logger;

public class SpigotSeasons extends JavaPlugin {

    public static Logger logger;
    public static ProtocolManager protocolManager;
    public static JavaPlugin plugin;

    @Override
    public void onEnable() {
        //TODO fix GUI registry
        //TODO fix setblock in CustomBlock
        //TODO make armorstands marker ad invisible
        plugin = this;
        protocolManager = ProtocolLibrary.getProtocolManager();
        logger = getLogger();

        super.onEnable();
    }

    @Override
    public void onDisable() {
    }
}
