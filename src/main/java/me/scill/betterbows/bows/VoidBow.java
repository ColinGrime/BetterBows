package me.scill.betterbows.bows;

import me.scill.betterbows.BetterBows;
import me.scill.betterbows.BlockType;
import me.scill.betterbows.CustomBow;
import me.scill.betterbows.utilities.CommonUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class VoidBow extends CustomBow {

	private final BetterBows plugin;

	public VoidBow(final BetterBows plugin) {
		super(plugin, "void");
		this.plugin = plugin;
	}

	@Override
	public void activateAbility(final ProjectileHitEvent event) {
		Location location = CommonUtil.getAccurateProjectileHit(event.getEntity(), true);
		for (Entity entity : location.getWorld().getNearbyEntities(location, 1, 1, 1)) {
			if (entity instanceof LivingEntity) {
				location = entity.getLocation();
				break;
			}
		}
		final Location bottomLocation = location.clone().add(2,0,2);
		bottomLocation.setY(0);

		final List<Location> voidLocations = CommonUtil.getLocationsBetween(location, bottomLocation);
		if (voidLocations.isEmpty())
			return;

		final List<BlockType> voidBlocks = new ArrayList<>();
		final int[] blockIteration = {0};
		final boolean[] isComplete = {false};

		new BukkitRunnable() {
			@Override
			public void run() {
				if (voidLocations.size() <= blockIteration[0]);

				else if (!isComplete[0]) {
					for (int i=blockIteration[0]; i<blockIteration[0]+4; i++) {
						final Location voidLocation = voidLocations.get(i);
						voidBlocks.add(new BlockType(voidLocation.getBlock()));
						voidLocation.getBlock().setType(Material.AIR);
					}
					blockIteration[0] += 4;

					if (voidLocations.size() <= blockIteration[0]) {
						new BukkitRunnable() {
							@Override
							public void run() {
								isComplete[0] = true;
								blockIteration[0]--;
							}
						}.runTaskLaterAsynchronously(plugin,20L);
					}
				}

				else if (isComplete[0]) {
					for (int i=blockIteration[0]; i>blockIteration[0]-4; i--) {
						final BlockType voidBlock = voidBlocks.get(i);
						voidBlock.getLocation().getBlock().setType(voidBlock.getMaterial());
					}
					blockIteration[0] -= 4;

					if (blockIteration[0] <= 0)
						cancel();
				}
			}
		}.runTaskTimer(plugin, 0L, 1L);
	}

	@Override
	public void activateAbility(final EntityDamageByEntityEvent event) {
		event.setCancelled(true);
	}
}
