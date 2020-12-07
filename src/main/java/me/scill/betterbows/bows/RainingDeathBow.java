package me.scill.betterbows.bows;

import me.scill.betterbows.BetterBows;
import me.scill.betterbows.CustomBow;
import me.scill.betterbows.utilities.CommonUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class RainingDeathBow extends CustomBow {

	public RainingDeathBow(final BetterBows plugin) {
		super("raining-death", plugin.getConfigData().getRainingDeathBow());
	}

	@Override
	public void activateAbility(final ProjectileHitEvent event) {
		// Creates a harming splash potion.
		final ItemStack item = new ItemStack(Material.POTION, 1, (short) 16451);
		final PotionMeta potionMeta = (PotionMeta) item.getItemMeta();
		potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.HARM, 0, 1), true);
		item.setItemMeta(potionMeta);

		// Spawns in multiple splash potions, and then sets them to the harming splash potion above.
		for (int i=0; i<10; i++) {
			final Location randomLocation = event.getEntity().getLocation().clone().add
					(CommonUtil.random(5, -5), CommonUtil.random(10, 5), CommonUtil.random(5, -5));
			final ThrownPotion thrownPotion = (ThrownPotion) event.getEntity().getWorld().spawnEntity
					(randomLocation, EntityType.SPLASH_POTION);
			thrownPotion.setItem(item);
		}
	}
}
