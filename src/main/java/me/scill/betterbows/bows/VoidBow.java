package me.scill.betterbows.bows;

import me.scill.betterbows.BetterBows;
import me.scill.betterbows.CustomBow;
import me.scill.betterbows.OldBlock;
import me.scill.betterbows.utilities.CommonUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.stream.Collectors;

public class VoidBow extends CustomBow {

	private final BetterBows plugin;
	private final Map<Queue<Block>, Stack<OldBlock>> voidBlocks = new HashMap<>();

	public VoidBow(final BetterBows plugin) {
		super(plugin, "void");
		this.plugin = plugin;
		startVoidAbility();
	}

	@Override
	public void activateAbility(final ProjectileHitEvent event) {
		Location location = CommonUtil.getAccurateProjectileHit(event.getEntity(), true);
		for (Entity entity : location.getWorld().getNearbyEntities(location, 2, 2, 2)) {
			if (entity instanceof LivingEntity) {
				location = entity.getLocation();
				break;
			}
		}

		final Location bottomLocation = location.clone().add(2,0,2);
		bottomLocation.setY(0);

		final Queue<Block> voidBlocks = CommonUtil.getLocationsBetween(location, bottomLocation)
				.stream().filter(voidLocation -> voidLocation.getBlock().getType() != Material.AIR)
				.map(Location::getBlock).collect(Collectors.toCollection(LinkedList::new));
		if (voidBlocks.isEmpty())
			return;

		final Stack<OldBlock> oldBlocks = new Stack<>();
		this.voidBlocks.put(voidBlocks, oldBlocks);
	}

	@Override
	public void activateAbility(final EntityDamageByEntityEvent event) {
		event.setCancelled(true);
	}

	// todo check for WorldEdit for efficient block edits.
	private void startVoidAbility() {
		new BukkitRunnable() {
			@Override
			public void run() {
				final Iterator<Map.Entry<Queue<Block>, Stack<OldBlock>>> iterator = voidBlocks.entrySet().iterator();
				while (iterator.hasNext()) {
					final Map.Entry<Queue<Block>, Stack<OldBlock>> voidBlock = iterator.next();
					for (int i=0; i<12; i++) {
						if (!voidBlock.getKey().isEmpty()) {
							final Block block = voidBlock.getKey().poll();
							voidBlock.getValue().push(new OldBlock(block));
							block.setType(Material.AIR);
						} else if (!voidBlock.getValue().isEmpty())
							voidBlock.getValue().pop().revert();
						else {
							iterator.remove();
							break;
						}
					}
				}
			}
		}.runTaskTimer(plugin, 0L, 5L);
	}
}
