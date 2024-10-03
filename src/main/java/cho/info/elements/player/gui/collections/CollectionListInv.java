package cho.info.elements.player.gui.collections;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.enginehub.linbus.stream.token.LinToken;

import java.util.List;

public class CollectionListInv {

    public JavaPlugin plugin;

    public CollectionListInv(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void buildInv(String name, Integer collected, Player player) {

        Inventory inventory = Bukkit.createInventory(null, 27, ChatColor.GOLD + "Collection: " + ChatColor.BLUE + name);

        ItemStack filler = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        if (filler.getItemMeta() != null) {
            ItemMeta fillerMeta = filler.getItemMeta();
            fillerMeta.setDisplayName(ChatColor.GRAY + " ");
            filler.setItemMeta(fillerMeta);
        }

        for (int i = 0; i < 27; i++) {
            inventory.setItem(i, filler);
        }

        for (int i = 10; i <= 19; i++) {
            ItemStack item = new ItemStack(Material.RED_STAINED_GLASS_PANE);
            if (item.getItemMeta() != null) {
                ItemMeta itemMeta = item.getItemMeta();
                String displayName = ChatColor.RED + name + " Locked!  " + ((i - 9) * 100);
                itemMeta.setDisplayName(displayName);
                item.setItemMeta(itemMeta);

                if (collected >= ((i - 9) * 100)) {
                    item = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
                    if (item.getItemMeta() != null) {
                        ItemMeta itemMeta2 = item.getItemMeta();
                        String displayName2 = ChatColor.GREEN + name + " Unlocked!  " + (i - 9);
                        itemMeta2.setDisplayName(displayName2);
                        item.setItemMeta(itemMeta2);
                    }
                }


            }
            inventory.setItem(i, item);
        }




        player.openInventory(inventory);
    }


}
