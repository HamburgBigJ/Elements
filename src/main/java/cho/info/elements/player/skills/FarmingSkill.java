package cho.info.elements.player.skills;

import cho.info.elements.managers.ConfigManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public class FarmingSkill implements Listener {
    public JavaPlugin plugin;
    public ConfigManager configManager;
    private final Set<Material> Materials;
    private final Set<Material> FixedMaterials; // Fixed spelling of "Materials"

    public FarmingSkill(JavaPlugin plugin, ConfigManager configManager) {
        this.plugin = plugin;
        this.configManager = configManager;
        this.Materials = new HashSet<>();
        this.FixedMaterials = new HashSet<>();

        // Add materials that are ageable and can be harvested
        this.Materials.add(Material.SWEET_BERRY_BUSH);
        this.Materials.add(Material.WHEAT);
        //this.Materials.add(Material.PUMPKIN); // Do not know how to fix
        //this.Materials.add(Material.MELON);
        this.Materials.add(Material.BEETROOTS);
        this.Materials.add(Material.PITCHER_CROP);
        this.Materials.add(Material.CARROTS);
        this.Materials.add(Material.POTATOES);
        this.Materials.add(Material.COCOA);

        // Add materials that are not ageable and have fixed harvesting properties
        this.FixedMaterials.add(Material.PUMPKIN);
        this.FixedMaterials.add(Material.MELON);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Material blockType = event.getBlock().getType();

        if (Materials.contains(blockType)) { // Check if the block is in the list of ageable materials

            if (event.getBlock().getBlockData() instanceof Ageable) {
                Ageable ageable = (Ageable) event.getBlock().getBlockData();
                int maxAge = ageable.getMaximumAge();
                int currentAge = ageable.getAge();

                if (currentAge == maxAge) { // Only award XP if the block is fully grown

                    Player player = event.getPlayer();

                    Object farmingXpObj = configManager.getPlayerValue(player, "FarmingXp");
                    Object farmingMaxXpObj = configManager.getPlayerValue(player, "FarmingMaxXp");

                    int farmingXp = (farmingXpObj != null) ? (int) farmingXpObj : 0;
                    int farmingMaxXp = (farmingMaxXpObj != null) ? (int) farmingMaxXpObj : 0;

                    farmingXp = farmingXp + 1;

                    configManager.setPlayerValue(player, "FarmingXp", farmingXp);

                    // Send a message to the player with their updated Farming XP
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
                            ChatColor.DARK_AQUA + "✂ Farming XP: " + farmingXp + " / " + farmingMaxXp
                    ));

                    Object basexpobj = configManager.getPlayerValue(player, "BaseXp");
                    Object xpmultipliorobj = configManager.getPlayerValue(player, "XpMultiplier");

                    int basexp = (basexpobj != null) ? (int) basexpobj : 0;
                    int xpmultiplior = (xpmultipliorobj != null) ? (int) xpmultipliorobj : 0;

                    int bonusxp = checkHomeDimension(player);

                    player.giveExp((basexp * xpmultiplior) + bonusxp); // Apply multiplier

                }
            }
        } else if (FixedMaterials.contains(blockType)) { // Check if the block is in the list of fixed materials

            Player player = event.getPlayer();

            Object farmingXpObj = configManager.getPlayerValue(player, "FarmingXp");
            Object farmingMaxXpObj = configManager.getPlayerValue(player, "FarmingMaxXp");

            int farmingXp = (farmingXpObj != null) ? (int) farmingXpObj : 0;
            int farmingMaxXp = (farmingMaxXpObj != null) ? (int) farmingMaxXpObj : 0;

            farmingXp = farmingXp + 1;

            configManager.setPlayerValue(player, "FarmingXp", farmingXp);

            // Send a message to the player with their updated Farming XP
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
                    ChatColor.DARK_AQUA + "✂ Farming XP: " + farmingXp + " / " + farmingMaxXp
            ));


            Object basexpobj = configManager.getPlayerValue(player, "BaseXp");
            Object xpmultipliorobj = configManager.getPlayerValue(player, "XpMultiplier");

            int basexp = (basexpobj != null) ? (int) basexpobj : 0;
            int xpmultiplior = (xpmultipliorobj != null) ? (int) xpmultipliorobj : 0;

            int bonusxp = checkHomeDimension(player);

            player.giveExp((basexp * xpmultiplior) + bonusxp); // Apply multiplier
        }
    }

    private int checkHomeDimension(Player player) {

        Object homedimensionobj = configManager.getPlayerValue(player, "HomeDimension");
        int homedimension = (homedimensionobj != null) ? (int) homedimensionobj : 0; // 1 = Skyblock, 2 = StoneBlock, 3 = WaterBlock


        String currentWorldName = player.getWorld().getName();


        int currentDimension = getDimensionFromWorldName(currentWorldName);


        if (currentDimension == homedimension) {
            return 2;
        }
        return 0;
    }

    private int getDimensionFromWorldName(String worldName) {

        if (worldName.equals("world_skyblock")) {
            return 1; // Skyblock
        } else if (worldName.equals("world_stone")) {
            return 2; // StoneBlock
        } else if (worldName.equals("world_whater")) {
            return 3; // WaterBlock
        } else {
            return 0;
        }
    }
}
