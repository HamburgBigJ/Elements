package cho.info.elements.player.skills;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

import static java.rmi.server.LogStream.log;

public class MiningSkill implements Listener {

    private static final Logger log = LoggerFactory.getLogger(MiningSkill.class);
    private JavaPlugin plugin;
    private final Set<Material> blockedMaterials;

    public MiningSkill(JavaPlugin plugin){
        this.plugin = plugin;
        this.blockedMaterials = new HashSet<>();

        this.blockedMaterials.add(Material.STONE);
        this.blockedMaterials.add(Material.BASALT);
        this.blockedMaterials.add(Material.DEEPSLATE);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Material blockType = event.getBlock().getType();


        if (blockedMaterials.contains(blockType)) {
            event.getPlayer().sendMessage("Blocklist");

        }
    }


}
