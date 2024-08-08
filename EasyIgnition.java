// Import the necessary Bukkit classes
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class EasyIgnition extends JavaPlugin implements Listener {

    // Define the permission node
    private static final String PERMISSION_NODE = "easyignition.use";

    // Define the toggle configuration key
    private static final String TOGGLE_KEY = "easy-ignition.enabled";
    

    private boolean toggleState = true;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);

        // Load the configuration file
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "config.yml"));

        // Load the toggle state from the configuration file
        toggleState = config.getBoolean(TOGGLE_KEY, true);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        // Check if the toggle is enabled
        if (toggleState) {
            // Check if the player right-clicked a block
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                // Get the block that was clicked
                Material blockType = event.getClickedBlock().getType();

                // Check if the block is TNT
                if (blockType == Material.TNT) {
                    // Get the player who clicked the block
                    Player player = event.getPlayer();

                    // Check if the player has the permission to use easy ignition
                    if (player.hasPermission(PERMISSION_NODE)) {
                        // Check if the player has an empty hand
                        ItemStack handItem = player.getInventory().getItemInMainHand();
                        if (handItem == null || handItem.getType() == Material.AIR) {
                            // Ignite the TNT
                            event.getClickedBlock().ignite();
                        }
                    }
                }
            }
        }
    }

    // Add a command to toggle the easy ignition feature
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("toggleeasyignition")) {
            if (sender.hasPermission(PERMISSION_NODE)) {
                toggleState = !toggleState;
                sender.sendMessage("Easy ignition " + (toggleState ? "enabled" : "disabled"));

                // Save the toggle state to the configuration file
                FileConfiguration config = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "config.yml"));
                config.set(TOGGLE_KEY, toggleState);
                try {
                    config.save(new File(getDataFolder(), "config.yml"));
                } catch (IOException e) {
                    getLogger().severe("Error saving configuration file: " + e.getMessage());
                }

                return true;
            } else {
                sender.sendMessage("You don't have permission to toggle easy ignition.");
                return true;
            }
        }
        return false;
    }
}