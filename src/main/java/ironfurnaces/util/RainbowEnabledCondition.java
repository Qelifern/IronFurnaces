package ironfurnaces.util;

import com.google.gson.JsonObject;
import ironfurnaces.Config;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

public class RainbowEnabledCondition implements ICondition
{
    private static final ResourceLocation NAME = new ResourceLocation("ironfurnaces", "rainbow");
    public RainbowEnabledCondition()
    {
    }

    @Override
    public ResourceLocation getID()
    {
        return NAME;
    }

    @Override
    public boolean test(IContext context) {
        return Config.enableRainbowContent.get();
    }

    @Override
    public String toString()
    {
        return "enabled(\"" + Config.enableRainbowContent.get() + "\")";
    }

    public static class Serializer implements IConditionSerializer<RainbowEnabledCondition>
    {
        public static final RainbowEnabledCondition.Serializer INSTANCE = new RainbowEnabledCondition.Serializer();

        @Override
        public void write(JsonObject json, RainbowEnabledCondition value)
        {

        }

        @Override
        public RainbowEnabledCondition read(JsonObject json)
        {
            return new RainbowEnabledCondition();
        }

        @Override
        public ResourceLocation getID()
        {
            return RainbowEnabledCondition.NAME;
        }
    }
}
