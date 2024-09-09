package cho.info.elements.managers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class VillagerManager implements Listener {

    private JavaPlugin plugin;

    public VillagerManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    // Methode, um einen Villager an der Spielerposition ohne KI zu spawnen
    public void spawnCustomVillager(Player player, List<MerchantRecipe> trades) {
        Location loc = player.getLocation();
        Villager villager = (Villager) loc.getWorld().spawnEntity(loc, EntityType.VILLAGER);

        // Villager Eigenschaften setzen
        villager.setAI(false); // Keine KI
        villager.setCustomName("Custom Trader");
        villager.setCustomNameVisible(true);

        // Villager Trades setzen
        setVillagerTrades(villager, trades);
    }

    // Methode, um benutzerdefinierte Trades zu setzen
    private void setVillagerTrades(Villager villager, List<MerchantRecipe> trades) {
        Merchant merchant = Bukkit.createMerchant(villager.getCustomName());

        // Füge die benutzerdefinierten Trades hinzu
        merchant.setRecipes(trades);

        // Villager mit diesen Trades verknüpfen
        // Villager Inventory kann nicht direkt geändert werden, also öffnen wir das Handelsinterface bei Bedarf
        // Dies kann durch andere Mechanismen in deinem Plugin geschehen, z.B. beim Rechtsklick auf den Villager
    }

    // Beispielmethode, um eine Liste von benutzerdefinierten Trades zu erstellen
    public List<MerchantRecipe> createCustomTrades() {
        List<MerchantRecipe> trades = new ArrayList<>();

        // Beispiel-Trade: 1 Diamant gegen 5 Goldbarren
        ItemStack result = new ItemStack(Material.GOLD_INGOT, 5);
        ItemStack ingredient = new ItemStack(Material.DIAMOND, 1);

        MerchantRecipe trade = new MerchantRecipe(result, 9999); // Unendliche Nutzung des Handels
        trade.addIngredient(ingredient);

        trades.add(trade);
        return trades;
    }
}
