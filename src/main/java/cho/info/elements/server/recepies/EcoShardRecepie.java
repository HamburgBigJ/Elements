package cho.info.elements.server.recepies;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class EcoShardRecepie {

    public JavaPlugin plugin;

    public EcoShardRecepie(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void createEcoShardRecepie() {
        ItemStack ecoShard = new ItemStack(Material.ECHO_SHARD, 1);

        // Create a NamespacedKey for the recipe
        NamespacedKey key = new NamespacedKey(plugin, "eco_shard");

        // Use the new constructor with the NamespacedKey
        ShapedRecipe recipe = new ShapedRecipe(key, ecoShard);

        recipe.shape(" D ", " C ", " D ");
        recipe.setIngredient('D', Material.DIAMOND);
        recipe.setIngredient('C', Material.COBBLESTONE);

        // Add the recipe to the server
        Bukkit.addRecipe(recipe);
    }
}
