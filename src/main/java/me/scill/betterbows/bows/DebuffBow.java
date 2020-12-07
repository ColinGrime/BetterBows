package me.scill.betterbows.bows;

import me.scill.betterbows.BetterBows;
import me.scill.betterbows.CustomBow;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class DebuffBow extends CustomBow {

	public DebuffBow(final BetterBows plugin) {
		super("debuff", plugin.getConfigData().getDebuffBow());
	}

	@Override
	public void activateAbility(final ProjectileHitEvent event) {
		// Creates a harming splash potion.
		final ItemStack item = new ItemStack(Material.POTION, 1, (short) 16451);
		final PotionMeta potionMeta = (PotionMeta) item.getItemMeta();
		potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.HARM, 0, 0), true);
		item.setItemMeta(potionMeta);

		// Spawns in a splash potion, and then sets it to the harming splash potion above.
		final ThrownPotion thrownPotion = (ThrownPotion) event.getEntity().getWorld().spawnEntity
				(event.getEntity().getLocation().clone().add(0,5,0), EntityType.SPLASH_POTION);
		thrownPotion.setItem(item);
	}
}
