package me.scill.betterbows.bows;

import me.scill.betterbows.BetterBows;
import me.scill.betterbows.CustomBow;
import org.bukkit.entity.Arrow;
import org.bukkit.event.player.PlayerInteractEvent;

public class RapidFireBow extends CustomBow {

	public RapidFireBow(final BetterBows plugin) {
		super(plugin, "rapid-fire");
	}

	@Override
	public void activateAbility(final PlayerInteractEvent event) {
		// Every time a player left/right clicks, an arrow spawns.
		event.getPlayer().launchProjectile(Arrow.class, event.getPlayer().getLocation().getDirection());
	}
}
