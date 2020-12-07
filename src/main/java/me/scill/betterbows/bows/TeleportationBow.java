package me.scill.betterbows.bows;

import me.scill.betterbows.BetterBows;
import me.scill.betterbows.CustomBow;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.ProjectileHitEvent;

public class TeleportationBow extends CustomBow {

	public TeleportationBow(final BetterBows plugin) {
		super("teleportation", plugin.getConfigData().getTeleportationBow());
	}

	@Override
	public void activateAbility(final ProjectileHitEvent event) {
		final Player shooter = (Player) event.getEntity().getShooter();
		final Location arrowLocation = event.getEntity().getLocation().clone();

		// Keeps your yaw/pitch on teleport.
		arrowLocation.setYaw(shooter.getLocation().getYaw());
		arrowLocation.setPitch(shooter.getLocation().getPitch());

		// Teleports you to the arrow hit.
		shooter.teleport(arrowLocation);
	}
}
