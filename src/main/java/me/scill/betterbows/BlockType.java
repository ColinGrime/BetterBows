package me.scill.betterbows;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

@Getter
public class BlockType {
	private final Location location;
	private final Material material;

	public BlockType(final Block block) {
		this.location = block.getLocation();
		this.material = block.getType();
	}
}