/*
Created by: HamburgBihJ
9/6/2024
10:04
Edit by: HamburgBigJ
 */
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class MiningSkill implements Listener {

    private static final Logger log = LoggerFactory.getLogger(MiningSkill.class);
    private JavaPlugin plugin;
    private final Set<Material> Materials;
    public ConfigManager configManager;

    public MiningSkill(JavaPlugin plugin, ConfigManager configManager) {
        this.plugin = plugin;
        this.configManager = configManager; // Save reference to ConfigManager
        this.Materials = new HashSet<>();

        this.Materials.add(Material.STONE);
        this.Materials.add(Material.BASALT);
        this.Materials.add(Material.DEEPSLATE);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Material blockType = event.getBlock().getType();

        if (Materials.contains(blockType)) {
            Player player = event.getPlayer();

            // Check if the player already has MiningXp; otherwise, use 0
            Object miningxpObj = configManager.getPlayerValue(player, "MiningXp");
            Object miningMaxXpObj = configManager.getPlayerValue(player, "MiningMaxXp");

            int miningMaxXp = (miningMaxXpObj != null) ? (int) miningMaxXpObj : 0;
            int miningXp = (miningxpObj != null) ? (int) miningxpObj : 0;

            miningXp = miningXp + 1;

            // Save the new MiningXp value
            configManager.setPlayerValue(player, "MiningXp", miningXp);

            // Send Action Bar message with colors
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
                    ChatColor.DARK_AQUA + "Mining XP: " + miningXp + " / " + miningMaxXp
            ));


            Object basexpobj = configManager.getPlayerValue(player, "BaseXp");
            Object xpmultipliorobj = configManager.getPlayerValue(player, "XpMultiplier");

            int basexp = (basexpobj != null) ? (int) basexpobj : 0;
            int xpmultiplior = (xpmultipliorobj != null) ? (int) xpmultipliorobj : 0;

            player.giveExp(basexp * xpmultiplior);
        }
    }
}
