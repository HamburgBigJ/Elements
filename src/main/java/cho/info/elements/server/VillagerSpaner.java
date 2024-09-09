package cho.info.elements.server;

import cho.info.elements.managers.ConfigManager;
import cho.info.elements.managers.ItemManager;
import cho.info.elements.managers.VariableManager;
import cho.info.elements.managers.VillagerManager;
import com.destroystokyo.paper.event.server.ServerTickStartEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class VillagerSpaner implements Listener {

    public JavaPlugin plugin;
    public ConfigManager configManager;
    public ItemManager itemManager;

    public VillagerSpaner(JavaPlugin plugin, ConfigManager configManager, ItemManager itemManager) {
        this.plugin = plugin;
        this.configManager = configManager;
        this.itemManager = itemManager;


    }

    @EventHandler
    public void onChangeTier(ServerTickStartEvent event) {
        Object stageobj = configManager.getPublicVar("Stage");

        int stage = (stageobj != null) ? (int) stageobj : 0;

    }
}
