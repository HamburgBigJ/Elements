package cho.info.elements.player;

import cho.info.elements.configs.ConfigManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class SkillLevelManeger implements Listener {

    private JavaPlugin plugin;
    public ConfigManager configManager;

    public SkillLevelManeger(JavaPlugin plugin, ConfigManager configManager){
        this.plugin = plugin;
        this.configManager = configManager;

    }

    @EventHandler
    public void onMiningSkillManeger(BlockBreakEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();

        Object miningxpObj = configManager.getPlayerValue(player, "MiningXp");
        Object miningLvObj = configManager.getPlayerValue(player, "MiningLv");
        Object miningMaxXpObj = configManager.getPlayerValue(player, "MiningMaxXp");

        int miningXp = (miningxpObj != null) ? (int) miningxpObj : 0;
        int miningLv = (miningLvObj != null) ? (int) miningLvObj : 0;
        int miningMaxXp = (miningMaxXpObj != null) ? (int) miningMaxXpObj : 0;

        if (miningXp >= miningMaxXp) {
            miningXp = 0;
            miningMaxXp = miningMaxXp + 100;
            miningLv = miningLv + 1;

            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
                    ChatColor.DARK_AQUA + "Mining XP: " + miningXp + " / " + miningMaxXp
            ));

            configManager.setPlayerValue(player, "MiningXp", miningXp);
            configManager.setPlayerValue(player, "MiningLv", miningLv);
            configManager.setPlayerValue(player, "MiningMaxXp", miningMaxXp);

            event.getPlayer().sendMessage(ChatColor.DARK_AQUA + "Mining Level up to --> " + miningLv);

            var playerlocation = event.getPlayer().getLocation();

            world.spawnParticle(Particle.FIREWORK, playerlocation, 5);


        }
    }

    @EventHandler
    public void onForestingSkillManeger(BlockBreakEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();

        Object forestingXpObj = configManager.getPlayerValue(player, "ForestingXp");
        Object forestingMacXpObj = configManager.getPlayerValue(player, "ForestingMaxXp");
        Object forestingLvObj = configManager.getPlayerValue(player, "ForestingLv");

        int forestingXp = (forestingXpObj != null) ? (int) forestingXpObj : 0;
        int forestingMaxXp = (forestingMacXpObj != null) ? (int) forestingMacXpObj : 0;
        int forestingLv = (forestingLvObj != null) ? (int) forestingLvObj : 0;

        if (forestingXp >= forestingMaxXp) {
            forestingXp = 0;
            forestingMaxXp = forestingMaxXp + 100;
            forestingLv = forestingLv + 1;

            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
                    ChatColor.DARK_AQUA + "Farming XP: " + forestingXp  + " / " + forestingMaxXp
            ));

            configManager.setPlayerValue(player, "ForestingXp", forestingXp);
            configManager.setPlayerValue(player, "ForestingMaxXp", forestingMaxXp);
            configManager.setPlayerValue(player, "ForestingLv", forestingLv);

            event.getPlayer().sendMessage(ChatColor.DARK_AQUA + "Foresting Level up to --> " + forestingLv);

            var playerlocation = event.getPlayer().getLocation();

            world.spawnParticle(Particle.FIREWORK, playerlocation, 5);
        }
    }
}
