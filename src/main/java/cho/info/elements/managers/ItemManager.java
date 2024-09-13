package cho.info.elements.managers;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class ItemManager {

    // Create an ItemStack with a custom name and lore
    public ItemStack createItem(Material material, int amount, String name, List<String> lore) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            // Set the item's name
            meta.setDisplayName(name);

            // Set the item's lore
            if (lore != null && !lore.isEmpty()) {
                meta.setLore(lore);
            }

            // Apply the changes to the item
            item.setItemMeta(meta);
        }

        return item;
    }

    // Helper method to create a simple item lore
    public List<String> createLore(String... lines) {
        List<String> lore = new ArrayList<>();
        for (String line : lines) {
            // Add line break
            lore.add(ChatColor.GRAY + line);
        }
        return lore;
    }
}
