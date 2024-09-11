package cho.info.elements.server.events;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class HubWeather implements Listener {

    @EventHandler
    public void onWeatherChangeInHub(WeatherChangeEvent event) {
        World world = event.getWorld();
        World hub = Bukkit.getWorld("world");


        if (world == Bukkit.getWorld("world")) {
            event.setCancelled(true);
            hub.setStorm(false);
            world.setThundering(false);
            world.setWeatherDuration(12000);

        }
    }

}
