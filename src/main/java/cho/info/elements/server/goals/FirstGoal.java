package cho.info.elements.server.goals;

import cho.info.elements.managers.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class FirstGoal implements Listener {

    public ConfigManager configManager;
    public JavaPlugin plugin;

    public FirstGoal(JavaPlugin plugin, ConfigManager configManager) {
        this.plugin = plugin;
        this.configManager = configManager;
    }

    @EventHandler
    public void onVillagerInteract(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity goalEntity = event.getRightClicked();

        // Überprüfen, ob das Entity ein Villager ist
        if (goalEntity.getType() == EntityType.VILLAGER) {
            Villager villager = (Villager) goalEntity;

            // Überprüfen, ob der Villager den Namen "FirstGoal" hat
            if (villager.getCustomName() != null && villager.getCustomName().equals(ChatColor.WHITE + "FirstGoal")) {
                player.sendMessage("Villager angeklickt: FirstGoal");
            }
        }
    }
}
