package ironfurnaces.mod.proxy;

import ironfurnaces.mod.init.IronFurnacesBlocks;
import ironfurnaces.mod.init.IronFurnacesItems;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy implements IProxy {

	@Override
	public void preInit(FMLPreInitializationEvent e) {
	}
	
	@Override
	public void init(FMLInitializationEvent e) {
		
	}


	@Override
	public void postInit(FMLPostInitializationEvent e) {
		IronFurnacesBlocks.registerRenders();
		IronFurnacesItems.registerRenders();
		
	}



}
