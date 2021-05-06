package me.wilux.spigot_seasons.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class SetAbility implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player)commandSender;
        net.minecraft.server.v1_16_R3.EntityPlayer NMSPlayer = ((CraftPlayer)player).getHandle();
        NMSPlayer.abilities.canInstantlyBuild = true;
        NMSPlayer.abilities.canFly = true;
        NMSPlayer.updateAbilities();


        /*
        commandSender.sendMessage("§bSending Packet");

        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        WrapperPlayServerAbilities abilities = new WrapperPlayServerAbilities();
        abilities.setCanInstantlyBuild(true);

        PacketContainer pack = abilities.getHandle();
        try {
            protocolManager.sendServerPacket(player, pack);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(
                    "Cannot send packet " + pack, e);
        }
        */
        return true;
    }
}
