package me.wilux.spigot_seasons.protocol;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.nbt.NbtBase;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import me.wilux.spigot_seasons.SpigotSeasons;
import me.wilux.spigot_seasons.protocol.wrapper.WrapperPlayServerMapChunk;

import java.util.List;

import static me.wilux.spigot_seasons.protocol.UnusedProtocol.printAllEventProperties;

public class PacketTesting {
    //public static PacketContainer superBadUnsafeRecipesAtBoot;
    public static void init(){
        SpigotSeasons.protocolManager.addPacketListener(
                new PacketAdapter(SpigotSeasons.plugin, ListenerPriority.NORMAL, PacketType.Play.Client.AUTO_RECIPE) {
                    @Override
                    public void onPacketReceiving(PacketEvent event) {
                        if (event.getPacketType() == PacketType.Play.Client.AUTO_RECIPE) {
                            printAllEventProperties(event);
                            //event.setCancelled(true);
                        }
                    }
                });
        SpigotSeasons.protocolManager.addPacketListener(
                new PacketAdapter(SpigotSeasons.plugin, ListenerPriority.NORMAL,PacketType.Play.Server.OPEN_WINDOW) {
                    @Override
                    public void onPacketSending(PacketEvent event) {
                        if (event.getPacketType() == PacketType.Play.Server.OPEN_WINDOW) {
                            printAllEventProperties(event);
                        }
                    }
                });
        SpigotSeasons.protocolManager.addPacketListener(
                new PacketAdapter(SpigotSeasons.plugin, ListenerPriority.NORMAL,PacketType.Play.Server.RECIPES) {
                    @Override
                    public void onPacketSending(PacketEvent event) {
                        if (event.getPacketType() == PacketType.Play.Server.RECIPES) {
                            printAllEventProperties(event);
                        }
                    }
                });
        SpigotSeasons.protocolManager.addPacketListener(
                new PacketAdapter(SpigotSeasons.plugin, ListenerPriority.NORMAL,PacketType.Play.Server.OPEN_WINDOW_HORSE) {
                    @Override
                    public void onPacketSending(PacketEvent event) {
                        if (event.getPacketType() == PacketType.Play.Server.OPEN_WINDOW_HORSE) {
                            printAllEventProperties(event);
                        }
                    }
                });
        SpigotSeasons.protocolManager.addPacketListener(
                new PacketAdapter(SpigotSeasons.plugin, ListenerPriority.NORMAL,PacketType.Play.Server.RECIPE_UPDATE) {
                    @Override
                    public void onPacketSending(PacketEvent event) {
                        if (event.getPacketType() == PacketType.Play.Server.RECIPE_UPDATE) {
                            //printAllEventProperties(event);
                            SpigotSeasons.logger.warning(event.toString());
                            /*if(superBadUnsafeRecipesAtBoot == null){
                                superBadUnsafeRecipesAtBoot = event.getPacket();
                            }*/
                        }
                    }
                });
        SpigotSeasons.protocolManager.addPacketListener(new PacketAdapter(SpigotSeasons.plugin, ListenerPriority.NORMAL,PacketType.Play.Server.MAP_CHUNK) {
            @Override
            public void onPacketSending(PacketEvent event) {
                if (event.getPacketType() == PacketType.Play.Server.MAP_CHUNK) {
                    PacketContainer p = event.getPacket();
                    WrapperPlayServerMapChunk wp = new WrapperPlayServerMapChunk(p);

                    int chunkX = wp.getChunkX();
                    int chunkZ = wp.getChunkZ();

                    List<NbtBase<?>> tileEntities = wp.getTileEntities();
                    if(!tileEntities.isEmpty()){
                        for (NbtBase nbtBase: tileEntities) {
                            NbtCompound nbt = (NbtCompound)nbtBase;
                            String id = (String) nbt.getValue("id").getValue();
                            int x = (int) nbt.getValue("x").getValue();
                            int y = (int) nbt.getValue("y").getValue();
                            int z = (int) nbt.getValue("z").getValue();
                            SpigotSeasons.logger.warning("Chunk["+chunkX+","+chunkZ+"] Packet TileEntity:'"+id+"' at ["+x+","+y+","+z+"]");
                        }
                        //printAllEventProperties(event);
                    }
                }
            }
        });
        /*Main.protocolManager.addPacketListener(
                new PacketAdapter(Main.plugin, ListenerPriority.NORMAL, PacketType.Play.Client.BLOCK_DIG) {
                    @Override
                    public void onPacketReceiving(PacketEvent event) {
                        if (event.getPacketType() == PacketType.Play.Client.BLOCK_DIG) {
                            printAllEventProperties(event);
                        }
                    }
                });*/
    }
    /*
    public static void unlockRecipeWithoutToast(Player player){
        PacketContainer recipePack = new PacketContainer(PacketType.Play.Server.RECIPES);

        try {
            Main.protocolManager.sendServerPacket(player, recipePack);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(
                    "Cannot send packet " + recipePack, e);
        }
    }
    */
}
