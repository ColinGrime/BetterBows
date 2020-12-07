package me.scill.betterbows.bows;

import me.scill.betterbows.BetterBows;
import me.scill.betterbows.CustomBow;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class RiderBow extends CustomBow {

	private final Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

	public RiderBow(final BetterBows plugin) {
		super("rider", plugin.getConfigData().getRiderBow());
	}

	@Override
	public void activateAbility(final EntityShootBowEvent event) {
		final Player rider = (Player) event.getEntity();

		// Prevents colliding with entities during the ride.
		rider.spigot().setCollidesWithEntities(false);

		// Hacky way to allow arrows to pass through the player.
		final Team team = scoreboard.registerNewTeam(rider.getName());
		team.addEntry(rider.getName());
		team.setAllowFriendlyFire(false);

		// Ride the arrow!
		event.getProjectile().setPassenger(event.getEntity());
	}

	@Override
	public void activateAbility(final ProjectileHitEvent event) {
		// Re-enables collision with entities.
		((Player) event.getEntity().getShooter()).spigot().setCollidesWithEntities(true);

		// Unregisters the team, allowing arrows to hit you again.
		scoreboard.getTeam(((Player) event.getEntity().getShooter()).getName()).unregister();
	}
}
