package cho.info.elements.server;

import cho.info.elements.managers.ConfigManager;
import cho.info.elements.managers.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class VillagersInHub {

    public ConfigManager configManager;
    public ItemManager itemManager;

    public void spawnVillagerStone() {

        Location villagerLocation = new Location(Bukkit.getWorld("world"), 4.5, 69,17.5);
        if (!isVillagerAtLocation(villagerLocation)) {
            Villager villagerStone = (Villager) Bukkit.getWorld("world").spawnEntity(villagerLocation, EntityType.VILLAGER);
            villagerStone.setCustomNameVisible(true);
            villagerStone.setCustomName(ChatColor.BLUE + "Stone Villager");
            villagerStone.setAI(false);
            villagerStone.setNoPhysics(true);
            villagerStone.setInvulnerable(true);
            villagerStone.setVillagerLevel(5);
            villagerStone.setProfession(Villager.Profession.WEAPONSMITH);

            // Set the villager's facing direction to North
            float yaw = 180.0f; // 180 degrees for North
            float pitch = 0.0f; // 0 degrees for a straight orientation
            villagerStone.setRotation(yaw, pitch);

            // Create trade offers
            List<MerchantRecipe> stoneVillagerTrades = new ArrayList<>();

            // First trade: Cobblestone to Compressed
            List<String> compressedStoneLore = itemManager.createLore(
                    ChatColor.GRAY + "Komprimierter Stein",
                    ChatColor.GRAY + " ",
                    ChatColor.BLUE + "" + ChatColor.BOLD + "Rare"
            );
            ItemStack compressedStone = itemManager.createItem(Material.DEEPSLATE, 1, ChatColor.BLUE + "Compressed Cobblestone", compressedStoneLore);
            MerchantRecipe compressedStoneTrade = new MerchantRecipe(compressedStone, 999999999);
            compressedStoneTrade.addIngredient(new ItemStack(Material.COBBLESTONE, 64));
            stoneVillagerTrades.add(compressedStoneTrade);

            // Second trade: Element Pickaxe
            List<String> elementPickaxeLore = itemManager.createLore(
                    ChatColor.GRAY + "Cobblestone Drops: " + ChatColor.GREEN + "+100%",
                    ChatColor.GRAY + "Xp Drops: " + ChatColor.GREEN + "+100%",
                    ChatColor.GRAY + " ",
                    ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Uncommon"
            );
            ItemStack elementPickaxe = itemManager.createItem(Material.IRON_PICKAXE, 1, ChatColor.DARK_GREEN + "Element Pickaxe", elementPickaxeLore);
            MerchantRecipe elementPickaxeTrade = new MerchantRecipe(elementPickaxe, 999999999);
            ItemMeta elemtPickaxeMeta = elementPickaxe.getItemMeta();

            if (elemtPickaxeMeta != null) {
                elemtPickaxeMeta.setUnbreakable(true);
                elementPickaxe.setItemMeta(elemtPickaxeMeta);
            }
            elementPickaxe.getItemMeta().setUnbreakable(true);

            ItemStack tradeCompressed = itemManager.createItem(Material.DEEPSLATE, 32, ChatColor.BLUE + "Compressed Cobblestone", compressedStoneLore);
            elementPickaxeTrade.addIngredient(tradeCompressed);
            stoneVillagerTrades.add(elementPickaxeTrade);

            // Stone Splitter
            List<String> stoneSplitterLore = itemManager.createLore(
                    ChatColor.GRAY + "Möglicherweise kann man mir diesem",
                    ChatColor.GRAY + "Item einen Stein aufspalten",
                    ChatColor.GRAY + " ",
                    ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Epic"
            );
            ItemStack stoneSplitter = itemManager.createItem(Material.STICK, 1, ChatColor.DARK_PURPLE + "Stone Splitter", stoneSplitterLore);
            ItemMeta metastonesplitter = stoneSplitter.getItemMeta();
            if (metastonesplitter != null) {
                metastonesplitter.addEnchant(Enchantment.AQUA_AFFINITY, 1, true);
                metastonesplitter.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                metastonesplitter.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                metastonesplitter.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
                stoneSplitter.setItemMeta(metastonesplitter);
            }

            MerchantRecipe stoneSplitterTrade = new MerchantRecipe(stoneSplitter, 999999999);
            stoneSplitterTrade.addIngredient(tradeCompressed);
            stoneVillagerTrades.add(stoneSplitterTrade);

            villagerStone.setRecipes(stoneVillagerTrades);


        }



    }

    private boolean isVillagerAtLocation(Location location) {
        for (Entity entity : location.getWorld().getNearbyEntities(location, 1, 1, 1)) {
            if (entity instanceof Villager) {
                return true;
            }
        }
        return false;

    }
}
