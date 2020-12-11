package me.scill.betterbows.bows;

import me.scill.betterbows.BetterBows;
import me.scill.betterbows.CustomBow;
import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ComboBow extends CustomBow {

	private final BetterBows plugin;
	private final Map<UUID, Integer> combos = new HashMap<>();

	public ComboBow(final BetterBows plugin) {
		super(plugin, "combo");
		this.plugin = plugin;
	}

	@Override
	public void activateAbility(final ProjectileHitEvent event) {
		new BukkitRunnable() {
			@Override
			public void run() {
				if (!event.getEntity().isDead())
					combos.remove(((Player) event.getEntity().getShooter()).getUniqueId());
			}
		}.runTaskLaterAsynchronously(plugin, 1L);
	}

	@Override
	public void activateAbility(final EntityDamageByEntityEvent event) {
		final UUID playerUuid = ((Player) ((Arrow) event.getDamager()).getShooter()).getUniqueId();
		final int combo = combos.containsKey(playerUuid) ? combos.get(playerUuid) + 1 : 1;
		combos.put(playerUuid, combo);

		double damage = event.getDamage() * (1.0 + (0.2 * combo));

		// Each hit in a row grants +20% damage.
		event.setDamage(event.getDamage() * (1.0 + (0.2 * combo)));
	}
}
