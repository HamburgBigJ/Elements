package cho.info.elements.player.skills;

import cho.info.elements.managers.ConfigManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public class ForestingSkill implements Listener {
    public JavaPlugin plugin;
    public ConfigManager configManager;
    private final Set<Material> Materials;

    // Constructor to initialize the ForestingSkill class
    public ForestingSkill(JavaPlugin plugin, ConfigManager configManager) {
        this.plugin = plugin;
        this.configManager = configManager;
        this.Materials = new HashSet<>();

        // Add different types of log materials to the set
        this.Materials.add(Material.OAK_LOG);
        this.Materials.add(Material.WARPED_STEM);
        this.Materials.add(Material.CRIMSON_STEM);
        this.Materials.add(Material.JUNGLE_LOG);
        this.Materials.add(Material.SPRUCE_LOG);
        this.Materials.add(Material.BIRCH_LOG);
        this.Materials.add(Material.ACACIA_LOG);
        this.Materials.add(Material.DARK_OAK_LOG);
        this.Materials.add(Material.MANGROVE_LOG);
        this.Materials.add(Material.CHERRY_LOG);
    }

    // Event handler for block break events
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Material blockType = event.getBlock().getType();

        // Check if the block broken is one of the log materials
        if (Materials.contains(blockType)) {
            Player player = event.getPlayer();

            // Retrieve the player's foresting XP and max XP from the configuration manager
            Object forestingXpObj = configManager.getPlayerValue(player, "ForestingXp");
            Object forestingMaxXpObj = configManager.getPlayerValue(player, "ForestingMaxXp");

            int forestingXp = (forestingXpObj != null) ? (int) forestingXpObj : 0;
            int forestingMaxXp = (forestingMaxXpObj != null) ? (int) forestingMaxXpObj : 0;

            // Increment the player's foresting XP
            forestingXp = forestingXp + 1;

            // Update the player's foresting XP in the configuration manager
            configManager.setPlayerValue(player, "ForestingXp", forestingXp);

            // Send a message to the player displaying their current foresting XP
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
                    ChatColor.DARK_AQUA + "Foresting XP: " + forestingXp  + " / " + forestingMaxXp
            ));

            Object basexpobj = configManager.getPlayerValue(player, "BaseXp");
            Object xpmultipliorobj = configManager.getPlayerValue(player, "XpMultiplier");

            int basexp = (basexpobj != null) ? (int) basexpobj : 0;
            int xpmultiplior = (xpmultipliorobj != null) ? (int) xpmultipliorobj : 0;

            player.giveExp(basexp * xpmultiplior);

        }
    }
}
