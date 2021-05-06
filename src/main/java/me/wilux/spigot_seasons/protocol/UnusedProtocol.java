package me.wilux.spigot_seasons.protocol;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import me.wilux.blockshelf_playground.BlockshelfPlayground;
import me.wilux.spigot_seasons.SpigotSeasons;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;

import java.util.function.Function;

public class UnusedProtocol {
    public static WrappedChatComponent superBadUnsafe;

    public static void init(){

        /*
        Main.protocolManager.addPacketListener(
                new PacketAdapter(Main.plugin, ListenerPriority.NORMAL,
                        PacketType.Play.Server.CHAT) {
                    @Override
                    public void onPacketSending(PacketEvent event) {
                        if (event.getPacketType() == PacketType.Play.Server.CHAT) {
                            PacketContainer fakeInv = event.getPacket();

                            for(int i : fakeInv.getIntegers().getValues()){
                                Main.logger.warning("CHAT INT " + i);
                            }
                            for(WrappedChatComponent i : fakeInv.getChatComponents().getValues()){
                                Main.logger.warning("CHAT CHAT " + i);
                                superBadUnsafeRecipesAtBoot = i;
                            }
                        }
                    }
                });

        Main.protocolManager.addPacketListener(
                new PacketAdapter(Main.plugin, ListenerPriority.NORMAL,
                        PacketType.Play.Server.OPEN_WINDOW) {
                    @Override
                    public void onPacketSending(PacketEvent event) {
                        if (event.getPacketType() == PacketType.Play.Server.OPEN_WINDOW) {
                            PacketContainer fakeInv = event.getPacket();
                            fakeInv.getChatComponents().write(0,superBadUnsafeRecipesAtBoot);

                            for(WrappedChatComponent i : fakeInv.getChatComponents().getValues()){
                                Main.logger.warning("OPEN_WINDOW CHAT " + i);
                                superBadUnsafeRecipesAtBoot = i;
                            }
                        }
                    }
                });
*/
        SpigotSeasons.protocolManager.addPacketListener(
                new PacketAdapter(SpigotSeasons.plugin, ListenerPriority.NORMAL,PacketType.Play.Client.AUTO_RECIPE) {
                    @Override
                    public void onPacketReceiving(PacketEvent event) {
                        if (event.getPacketType() == PacketType.Play.Client.AUTO_RECIPE) printAllEventProperties(event);
                    }
                });

        SpigotSeasons.protocolManager.addPacketListener(
                new PacketAdapter(SpigotSeasons.plugin, ListenerPriority.NORMAL,PacketType.Play.Server.OPEN_WINDOW) {
                    @Override
                    public void onPacketSending(PacketEvent event) {
                        if (event.getPacketType() == PacketType.Play.Server.OPEN_WINDOW) {
                            printAllEventProperties(event);
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"recipe take wilux *");

                            event.getPlayer().discoverRecipe(new NamespacedKey("minecraft","dried_kelp_from_smelting"));
                            //event.setCancelled(true);

                            /*
                            Player wilux = Bukkit.getPlayer("Wilux");
                            Main.logger.severe(wilux.getDisplayName());
                            Main.logger.severe(""+wilux.getLocation().getX());

                            PacketContainer fakeExplosion = new PacketContainer(PacketType.Play.Server.EXPLOSION);

                            printAllProperties(fakeExplosion);

                            fakeExplosion.getDoubles().
                                    write(0, wilux.getLocation().getX()).
                                    write(1, wilux.getLocation().getY()).
                                    write(2, wilux.getLocation().getZ());
                            fakeExplosion.getFloat().write(0, 3.0F);

                            printAllProperties(fakeExplosion);

                            try {
                                Main.protocolManager.sendServerPacket(wilux,fakeExplosion);
                            } catch (InvocationTargetException e) {
                                throw new RuntimeException("Cannot send packet " + fakeExplosion, e);
                            }*/
                        }
                    }
                });

