package me.wilux.spigot_seasons.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import me.wilux.spigot_seasons.LoginPacket;
import me.wilux.spigot_seasons.SpigotSeasons;
import me.wilux.spigot_seasons.season.Season;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import sun.security.provider.ConfigFile;

public class SeasonCommand implements CommandExecutor {
    public static int currentGrassColor = 0x79C05A;
    public static int currentFoliageColor = 0x79C05A;

    private Season[] seasons;

    public SeasonCommand(Season[] seasons){
        this.seasons = seasons;
    }

    public LiteralCommandNode commandNode(String name){
        LiteralArgumentBuilder<Object> setSeason = LiteralArgumentBuilder.literal("set");
        for (Season season : seasons){
            setSeason = setSeason.then(LiteralArgumentBuilder.literal(season.name));
        }
        LiteralCommandNode<?> xCommand = LiteralArgumentBuilder.literal(name)
                .then(setSeason).build();

        return xCommand;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        Player player = (Player)commandSender;

        Season setSeason = null;
        for (Season season : seasons){
            SpigotSeasons.logger.warning(season.name);
            if(season.name.equals(strings[1])){
                setSeason = season;
                break;
            }
        }

        currentGrassColor = setSeason.grassColor;
        currentFoliageColor = setSeason.foliageColor;

        LoginPacket.resend(player);
        double x = player.getLocation().getX();
        double y = player.getLocation().getY();
        double z = player.getLocation().getZ();
        player.performCommand("execute in minecraft:the_nether run tp @s "+x+" "+y+" "+z);
        player.performCommand("execute in minecraft:overworld run tp @s "+x+" "+y+" "+z);

        return true;
    }
}
