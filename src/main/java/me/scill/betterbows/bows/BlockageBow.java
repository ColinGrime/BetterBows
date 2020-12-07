package me.scill.betterbows.bows;

import me.scill.betterbows.BetterBows;
import me.scill.betterbows.CustomBow;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class BlockageBow extends CustomBow {

	private final BetterBows plugin;

	public BlockageBow(final BetterBows plugin) {
		super("blockage", plugin.getConfigData().getBlockageBow());
		this.plugin = plugin;
	}

	@Deprecated
	@Override
	public void activateAbility(final ProjectileHitEvent event) {
		final Location location = event.getEntity().getLocation().clone();
		for (Entity entity :  location.getWorld().getNearbyEntities(location,50, 50, 50)) {
			if (entity instanceof Player) {
				// Create a fake red wall.
				((Player) entity).sendBlockChange(location, Material.STAINED_GLASS, (byte) 14);
				((Player) entity).sendBlockChange(location.clone().add(0, 1, 0), Material.STAINED_GLASS, (byte) 14);
			}
		}

		// Show the real blocks again!
		new BukkitRunnable() {
			@Override
			public void run() {
				location.getBlock().getState().update();
				location.clone().add(0, 1, 0).getBlock().getState().update();
			}
		}.runTaskLater(plugin, 200L);
	}
}
