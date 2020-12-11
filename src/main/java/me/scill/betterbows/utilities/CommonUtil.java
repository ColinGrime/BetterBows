package me.scill.betterbows.utilities;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.BlockIterator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CommonUtil {

	private final static Random random = new Random();

	/**
	 * If the message contains any valid
	 * color codes, they will be applied.
	 *
	 * @param message any string
	 * @return colored version of the string
	 */
	public static String color(final String message) {
		if (message == null)
			return "";
		return ChatColor.translateAlternateColorCodes('&', message);
	}

	/**
	 * If the list of messages contains any
	 * valid color codes, they will be applied.
	 *
	 * @param messages any list of strings
	 * @return colored version of the list
	 */
	public static List<String> color(final List<String> messages) {
		final List<String> newList = new ArrayList<>();
		if (messages == null || messages.isEmpty())
			return newList;

		for (String message : messages)
			newList.add(color(message));
		return newList;
	}

	/**
	 * If the list of messages contains any
	 * valid color codes, they will be applied.
	 *
	 * @param messages any list of strings
	 * @return colored version of the list
	 */
	public static List<String> color(final String...messages) {
		return color(Arrays.asList(messages));
	}

	/**
	 * Creates a new ItemStack
	 * with a colored name and lore
	 *
	 * @param material any valid material
	 * @param name any name
	 * @param lore any lore
	 * @return a new itemstack
	 */
	public static ItemStack createItemStack(final Material material, final String name, final List<String> lore) {
		final ItemStack itemStack = new ItemStack(material);
		final ItemMeta itemMeta = itemStack.getItemMeta();

		itemMeta.setDisplayName(color(name));
		itemMeta.setLore(color(lore));

		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}

	/**
	 * Gets a random number.
	 *
	 * @param num any integer
	 * @return random number
	 */
	public static int random(int num) {
		return random.nextInt(num);
	}

	/**
	 * Gets a random number between two integers.
	 *
	 * @param highNum high integer
	 * @param lowNum low integer
	 * @return random number
	 */
	public static int random(int highNum, int lowNum) {
		return random.nextInt(highNum - lowNum) + lowNum;
	}

	/**
	 * Gets a random number between two doubles.
	 *
	 * @param highNum high double
	 * @param lowNum low double
	 * @return random number
	 */
	public static double random(double highNum, double lowNum) {
		return lowNum + (highNum - lowNum) * random.nextDouble();
	}

	/**
	 * Retrieves all locations between two locations.
	 *
	 * @param location1 a location
	 * @param location2 another location
	 * @return all blocks between two locations
	 */
	public static List<Location> getLocationsBetween(final Location location1, final Location location2) {
		double lowX = Math.min(location1.getX(), location2.getX());
		double lowY = Math.min(location1.getY(), location2.getY());
		double lowZ = Math.min(location1.getZ(), location2.getZ());

		final List<Location> locations = new ArrayList<>();
		for (int blockY = Math.abs(location1.getBlockY() - location2.getBlockY()); blockY >= 0; blockY--) {
			for (int blockX = 0; blockX < Math.abs(location1.getBlockX() - location2.getBlockX()); blockX++) {
				for (int blockZ = 0; blockZ < Math.abs(location1.getBlockZ() - location2.getBlockZ()); blockZ++)
					locations.add(new Location(location1.getWorld(), lowX + blockX, lowY + blockY, lowZ + blockZ));
			}
		}
		return locations;
	}

	public static Location getAccurateProjectileHit(final Projectile projectile, final boolean isSolidBlock) {
		final BlockIterator iterator = new BlockIterator
				(projectile.getWorld(),
				 projectile.getLocation().toVector(),
				 projectile.getVelocity().normalize(),
				 0,
				 4);

		Block block = projectile.getLocation().getBlock();
		while (iterator.hasNext()) {
			final Block nextBlock = iterator.next();
			if (nextBlock.getType() != Material.AIR) {
				if (isSolidBlock)
					block = nextBlock;
				break;
			}
			block = nextBlock;
		}
		return block.getLocation();
	}
}