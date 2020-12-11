package me.scill.betterbows.bows;

import me.scill.betterbows.BetterBows;
import me.scill.betterbows.BlockType;
import me.scill.betterbows.CustomBow;
import me.scill.betterbows.utilities.CommonUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class BlockageBow extends CustomBow {

	private final BetterBows plugin;

	public BlockageBow(final BetterBows plugin) {
		super(plugin, "blockage");
		this.plugin = plugin;
	}

	@Override
	public void activateAbility(final ProjectileHitEvent event) {
		final Location arrowLocation = CommonUtil.getAccurateProjectileHit(event.getEntity(), true);
		final List<Location> blockageLocations = CommonUtil.getLocationsBetween
				(arrowLocation.clone().add(2,2,2), arrowLocation.clone().add(-1,0,-1));

		final List<BlockType> blockageBlocks = new ArrayList<>();

		blockageLocations.stream()
				.filter(trapLocation -> trapLocation.getBlock().getType() == Material.AIR
						&& trapLocation.getBlockX() != arrowLocation.getBlockX()
						&& trapLocation.getBlockZ() != arrowLocation.getBlockZ())
				.forEach(trapLocation -> {
					final Block block = trapLocation.getBlock();
					blockageBlocks.add(new BlockType(block));
					block.getLocation().getBlock().setType(Material.GLASS);
				});

		final int[] timer = {0};
		new BukkitRunnable() {
			@Override
			public void run() {
				if (timer[0]++ == 30) {
					for (BlockType trapBlock : blockageBlocks)
						trapBlock.getLocation().getBlock().setType(trapBlock.getMaterial());
					cancel();
				}
			}
		}.runTaskTimer(plugin,0L,5L);
	}
}
