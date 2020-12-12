package me.scill.betterbows;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.material.MaterialData;

@Getter
public class OldBlock {

	private final Block block;
	private final Material material;
	private final byte data;

	public OldBlock(final Block block) {
		this.block = block;
		this.material = block.getType();
		this.data = block.getData();
	}

	public void revert() {
		block.setType(material);
		block.setData(data);
	}
}