package ironfurnaces.mod.oredictionary;

import ironfurnaces.mod.init.IronFurnacesBlocks;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictionaryHandler {

	public static void registerOres() {
		OreDictionary.registerOre("furnaceIron", IronFurnacesBlocks.iron_furnace);
		OreDictionary.registerOre("furnaceGold", IronFurnacesBlocks.gold_furnace);
		OreDictionary.registerOre("furnaceDiamond", IronFurnacesBlocks.diamond_furnace);
		OreDictionary.registerOre("furnaceEmerald", IronFurnacesBlocks.emerald_furnace);
		OreDictionary.registerOre("furnaceObsidian", IronFurnacesBlocks.obsidian_furnace);
		OreDictionary.registerOre("furnaceCopper", IronFurnacesBlocks.copper_furnace);
		OreDictionary.registerOre("furnaceSilver", IronFurnacesBlocks.silver_furnace);
	}
	
}
