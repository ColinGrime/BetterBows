package me.scill.betterbows.bows;

import me.scill.betterbows.BetterBows;
import me.scill.betterbows.CustomBow;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class HealerBow extends CustomBow {

	private final BetterBows plugin;

	public HealerBow(final BetterBows plugin) {
		super("healer", plugin.getConfigData().getHealerBow());
		this.plugin = plugin;
	}

	@Override
	public void activateAbility(final ProjectileHitEvent event) {
		final Location location = event.getEntity().getLocation();
		final List<Location> blocks = getBlocksBetween(location.clone().add(2,0,2),
													   location.clone().add(-2,1,-2));

		final int[] timer = {0};
		new BukkitRunnable() {
			@Override
			public void run() {
				// After 10 seconds, cancel.
				if (timer[0]++ > 40)
					cancel();

				// Plays a heart effect on the arror hit.
				for (Location location : blocks)
					location.getWorld().playEffect(location, Effect.HEART, 1);

				// Gives the player regeneration / saturation over the radius.
				for (Entity entity : location.getWorld().getNearbyEntities(location, 2, 2, 2)) {
					if (entity == event.getEntity().getShooter()) {
						((Player) entity).addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20, 2));
						((Player) entity).addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 1, 0));
						break;
					}
				}
			}
		}.runTaskTimer(plugin,0L,5L);
	}

	/**
	 * Retrieves all locations between two locations.
	 *
	 * @param location1 a location
	 * @param location2 another location
	 * @return all blocks between two locations
	 */
	private List<Location> getBlocksBetween(final Location location1, final Location location2){
		int lowX = Math.min(location1.getBlockX(), location2.getBlockX());
		int lowY = Math.min(location1.getBlockY(), location2.getBlockY());
		int lowZ = Math.min(location1.getBlockZ(), location2.getBlockZ());

		final List<Location> locations = new ArrayList<>();
		for (int blockX = 0; blockX < Math.abs(location1.getBlockX() - location2.getBlockX()); blockX++){
			for (int blockY = 0; blockY < Math.abs(location1.getBlockY() - location2.getBlockY()); blockY++){
				for (int blockZ = 0; blockZ < Math.abs(location1.getBlockZ() - location2.getBlockZ()); blockZ++){
					locations.add(new Location(location1.getWorld(),lowX+blockX, lowY+blockY, lowZ+blockZ));
				}
			}
		}
		return locations;
	}
}
