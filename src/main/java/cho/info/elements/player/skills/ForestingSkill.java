package cho.info.elements.player.skills;

import cho.info.elements.configs.ConfigManager;
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
    private JavaPlugin plugin;
    public ConfigManager configManager;
    private final Set<Material> blockedMaterials;

    public ForestingSkill(JavaPlugin plugin, ConfigManager configManager) {
        this.plugin = plugin;
        this.configManager = configManager;
        this.blockedMaterials = new HashSet<>();

        this.blockedMaterials.add(Material.OAK_LOG);
        this.blockedMaterials.add(Material.WARPED_STEM);
        this.blockedMaterials.add(Material.CRIMSON_STEM);
        this.blockedMaterials.add(Material.JUNGLE_LOG);
        this.blockedMaterials.add(Material.SPRUCE_LOG);
        this.blockedMaterials.add(Material.BIRCH_LOG);
        this.blockedMaterials.add(Material.ACACIA_LOG);
        this.blockedMaterials.add(Material.DARK_OAK_LOG);
        this.blockedMaterials.add(Material.MANGROVE_LOG);
        this.blockedMaterials.add(Material.CHERRY_LOG);

    }

    @EventHandler
    public void onBlockBreake(BlockBreakEvent event) {
        Material blockType = event.getBlock().getType();

        if (blockedMaterials.contains(blockType)) {
            Player player = event.getPlayer();

            Object forestingXpObj = configManager.getPlayerValue(player, "ForestingXp");
            Object forestingMacXpObj = configManager.getPlayerValue(player, "ForestingMaxXp");

            int forestingXp = (forestingXpObj != null) ? (int) forestingXpObj : 0;
            int forestingMaxXp = (forestingMacXpObj != null) ? (int) forestingMacXpObj : 0;

            forestingXp = forestingXp + 1;

            configManager.setPlayerValue(player, "ForestingXp", forestingXp);

            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
                    ChatColor.DARK_AQUA + "Farming XP: " + forestingXp  + " / " + forestingMaxXp
            ));
        }

    }

}
