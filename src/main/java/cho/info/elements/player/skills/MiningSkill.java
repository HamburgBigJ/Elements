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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;


public class MiningSkill implements Listener {

    private static final Logger log = LoggerFactory.getLogger(MiningSkill.class);
    private JavaPlugin plugin;
    private final Set<Material> blockedMaterials;
    public ConfigManager configManager;

    public MiningSkill(JavaPlugin plugin, ConfigManager configManager) {
        this.plugin = plugin;
        this.configManager = configManager; // ConfigManager-Referenz speichern
        this.blockedMaterials = new HashSet<>();

        this.blockedMaterials.add(Material.STONE);
        this.blockedMaterials.add(Material.BASALT);
        this.blockedMaterials.add(Material.DEEPSLATE);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Material blockType = event.getBlock().getType();

        if (blockedMaterials.contains(blockType)) {
            Player player = event.getPlayer();

            // Überprüfen, ob der Spieler bereits MiningXp hat, ansonsten 0 verwenden
            Object miningxpObj = configManager.getPlayerValue(player, "MiningXp");
            Object miningMaxXpObj = configManager.getPlayerValue(player, "MiningMaxXp");

            int miningMaxXp = (miningMaxXpObj != null) ? (int) miningMaxXpObj : 0;
            int miningXp = (miningxpObj != null) ? (int) miningxpObj : 0;

            miningXp = miningXp + 1;

            // Speichern des neuen MiningXp-Wertes
            configManager.setPlayerValue(player, "MiningXp", miningXp);

            // Action Bar mit Farben senden
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
                    ChatColor.DARK_AQUA + "Mining XP: " + miningXp + " / " + miningMaxXp
            ));
        }
    }


}
