package cho.info.elements.server.servergoals;

import cho.info.elements.Elements;
import cho.info.elements.managers.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Barrel;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CheckBarrel implements Listener {

    private final Elements plugin;
    public ConfigManager configManager;

    // Hardcodierte Position des Fasses
    private final Location barrelLocation = new Location(Bukkit.getWorld("world"), 8, 70, -8); // Ändere die Koordinaten nach Bedarf
    private final Location signLocation = new Location(Bukkit.getWorld("world"), 8, 71, -8); // Position für das Schild


    public CheckBarrel(Elements plugin, ConfigManager configManager) {
        this.plugin = plugin;
        this.configManager = configManager; // Stelle sicher, dass configManager initialisiert ist
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        // Überprüfen, ob der Spieler mit einem Barrel interagiert
        if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.BARREL && event.getClickedBlock().getLocation().equals(barrelLocation)) {
            int stage = (int) configManager.getPublicVar("Stage"); // Hole die aktuelle Stage aus der Konfiguration
            checkBarrel(stage);
        }
    }

    private void checkBarrel(int stage) {
        if (barrelLocation.getBlock().getType() == Material.BARREL) {
            Barrel barrel = (Barrel) barrelLocation.getBlock().getState();
            ItemStack[] contents = barrel.getInventory().getContents();

            // Zähle die Anzahl der Items im Barrel
            int itemCount = 0;
            boolean hasSpecificItem = false;

            // Überprüfen, was je nach Stage geschehen soll
            switch (stage) {
                case 1:
                    // Stage 1: Zähle die Items und überprüfe auf DEEPSLATE
                    for (ItemStack item : contents) {
                        if (item != null) {
                            itemCount += item.getAmount(); // Zähle die Menge jedes Items
                            if (item.getType() == Material.DEEPSLATE) {
                                hasSpecificItem = true;
                                updateGoal(itemCount, stage);
                            }
                        }
                    }
                    break;
                case 2:
                    // Stage 2: Überprüfe auf GOLDEN_APPLE
                    for (ItemStack item : contents) {
                        if (item != null) {
                            itemCount += item.getAmount();
                            if (item.getType() == Material.GOLDEN_APPLE) {
                                hasSpecificItem = true;
                                updateGoal(itemCount, stage);
                            }
                        }
                    }
                    break;
                case 3:
                    // Stage 3: Überprüfe auf DIAMOND
                    for (ItemStack item : contents) {
                        if (item != null) {
                            itemCount += item.getAmount();
                            if (item.getType() == Material.DIAMOND) {
                                hasSpecificItem = true;
                                updateGoal(itemCount, stage);
                            }
                        }
                    }
                    break;
                default:
                    plugin.getLogger().info("Unbekannte Stage: " + stage);
                    return; // Beende die Methode, wenn die Stage unbekannt ist
            }

            // Protokolliere die Anzahl der Items und das Vorhandensein eines bestimmten Gegenstands
            plugin.getLogger().info("Das Fass an der Position " + barrelLocation + " enthält " + itemCount + " Items.");
            if (hasSpecificItem) {
                plugin.getLogger().info("Das Fass an der Position " + barrelLocation + " enthält den spezifischen Gegenstand.");
                createSign("Items vorhanden: " + itemCount); // Schild erstellen
            } else {
                plugin.getLogger().info("Das Fass an der Position " + barrelLocation + " enthält den spezifischen Gegenstand nicht.");
                createSign("Keine Items vorhanden."); // Schild erstellen
            }
        } else {
            plugin.getLogger().info("An der Position " + barrelLocation + " befindet sich kein Fass.");
        }
    }

    private void createSign(String message) {
        if (signLocation.getBlock().getType() == Material.OAK_WALL_SIGN || signLocation.getBlock().getType() == Material.OAK_SIGN) {
            signLocation.getBlock().setType(Material.AIR); // Lösche das bestehende Schild, wenn vorhanden
        }

        Sign sign = (Sign) signLocation.getBlock().getState();
        sign.setLine(0, "Info:");
        sign.setLine(1, message);
        sign.update();
        plugin.getLogger().info("Schild aktualisiert: " + message);
    }

    private void updateGoal(int itemCount, int stage) {
        int goal = (int) configManager.getPublicVar("Goal");
        int maxGoal = (int) configManager.getPublicVar("GoalMax");

        goal += itemCount; // Erhöhe das Ziel um die Anzahl der Items

        configManager.setPublicVar("Goal", goal);

        if (goal >= maxGoal) {
            // Erhöhe die Stage, wenn das Ziel erreicht ist
            if (stage < 3) {
                stage++;
                configManager.setPublicVar("Stage", stage);
                plugin.getServer().broadcastMessage("Das Ziel wurde erreicht! Jetzt in Stage " + stage);
                configManager.setPublicVar("MaxGoal", (1000 * stage)); // Setze das neue Ziel für die nächste Stage
                plugin.spawnvillager(); // Spawne einen Dorfbewohner
                plugin.getLogger().info("Update Villager");
            } else {
                plugin.getServer().broadcastMessage("Alle Ziele wurden erreicht!");
            }
        }
    }
}
