package ironfurnaces.mod.init;

import ironfurnaces.mod.item.ItemUpgrade;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

public class IronFurnacesItems {


    @GameRegistry.ObjectHolder("ironfurnaces:upgrade_iron")
    public static Item upgrade_iron;

    @GameRegistry.ObjectHolder("ironfurnaces:upgrade_gold")
    public static ItemUpgrade upgrade_gold;

    @GameRegistry.ObjectHolder("ironfurnaces:upgrade_diamond")
    public static ItemUpgrade upgrade_diamond;

    @GameRegistry.ObjectHolder("ironfurnaces:upgrade_emerald")
    public static ItemUpgrade upgrade_emerald;

    @GameRegistry.ObjectHolder("ironfurnaces:upgrade_obsidian")
    public static ItemUpgrade upgrade_obsidian;

    public static final void register(IForgeRegistry<Item> registry) {
        registerFromBlock(registry, IronFurnacesBlocks.iron_furnace);
        registerFromBlock(registry, IronFurnacesBlocks.gold_furnace);
        registerFromBlock(registry, IronFurnacesBlocks.diamond_furnace);
        registerFromBlock(registry, IronFurnacesBlocks.emerald_furnace);
        registerFromBlock(registry, IronFurnacesBlocks.obsidian_furnace);
        registerFromBlock(registry, IronFurnacesBlocks.copper_furnace);
        registerFromBlock(registry, IronFurnacesBlocks.silver_furnace);
        registry.register(new ItemUpgrade(new int[] {1, 0, 0, 0, 0}).setUnlocalizedName("upgrade_iron").setRegistryName("upgrade_iron"));
        registry.register(new ItemUpgrade(new int[] {0, 1, 0, 0, 0}).setUnlocalizedName("upgrade_gold").setRegistryName("upgrade_gold"));
        registry.register(new ItemUpgrade(new int[] {0, 0, 1, 0, 0}).setUnlocalizedName("upgrade_diamond").setRegistryName("upgrade_diamond"));
        registry.register(new ItemUpgrade(new int[] {0, 0, 0, 1, 0}).setUnlocalizedName("upgrade_emerald").setRegistryName("upgrade_emerald"));
        registry.register(new ItemUpgrade(new int[] {0, 0, 0, 0, 1}).setUnlocalizedName("upgrade_obsidian").setRegistryName("upgrade_obsidian"));
    }

    public static void registerFromBlock(IForgeRegistry<Item> registry, Block block) {
        ItemBlock item = new ItemBlock(block);
        item.setRegistryName(block.getRegistryName());
        registry.register(item);
    }

    public static final void registerRenders() {
        registerRender(upgrade_iron);
        registerRender(upgrade_gold);
        registerRender(upgrade_diamond);
        registerRender(upgrade_emerald);
        registerRender(upgrade_obsidian);
    }

    public static final void registerRender(Item item) {

        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0,
                new ModelResourceLocation(item.getRegistryName(), "inventory"));

    }

}
