package cho.info.elements.managers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BossBar;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BarFlag;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class MobManager implements Listener {

    private final JavaPlugin plugin;
    private final HashMap<UUID, BossBar> mobBossBars = new HashMap<>();

    public MobManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    // Method to spawn a custom mob using only Location
    public LivingEntity spawnCustomMob(Location location, EntityType type, String customName, BossBar bossBar) {
        // Ensure the world is valid
        if (location.getWorld() == null) {
            throw new IllegalArgumentException("Location must have a valid world.");
        }

        LivingEntity mob = (LivingEntity) location.getWorld().spawnEntity(location, type);
        mob.setCustomName(customName);
        mob.setCustomNameVisible(true);

        // Update BossBar for players in range
        updateBossBarForPlayers(mob, bossBar);

        // Store the BossBar in the map
        mobBossBars.put(mob.getUniqueId(), bossBar);

        // Start task to update boss bar health
        new BukkitRunnable() {
            @Override
            public void run() {
                if (mob.isDead()) {
                    // Remove boss bar when mob dies
                    BossBar bar = mobBossBars.remove(mob.getUniqueId());
                    if (bar != null) {
                        for (Player player : bar.getPlayers()) {
                            bar.removePlayer(player);
                        }
                        bar.removeAll();
                    }
                    cancel();
                } else {
                    // Update boss bar health
                    BossBar bar = mobBossBars.get(mob.getUniqueId());
                    if (bar != null) {
                        double healthPercentage = mob.getHealth() / mob.getMaxHealth();
                        bar.setProgress(healthPercentage);

                        // Update BossBar for players in range
                        updateBossBarForPlayers(mob, bar);
                    }
                }
            }
        }.runTaskTimer(plugin, 0L, 20L); // Update every second

        return mob;
    }

    private void updateBossBarForPlayers(LivingEntity mob, BossBar bossBar) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getWorld().equals(mob.getWorld()) && player.getLocation().distanceSquared(mob.getLocation()) <= 400) { // 400 = 20^2
                if (!bossBar.getPlayers().contains(player)) {
                    bossBar.addPlayer(player);
                }
            } else {
                if (bossBar.getPlayers().contains(player)) {
                    bossBar.removePlayer(player);
                }
            }
        }
    }

    // Method to drop custom items
    public void setCustomDrops(LivingEntity mob, ItemStack... drops) {
        for (ItemStack drop : drops) {
            mob.getWorld().dropItemNaturally(mob.getLocation(), drop);
        }
    }

    // Method to create a custom BossBar
    public BossBar createBossBar(String title, double progress) {
        BossBar bossBar = Bukkit.createBossBar(title, BarColor.RED, BarStyle.SOLID, BarFlag.CREATE_FOG);
        bossBar.setProgress(progress);
        return bossBar;
    }

    // Event to update boss bar on mob damage
    @EventHandler
    public void onBossBarUpdate(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof LivingEntity)) return;

        LivingEntity mob = (LivingEntity) event.getEntity();
        BossBar bossBar = mobBossBars.get(mob.getUniqueId());
        if (bossBar != null) {
            double healthPercentage = mob.getHealth() / mob.getMaxHealth();
            bossBar.setProgress(healthPercentage);
        }
    }
}
