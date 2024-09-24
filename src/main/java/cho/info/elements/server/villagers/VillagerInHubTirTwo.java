package cho.info.elements.server.villagers;

import cho.info.elements.managers.ConfigManager;
import cho.info.elements.managers.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class VillagerInHubTirTwo {

    public ConfigManager configManager;
    public ItemManager itemManager;


    public void spawnVillagerWeat() {

        Location location = new Location(Bukkit.getWorld("world"), 1.5, 69, 17.5);

        if (!isVillagerAtLocation(location)) {
            Villager villager = (Villager) Bukkit.getWorld("world").spawnEntity(location, EntityType.VILLAGER);
            villager.setCustomNameVisible(true);
            villager.setInvulnerable(true);
            villager.setCustomName(ChatColor.GREEN + "Farmer Villager");
            villager.setAI(false);
            villager.setSilent(true);
            villager.setNoPhysics(true);
            villager.setVillagerLevel(5);
            villager.setProfession(Villager.Profession.FARMER);

            // Set the villager's facing direction to North
            float yaw = 180.0f; // 180 degrees for North
            float pitch = 0.0f; // 0 degrees for a straight orientation
            villager.setRotation(yaw, pitch);

            List<MerchantRecipe> farmerVillagerTrades = new ArrayList<>();

            List<String> compresstWheeatLore = itemManager.createLore(
                    ChatColor.GRAY + "Komprimierter Wheat",
                    ChatColor.GRAY + " ",
                    ChatColor.BLUE + "" + ChatColor.BOLD + "Rare"
            );
            ItemStack compresstWeate = itemManager.createItem(Material.HAY_BLOCK, 1, ChatColor.BLUE + "Compress Wheat", compresstWheeatLore);
            MerchantRecipe comprestWeatTrade = new MerchantRecipe(compresstWeate, 999999999);
            comprestWeatTrade.addIngredient(new ItemStack(Material.WHEAT, 64));
            farmerVillagerTrades.add(comprestWeatTrade);

            List<String> elementHowLore = itemManager.createLore(
                    ChatColor.GRAY + "Farming Drops: " + ChatColor.GREEN + "+100%",
                    ChatColor.GRAY + "Xp Drops: " + ChatColor.GREEN + "+100%",
                    ChatColor.GRAY + " ",
                    ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Uncommon"
            );
            ItemStack elementHow = itemManager.createItem(Material.IRON_HOE, 1, ChatColor.DARK_GREEN + "Element Hoe", elementHowLore);
            MerchantRecipe elementHowTrade = new MerchantRecipe(elementHow, 999999999);
            ItemMeta elementHowMeta = elementHow.getItemMeta();

            if (elementHowMeta != null) {
                elementHowMeta.setUnbreakable(true);
                elementHow.setItemMeta(elementHowMeta);
            }
            elementHow.getItemMeta().setUnbreakable(true);
            ItemStack compresstWeatetradeItem = itemManager.createItem(Material.HAY_BLOCK, 40, ChatColor.BLUE + "Compress Wheat", compresstWheeatLore);
            elementHowTrade.addIngredient(compresstWeatetradeItem);
            farmerVillagerTrades.add(elementHowTrade);

            String scramble = "\\u00A7k";

            List<String> saplingLore = itemManager.createLore(
                    ChatColor.GRAY + scramble + "ölakjdflöakjdf",
                    ChatColor.GRAY + " ",
                    ChatColor.GRAY + " ",
                    ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Epic"
            );
            ItemStack sabling = itemManager.createItem(Material.OAK_SAPLING, 1, ChatColor.GRAY + "Sapling of Demize", saplingLore);
            MerchantRecipe sablingtrade = new MerchantRecipe(sabling, 999999999);
            ItemStack compresstWeatetradeItemTwo = itemManager.createItem(Material.HAY_BLOCK, 64, ChatColor.BLUE + "Compress Wheat", compresstWheeatLore);
            sablingtrade.addIngredient(compresstWeatetradeItemTwo);
            farmerVillagerTrades.add(sablingtrade);

            villager.setRecipes(farmerVillagerTrades);


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
