package me.scill.betterbows.data;

import lombok.Getter;
import me.scill.betterbows.utilities.CommonUtil;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
public class ConfigData {

	// Freeze bow!
	private final ItemStack freezeBow;
	private final double freezeTime;

	// Replenish bow!
	private final ItemStack replenishBow;

	private final int replenishHealthMax;
	private final int replenishHealthMin;

	private final int replenishPotionMax;
	private final int replenishPotionMin;

	// Explosive bow!
	private final ItemStack explosiveBow;

	// Freeze bow!
	private final ItemStack blockBow;
	private final Material blockType;

	// Teleport bow!
	private final ItemStack teleportationBow;

	// Rider bow!
	private final ItemStack riderBow;

	// Rapid Fire bow!
	private final ItemStack rapidFireBow;

	// Debuff bow!
	private final ItemStack debuffBow;

	// Raining Death bow!
	private final ItemStack rainingDeathBow;

	// Blockage bow!
	private final ItemStack blockageBow;

	// Burst bow!
	private final ItemStack burstBow;

	// Triplet bow!
	private final ItemStack tripletBow;

	// Ricochet bow!
	private final ItemStack ricochetBow;

	// Summoner bow!
	private final ItemStack summonerBow;

	// Zeus bow!
	private final ItemStack zeusBow;

	// Sniper bow!
	private final ItemStack sniperBow;

	// Cannoneer bow!
	private final ItemStack cannoneerBow;

	// Healer bow!
	private final ItemStack healerBow;

	// Randomizer bow!
	private final ItemStack randomizerBow;

	// Extra...
	private final List<String> commandHelp;
	private final String reloadMessage, noPermsError, noValidPlayerError, noValidBowError;

	public ConfigData(final FileConfiguration config) {
		// Freeze bow!
		freezeBow = getBow(config, "freeze");
		freezeTime = config.getDouble("bows.freeze.time");

		// Replenish bow!
		replenishBow = getBow(config, "replenish");

		replenishHealthMax = config.getInt("bows.replenish.health-steal.max-amount");
		replenishHealthMin = config.getInt("bows.replenish.health-steal.min-amount");

		replenishPotionMax = config.getInt("bows.replenish.potion-steal.max-time");
		replenishPotionMin = config.getInt("bows.replenish.potion-steal.min-time");

		// Explosive bow!
		explosiveBow = getBow(config, "explosive");

		// Block bow!
		blockBow = getBow(config, "block");
		blockType = Material.getMaterial(config.getString("bows.block.type")) == null
				? Material.DIRT
				: Material.getMaterial(config.getString("bows.block.type"));

		// Teleporation bow!
		teleportationBow = getBow(config, "teleportation");

		// Rider bow!
		riderBow = getBow(config, "rider");

		// Rapid Fire bow!
		rapidFireBow = getBow(config, "rapid-fire");

		// Debuff bow!
		debuffBow = getBow(config, "debuff");

		// Raining Death bow.
		rainingDeathBow = getBow(config, "raining-death");

		// Blockage bow!
		blockageBow = getBow(config, "blockage");

		// Burst bow!
		burstBow = getBow(config, "burst");

		// Triplet bow!
		tripletBow = getBow(config, "triplet");

		// Ricochet bow!
		ricochetBow = getBow(config, "ricochet");

		// Summoner bow!
		summonerBow = getBow(config, "summoner");

		// Zeus bow!
		zeusBow = getBow(config, "zeus");

		// Sniper bow!
		sniperBow = getBow(config, "sniper");

		// Cannoneer bow!
		cannoneerBow = getBow(config, "cannoneer");

		// Healer bow!
		healerBow = getBow(config, "healer");

		// Randomizer bow!
		randomizerBow = getBow(config, "randomizer");

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