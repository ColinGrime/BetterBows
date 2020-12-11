package me.scill.betterbows;

import de.tr7zw.nbtapi.NBTItem;
import lombok.Getter;
import me.scill.betterbows.utilities.CommonUtil;
import org.bukkit.Material;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@Getter
public abstract class CustomBow {

	private final String ID;
	private final ItemStack bow;

	public CustomBow(final BetterBows plugin, final String ID) {
		this.ID = ID;

		if (plugin.getConfigData().getBows().get(ID) == null) {
			plugin.getLogger().warning("Bow type \"" + ID + "\" has failed to load! Did you delete the configuration option for this bow?");
			this.bow = getBow(getFailedBow());
		}
		else
			this.bow = getBow(plugin.getConfigData().getBows().get(ID));
	}

	public void activateAbility(final PlayerInteractEvent event) {}
	public void activateAbility(final EntityShootBowEvent event) {}
	public void activateAbility(final ProjectileHitEvent event) {}
	public void activateAbility(final EntityDamageByEntityEvent event) {}

	/**
	 * Gets a custom bow with NBT data attached.
	 * @return any bow
	 */
	public ItemStack getBow(final ItemStack bow) {
		final NBTItem item = new NBTItem(bow);
		item.setString("better-bow", ID);
		return item.getItem();
	}

	private ItemStack getFailedBow() {
		final ItemStack failedBow = new ItemStack(Material.BOW);
		final ItemMeta itemMeta = failedBow.getItemMeta();
		itemMeta.setDisplayName(CommonUtil.color("&cFailed Bow"));
		itemMeta.setLore(CommonUtil.color("&7Bow type \"" + ID + "\" has failed to load!",
										  "&7Did you delete the configuration option for this bow?"));
		failedBow.setItemMeta(itemMeta);
		return failedBow;
	}
}
