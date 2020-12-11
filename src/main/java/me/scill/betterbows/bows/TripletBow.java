package me.scill.betterbows.bows;

import me.scill.betterbows.BetterBows;
import me.scill.betterbows.CustomBow;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.util.Vector;

public class TripletBow extends CustomBow {

	public TripletBow(final BetterBows plugin) {
		super(plugin, "triplet");
	}

	@Override
	public void activateAbility(final EntityShootBowEvent event) {
		// Fires two arrows: left and right.
		fireArrow(event, 3);
		fireArrow(event, -3);
	}

	/**
	 * Sets the new yaw & launches the additional arrow.
	 *
	 * @param event the event
	 * @param yaw the new yaw value
	 */
	private void fireArrow(final EntityShootBowEvent event, final int yaw) {
		final Location arrowLocation = event.getEntity().getLocation().clone();
		arrowLocation.setYaw(arrowLocation.getYaw() + yaw);

		final Vector velocity = arrowLocation.getDirection();
		final double speed = event.getProjectile().getVelocity().length();

		event.getEntity().launchProjectile(Arrow.class, velocity.multiply(speed));
	}
}
