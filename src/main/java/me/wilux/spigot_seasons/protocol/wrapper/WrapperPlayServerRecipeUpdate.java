package me.wilux.spigot_seasons.protocol.wrapper;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;

public class WrapperPlayServerRecipeUpdate extends AbstractPacket {

    public static final PacketType TYPE = PacketType.Play.Server.RECIPE_UPDATE;

    public WrapperPlayServerRecipeUpdate() {
        super(new PacketContainer(TYPE), TYPE);
        handle.getModifier().writeDefaults();
    }

    public WrapperPlayServerRecipeUpdate(PacketContainer packet) {
        super(packet, TYPE);
    }

    // TODO manually upon request
}
