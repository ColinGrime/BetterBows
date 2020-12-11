package me.scill.betterbows.bows;

import me.scill.betterbows.BetterBows;
import me.scill.betterbows.CustomBow;
import org.bukkit.Location;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ExplosiveBow extends CustomBow {

	public ExplosiveBow(final BetterBows plugin) {
		super(plugin, "explosive");
	}

	@Override
	public void activateAbility(final ProjectileHitEvent event) {
		final Location location = event.getEntity().getLocation();

		// Creates an explosion w/o breaking blocks.
		location.getWorld().createExplosion(location.getX(), location.getY(), location.getZ(), (float) 2.0, false, false);
	}
}