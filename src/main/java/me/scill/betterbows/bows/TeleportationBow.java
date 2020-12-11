package me.scill.betterbows.bows;

import me.scill.betterbows.BetterBows;
import me.scill.betterbows.CustomBow;
import me.scill.betterbows.utilities.CommonUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.ProjectileHitEvent;

public class TeleportationBow extends CustomBow {

	public TeleportationBow(final BetterBows plugin) {
		super(plugin, "teleportation");
	}

	@Override
	public void activateAbility(final ProjectileHitEvent event) {
		final Player shooter = (Player) event.getEntity().getShooter();
		final Location arrowLocation = CommonUtil.getAccurateProjectileHit(event.getEntity(), false);

		// Keeps your yaw/pitch on teleport.
		arrowLocation.setYaw(shooter.getLocation().getYaw());
		arrowLocation.setPitch(shooter.getLocation().getPitch());

		// Teleports you to the arrow hit.
		shooter.teleport(arrowLocation);
	}
}
