package me.wilux.spigot_seasons;

//https://wiki.vg/Protocol#Join_Game

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import me.wilux.spigot_seasons.commands.DebugCommand;
import me.wilux.spigot_seasons.commands.SeasonCommand;
import me.wilux.spigot_seasons.nms.FieldMapper;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Optional;

public class LoginPacket {
    public static PacketContainer superBadUnsafe;

    public static void register(){
        SpigotSeasons.protocolManager.addPacketListener(
                new PacketAdapter(SpigotSeasons.plugin, ListenerPriority.NORMAL, PacketType.Play.Server.LOGIN) {
                    @Override
                    public void onPacketSending(PacketEvent event) {
                        if (event.getPacketType() == PacketType.Play.Server.LOGIN) {
                            //printAllEventProperties(event);

                            if (superBadUnsafe == null) {
                                superBadUnsafe = event.getPacket();
                            }
                            SpigotSeasons.logger.warning("Player Received Data");
                        }
                    }
                });
    }

    public static void updatePacket(PacketContainer packetContainer){
        PacketPlayOutLogin nmsPack = ((PacketPlayOutLogin)packetContainer.getHandle());
        try {
            Field fieldDimension = nmsPack.getClass().getDeclaredField("g");
            fieldDimension.setAccessible(true);
            IRegistryCustom.Dimension dimension = (IRegistryCustom.Dimension) fieldDimension.get(nmsPack);

            IRegistryWritable<BiomeBase> biomeRegistry = dimension.b(IRegistry.ay);

            for (Iterator<BiomeBase> it = biomeRegistry.iterator(); it.hasNext(); ) {
                BiomeBase biome = it.next();

                String name = biomeRegistry.c(biome).get().a().getKey();

                //BiomeFog = "effects" in biome json
                BiomeFog biomeEffects = biome.l();

                FieldMapper.BiomeFog.grassColor.setIn(biomeEffects, Optional.of(SeasonCommand.currentGrassColor));
                FieldMapper.BiomeFog.foliageColor.setIn(biomeEffects, Optional.of(SeasonCommand.currentFoliageColor));
            }
            /* dimension_type
            Field fieldDimensionManager = nmsPack.getClcass().getDeclaredField("h");
            fieldDimensionManager.setAccessible(true);
            DimensionManager dimensionManager = (DimensionManager) fieldDimensionManager.get(nmsPack);
            */

            SpigotSeasons.logger.warning("Succesfully accessed vanilla files");

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            SpigotSeasons.logger.severe("Unable to access biome files");
        }
    }

    public static void resend(Player player){
        updatePacket(superBadUnsafe);
        try {
            SpigotSeasons.protocolManager.sendServerPacket(player,superBadUnsafe);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
