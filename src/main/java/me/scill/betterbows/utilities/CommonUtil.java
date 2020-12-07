package me.scill.betterbows.utilities;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
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
}