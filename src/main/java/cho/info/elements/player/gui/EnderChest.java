package cho.info.elements.player.gui;

import cho.info.elements.managers.ConfigManager;
import cho.info.elements.managers.ItemManager;
import cho.info.elements.managers.VariableManager;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class EnderChest implements Listener {

    public JavaPlugin plugin;
    public VariableManager variableManager;
    public ItemManager itemManager;
    public ConfigManager configManager;


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




            if (block != null && block.getType() == Material.ENDER_CHEST) {

                ItemStack air = new ItemStack(Material.AIR);


                // Reset all  items
                enderchest.setItem(0, air);
                enderchest.setItem(1, air);
                enderchest.setItem(2, air);
                enderchest.setItem(3, air);
                enderchest.setItem(4, air);
                enderchest.setItem(5, air);
                enderchest.setItem(6, air);
                enderchest.setItem(7, air);
                enderchest.setItem(8, air);

                enderchest.setItem(9, air);
                enderchest.setItem(10, air);
                enderchest.setItem(11, air);
                enderchest.setItem(12, air);
                enderchest.setItem(13, air);
                enderchest.setItem(14, air);
                enderchest.setItem(16, air);
                enderchest.setItem(17, air);

                enderchest.setItem(19, air);
                enderchest.setItem(20, air);
                enderchest.setItem(21, air);
                enderchest.setItem(22, air);
                enderchest.setItem(23, air);
                enderchest.setItem(24, air);
                enderchest.setItem(25, air);
                enderchest.setItem(26, air);

                // Sicherstellen, dass "EnderGui" korrekt referenziert wird
                Object endertierObj = configManager.getPlayerValue(player, "EdderGui");
                int endertier = (endertierObj != null) ? (int) endertierObj : 0;



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
                    configManager.setPlayerValue(player, "Selector", 1);


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

                        if (endertier >= 3) {

                            ItemStack item = player.getEnderChest().getItem(18); // Hole das Item im Slot 18

                            // Überprüfe, ob der Slot leer ist oder das Item Luft ist
                            if (item == null || item.getType() == Material.AIR) {
                                List<String> extraenderchestlore = itemManager.createLore(ChatColor.GOLD + "Click to open EnderChest");

                                // Erstelle eine weiße Shulkerbox mit einem benutzerdefinierten Namen und Lore
                                ItemStack extraenderchest = itemManager.createItem(Material.WHITE_SHULKER_BOX, 1, ChatColor.DARK_PURPLE + "Element EnderChest", extraenderchestlore);

                                // Setze das Item in den Slot 18 der Enderchest
                                player.getEnderChest().setItem(18, extraenderchest);

                            }

                            if (endertier >= 4) {
                                //Settings

                                List<String> settingslore = itemManager.createLore(ChatColor.GOLD + "Click to edit");
                                ItemStack settings = itemManager.createItem(Material.COMPASS, 1, ChatColor.WHITE + "Settings",settingslore);

                                player.getEnderChest().setItem(0, settings);



                            }else {
                                ItemStack itemStack = new ItemStack(Material.AIR);

                                enderchest.setItem(0, itemStack);
                            }


                        }else {
                            ItemStack itemStack = new ItemStack(Material.AIR);

                            enderchest.setItem(18, itemStack);
                        }

                    }else {
                        ItemStack itemStack = new ItemStack(Material.AIR);

                        enderchest.setItem(26, itemStack);
                    }
                }else {


                    enderchest.clear();
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
                                Location teleportLocation = new Location(world, 1.50, 70.00, 1.50);

                                // Teleportiere den Spieler zur Location
                                player.teleport(teleportLocation);
                                player.sendMessage(ChatColor.GREEN + "You have been teleported to the location!");

                                player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 3f, 3f);
                            } else {
                                player.sendMessage(ChatColor.RED + "The world 'world_skyblock' could not be found!");
                            }

                        } else if (selector == 2) {
                            World world = Bukkit.getWorld("world_stone");

                            if (world != null) {
                                // Erstelle die Location für den Teleport
                                Location teleportLocation = new Location(world, 1.50, 70.00, 1.50);

                                // Teleportiere den Spieler zur Location
                                player.teleport(teleportLocation);
                                player.sendMessage(ChatColor.GREEN + "You have been teleported to the location!");

                                player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 3f, 3f);
                            } else {
                                player.sendMessage(ChatColor.RED + "The world 'world_stone' could not be found!");
                            }

                        }else if (selector == 3) {
                            World world = Bukkit.getWorld("world_whater");

                            if (world != null) {
                                // Erstelle die Location für den Teleport
                                Location teleportLocation = new Location(world, 1.50, 70.00, 1.50);

                                // Teleportiere den Spieler zur Location
                                player.teleport(teleportLocation);
                                player.sendMessage(ChatColor.GREEN + "You have been teleported to the location!");

                                player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 3f, 3f);
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
                            Location teleportLocation = new Location(world, 1.50, 70.00, 1.50);

                            // Teleportiere den Spieler zur Location
                            player.teleport(teleportLocation);
                            player.sendMessage(ChatColor.GREEN + "You have been teleported to the location!");
                        } else {
                            player.sendMessage(ChatColor.RED + "The world 'world' could not be found!");
                        }

                        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 3f, 3f);

                        event.setCancelled(true);

                    }

                    // Selector Item // Test Version
                    if (displayName.equals(ChatColor.BLUE + "SkyBlock") || displayName.equals(ChatColor.GRAY + "StoneBlock") || displayName.equals(ChatColor.AQUA + "WarherBlock")) {

                        Object selectorobj = configManager.getPlayerValue(player, "Selector");

                        int selector = (selectorobj != null) ? (int) selectorobj : 0;

                        selector = selector + 1;

                        if (selector >= 4) { // NOt 3 !!!
                            selector = 1;
                        }

                        configManager.setPlayerValue(player, "Selector", selector);

                        setSelector(player, selector);

                        player.playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_BREAK, 3f, 3f);


                        event.setCancelled(true);
                    }

                    // Stat Item
                    if (displayName.equals(ChatColor.GOLD + "Stats")) {
                        event.setCancelled(true);
                        player.sendMessage(ChatColor.BLUE + "Stats Reloaded!");
                        player.playSound(player.getLocation(), Sound.BLOCK_BARREL_OPEN, 3f, 3f);


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

                        player.getEnderChest().setItem(26, statitem);

                    }


                    // Extra EnderChest
                    if (displayName.equals(ChatColor.DARK_PURPLE + "Element EnderChest")) {
                        event.setCancelled(true);

                        // Lade die Shulkerbox aus dem Ender Chest
                        ItemStack shulkerBox = player.getEnderChest().getItem(18);
                        if (shulkerBox != null && shulkerBox.getType() == Material.WHITE_SHULKER_BOX) {
                            // Hole das BlockStateMeta der Shulkerbox
                            if (shulkerBox.getItemMeta() instanceof BlockStateMeta) {
                                BlockStateMeta blockStateMeta = (BlockStateMeta) shulkerBox.getItemMeta();
                                if (blockStateMeta.getBlockState() instanceof ShulkerBox) {
                                    ShulkerBox boxState = (ShulkerBox) blockStateMeta.getBlockState();

                                    // Erstelle ein neues Inventar mit dem Inhalt der Shulkerbox
                                    Inventory shulkerInventory = Bukkit.createInventory(null, 27, ChatColor.DARK_PURPLE + "Elements EnderChest");
                                    shulkerInventory.setContents(boxState.getInventory().getContents());

                                    // Öffne das neue Inventar für den Spieler
                                    player.openInventory(shulkerInventory);

                                    //Sound
                                    player.playSound(player.getLocation(), Sound.BLOCK_CHEST_LOCKED, 3f, 3f);
                                }
                            }
                        }
                    }

                    if (displayName.equals(ChatColor.WHITE + "Settings")) {
                        Inventory enderchest = player.getEnderChest();

                        List<String> glasslore = itemManager.createLore(ChatColor.GRAY + "²");


                        ItemStack grayglass = itemManager.createItem(Material.BLACK_STAINED_GLASS_PANE, 1, ChatColor.GRAY + "²", glasslore);



                        //Set EnderChest
                        enderchest.setItem(0, grayglass);
                        enderchest.setItem(1, grayglass);
                        enderchest.setItem(2, grayglass);
                        enderchest.setItem(3, grayglass);
                        enderchest.setItem(4, grayglass); // Set to gorl bossbar
                        enderchest.setItem(5, grayglass);
                        enderchest.setItem(6, grayglass);
                        enderchest.setItem(7, grayglass);
                        enderchest.setItem(8, grayglass);

                        enderchest.setItem(9, grayglass);
                        enderchest.setItem(17, grayglass);

                        enderchest.setItem(19, grayglass);
                        enderchest.setItem(20, grayglass);
                        enderchest.setItem(21, grayglass);
                        enderchest.setItem(22, grayglass);
                        enderchest.setItem(23, grayglass);
                        enderchest.setItem(24, grayglass);
                        enderchest.setItem(25, grayglass);
                        enderchest.setItem(26, grayglass);

                        event.setCancelled(true);

                        player.playSound(player.getLocation(), Sound.ITEM_SHIELD_BREAK, 3f, 3f);
                    }

                    if (displayName.equals(ChatColor.GRAY + "²")) {
                        event.setCancelled(true);
                    }
                }
            }

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

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getView().getTitle().equals(ChatColor.DARK_PURPLE + "Elements EnderChest")) {
            Inventory shulkerInventory = event.getInventory();
            Player player = (Player) event.getPlayer();

            // Hole die Shulkerbox aus dem Ender Chest des Spielers
            ItemStack shulkerBox = player.getEnderChest().getItem(18);

            // Überprüfe, ob es sich um eine weiße Shulkerbox handelt
            if (shulkerBox != null && shulkerBox.getType() == Material.WHITE_SHULKER_BOX) {
                // Speichere die Inhalte in den NBT-Daten der Shulkerbox
                shulkerBox = saveInventoryToShulkerBox(shulkerBox, shulkerInventory);

                // Setze die aktualisierte Shulkerbox wieder in das Ender Chest des Spielers
                player.getEnderChest().setItem(18, shulkerBox);
            }
        }
    }

    private ItemStack saveInventoryToShulkerBox(ItemStack shulkerBox, Inventory inventory) {
        // Hole das ItemMeta der Shulkerbox
        ItemMeta meta = shulkerBox.getItemMeta();
        if (meta instanceof BlockStateMeta) {
            BlockStateMeta blockStateMeta = (BlockStateMeta) meta;
            if (blockStateMeta.getBlockState() instanceof ShulkerBox) {
                ShulkerBox boxState = (ShulkerBox) blockStateMeta.getBlockState();

                // Setze die Inhalte des Inventars in die Shulkerbox
                boxState.getInventory().setContents(inventory.getContents());

                // Speichere den neuen Zustand in die Shulkerbox
                blockStateMeta.setBlockState(boxState);
                shulkerBox.setItemMeta(blockStateMeta);
            }
        }
        return shulkerBox;
    }

}
