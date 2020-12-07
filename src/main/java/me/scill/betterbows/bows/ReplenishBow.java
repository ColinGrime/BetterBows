package me.scill.betterbows.bows;

import me.scill.betterbows.BetterBows;
import me.scill.betterbows.CustomBow;
import me.scill.betterbows.data.ConfigData;
import me.scill.betterbows.utilities.CommonUtil;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;

public class ReplenishBow extends CustomBow {

	private final ConfigData configData;

	public ReplenishBow(final BetterBows plugin) {
		super("replenish", plugin.getConfigData().getReplenishBow());
		this.configData = plugin.getConfigData();
	}

	@Override
	public void activateAbility(final EntityDamageByEntityEvent event) {
		if (!(event.getEntity() instanceof LivingEntity))
			return;

		final LivingEntity victim = (LivingEntity) event.getEntity();
		final Player bowOwner = (Player) ((Arrow) event.getDamager()).getShooter();

		// If the player shoots themselves, cancel the ability.
		if (victim == bowOwner)
			return;

		// Damages a player, and then steals that damaged health.
		final int healthToSteal = CommonUtil.random(configData.getReplenishHealthMax(), configData.getReplenishHealthMin());
		victim.setHealth(Math.max(victim.getHealth() - healthToSteal, 1));
		bowOwner.setHealth(Math.min(bowOwner.getHealth() + healthToSteal, bowOwner.getMaxHealth()));

		// Steals a player's potion effects, and then steals that stolen potion effects.
		for (PotionEffect stolenPotionEffect : victim.getActivePotionEffects()) {
			int timeToSteal = 20 * CommonUtil.random(configData.getReplenishPotionMax(), configData.getReplenishPotionMin());
			updatePotionEffect(victim, stolenPotionEffect, stolenPotionEffect.getDuration() - timeToSteal);

			// Checks if the player already has the potion effect active.
			// If so, add it to the stolen potion effect duration.
			for (PotionEffect potionEffect : bowOwner.getActivePotionEffects()) {
				if (stolenPotionEffect.getType() == potionEffect.getType()) {
					timeToSteal += potionEffect.getDuration();
					break;
				}
			}
			updatePotionEffect(bowOwner, stolenPotionEffect, timeToSteal);
		}
	}

	/**
	 * Removes & then re-adds a potion effect.
	 *
	 * @param player any player
	 * @param potionEffect any potion effect
	 * @param potionDuration any potion duration
	 */
	private void updatePotionEffect(final LivingEntity player, final PotionEffect potionEffect, final int potionDuration) {
		player.removePotionEffect(potionEffect.getType());
		player.addPotionEffect(new PotionEffect(potionEffect.getType(), potionDuration, potionEffect.getAmplifier()));
	}
}
