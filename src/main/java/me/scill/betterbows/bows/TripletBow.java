package me.scill.betterbows.bows;

import me.scill.betterbows.BetterBows;
import me.scill.betterbows.CustomBow;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.event.entity.EntityShootBowEvent;

public class TripletBow extends CustomBow {

	public TripletBow(final BetterBows plugin) {
		super("triplet", plugin.getConfigData().getTripletBow());
	}

	@Override
	public void activateAbility(final EntityShootBowEvent event) {
		// Fires two arrows: left and right.
		fireArrow(event, 10);
		fireArrow(event, -10);
	}

	/**
	 * Sets the new yaw & launches the additional arrow.
	 *
	 * @param event the event
	 * @param yaw the new yaw value
	 */
	private void fireArrow(final EntityShootBowEvent event, final int yaw) {
		final Location arrow = event.getEntity().getLocation().clone();
		arrow.setYaw(arrow.getYaw() + yaw);
		event.getEntity().launchProjectile(Arrow.class, arrow.getDirection());
	}
}
