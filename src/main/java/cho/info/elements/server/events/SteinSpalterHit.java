package cho.info.elements.server.events;

import cho.info.elements.managers.ItemManager;
import cho.info.elements.managers.MobManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class SteinSpalterHit implements Listener {

    private final String steinSpalterName = ChatColor.DARK_PURPLE + "Stone Splitter";
    private final Material deepslateMaterial = Material.DEEPSLATE;

    private final ItemManager itemManager;
    public MobManager mobManager;

    public SteinSpalterHit(ItemManager itemManager, MobManager mobManager) {
        this.itemManager = itemManager;
        this.mobManager = mobManager;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        // Check if the player right-clicked on a block
        if (event.getAction().toString().contains("RIGHT_CLICK_BLOCK")) {
            Player player = event.getPlayer();
            ItemStack itemInHand = player.getInventory().getItemInMainHand();
            Block clickedBlock = event.getClickedBlock();

            if (clickedBlock != null) {
                // Check if the item in hand is a stick and has the correct name
                if (itemInHand.getType() == Material.STICK && itemInHand.hasItemMeta() && itemInHand.getItemMeta().hasDisplayName()) {
                    String displayName = itemInHand.getItemMeta().getDisplayName();

                    if (displayName.equals(steinSpalterName) && clickedBlock.getType() == deepslateMaterial) {
                        // Remove the block and the item from the player's inventory
                        clickedBlock.setType(Material.AIR);

                        // Decrease the amount of "Stone Splitter" in the player's inventory by 1
                        ItemStack stoneSplitter = new ItemStack(Material.STICK, 1);
                        stoneSplitter.getItemMeta().setDisplayName(steinSpalterName);
                        player.getInventory().removeItem(stoneSplitter);

                        // Update the amount of the item in the player's hand
                        ItemStack updatedItemInHand = itemInHand.clone();
                        updatedItemInHand.setAmount(updatedItemInHand.getAmount() - 1);
                        player.getInventory().setItemInMainHand(updatedItemInHand);


                        Location location = clickedBlock.getLocation();

                        BossBar bossBar = mobManager.createBossBar("Silphy", 20.0);
                        mobManager.spawnCustomMob(location, EntityType.SILVERFISH, ChatColor.RED + "Silphy", bossBar);

                        /*  Lagay Verson
                        // Spawning the "Steinspalter" mini boss
                        Location location = clickedBlock.getLocation();
                        Zombie miniBoss = (Zombie) player.getWorld().spawnEntity(location, EntityType.ZOMBIE);

                        // Customize the mini-boss, for example, by setting its name and increasing its health
                        miniBoss.setCustomName(ChatColor.RED + "Steinspalter Mini-Boss");
                        miniBoss.setCustomNameVisible(true);
                        miniBoss.setHealth(40.0); // Double the health of a regular zombie
                        miniBoss.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
                        miniBoss.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));

                        // Create the custom item with lore using ItemManager
                        List<String> itemLore = itemManager.createLore(ChatColor.WHITE + "Common");
                        ItemStack customItem = itemManager.createItem(Material.EXPERIENCE_BOTTLE, 2, ChatColor.WHITE + "Experience Bottle", itemLore);

                        // Drop the custom item from the mini-boss
                        miniBoss.getWorld().dropItemNaturally(miniBoss.getLocation(), customItem);

                         */
                    }
                }
            }
        }
    }
}
