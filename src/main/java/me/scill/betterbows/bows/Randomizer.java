package me.scill.betterbows.bows;

import me.scill.betterbows.BetterBows;
import me.scill.betterbows.CustomBow;
import me.scill.betterbows.data.ConfigData;
import me.scill.betterbows.utilities.CommonUtil;
import org.bukkit.Location;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

public class Randomizer extends CustomBow {

	private final BetterBows plugin;

	public Randomizer(final BetterBows plugin) {
		super("randomizer", plugin.getConfigData().getRandomizerBow());
		this.plugin = plugin;
	}

	@Override
	public void activateAbility(final EntityShootBowEvent event) {
		plugin.getCustomBows().get(CommonUtil.random(plugin.getCustomBows().size())).activateAbility(event);
	}

	@Override
	public void activateAbility(final ProjectileHitEvent event) {
		plugin.getCustomBows().get(CommonUtil.random(plugin.getCustomBows().size())).activateAbility(event);
	}

	@Override
	public void activateAbility(final EntityDamageByEntityEvent event) {
		plugin.getCustomBows().get(CommonUtil.random(plugin.getCustomBows().size())).activateAbility(event);
	}
}
