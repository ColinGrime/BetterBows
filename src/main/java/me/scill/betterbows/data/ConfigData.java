package me.scill.betterbows.data;

import lombok.Getter;
import me.scill.betterbows.utilities.CommonUtil;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class ConfigData {

	// List of all the bows!
	private final Map<String, ItemStack> bows = new HashMap<>();

	// Freeze bow!
	private final double freezeTime;

	// Replenish bow!
	private final int replenishHealthMax;
	private final int replenishHealthMin;
	private final int replenishPotionMax;
	private final int replenishPotionMin;

	// Block bow!
	private final Material blockType;

	// Harming bow!
	private final int harmingDebuffPots;

	// Extra...
	private final List<String> commandHelp;
	private final String reloadMessage, noPermsError, noValidPlayerError, noValidBowError;

	public ConfigData(final FileConfiguration config) {
		for (String bow : config.getConfigurationSection("bows").getKeys(false))
			bows.put(bow, getBow(config, bow));

		// Freeze bow!
		freezeTime = config.getDouble("bows.freeze.time");

		// Replenish bow!
		replenishHealthMax = config.getInt("bows.replenish.health-steal.max-amount");
		replenishHealthMin = config.getInt("bows.replenish.health-steal.min-amount");
		replenishPotionMax = config.getInt("bows.replenish.potion-steal.max-time");
		replenishPotionMin = config.getInt("bows.replenish.potion-steal.min-time");

		// Block bow!
		blockType = Material.getMaterial(config.getString("bows.block.type")) == null
				? Material.DIRT
				: Material.getMaterial(config.getString("bows.block.type"));

		// Harming bow!
		harmingDebuffPots = config.getInt("bows.harming.debuff-pots");

		// Extra...
		commandHelp = CommonUtil.color(config.getStringList("admin.command-help"));
		reloadMessage = CommonUtil.color(config.getString("admin.reload"));
		noPermsError = CommonUtil.color(config.getString("errors.insufficient-permissions"));
		noValidPlayerError = CommonUtil.color(config.getString("errors.no-valid-player"));
		noValidBowError = CommonUtil.color(config.getString("errors.no-valid-bow"));
	}

	private ItemStack getBow(final FileConfiguration config, final String bowName) {
		return CommonUtil.createItemStack(Material.BOW,
				config.getString("bows." + bowName + ".name"),
				config.getStringList("bows." + bowName + ".lore"));
	}
}