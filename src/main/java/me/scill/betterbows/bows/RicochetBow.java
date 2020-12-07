package me.scill.betterbows.bows;

import me.scill.betterbows.BetterBows;
import me.scill.betterbows.CustomBow;
import me.scill.betterbows.utilities.CommonUtil;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.util.Vector;

public class RicochetBow extends CustomBow {

	public RicochetBow(final BetterBows plugin) {
		super("ricochet", plugin.getConfigData().getRicochetBow());
	}

	@Override
	public void activateAbility(final ProjectileHitEvent event) {
		final Location location = event.getEntity().getLocation();

		// Spawns a single arrow in a random direction from the arrow hit.
		final Arrow arrow = location.getWorld().spawnArrow(location, new Vector(0, 1, 0), 0, 20);
		arrow.setVelocity(location.getDirection().multiply(CommonUtil.random(1.0,-1.0))
				.add(new Vector(
						CommonUtil.random(0.1, -0.1),
						CommonUtil.random(1.0, 0.5),
						CommonUtil.random(0.1, -0.1))));
		event.getEntity().remove();
	}
}
