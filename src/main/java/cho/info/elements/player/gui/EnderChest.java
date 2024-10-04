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
import org.bukkit.inventory.ItemFlag;
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


                // Reset all items
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

                // Make sure "EdderGui" is referenced correctly
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


                    //Colection
                    List<String> colectionlore = itemManager.createLore(ChatColor.WHITE + "Click!");
                    ItemStack colecion = itemManager.createItem(Material.DIAMOND_PICKAXE, 1, ChatColor.GOLD + "Collection", colectionlore);

                    ItemMeta colectionmeta = colecion.getItemMeta();

                    colectionmeta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);

                    colecion.setItemMeta(colectionmeta);

                    enderchest.setItem(13, colecion);


                    //EnderChest Tier 2
                    if (endertier >= 2){
                        //Stats Item

                        //Vars

                        int miningLv = (int) configManager.getPlayerValue(player, "MiningLv");
                        int forestingLv = (int) configManager.getPlayerValue(player, "ForestingLv");
                        int farmingLv = (int) configManager.getPlayerValue(player, "FarmingLv");



                        List<String> statitemlore = itemManager.createLore(
                                ChatColor.DARK_AQUA + "MiningLv: " + miningLv,
                                ChatColor.DARK_AQUA + "ForestingLv: " + forestingLv,
                                ChatColor.DARK_AQUA + "FarmingLv: " + farmingLv
                        );

                        ItemStack statitem = itemManager.createItem(Material.LECTERN, 1, ChatColor.GOLD + "Stats", statitemlore);

                        enderchest.setItem(26, statitem);

                        if (endertier >= 3) {

                            ItemStack item = player.getEnderChest().getItem(18); // Hole das Item im Slot 18

                            // Check if the slot is empty or the item is air
                            if (item == null || item.getType() == Material.AIR) {
                                List<String> extraenderchestlore = itemManager.createLore(ChatColor.GOLD + "Click to open EnderChest");

                                // Create a white Shulker Box with a custom name and lore
                                ItemStack extraenderchest = itemManager.createItem(Material.WHITE_SHULKER_BOX, 1, ChatColor.DARK_PURPLE + "Element EnderChest", extraenderchestlore);

                                // Set the item in slot 18 of the Ender Chest
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

        // If its Players inv
        if (inventory.equals(player.getEnderChest())) {
            if (clickedItem != null && clickedItem.hasItemMeta()) {
                ItemMeta meta = clickedItem.getItemMeta();
                if (meta != null && meta.hasDisplayName()) {
                    String displayName = meta.getDisplayName();


                    if (displayName.equals(ChatColor.GOLD + "Teleport")) {

                        Object selectorobj = configManager.getPlayerValue(player, "Selector");

                        int selector = (selectorobj != null) ? (int) selectorobj : 0;

                        if (selector == 1) {
                            World world = Bukkit.getWorld("world_skyblock");

                            if (world != null) {

                                Location teleportLocation = new Location(world, 1.50, 70.00, 1.50);


                                player.teleport(teleportLocation);
                                player.sendMessage(ChatColor.GREEN + "You have been teleported to the location!");

                                player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 3f, 3f);

                                player.setGameMode(GameMode.SURVIVAL);
                            } else {
                                player.sendMessage(ChatColor.RED + "The world 'world_skyblock' could not be found!");
                            }

                        } else if (selector == 2) {
                            World world = Bukkit.getWorld("world_stone");

                            if (world != null) {

                                Location teleportLocation = new Location(world, 1.50, 70.00, 1.50);


                                player.teleport(teleportLocation);
                                player.sendMessage(ChatColor.GREEN + "You have been teleported to the location!");

                                player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 3f, 3f);

                                player.setGameMode(GameMode.SURVIVAL);
                            } else {
                                player.sendMessage(ChatColor.RED + "The world 'world_stone' could not be found!");
                            }

                        }else if (selector == 3) {
                            World world = Bukkit.getWorld("world_whater");

                            if (world != null) {

                                Location teleportLocation = new Location(world, 1.50, 70.00, 1.50);


                                player.teleport(teleportLocation);
                                player.sendMessage(ChatColor.GREEN + "You have been teleported to the location!");

                                player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 3f, 3f);

                                player.setGameMode(GameMode.SURVIVAL);
                            } else {
                                player.sendMessage(ChatColor.RED + "The world 'world_whater' could not be found!");
                            }

                        }

                        event.setCancelled(true);
                    }


                    // Hub Item
                    if (displayName.equals(ChatColor.GOLD + "Hub")) {



                        World world = Bukkit.getWorld("world");

                        if (world != null) {

                            Location teleportLocation = new Location(world, 1.50, 70.00, 1.50);


                            player.teleport(teleportLocation);
                            player.sendMessage(ChatColor.GREEN + "You have been teleported to the location!");

                            player.setGameMode(GameMode.ADVENTURE);
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

                        int miningLv = (int) configManager.getPlayerValue(player, "MiningLv");
                        int forestingLv = (int) configManager.getPlayerValue(player, "ForestingLv");
                        int farmingLv = (int) configManager.getPlayerValue(player, "FarmingLv");



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

                        // Load Shulker Box
                        ItemStack shulkerBox = player.getEnderChest().getItem(18);
                        if (shulkerBox != null && shulkerBox.getType() == Material.WHITE_SHULKER_BOX) {
                            // Hole das BlockStateMeta der Shulkerbox
                            if (shulkerBox.getItemMeta() instanceof BlockStateMeta) {
                                BlockStateMeta blockStateMeta = (BlockStateMeta) shulkerBox.getItemMeta();
                                if (blockStateMeta.getBlockState() instanceof ShulkerBox) {
                                    ShulkerBox boxState = (ShulkerBox) blockStateMeta.getBlockState();


                                    Inventory shulkerInventory = Bukkit.createInventory(null, 27, ChatColor.DARK_PURPLE + "Elements EnderChest");
                                    shulkerInventory.setContents(boxState.getInventory().getContents());


                                    player.openInventory(shulkerInventory);

                                    //Sound
                                    player.playSound(player.getLocation(), Sound.BLOCK_CHEST_LOCKED, 3f, 3f);
                                }
                            }
                        }
                    }

                    if (displayName.equals(ChatColor.WHITE + "Settings")) {
                        Inventory enderchest = player.getEnderChest();

                        List<String> glasslore = itemManager.createLore(ChatColor.GRAY + " ");


                        ItemStack grayglass = itemManager.createItem(Material.BLACK_STAINED_GLASS_PANE, 1, ChatColor.GRAY + " ", glasslore);
                        ItemStack air = new ItemStack(Material.AIR);



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
                        enderchest.setItem(13, air); // only air slot
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

                    if (displayName.equals(ChatColor.GRAY + " ")) {
                        event.setCancelled(true);
                    }

                    if (displayName.equals(ChatColor.GOLD + "Collection")) {
                        event.setCancelled(true);

                        Inventory collectionInv = Bukkit.createInventory(null, 27, ChatColor.DARK_PURPLE + "Collection");

                        // Create gray glass pane item for filling the slots
                        List<String> glasslore = itemManager.createLore(ChatColor.GRAY + " ");
                        ItemStack grayglass = itemManager.createItem(Material.BLACK_STAINED_GLASS_PANE, 1, ChatColor.GRAY + " ", glasslore);

                        // Create the cobblestone item
                        List<String> cobblestonelore = itemManager.createLore(ChatColor.GRAY + "Click");
                        ItemStack cobblestone = itemManager.createItem(Material.COBBLESTONE, 1, ChatColor.GOLD + "Cobblestone", cobblestonelore);

                        // Create the oak log item
                        List<String> oaklore = itemManager.createLore(ChatColor.GRAY + "Click");
                        ItemStack oak = itemManager.createItem(Material.OAK_LOG, 1, ChatColor.GOLD + "Oak", oaklore);

                        // Create the wheat item
                        List<String> wheetlore = itemManager.createLore(ChatColor.GRAY + "Click");
                        ItemStack wheat = itemManager.createItem(Material.WHEAT, 1, ChatColor.GOLD + "Wheat", wheetlore);

                        // Create the amethyst cluster item
                        List<String> amethystlore = itemManager.createLore(ChatColor.GRAY + "Click");
                        ItemStack amethyst = itemManager.createItem(Material.AMETHYST_CLUSTER, 1, ChatColor.GOLD + "Amethyst", amethystlore);

                        // Create Eco Sharts Collection maby error
                        List<String> ecoshartlore = itemManager.createLore(ChatColor.GRAY + "Click");
                        ItemStack ecoshart = itemManager.createItem(Material.ECHO_SHARD, 1, ChatColor.GOLD + "Eco Shard", ecoshartlore);

                        //Create kelp Coellection
                        List<String> kelplore = itemManager.createLore(ChatColor.GRAY + "Click");
                        ItemStack kelp = itemManager.createItem(Material.KELP, 1, ChatColor.GOLD + "Kelp", kelplore);

                        //Create Potato Collection
                        List<String> potatolore = itemManager.createLore(ChatColor.GRAY + "Click");
                        ItemStack potato = itemManager.createItem(Material.POTATO, 1, ChatColor.GOLD + "Potato", potatolore);

                        //Create Carrot Collection
                        List<String> carrotlore = itemManager.createLore(ChatColor.GRAY + "Click");
                        ItemStack carrot = itemManager.createItem(Material.CARROT, 1, ChatColor.GOLD + "Carrot", carrotlore);

                        //Create Apple Collection
                        List<String> applelore = itemManager.createLore(ChatColor.GRAY + "Click");
                        ItemStack apple = itemManager.createItem(Material.APPLE, 1, ChatColor.GOLD + "Apple", applelore);

                        // Set the items in the inventory to match the layout in the image
                        collectionInv.setItem(9, cobblestone); // Cobblestone in the first slot
                        collectionInv.setItem(10, oak); // Oak log in the second row, first slot
                        collectionInv.setItem(11, wheat); // Wheat in the second row, second slot
                        collectionInv.setItem(12, amethyst); // Amethyst in the second row, third slot
                        collectionInv.setItem(13, ecoshart); // Eco shart Collection
                        collectionInv.setItem(14, kelp); // Kelp Collection
                        collectionInv.setItem(15, potato); // Potato Collection
                        collectionInv.setItem(16, carrot); // Carrot Collection
                        collectionInv.setItem(17, apple); // Apple Collection

                        // Fill the rest of the slots with gray stained glass pane
                        for (int i = 0; i < 27; i++) {
                            if (collectionInv.getItem(i) == null) {
                                collectionInv.setItem(i, grayglass);
                            }
                        }

                        // Open the custom inventory for the player
                        player.openInventory(collectionInv);
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


            ItemStack shulkerBox = player.getEnderChest().getItem(18);


            if (shulkerBox != null && shulkerBox.getType() == Material.WHITE_SHULKER_BOX) {

                shulkerBox = saveInventoryToShulkerBox(shulkerBox, shulkerInventory);


                player.getEnderChest().setItem(18, shulkerBox);
            }
        }
    }

    private ItemStack saveInventoryToShulkerBox(ItemStack shulkerBox, Inventory inventory) {

        ItemMeta meta = shulkerBox.getItemMeta();
        if (meta instanceof BlockStateMeta) {
            BlockStateMeta blockStateMeta = (BlockStateMeta) meta;
            if (blockStateMeta.getBlockState() instanceof ShulkerBox) {
                ShulkerBox boxState = (ShulkerBox) blockStateMeta.getBlockState();


                boxState.getInventory().setContents(inventory.getContents());


                blockStateMeta.setBlockState(boxState);
                shulkerBox.setItemMeta(blockStateMeta);
            }
        }
        return shulkerBox;
    }

}
