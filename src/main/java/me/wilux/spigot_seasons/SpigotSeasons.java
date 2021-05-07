package me.wilux.spigot_seasons;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import me.lucko.commodore.Commodore;
import me.lucko.commodore.CommodoreProvider;
import me.wilux.spigot_seasons.commands.SeasonCommand;
import me.wilux.spigot_seasons.season.*;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
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

        PluginCommand command = Bukkit.getPluginCommand("season");
        Season[] seasons = new Season[]{new Spring(), new Summer(), new Autumn(), new Winter()};
        SeasonCommand seasonCommand = new SeasonCommand(seasons);
        command.setExecutor(seasonCommand);
        if (CommodoreProvider.isSupported()) {
            Commodore commodore = CommodoreProvider.getCommodore(this);
            commodore.register(command,seasonCommand.commandNode("season"));
        }

        LoginPacket.register();

        /*WorldCreator seasonWorld = new WorldCreator("seasonworld");
        for(World x : getServer().getWorlds()){
            if(x.getName().equals("world")){
                seasonWorld.copy(x);
                break;
            }
        }*/
        logger.warning("###########################");
    }

    @Override
    public void onDisable() {
    }
}
