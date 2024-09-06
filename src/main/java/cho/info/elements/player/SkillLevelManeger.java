package cho.info.elements.player;

import cho.info.elements.configs.ConfigManager;
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

            configManager.setPlayerValue(player, "MiningXp", miningXp);
            configManager.setPlayerValue(player, "MiningLv", miningLv);
            configManager.setPlayerValue(player, "MiningMaxXp", miningMaxXp);

            event.getPlayer().sendMessage(ChatColor.DARK_AQUA + "Mining Level up to --> " + miningLv);

            var playerlocation = event.getPlayer().getLocation();

            world.spawnParticle(Particle.FIREWORK, playerlocation, 2);


        }
    }
}
