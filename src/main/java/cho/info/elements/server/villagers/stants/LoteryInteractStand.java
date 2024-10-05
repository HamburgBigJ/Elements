package cho.info.elements.server.villagers.stants;

import cho.info.elements.managers.ConfigManager;
import cho.info.elements.managers.ItemManager;
import cho.info.elements.server.items.Items;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class LoteryInteractStand implements Listener {

    public ConfigManager configManager;
    public ItemManager itemManager;
    public Items items;

    public LoteryInteractStand(ConfigManager configManager, ItemManager intemManager, Items items) {
        this.configManager = configManager;
        this.itemManager = intemManager;
        this.items = items;
    }

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();

        if (entity instanceof Villager) {
            Villager villager = (Villager) entity;
            if (villager.getCustomName().equals(ChatColor.DARK_AQUA + "Lotery Villager")) {

                if (player.getLevel() >= 1000) {
                    player.setLevel(player.getLevel() - 1000);
                    player.sendMessage(ChatColor.GREEN + "You have paid 1000 levels to the Lotery Villager!");

                    // Random number between 1 and 100
                    int random = (int) (Math.random() * 100) + 1;

                    if (random == 1) {
                        player.sendMessage(ChatColor.GREEN + "You have won the jackpot! 10000 levels have been added to your account!");
                        player.setLevel(player.getLevel() + 10000);

                        player.getInventory().addItem(items.lebensFrucht(2));

                    } else if (random == 2 || random == 3) {
                        player.sendMessage(ChatColor.GREEN + "You have won 700 levels!");
                        player.setLevel(player.getLevel() + 700);

                    } else if (random >= 4 && random <= 10) {
                        player.sendMessage(ChatColor.GREEN + "You have won 100 levels!");
                        player.setLevel(player.getLevel() + 100);

                        player.getInventory().addItem(items.gravatyStone(1));

                    } else if (random >= 11 && random <= 30) {
                        player.sendMessage(ChatColor.GREEN + "You have won 50 levels!");
                        player.setLevel(player.getLevel() + 50);

                    } else if (random >= 31 && random <= 60) {
                        player.sendMessage(ChatColor.GREEN + "You have won 25 levels!");
                        player.setLevel(player.getLevel() + 25);

                    } else if (random >= 61 && random <= 100) {
                        player.sendMessage(ChatColor.GREEN + "You have won 10 levels!");
                        player.setLevel(player.getLevel() + 10);

                    }
                } else {
                    player.sendMessage(ChatColor.RED + "Du bracuhst mindestens 1000 Level, um am Lotery teilzunehmen!");
                }
            }
        }
    }

}
