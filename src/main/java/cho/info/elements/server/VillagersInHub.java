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

        Location villagerLocation = new Location(Bukkit.getWorld("world"), 5, 69, 8);
        if (!isVillagerAtLocation(villagerLocation)) {
            Villager villagerStone = (Villager) Bukkit.getWorld("world").spawnEntity(villagerLocation, EntityType.VILLAGER);
            villagerStone.setCustomNameVisible(true);
            villagerStone.setCustomName(ChatColor.BLUE + "Stone Villager");
            villagerStone.setAI(false);
            villagerStone.setInvulnerable(true);
            villagerStone.setVillagerLevel(5);
            villagerStone.setProfession(Villager.Profession.WEAPONSMITH);

            // Setze die Blickrichtung des Villagers auf Norden
            float yaw = 180.0f; // 180 Grad für Norden
            float pitch = 0.0f; // 0 Grad für eine gerade Ausrichtung
            villagerStone.setRotation(yaw, pitch);

            // Erstelle Handelsangebote
            List<MerchantRecipe> stoneVillagerTrades = new ArrayList<>();

            // Erster Handel: Cobble zu Komprimiertem
            List<String> compressedStoneLore = itemManager.createLore(
                    ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "Rare"
            );
            ItemStack compressedStone = itemManager.createItem(Material.DEEPSLATE, 1, ChatColor.DARK_BLUE + "Compressed Cobblestone", compressedStoneLore);
            MerchantRecipe compressedStoneTrade = new MerchantRecipe(compressedStone, 999999999);
            compressedStoneTrade.addIngredient(new ItemStack(Material.COBBLESTONE, 64));
            stoneVillagerTrades.add(compressedStoneTrade);

            // Zweiter Handel: Element-Pickaxe
            List<String> elementPickaxeLore = itemManager.createLore(
                    ChatColor.BLUE + "+100 Drops",
                    ChatColor.BLUE + "+ %100 XP",
                    ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "Rare"
            );
            ItemStack elementPickaxe = itemManager.createItem(Material.IRON_PICKAXE, 1, ChatColor.GREEN + "Element Pickaxe", elementPickaxeLore);
            MerchantRecipe elementPickaxeTrade = new MerchantRecipe(elementPickaxe, 999999999);
            ItemMeta elemtPickaxeMeta = elementPickaxe.getItemMeta();

            if (elemtPickaxeMeta != null) {
                elemtPickaxeMeta.setUnbreakable(true);
                elementPickaxe.setItemMeta(elemtPickaxeMeta);
            }
            elementPickaxe.getItemMeta().setUnbreakable(true);

            ItemStack tradeCompressed = itemManager.createItem(Material.DEEPSLATE, 64, ChatColor.DARK_BLUE + "Compressed Cobblestone", compressedStoneLore);
            elementPickaxeTrade.addIngredient(tradeCompressed);
            stoneVillagerTrades.add(elementPickaxeTrade);

            // Stone Splitter
            List<String> stoneSplitterLore = itemManager.createLore(ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "Rare");
            ItemStack stoneSplitter = itemManager.createItem(Material.STICK, 1, ChatColor.BLUE + "Stone Splitter", stoneSplitterLore);
            ItemMeta metastonesplitter = stoneSplitter.getItemMeta();
            if (metastonesplitter != null) {
                metastonesplitter.addEnchant(Enchantment.AQUA_AFFINITY, 1, true);
                metastonesplitter.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                metastonesplitter.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                stoneSplitter.setItemMeta(metastonesplitter);
            }

            MerchantRecipe stoneSplitterTrade = new MerchantRecipe(stoneSplitter, 999999999);
            stoneSplitterTrade.addIngredient(tradeCompressed);
            stoneVillagerTrades.add(stoneSplitterTrade);

            villagerStone.setRecipes(stoneVillagerTrades);

            Bukkit.getLogger().info("Spawn 1 Villager");
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
