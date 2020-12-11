package me.scill.betterbows.bows;

import me.scill.betterbows.BetterBows;
import me.scill.betterbows.CustomBow;
import me.scill.betterbows.data.ConfigData;
import me.scill.betterbows.utilities.CommonUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.util.BlockIterator;

public class BlockBow extends CustomBow {

	private final ConfigData configData;

	public BlockBow(final BetterBows plugin) {
		super(plugin, "block");
		this.configData = plugin.getConfigData();
	}

	@Override
	public void activateAbility(final ProjectileHitEvent event) {
		final Location airLocation = CommonUtil.getAccurateProjectileHit(event.getEntity(), false);
		airLocation.getBlock().setType(configData.getBlockType());
	}
}
