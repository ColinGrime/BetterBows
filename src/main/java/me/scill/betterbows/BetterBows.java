package me.scill.betterbows;

import lombok.Getter;
import me.scill.betterbows.commands.BowsCommand;
import me.scill.betterbows.data.ConfigData;
import me.scill.betterbows.listeners.BowsListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@Getter
public class BetterBows extends JavaPlugin {

	private ConfigData configData;
	private List<CustomBow> customBows;

	@Override
	public void onEnable() {
		// Data
		saveDefaultConfig();
		reload();

		// Listeners
		getServer().getPluginManager().registerEvents(new BowsListener(this), this);

		// Commands
		getCommand("bows").setExecutor(new BowsCommand(this));
	}

	@Override
	public void onDisable() {

	}

	/**
	 * Reloads the plugin with new hopper values.
	 */
	public void reload() {
		reloadConfig();
		configData = new ConfigData(getConfig());
		try {
			createCustomBows();
		} catch (ClassNotFoundException | IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates all the custom bows.
	 */
	private void createCustomBows() throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
		customBows = new ArrayList<>();

		for (String className : getBowClasses()) {
			final Class<?> clazz = Class.forName("me.scill.betterbows.bows." + className);
			final Constructor<?> constructor = clazz.getConstructor(BetterBows.class);
			customBows.add((CustomBow) constructor.newInstance(new Object[] { this }));
		}

		getLogger().info("Loaded " + getBowClasses().size() + " bows.");
	}

	/**
	 * Retrieves all the class names from a package.
	 * Source: https://stackoverflow.com/questions/1456930/how-do-i-read-all-classes-from-a-java-package-in-the-classpath/7461653#7461653
	 *
	 * @return a list of bow classes from the package
	 */

	private ArrayList<String> getBowClasses() {
		final String packageName = "me.scill.betterbows.bows.".replace(".", "/");
		final ArrayList<String> classNames = new ArrayList<>();

		try (final JarFile jarFile = new JarFile(getFile())) {
			final Enumeration<JarEntry> jarEntries = jarFile.entries();

			while (jarEntries.hasMoreElements()) {
				final String entryName = jarEntries.nextElement().getName();

				if (entryName.startsWith(packageName) && !entryName.contains("$") && entryName.length() > packageName.length() + 5)
					classNames.add(entryName.substring(packageName.length(), entryName.lastIndexOf('.')));
			}
		}

		catch (IOException ex) {
			ex.printStackTrace();
		}

		return classNames;
	}
}