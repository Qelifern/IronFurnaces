package ironfurnaces.mod;

import ironfurnaces.mod.gui.GuiHandler;
import ironfurnaces.mod.init.IronFurnacesBlocks;
import ironfurnaces.mod.init.IronFurnacesItems;
import ironfurnaces.mod.oredictionary.OreDictionaryHandler;
import ironfurnaces.mod.proxy.IProxy;
import ironfurnaces.mod.recipe.CraftingRecipes;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = IronFurnaces.MOD_ID, name = IronFurnaces.MOD_NAME, version = IronFurnaces.VERSION)
@Mod.EventBusSubscriber
public class IronFurnaces {

	public static final Logger logger = LogManager.getFormatterLogger(IronFurnaces.MOD_ID);

	public static final String MOD_ID = "ironfurnaces";
	public static final String MOD_NAME = "Iron Furnaces";
	public static final String VERSION = "1.0.0";

	public static final String CLIENT_PROXY = "ironfurnaces.mod.proxy.ClientProxy";
	public static final String SERVER_PROXY = "ironfurnaces.mod.proxy.ServerProxy";

	@Instance(MOD_ID)
	public static IronFurnaces instance = new IronFurnaces();

	@SidedProxy(clientSide = IronFurnaces.CLIENT_PROXY, serverSide = IronFurnaces.SERVER_PROXY)
	public static IProxy proxy;

	public static final CreativeTabs TAB_IRONFURNACES = new CreativeTabs("tab_iron_furnaces") {
		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(IronFurnacesBlocks.iron_furnace);
		}
	};

	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		proxy.preInit(e);
		MinecraftForge.EVENT_BUS.register(this);
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
		proxy.init(e);
		ConfigManager.sync(IronFurnaces.MOD_ID, net.minecraftforge.common.config.Config.Type.INSTANCE);
		OreDictionaryHandler.registerOres();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		proxy.postInit(e);
		CraftingRecipes.recipes();
	}

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		IronFurnacesBlocks.register(event.getRegistry());
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		IronFurnacesItems.register(event.getRegistry());
	}

	@SubscribeEvent
	public void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
	{
		if (event.getModID().equals(IronFurnaces.MOD_ID)) {
			ConfigManager.sync(IronFurnaces.MOD_ID, net.minecraftforge.common.config.Config.Type.INSTANCE);
		}
	}

}
