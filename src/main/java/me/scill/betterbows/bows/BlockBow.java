package me.scill.betterbows.bows;

import me.scill.betterbows.BetterBows;
import me.scill.betterbows.CustomBow;
import me.scill.betterbows.data.ConfigData;
import org.bukkit.Location;
import org.bukkit.event.entity.ProjectileHitEvent;

public class BlockBow extends CustomBow {

	private final ConfigData configData;

	public BlockBow(final BetterBows plugin) {
		super("block", plugin.getConfigData().getBlockBow());
		this.configData = plugin.getConfigData();
	}

	@Override
	public void activateAbility(final ProjectileHitEvent event) {
		final Location location = event.getEntity().getLocation();
		location.add(event.getEntity().getVelocity().normalize().multiply(0.01));

		// Place a block.
		location.getBlock().setType(configData.getBlockType());
	}
}
