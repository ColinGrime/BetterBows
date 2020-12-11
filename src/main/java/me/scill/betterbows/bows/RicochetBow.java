package me.scill.betterbows.bows;

import me.scill.betterbows.BetterBows;
import me.scill.betterbows.CustomBow;
import me.scill.betterbows.utilities.CommonUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

public class RicochetBow extends CustomBow implements Listener {

	private final Map<Entity, Integer> ricochetArrows = new HashMap<>();

	public RicochetBow(final BetterBows plugin) {
		super(plugin, "ricochet");
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@Override
	public void activateAbility(final ProjectileHitEvent event) {
		Entity arrow = event.getEntity();
		arrow.remove();

		final Location location = arrow.getLocation();
		final int ricochets = ricochetArrows.containsKey(arrow) ? ricochetArrows.get(arrow) + 1 : 1;

		arrow.setVelocity(location.getDirection()
				.multiply(CommonUtil.random(0.7,0.6))
				.add(new Vector(0, 0.1, 0)));
		arrow = location.getWorld().spawnArrow(location, arrow.getVelocity(), (float) arrow.getVelocity().length(),0);

		if (ricochets > 5)
			ricochetArrows.remove(arrow);
		else
			ricochetArrows.put(arrow, ricochets);
	}

	@EventHandler
	public void onRicochetArrowHit(final ProjectileHitEvent event) {
		if (ricochetArrows.containsKey(event.getEntity()))
			activateAbility(event);
	}
}
