package me.scill.betterbows.listeners;

import de.tr7zw.nbtapi.NBTItem;
import me.scill.betterbows.BetterBows;
import me.scill.betterbows.CustomBow;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BowsListener implements Listener {

	private final BetterBows plugin;
	private final Map<Entity, CustomBow> firedArrows = new HashMap<>();

	public BowsListener(final BetterBows plugin) {
		this.plugin = plugin;
		checkForDeadArrows();
	}

	/**
	 * Every 20 seconds, check for dead entity
	 * arrows, and remove them from the map.
	 */
	private void checkForDeadArrows() {
		new BukkitRunnable() {
			@Override
			public void run() {
				firedArrows.keySet().removeIf(Entity::isDead);

				if (firedArrows.size() > 100) {
					final Iterator<Entity> iterator = firedArrows.keySet().iterator();
					while (iterator.hasNext()) {
						iterator.next().remove();
						iterator.remove();
					}
					plugin.getLogger().info("Too many arrows are alive! Deleting all current arrows.");
				}
			}
		}.runTaskTimer(plugin, 0L, 20L * 20);
	}

	@EventHandler
	public void onBowInteract(final PlayerInteractEvent event) {
		// No bow in hand.
		if (event.getPlayer().getItemInHand().getType() != Material.BOW)
			return;

		// Check if the bow is a custom bow.
		final NBTItem item = new NBTItem(event.getPlayer().getItemInHand());
		if (item.getString("better-bow") == null)
			return;

		// Activate the bow's ability.
		for (CustomBow bow : plugin.getCustomBows()) {
			if (item.getString("better-bow").equals(bow.getID())) {
				bow.activateAbility(event);
				return;
			}
		}
	}

	@EventHandler
	public void onBowFire(final EntityShootBowEvent event) {
		// Check if the bow is a custom bow.
		final NBTItem item = new NBTItem(event.getBow());
		if (!(event.getEntity() instanceof Player) || item.getString("better-bow") == null)
			return;

		// Activate the bow's ability.
		for (CustomBow bow : plugin.getCustomBows()) {
			if (item.getString("better-bow").equals(bow.getID())) {
				firedArrows.put(event.getProjectile(), bow);
				bow.activateAbility(event);
				return;
			}
		}
	}

	@EventHandler
	public void onArrowHit(final ProjectileHitEvent event) {
		// Activate the bow's ability if the arrow was shot from a custom bow.
		if (event.getEntity() instanceof Arrow && firedArrows.containsKey(event.getEntity())) {
			firedArrows.get(event.getEntity()).activateAbility(event);

			// Removes it a few ticks later to allow the EntityDamageByEntityEvent to activate.
			new BukkitRunnable() {
				@Override
				public void run() {
					firedArrows.remove(event.getEntity());
				}
			}.runTaskLaterAsynchronously(plugin,5L);
		}
	}

	@EventHandler
	public void onPlayerArrowHit(final EntityDamageByEntityEvent event) {
		// Activate the bow's ability if the arrow was shot from a custom bow.
		if (event.getDamager() instanceof Arrow && firedArrows.containsKey(event.getDamager()))
			firedArrows.get(event.getDamager()).activateAbility(event);
	}
}
