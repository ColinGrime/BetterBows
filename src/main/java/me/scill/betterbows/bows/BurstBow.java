package me.scill.betterbows.bows;

import me.scill.betterbows.BetterBows;
import me.scill.betterbows.CustomBow;
import me.scill.betterbows.utilities.CommonUtil;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.util.Vector;

public class BurstBow extends CustomBow {

	public BurstBow(final BetterBows plugin) {
		super("burst", plugin.getConfigData().getBurstBow());
	}

	@Override
	public void activateAbility(final ProjectileHitEvent event) {
		final Location location = event.getEntity().getLocation();

		// Burst out 10 arrows in random directions!
		for (int i=0; i<10; i++) {
			final Arrow arrow = location.getWorld().spawnArrow(location, new Vector(0, 1, 0), 0, 10);
			arrow.setVelocity(location.getDirection().multiply(CommonUtil.random(0.5,-0.5))
					.add(new Vector(
							CommonUtil.random(0.1, -0.1),
							CommonUtil.random(1.0, 0.5),
							CommonUtil.random(0.1, -0.1))));
		}
	}
}
