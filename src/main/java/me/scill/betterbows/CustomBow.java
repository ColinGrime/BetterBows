package me.scill.betterbows;

import de.tr7zw.nbtapi.NBTItem;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.scill.betterbows.data.ConfigData;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public abstract class CustomBow {

	@Getter
	private final String ID;
	private final ItemStack bow;

	public void activateAbility(final PlayerInteractEvent event) {}
	public void activateAbility(final EntityShootBowEvent event) {}
	public void activateAbility(final ProjectileHitEvent event) {}
	public void activateAbility(final EntityDamageByEntityEvent event) {}

	/**
	 * Gets a custom bow with NBT data attached.
	 * @return any bow
	 */
	public ItemStack getBow() {
		final NBTItem item = new NBTItem(bow);
		item.setString("better-bow", ID);
		return item.getItem();
	}
}
