package me.scill.betterbows.bows;

import me.scill.betterbows.BetterBows;
import me.scill.betterbows.CustomBow;
import org.bukkit.Effect;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.util.Vector;

public class SniperBow extends CustomBow {

	public SniperBow(final BetterBows plugin) {
		super("sniper", plugin.getConfigData().getSniperBow());
	}

	@Override
	public void activateAbility(final EntityShootBowEvent event) {
		final Vector velocity = event.getProjectile().getVelocity();

		// If the arrow is fully drawn, significantly increase the speed.
		if (Math.abs(velocity.getX()) >= 2 || Math.abs(velocity.getY()) >= 2 || Math.abs(velocity.getZ()) >= 2)
			event.getProjectile().setVelocity(velocity.multiply(2));
	}

	@Override
	public void activateAbility(final ProjectileHitEvent event) {
		// Add cool particle effects on arrow hit!
		for (int i=0; i<20; i++)
			event.getEntity().getWorld().playEffect(event.getEntity().getLocation(), Effect.MAGIC_CRIT, 1);
	}
}
