package cho.info.elements.player.gui;

import cho.info.elements.managers.ConfigManager;
import cho.info.elements.managers.ItemManager;
import cho.info.elements.managers.VariableManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class EnderChest implements Listener {

    private final JavaPlugin plugin;
    private final VariableManager variableManager;
    private final ItemManager itemManager;
    private final ConfigManager configManager;

    public EnderChest(JavaPlugin plugin, ConfigManager configManager, VariableManager variableManager, ItemManager itemManager) {
        this.configManager = configManager;
        this.variableManager = variableManager;
        this.plugin = plugin;
        this.itemManager = itemManager;

        if (this.itemManager == null) {
            plugin.getLogger().warning("ItemManager is not initialized.");
        }
    }

    @EventHandler
    public void onEnderGui(PlayerInteractEvent event) {
        if (event.getAction().isRightClick() && event.hasBlock()) {
            Block block = event.getClickedBlock();
            Player player = event.getPlayer();
            Inventory enderchest = player.getEnderChest();

            // Sicherstellen, dass "EnderGui" korrekt referenziert wird
            Object endertierObj = configManager.getPlayerValue(player, "EnderGui");
            int endertier = (endertierObj != null) ? (int) endertierObj : 0;



            if (block != null && block.getType() == Material.ENDER_CHEST) {

                //EnderChest Tier 1
                if (endertier >= 1) {
                    // Tteleport item
                    List<String> teleportLore = itemManager.createLore(ChatColor.WHITE + "Click to Teleport");
                    ItemStack teleport = itemManager.createItem(Material.ENDER_EYE, 1, ChatColor.GOLD + "Teleport", teleportLore);
                    enderchest.setItem(22, teleport);

                    // Hub Item
                    List<String> hubLore = itemManager.createLore(ChatColor.WHITE + "Click to Teleport");
                    ItemStack hubitem = itemManager.createItem(Material.NETHER_STAR, 1, ChatColor.GOLD + "Hub", hubLore);
                    enderchest.setItem(23, hubitem);

                    // Selector
                    List<String> selectorLore = itemManager.createLore(ChatColor.WHITE + "Click to switch");
                    ItemStack selectorItem = itemManager.createItem(Material.BLUE_STAINED_GLASS_PANE, 1, ChatColor.BLUE + "SkyBlock", selectorLore);
                    enderchest.setItem(21, selectorItem);

                    //Enderchest Tier 2
                    if (endertier >= 2){
                        //Stats Item

                        //Vars
                        Object miningLvObj = configManager.getPlayerValue(player, "MiningLv");
                        Object forestryLvObj = configManager.getPlayerValue(player, "ForestingLv");
                        Object farmingLvObj = configManager.getPlayerValue(player, "FarmingLv");

                        int miningLv = (miningLvObj != null) ? (int) miningLvObj : 0;
                        int forestingLv = (forestryLvObj != null) ? (int) forestryLvObj : 0;
                        int farmingLv = (farmingLvObj != null) ? (int) farmingLvObj :0;


                        List<String> statitemlore = itemManager.createLore(
                                ChatColor.DARK_AQUA + "MiningLv: " + miningLv,
                                ChatColor.DARK_AQUA + "ForestingLv: " + forestingLv,
                                ChatColor.DARK_AQUA + "FarmingLv: " + farmingLv
                        );

                        ItemStack statitem = itemManager.createItem(Material.LECTERN, 1, ChatColor.GOLD + "Stats", statitemlore);

                        enderchest.setItem(26, statitem);

                    }
                }
            }
        }
    }

    @EventHandler
    public void onEnderInteract(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        ItemStack clickedItem = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();

        // Überprüfen, ob das Inventar das Enderchest des Spielers ist
        if (inventory.equals(player.getEnderChest())) {
            if (clickedItem != null && clickedItem.hasItemMeta()) {
                ItemMeta meta = clickedItem.getItemMeta();
                if (meta != null && meta.hasDisplayName()) {
                    String displayName = meta.getDisplayName();

                    // Überprüfen, ob der angeklickte Gegenstand ein Teleport-Gegenstand ist
                    if (displayName.equals(ChatColor.GOLD + "Teleport")) {

                        Object selectorobj = configManager.getPlayerValue(player, "Selector");

                        int selector = (selectorobj != null) ? (int) selectorobj : 0;

                        if (selector == 1) {
                            World world = Bukkit.getWorld("world_skyblock");

                            if (world != null) {
                                // Erstelle die Location für den Teleport
                                Location teleportLocation = new Location(world, 1, 70, 1);

                                // Teleportiere den Spieler zur Location
                                player.teleport(teleportLocation);
                                player.sendMessage(ChatColor.GREEN + "You have been teleported to the location!");
                            } else {
                                player.sendMessage(ChatColor.RED + "The world 'world_skyblock' could not be found!");
                            }

                        } else if (selector == 2) {
                            World world = Bukkit.getWorld("world_stone");

                            if (world != null) {
                                // Erstelle die Location für den Teleport
                                Location teleportLocation = new Location(world, 1, 70, 1);

                                // Teleportiere den Spieler zur Location
                                player.teleport(teleportLocation);
                                player.sendMessage(ChatColor.GREEN + "You have been teleported to the location!");
                            } else {
                                player.sendMessage(ChatColor.RED + "The world 'world_stone' could not be found!");
                            }

                        }else if (selector == 3) {
                            World world = Bukkit.getWorld("world_whater");

                            if (world != null) {
                                // Erstelle die Location für den Teleport
                                Location teleportLocation = new Location(world, 1, 70, 1);

                                // Teleportiere den Spieler zur Location
                                player.teleport(teleportLocation);
                                player.sendMessage(ChatColor.GREEN + "You have been teleported to the location!");
                            } else {
                                player.sendMessage(ChatColor.RED + "The world 'world_whater' could not be found!");
                            }

                        }

                        event.setCancelled(true);
                    }


                    // Hub Item
                    if (displayName.equals(ChatColor.GOLD + "Hub")) {


                        // Welt "world" laden
                        World world = Bukkit.getWorld("world");

                        if (world != null) {
                            // Erstelle die Location für den Teleport
                            Location teleportLocation = new Location(world, 1, 70, 1);

                            // Teleportiere den Spieler zur Location
                            player.teleport(teleportLocation);
                            player.sendMessage(ChatColor.GREEN + "You have been teleported to the location!");
                        } else {
                            player.sendMessage(ChatColor.RED + "The world 'world' could not be found!");
                        }

                        event.setCancelled(true);

                    }

                    // Selector Item // Test Version
                    if (displayName.equals(ChatColor.BLUE + "SkyBlock") || displayName.equals(ChatColor.GRAY + "StoneBlock") || displayName.equals(ChatColor.AQUA + "WarherBlock")) {

                        Object selectorobj = configManager.getPlayerValue(player, "Selector");

                        int selector = (selectorobj != null) ? (int) selectorobj : 0;

                        selector = selector + 1;

                        if (selector >= 3) {
                            selector = 1;
                        }

                        configManager.setPlayerValue(player, "Selector", selector);

                        setSelector(player, selector);


                        event.setCancelled(true);
                    }
                }
            }

        }
        //Prevent Ender CHest Edeting
        Object enderChestEditobj = configManager.getPlayerValue(player, "EnderChestEdit");

        int enderChestEdit = (enderChestEditobj != null) ? (int) enderChestEditobj : 0;

        if (enderChestEdit == 0) {
            event.setCancelled(true);
        } else if (enderChestEdit == 1) {
            plugin.getLogger().info("Not Cancelt");
        } else if (enderChestEdit >= 1) {
            enderChestEdit = 0;
            configManager.setPlayerValue(player, "EnderChestEdit", enderChestEdit);
        }

    }

    public void setSelector(Player player, Integer selector) {
        Inventory inventory = player.getEnderChest();

        if (selector == 1) {

            List<String> selectorLore = itemManager.createLore(ChatColor.WHITE + "Click to switch");
            ItemStack selectorItem = itemManager.createItem(Material.BLUE_STAINED_GLASS_PANE, 1, ChatColor.BLUE + "SkyBlock", selectorLore);
            inventory.setItem(21, selectorItem);
        }else if (selector == 2) {

            List<String> selectorLore = itemManager.createLore(ChatColor.WHITE + "Click to switch");
            ItemStack selectorItem = itemManager.createItem(Material.BLACK_STAINED_GLASS_PANE, 1, ChatColor.GRAY + "StoneBlock", selectorLore);
            inventory.setItem(21, selectorItem);
        }else if (selector == 3) {

            List<String> selectorLore = itemManager.createLore(ChatColor.WHITE + "Click to switch");
            ItemStack selectorItem = itemManager.createItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE, 1, ChatColor.AQUA + "WarherBlock", selectorLore);
            inventory.setItem(21, selectorItem);
        }


    }
}
