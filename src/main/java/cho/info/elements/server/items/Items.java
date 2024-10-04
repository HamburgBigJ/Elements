package cho.info.elements.server.items;

import cho.info.elements.managers.ItemManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class Items {

    public ItemManager itemManager;

    public Items(ItemManager itemManager) {
        this.itemManager = itemManager;
    }

    public ItemStack lebensFrucht(int amount) {

        List<String> lebensFruchtLore = itemManager.createLore(ChatColor.GRAY + "Click to Consume");
        ItemStack lebensFrucht = itemManager.createItem(Material.FERMENTED_SPIDER_EYE, amount, ChatColor.GOLD + "Health Fruit", lebensFruchtLore);
        if (lebensFrucht.getItemMeta() != null) {
            ItemMeta lebensFruchtMeta = lebensFrucht.getItemMeta();

            lebensFruchtMeta.addEnchant(Enchantment.AQUA_AFFINITY, 1, true);

            lebensFrucht.setItemMeta(lebensFruchtMeta);
        }

        return lebensFrucht;
    }

    public ItemStack gravatyStone(int amount) {
        List<String> gravatyStoneLore = itemManager.createLore(ChatColor.GRAY + "Halte in der offhand um Alle items",
                ChatColor.GRAY + "in einem 5x5 Radius zu dir zu ziehen");
        ItemStack gravatyStone = itemManager.createItem(Material.GRAY_DYE, amount, ChatColor.GREEN + "Gravaty Stone", gravatyStoneLore);

        if (gravatyStone.getItemMeta() != null) {
            ItemMeta gravatyStoneMeta = gravatyStone.getItemMeta();

            gravatyStoneMeta.addEnchant(Enchantment.LOOTING, 1, true);

            gravatyStone.setItemMeta(gravatyStoneMeta);
        }

        return gravatyStone;
    }

    public ItemStack upgradeItem(int amount) {
        List<String> upgradeItemLore = itemManager.createLore(ChatColor.GRAY + "Upgrade your items!");
        ItemStack upgradeItem = itemManager.createItem(Material.BOLT_ARMOR_TRIM_SMITHING_TEMPLATE, amount, ChatColor.GREEN + "Upgrade Item", upgradeItemLore);

        if (upgradeItem.getItemMeta() != null) {
            ItemMeta upgradeItemMeta = upgradeItem.getItemMeta();

            upgradeItemMeta.addEnchant(Enchantment.MENDING, 1, true);

            upgradeItem.setItemMeta(upgradeItemMeta);
        }

        return upgradeItem;
    }



}
