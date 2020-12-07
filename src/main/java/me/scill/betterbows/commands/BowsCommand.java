package me.scill.betterbows.commands;

import me.scill.betterbows.BetterBows;
import me.scill.betterbows.CustomBow;
import me.scill.betterbows.utilities.CommonUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BowsCommand implements CommandExecutor {
	
	private final BetterBows plugin;
	
	public BowsCommand(final BetterBows plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("bows")) {
			// No args? Send help!
			if (args.length == 0) {
				displayHelp(sender);
				return true;
			}

			// Grab a list of custom bows.
			else if (args[0].equalsIgnoreCase("list") && sender.hasPermission("betterbows.list")) {
				sender.sendMessage(CommonUtil.color("&2Bows:"));
				for (CustomBow bow : plugin.getCustomBows())
					sender.sendMessage(CommonUtil.color("&a" + bow.getID().substring(0, 1).toUpperCase() + bow.getID().substring(1)));
				return true;
			}

			// Give a player a custom bow.
			else if (args[0].equalsIgnoreCase("give") && sender.hasPermission("betterbows.give")) {
				// Not enough args!
				if (args.length < 3)
					displayHelp(sender);

				// Player does not exist.
				else if (Bukkit.getPlayer(args[1]) == null)
					sender.sendMessage(plugin.getConfigData().getNoValidPlayerError().replace("%player%", args[1]));

				else {
					// Give the player a custom bow.
					for (CustomBow bow : plugin.getCustomBows()) {
						if (bow.getID().equalsIgnoreCase(args[2])) {
							Bukkit.getPlayer(args[1]).getInventory().addItem(bow.getBow());
							return true;
						}
					}

					// Invalid bow.
					sender.sendMessage(plugin.getConfigData().getNoValidBowError().replace("%bow%", args[2]));
					return true;
				}

				return true;
			}

			// Reload command.
			else if (args[0].equalsIgnoreCase("reload") && sender.hasPermission("betterbows.reload")) {
				plugin.reload();
				sender.sendMessage(plugin.getConfigData().getReloadMessage());
				return true;
			}

			// Send help if nothing else works!
			displayHelp(sender);

			return true;
		}
		return false;
	}

	/**
	 * Sends the help message to the specified sender.
	 *
	 * @param sender any commandsender
	 */
	private void displayHelp(final CommandSender sender) {
		if (sender.hasPermission("betterbows.list") || sender.hasPermission("betterbows.give") || sender.hasPermission("betterbows.reload")) {
			for (String help : plugin.getConfigData().getCommandHelp())
				sender.sendMessage(help);
		}

		else
			sender.sendMessage(plugin.getConfigData().getNoPermsError());
	}
}
