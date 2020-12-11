package me.scill.betterbows.bows;

import me.scill.betterbows.BetterBows;
import me.scill.betterbows.CustomBow;
import org.bukkit.entity.Fireball;
import org.bukkit.event.entity.EntityShootBowEvent;

public class FireBow extends CustomBow {

	public FireBow(final BetterBows plugin) {
		super(plugin, "fire");
	}

	@Override
	public void activateAbility(final EntityShootBowEvent event) {
		event.getProjectile().remove();
		event.getEntity().launchProjectile(Fireball.class);
	}
}
