package cho.info.elements.player.skills;

import cho.info.elements.managers.ConfigManager;
import cho.info.elements.server.events.PlayerRespawn;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class MiningSkill implements Listener {

    private static final Logger log = LoggerFactory.getLogger(MiningSkill.class);
    public JavaPlugin plugin;
    private final Set<Material> Materials;
    public ConfigManager configManager;
    private Random random;

    public MiningSkill(JavaPlugin plugin, ConfigManager configManager) {
        this.plugin = plugin;
        this.configManager = configManager;
        this.Materials = new HashSet<>();

        this.Materials.add(Material.STONE);
        this.Materials.add(Material.BASALT);
        this.Materials.add(Material.DEEPSLATE);
        this.Materials.add(Material.NETHER_WART);
        this.Materials.add(Material.COBBLESTONE);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Material blockType = event.getBlock().getType();
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        // Check if the block type is in the set of materials
        if (Materials.contains(blockType)) {


            int xpMultiplier = 1; // Default XP multiplier
            int dropMultiplier = 0; // Default drop multiplier

            if (isSpecialPickaxe(item)) {
                xpMultiplier = 2; // Apply special XP multiplier

                if (Bukkit.getCurrentTick() >= 5) {
                    dropMultiplier = 1;
                } else {
                    dropMultiplier = Bukkit.getCurrentTick(); // Adjust as needed
                }
            }

            handleMiningXp(player, xpMultiplier);

            modifyBlockDrops(event, dropMultiplier);


        } else if (blockType == Material.NETHER_WART) {
            if (event.getBlock().getBlockData() instanceof Ageable ageable && ageable.getAge() == 3) {

                int xpMultiplier = 1; // Default XP multiplier
                int dropMultiplier = 0; // Default drop multiplier

                if (isSpecialPickaxe(item)) {
                    xpMultiplier = 2; // Apply special XP multiplier

                    if (Bukkit.getCurrentTick() >= 5) {
                        dropMultiplier = 1;
                    } else {
                        dropMultiplier = Bukkit.getCurrentTick(); // Adjust as needed
                    }
                }

                handleMiningXp(player, xpMultiplier);

                modifyBlockDrops(event, dropMultiplier);

            }
        }
    }

    private void handleMiningXp(Player player, int xpMultiplier) {
        // Retrieve existing XP values
        Object miningxpObj = configManager.getPlayerValue(player, "MiningXp");
        Object miningMaxXpObj = configManager.getPlayerValue(player, "MiningMaxXp");

        int miningMaxXp = (miningMaxXpObj != null) ? (int) miningMaxXpObj : 0;
        int miningXp = (miningxpObj != null) ? (int) miningxpObj : 0;

        miningXp += 1; // Increase XP

        // Save the new MiningXp value
        configManager.setPlayerValue(player, "MiningXp", miningXp);

        // Display XP in the Action Bar
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
                ChatColor.DARK_AQUA + "⛏ Mining XP: " + miningXp + " / " + miningMaxXp
        ));

        // Get base XP and apply the multiplier
        Object basexpobj = configManager.getPlayerValue(player, "BaseXp");
        int basexp = (basexpobj != null) ? (int) basexpobj : 0;

        int bonusxp = checkHomeDimension(player);

        player.giveExp((basexp * xpMultiplier) + bonusxp); // Apply multiplier
    }

    private boolean isSpecialPickaxe(ItemStack item) {
        if (item != null && item.getType() == Material.IRON_PICKAXE) {
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                // Check for the specific name of the pickaxe
                if (meta.hasDisplayName() && meta.getDisplayName().equals(ChatColor.DARK_GREEN + "Element Pickaxe")) {
                    return true;
                }

                // Optionally check for lore
                // Example: return meta.hasLore() && meta.getLore().contains(ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "Rare");
            }
        }
        return false;
    }

    private void modifyBlockDrops(BlockBreakEvent event, int dropMultiplier) {
        // Clear existing drops
        event.getBlock().getDrops().clear();

        // Define a mapping from block types to desired drops
        Map<Material, Material> blockToDropMapping = Map.of(
                Material.STONE, Material.COBBLESTONE,
                Material.BASALT, Material.BASALT, // Example mapping, adjust as needed
                Material.DEEPSLATE, Material.COBBLESTONE // Adjust as needed
        );

        // Get the desired drop material
        Material dropMaterial = blockToDropMapping.getOrDefault(event.getBlock().getType(), event.getBlock().getType());
        ItemStack dropItem = new ItemStack(dropMaterial);

        // Set the new drop amount based on the multiplier
        int baseDropAmount = 1; // Default drop amount
        int newDropAmount = (int) Math.round(baseDropAmount + dropMultiplier - 1);

        for (int i = 0; i < newDropAmount; i++) {
            event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), dropItem);
        }
    }

    private int checkHomeDimension(Player player) {
        // Hole die Heimdimension des Spielers aus der Konfiguration
        Object homedimensionobj = configManager.getPlayerValue(player, "HomeDimension");
        int homedimension = (homedimensionobj != null) ? (int) homedimensionobj : 0; // 1 = Skyblock, 2 = StoneBlock, 3 = WaterBlock

        // Ermitteln der aktuellen Dimension des Spielers (hier als Welt angenommen)
        String currentWorldName = player.getWorld().getName();

        // Hier solltest du eine Methode oder Logik haben, um die Dimension anhand des Weltnamens zu bestimmen
        int currentDimension = getDimensionFromWorldName(currentWorldName);

        // Vergleiche die Dimensionen
        if (currentDimension == homedimension) {
            return 2;
        }
        return 0;
    }

    private int getDimensionFromWorldName(String worldName) {
        // Füge hier die Logik hinzu, um die Dimension anhand des Weltnamens zu bestimmen
        // Zum Beispiel:
        if (worldName.equals("world_skyblock")) {
            return 1; // Skyblock
        } else if (worldName.equals("world_stone")) {
            return 2; // StoneBlock
        } else if (worldName.equals("world_whater")) {
            return 3; // WaterBlock
        } else {
            return 0; // Unbekannte Dimension
        }
    }
}
