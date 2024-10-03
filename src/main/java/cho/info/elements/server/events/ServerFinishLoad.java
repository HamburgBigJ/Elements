package cho.info.elements.server.events;

import cho.info.elements.Elements;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ServerFinishLoad implements Listener {

    public Elements elements;
    public JavaPlugin plugin;

    public ServerFinishLoad(Elements elements, JavaPlugin plugin) {
        this.elements = elements;
        this.plugin = plugin;
    }

    @EventHandler
    public void onServerLoad(ServerLoadEvent event) {
        plugin.getLogger().info("Server full load  Finish");
        elements.runAfterServerLoad();

    }
}
