package cho.info.elements.managers;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class ItemManager {

    // Erstelle ein ItemStack mit benutzerdefiniertem Namen und Lore
    public ItemStack createItem(Material material, int amount, String name, List<String> lore) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            // Setze den Namen des Items
            meta.setDisplayName(name);

            // Setze die Lore des Items
            if (lore != null && !lore.isEmpty()) {
                meta.setLore(lore);
            }

            // Wende die Änderungen auf das Item an
            item.setItemMeta(meta);
        }

        return item;
    }

    // Hilfsmethode, um eine einfache ItemLore zu erstellen
    public List<String> createLore(String... lines) {
        List<String> lore = new ArrayList<>();
        for (String line : lines) {
            // Füge den Zeilenumbruch hinzu
            lore.add(ChatColor.GRAY + line);
        }
        return lore;
    }
}
