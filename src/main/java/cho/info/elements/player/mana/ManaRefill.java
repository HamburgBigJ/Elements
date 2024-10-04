package cho.info.elements.player.mana;

import cho.info.elements.managers.ConfigManager;
import cho.info.elements.managers.ItemManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class ManaRefill implements Listener {
    private final ConfigManager configManager;
    private final ItemManager itemManager;
    private final JavaPlugin plugin;

    public ManaRefill(JavaPlugin plugin, ConfigManager configManager, ItemManager itemManager) {
        this.plugin = plugin;
        this.configManager = configManager;
        this.itemManager = itemManager;

        // Initialize mana refill
        initManaRefill();
    }

    public void initManaRefill() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    // Get current mana for the player
                    int mana = (configManager.getPlayerValue(player, "Mana") != null) ? (int) configManager.getPlayerValue(player, "Mana") : 0;
                    int maxMana = (configManager.getPlayerValue(player, "MaxMana") != null) ? (int) configManager.getPlayerValue(player, "MaxMana") : 0;


                    mana += 1;

                    if (mana > maxMana) {
                        mana = maxMana;
                    } else {
                        // Update the player's mana
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.DARK_AQUA + "Mana: " + mana + " / " + maxMana + "             "));
                    }


                    // Update the player's mana
                    configManager.setPlayerValue(player, "Mana", mana);


                }
            }
        }.runTaskTimer(plugin, 20, 20); // Run every second
    }
}
