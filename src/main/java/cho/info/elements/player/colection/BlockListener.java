package cho.info.elements.player.colection;

import cho.info.elements.managers.ConfigManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.HashSet;
import java.util.Set;

public class BlockListener implements Listener {
    public ConfigManager configManager;


    public BlockListener(ConfigManager configManager){
        this.configManager = configManager;

    }

    @EventHandler
    public void onBreakBlock(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (block.getType().equals(Material.COBBLESTONE)) {
            Object cobblestonObj = configManager.getPlayerValue(player, "cobblestone");
            int cobblestone = (cobblestonObj != null) ? (int) cobblestonObj : 0;
            
            cobblestone = cobblestone + 1;
            
            configManager.setPlayerValue(player, "cobblestone", cobblestone);
        } else if (block.getType().equals(Material.WHEAT)) {
            if (block.getBlockData() instanceof Ageable){
                Object wheeatObj = configManager.getPlayerValue(player, "Wheat");
                int wheet = (wheeatObj != null) ? (int) wheeatObj : 0;
                
                wheet = wheet + 1;
                
                configManager.setPlayerValue(player, "Wheat", wheet);
            }
        } else if (block.getType().equals(Material.OAK_LOG)) {
            Object oakObj = configManager.getPlayerValue(player, "Oak");
            int oak = (oakObj != null) ? (int) oakObj : 0;
            
            oak = oak + 1;
            
            configManager.setPlayerValue(player, "Oak", oak);
            
        } else if (block.getType().equals(Material.AMETHYST_CLUSTER)) {
            Object amaObj = configManager.getPlayerValue(player, "Amethyst");
            int ama = (amaObj != null) ? (int) amaObj : 0;

            ama = ama + 1;

            configManager.setPlayerValue(player, "Amethyst", ama);
            
        }

        //More COllection
    }
}
