package me.scill.betterbows.bows;

import me.scill.betterbows.BetterBows;
import me.scill.betterbows.CustomBow;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ZeusBow extends CustomBow {

	public ZeusBow(final BetterBows plugin) {
		super("zeus", plugin.getConfigData().getZeusBow());
	}

	@Override
	public void activateAbility(final ProjectileHitEvent event) {
		// Strikes lightning on arrow hit.
		event.getEntity().getWorld().strikeLightning(event.getEntity().getLocation());
	}
}
