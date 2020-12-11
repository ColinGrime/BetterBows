package me.scill.betterbows.bows;

import me.scill.betterbows.BetterBows;
import me.scill.betterbows.CustomBow;
import me.scill.betterbows.utilities.CommonUtil;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class HealerBow extends CustomBow {

	private final BetterBows plugin;

	public HealerBow(final BetterBows plugin) {
		super(plugin, "healer");
		this.plugin = plugin;
	}

	@Override
	public void activateAbility(final ProjectileHitEvent event) {
		final Location location = event.getEntity().getLocation();
		final List<Location> blocks = CommonUtil.getLocationsBetween
				(location.clone().add(2,0,2), location.clone().add(-1,0,-1));

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
}
