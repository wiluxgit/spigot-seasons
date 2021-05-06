package me.wilux.spigot_seasons.commands;

import me.wilux.spigot_seasons.SpigotSeasons;
import me.wilux.spigot_seasons.Register.FakeRecipe;
import me.wilux.spigot_seasons.Register._DummyText;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Collection;

public class DebugCommand2 implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player)commandSender;

        commandSender.sendMessage("§bDelete Recipes");

        //write Recipe
        ItemStack outStack = new ItemStack(Material.DIRT);
        NamespacedKey bukkitRecipeKey = new NamespacedKey(SpigotSeasons.plugin, "indexer.stone");
        FurnaceRecipe recipe = new FurnaceRecipe(bukkitRecipeKey, outStack, Material.BARRIER, 0, 1);

        net.minecraft.server.v1_16_R3.FurnaceRecipe nmsRecipe = FakeRecipe.asVanillaRecipe(recipe);
        Collection<IRecipe<?>> fakeRecipeCollection = Arrays.asList(nmsRecipe);

        PacketPlayOutRecipeUpdate fakeDeclaredRecipesPacket = new PacketPlayOutRecipeUpdate(fakeRecipeCollection);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(fakeDeclaredRecipesPacket);

        Collection<MinecraftKey> fakeKeys = Arrays.asList(nmsRecipe.getKey());

        RecipeBookSettings recipeBookSettings = new RecipeBookSettings();
        recipeBookSettings.a(RecipeBookType.FURNACE,true);
        recipeBookSettings.a(RecipeBookType.CRAFTING,true);
        recipeBookSettings.a(RecipeBookType.SMOKER,true);
        recipeBookSettings.a(RecipeBookType.BLAST_FURNACE,true);

        PacketPlayOutRecipes fakeUnlockRecipesPacket = new PacketPlayOutRecipes(
                PacketPlayOutRecipes.Action.INIT, fakeKeys, fakeKeys, recipeBookSettings
        );
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(fakeUnlockRecipesPacket);

        player.openInventory(
                Bukkit.createInventory((InventoryHolder) player, InventoryType.FURNACE, "§f"+ _DummyText.INDEXER));

        return true;
    }
}
