package cho.info.elements.server.recepies;

import org.bukkit.Bukkit;
import org.bukkit.Material;
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

        ShapedRecipe recepie = new ShapedRecipe(ecoShard);

        recepie.shape(" D ", " C ", " D ");

        recepie.setIngredient('D', Material.DIAMOND);
        recepie.setIngredient('C', Material.COBBLESTONE);

        Bukkit.addRecipe(recepie);
    }
}
