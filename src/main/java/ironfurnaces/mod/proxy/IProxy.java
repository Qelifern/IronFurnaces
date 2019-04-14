package ironfurnaces.mod.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public interface IProxy {

	public void init(FMLInitializationEvent e);

	public void preInit(FMLPreInitializationEvent e);

	public void postInit(FMLPostInitializationEvent e);

}
