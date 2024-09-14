package cho.info.elements.server;

import cho.info.elements.managers.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

public class VillagerTechniker implements Listener {

    public ConfigManager configManager;

    public VillagerTechniker(ConfigManager configManager) {
        this.configManager = configManager;
    }

    public void villagerSpawn() {
        // Define locations in different worlds
        Location technikerSkyLocation = new Location(Bukkit.getWorld("world_skyblock"), 0, 70, 2);
        Location technikerStoneLocation = new Location(Bukkit.getWorld("world_stone"), 10, 65, 10);
        Location technikerWaterLocation = new Location(Bukkit.getWorld("world_water"), -10, 63, -5);

        // Spawn villagers in each location if they aren't already present
        spawnVillagerAtLocation(technikerSkyLocation, ChatColor.GOLD + "Techniker");
        spawnVillagerAtLocation(technikerStoneLocation, ChatColor.GOLD + "Techniker");
        spawnVillagerAtLocation(technikerWaterLocation, ChatColor.GOLD + "Techniker");
    }

    private void spawnVillagerAtLocation(Location location, String customName) {
        if (!isVillagerAtLocation(location)) {
            Villager techniker = (Villager) location.getWorld().spawnEntity(location, EntityType.VILLAGER);
            techniker.setAI(false);
            techniker.setSilent(true);
            techniker.setNoPhysics(true);
            techniker.setInvisible(true);
            techniker.setCustomName(customName);
            techniker.setCustomNameVisible(true);
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

    @EventHandler
    public void onVillagerClick(PlayerInteractEntityEvent event) {
        // Check if the entity is a Villager
        if (event.getRightClicked() instanceof Villager) {
            Villager villager = (Villager) event.getRightClicked();
            Player player = event.getPlayer();

            // Check if the clicked villager is the "Techniker"
            if (villager.getCustomName() != null && villager.getCustomName().equals(ChatColor.GOLD + "Techniker")) {
                Object stageObj = configManager.getPlayerValue(player, "TechnikerStage");

                int stage = (stageObj != null) ? (int) stageObj : 0;

                if (stage == 0) {
                    // Give the player the starter Shulker Box as in the previous stage
                    player.sendMessage("Du hast deine Dimension gewählt.\n" +
                            "Leider kannst du deine Dimension nicht wechseln!\n" +
                            "Bringe mir für ein Upgrade deiner Enderchest diese Items:\n" +
                            "64 Oak Log\n" +
                            "64 Cobblestone\n" +
                            "50 Level XP\n" +
                            "\n" +
                            "Hier für den Start eine Starterkiste!"
                    );

                    giveShulkerBox(player);
                    configManager.setPlayerValue(player, "TechnikerStage", 1);

                } else if (stage == 1) {
                    if (player.getLevel() >= 50 &&
                            hasRequiredItems(player, new ItemStack(Material.OAK_LOG, 64), new ItemStack(Material.COBBLESTONE, 64))) {

                        // Remove the required items and XP from the player
                        player.getInventory().removeItem(new ItemStack(Material.OAK_LOG, 64));
                        player.getInventory().removeItem(new ItemStack(Material.COBBLESTONE, 64));
                        player.setLevel(player.getLevel() - 50);

                        player.sendMessage(ChatColor.GREEN + "Du hast das Enderchest-Upgrade erfolgreich erhalten!\n" +
                                "Du kannst nun zwischen den Dimensionen reisen!");

                        // Proceed to next stage (Stage 4)
                        configManager.setPlayerValue(player, "TechnikerStage", 2);

                    } else {
                        player.sendMessage(ChatColor.RED + "Du benötigst folgende Items, um fortzufahren:\n" +
                                "64 Oak Logs\n" +
                                "64 Cobblestone\n" +
                                "50 Level XP"
                        );
                    }
                }
            }
        }
    }

    private void giveShulkerBox(Player player) {
        // Create a black Shulker Box item
        ItemStack shulkerBoxItem = new ItemStack(Material.BLACK_SHULKER_BOX);

        // Get the BlockStateMeta to modify the contents of the Shulker Box
        BlockStateMeta blockStateMeta = (BlockStateMeta) shulkerBoxItem.getItemMeta();
        ShulkerBox shulkerBox = (ShulkerBox) blockStateMeta.getBlockState();

        // Create an inventory with the desired contents
        Inventory inventory = shulkerBox.getInventory();

        // Add items to the inventory based on the screenshot
        inventory.setItem(0, new ItemStack(Material.ICE)); // Ice
        inventory.setItem(1, new ItemStack(Material.LAVA_BUCKET)); // Lava bucket
        inventory.setItem(2, new ItemStack(Material.OAK_SAPLING)); // Oak Sapling
        inventory.setItem(3, new ItemStack(Material.BAMBOO)); // Bamboo
        inventory.setItem(4, new ItemStack(Material.SPRUCE_SAPLING)); // Spruce Sapling
        inventory.setItem(5, new ItemStack(Material.DEAD_BUSH)); // Dead Bush
        inventory.setItem(6, new ItemStack(Material.MOSS_BLOCK)); // Moss Block
        inventory.setItem(7, new ItemStack(Material.MELON_SLICE)); // Melon Slice
        inventory.setItem(8, new ItemStack(Material.PUMPKIN_SEEDS)); // Pumpkin Seeds
        inventory.setItem(9, new ItemStack(Material.GLOW_BERRIES)); // Glow Berries
        inventory.setItem(10, new ItemStack(Material.SPRUCE_SAPLING)); // Spruce Sapling
        inventory.setItem(11, new ItemStack(Material.MOSS_BLOCK, 64)); // 64x Moss Block
        inventory.setItem(12, new ItemStack(Material.MOSS_BLOCK, 64)); // 64x Moss Block
        inventory.setItem(13, new ItemStack(Material.SUGAR_CANE)); // Sugar Cane
        inventory.setItem(14, new ItemStack(Material.CACTUS)); // Cactus
        inventory.setItem(15, new ItemStack(Material.OAK_SAPLING)); // Oak Sapling
        inventory.setItem(16, new ItemStack(Material.ACACIA_SAPLING)); // Acacia Sapling
        inventory.setItem(17, new ItemStack(Material.CRIMSON_NYLIUM)); // Crimson Nylium

        // Set the inventory in the Shulker Box
        shulkerBox.update();

        // Set the modified BlockStateMeta back to the Shulker Box item
        blockStateMeta.setBlockState(shulkerBox);
        shulkerBoxItem.setItemMeta(blockStateMeta);

        // Give the Shulker Box to the player
        player.getInventory().addItem(shulkerBoxItem);
    }

    // Method to check if the player has the required items
    private boolean hasRequiredItems(Player player, ItemStack... items) {
        for (ItemStack item : items) {
            if (!player.getInventory().containsAtLeast(item, item.getAmount())) {
                return false;
            }
        }
        return true;
    }
}