        /*Main.protocolManager.addPacketListener(
                new PacketAdapter(Main.plugin, ListenerPriority.NORMAL,PacketType.Play.Server.RECIPE_UPDATE) {
                    @Override
                    public void onPacketSending(PacketEvent event) {
                        if (event.getPacketType() == PacketType.Play.Server.RECIPE_UPDATE) printAllProperties(event);
                    }
                });
*/
        //RECIPE CRATED
        /*Main.protocolManager.addPacketListener(
                new PacketAdapter(Main.plugin, ListenerPriority.NORMAL,PacketType.Play.Server.RECIPES) {
                    @Override
                    public void onPacketSending(PacketEvent event) {
                        if (event.getPacketType() == PacketType.Play.Server.RECIPES) printAllEventProperties(event);
                    }});*/
        SpigotSeasons.protocolManager.addPacketListener(
                new PacketAdapter(SpigotSeasons.plugin, ListenerPriority.NORMAL,PacketType.Play.Client.USE_ITEM) {
                    @Override
                    public void onPacketReceiving(PacketEvent event) {
                        if (event.getPacketType() == PacketType.Play.Client.USE_ITEM) printAllEventProperties(event);
                    }});
        SpigotSeasons.protocolManager.addPacketListener(
                new PacketAdapter(SpigotSeasons.plugin, ListenerPriority.NORMAL,PacketType.Play.Client.RECIPE_DISPLAYED) {
                    @Override
                    public void onPacketReceiving(PacketEvent event) {
                        if (event.getPacketType() == PacketType.Play.Client.RECIPE_DISPLAYED) printAllEventProperties(event);
                    }});
    }
    public static void printAllEventProperties(PacketEvent event){
        PacketContainer p = event.getPacket();
        SpigotSeasons.logger.warning("EVT "+event.getPacketType());
        printAllProperties(p);
    }
    public static void printAllProperties(PacketContainer p){
        lazyTry(p,PacketContainer::getBytes,"getBytes");
        lazyTry(p,PacketContainer::getBooleans,"getBooleans");
        lazyTry(p,PacketContainer::getShorts,"getShorts");
        lazyTry(p,PacketContainer::getIntegers,"getIntegers");
        lazyTry(p,PacketContainer::getLongs,"getLongs");
        lazyTry(p,PacketContainer::getFloat,"getFloat");
        lazyTry(p,PacketContainer::getDoubles,"getDoubles");
        lazyTry(p,PacketContainer::getStrings,"getStrings");
        lazyTry(p,PacketContainer::getUUIDs,"getUUIDs");
        lazyTry(p,PacketContainer::getStringArrays,"getStringArrays");
        lazyTry(p,PacketContainer::getByteArrays,"getByteArrays");
        //lazyTry(p,PacketContainer::getByteArraySerializer,"getByteArraySerializer");
        lazyTry(p,PacketContainer::getIntegerArrays,"getIntegerArrays");
        lazyTry(p,PacketContainer::getItemModifier,"getItemModifier");
        lazyTry(p,PacketContainer::getItemArrayModifier,"getItemArrayModifier");
        lazyTry(p,PacketContainer::getItemListModifier,"getItemListModifier");
        lazyTry(p,PacketContainer::getStatisticMaps,"getStatisticMaps");
        lazyTry(p,PacketContainer::getWorldTypeModifier,"getWorldTypeModifier");
        lazyTry(p,PacketContainer::getDataWatcherModifier,"getDataWatcherModifier");
        lazyTry(p,PacketContainer::getEntityTypeModifier,"getEntityTypeModifier");
        lazyTry(p,PacketContainer::getPositionModifier,"getPositionModifier");
        lazyTry(p,PacketContainer::getBlockPositionModifier,"getBlockPositionModifier");
        lazyTry(p,PacketContainer::getChunkCoordIntPairs,"getChunkCoordIntPairs");
        lazyTry(p,PacketContainer::getNbtModifier,"getNbtModifier");
        lazyTry(p,PacketContainer::getListNbtModifier,"getListNbtModifier");
        lazyTry(p,PacketContainer::getVectors,"getVectors");
        lazyTry(p,PacketContainer::getAttributeCollectionModifier,"getAttributeCollectionModifier");
        lazyTry(p,PacketContainer::getPositionCollectionModifier,"getPositionCollectionModifier");
        lazyTry(p,PacketContainer::getBlockPositionCollectionModifier,"getBlockPositionCollectionModifier");
        lazyTry(p,PacketContainer::getWatchableCollectionModifier,"getWatchableCollectionModifier");
        lazyTry(p,PacketContainer::getBlocks,"getBlocks");
        lazyTry(p,PacketContainer::getGameProfiles,"getGameProfiles");
        lazyTry(p,PacketContainer::getBlockData,"getBlockData");
        //lazyTry(p,PacketContainer::getMultiBlockChangeInfoArrays,"getMultiBlockChangeInfoArrays");
        lazyTry(p,PacketContainer::getChatComponents,"getChatComponents");
        lazyTry(p,PacketContainer::getChatComponentArrays,"getChatComponentArrays");
        lazyTry(p,PacketContainer::getServerPings,"getServerPings");
        lazyTry(p,PacketContainer::getPlayerInfoDataLists,"getPlayerInfoDataLists");
        lazyTry(p,PacketContainer::getProtocols,"getProtocols");
        lazyTry(p,PacketContainer::getClientCommands,"getClientCommands");
        lazyTry(p,PacketContainer::getChatVisibilities,"getChatVisibilities");
        lazyTry(p,PacketContainer::getDifficulties,"getDifficulties");
        lazyTry(p,PacketContainer::getEntityUseActions,"getEntityUseActions");
        lazyTry(p,PacketContainer::getGameModes,"getGameModes");
        lazyTry(p,PacketContainer::getResourcePackStatus,"getResourcePackStatus");
        lazyTry(p,PacketContainer::getPlayerInfoAction,"getPlayerInfoAction");
        lazyTry(p,PacketContainer::getTitleActions,"getTitleActions");
        lazyTry(p,PacketContainer::getWorldBorderActions,"getWorldBorderActions");
        lazyTry(p,PacketContainer::getCombatEvents,"getCombatEvents");
        lazyTry(p,PacketContainer::getPlayerDigTypes,"getPlayerDigTypes");
        lazyTry(p,PacketContainer::getPlayerActions,"getPlayerActions");
        lazyTry(p,PacketContainer::getScoreboardActions,"getScoreboardActions");
        lazyTry(p,PacketContainer::getParticles,"getParticles");
        lazyTry(p,PacketContainer::getNewParticles,"getNewParticles");
        lazyTry(p,PacketContainer::getEffectTypes,"getEffectTypes");
        lazyTry(p,PacketContainer::getSoundCategories,"getSoundCategories");
        lazyTry(p,PacketContainer::getSoundEffects,"getSoundEffects");
        lazyTry(p,PacketContainer::getItemSlots,"getItemSlots");
        lazyTry(p,PacketContainer::getHands,"getHands");
        lazyTry(p,PacketContainer::getDirections,"getDirections");
        lazyTry(p,PacketContainer::getChatTypes,"getChatTypes");
        lazyTry(p,PacketContainer::getMinecraftKeys,"getMinecraftKeys");
        lazyTry(p,PacketContainer::getDimensions,"getDimensions");
        SpigotSeasons.logger.warning("");





    }
    static <T> void lazyTry(PacketContainer pack,Function<PacketContainer, StructureModifier<T>> func, String name){
        try{
            func.apply(pack).getValues().stream().forEach(
                    x-> SpigotSeasons.logger.warning("    "+name+"() "+atMaxLength(x.toString())));
        } catch (Exception e){
            SpigotSeasons.logger.severe("ERROR For:"+name);
        }
    }
    static String atMaxLength(String s){
        if(s.length() > 100){
            return s.substring(0,98)+"..";
        }
        return s;
    }

}
