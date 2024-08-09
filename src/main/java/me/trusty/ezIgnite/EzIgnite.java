package me.trusty.ezIgnite;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class EzIgnite extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        // Check if the player right-clicked a block
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            // Get the block that was clicked
            //Material blockType = event.getClickedBlock().getType();
            Material blockType = Objects.requireNonNull(event.getClickedBlock()).getType();

            // Check if the block is TNT
            if (blockType == Material.TNT) {
                // Get the player who clicked the block
                Player player = event.getPlayer();
                Player p = event.getPlayer();
                // Check if the player has an empty hand
                ItemStack handItem = player.getInventory().getItemInMainHand();
                if (handItem.getType() == Material.AIR && p.hasPermission("ezignite.light")){
                    // Ignite the TNT
                    org.bukkit.Location l = event.getClickedBlock().getLocation();
                    event.getClickedBlock().setType(Material.AIR);
                    TNTPrimed tnt = (TNTPrimed) l.getWorld().spawnEntity(l.add(0.5,0,0.5), EntityType.TNT);
                } else {
                    p.sendMessage("You do not have permission.");
                }
                }
            }
        }
    @Override
    public void onDisable() {
        //section for code here
    }
}
