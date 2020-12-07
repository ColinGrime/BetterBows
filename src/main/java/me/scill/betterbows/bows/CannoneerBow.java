package me.scill.betterbows.bows;

import me.scill.betterbows.BetterBows;
import me.scill.betterbows.CustomBow;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.ProjectileHitEvent;

public class CannoneerBow extends CustomBow {

	public CannoneerBow(final BetterBows plugin) {
		super("cannoneer", plugin.getConfigData().getCannoneerBow());
	}

	@Override
	public void activateAbility(final ProjectileHitEvent event) {
		// Spawn 3 lit TNT from the arrow hit.
		for (int i=0; i<3; i++)
			event.getEntity().getWorld().spawnEntity(event.getEntity().getLocation(), EntityType.PRIMED_TNT);
	}
}
