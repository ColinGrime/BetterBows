package me.scill.betterbows.bows;

import me.scill.betterbows.BetterBows;
import me.scill.betterbows.CustomBow;
import me.scill.betterbows.utilities.CommonUtil;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.ProjectileHitEvent;

public class SummonerBow extends CustomBow {

	// List of random passive entities.
	private final EntityType[] randomAnimals = { EntityType.PIG,
												 EntityType.COW,
									       		 EntityType.SHEEP,
										   		 EntityType.CHICKEN,
										   		 EntityType.HORSE };

	public SummonerBow(final BetterBows plugin) {
		super(plugin, "summoner");
	}

	@Override
	public void activateAbility(final ProjectileHitEvent event) {
		// Summons random animals on arrow hit.
		for (int i=0; i< CommonUtil.random(4,2); i++)
			event.getEntity().getWorld().spawnEntity(
					event.getEntity().getLocation(),
					randomAnimals[CommonUtil.random(randomAnimals.length)]);
	}
}
