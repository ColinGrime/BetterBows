package me.scill.betterbows.bows;

import me.scill.betterbows.BetterBows;
import me.scill.betterbows.CustomBow;
import me.scill.betterbows.data.ConfigData;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class FreezeBow extends CustomBow {

	private final ConfigData configData;

	public FreezeBow(final BetterBows plugin) {
		super(plugin, "freeze");
		this.configData = plugin.getConfigData();
	}

	@Override
	public void activateAbility(final EntityDamageByEntityEvent event) {
		final PotionEffect potion = new PotionEffect(PotionEffectType.SLOW,
													(int) (20 * configData.getFreezeTime()),
													100);

		// Prevents an entity from moving for a specified amount of time.
		((LivingEntity) event.getEntity()).addPotionEffect(potion);
	}
}