package ironfurnaces.mod.config;

import ironfurnaces.mod.IronFurnaces;


@net.minecraftforge.common.config.Config(modid = IronFurnaces.MOD_ID, type = net.minecraftforge.common.config.Config.Type.INSTANCE)
public class Config {

    @net.minecraftforge.common.config.Config.Name("Iron Furnace Speed")
    @net.minecraftforge.common.config.Config.Comment({"Number of ticks to complete one smelting operation.", "200 is what a regular furnace takes.", "Default: 160"})
    @net.minecraftforge.common.config.Config.RangeInt(min = 2, max = 72000)
    public static int IRON_FURNACE_SPEED = 160;

    @net.minecraftforge.common.config.Config.Name("Golden Furnace Speed")
    @net.minecraftforge.common.config.Config.Comment({"Number of ticks to complete one smelting operation.", "200 is what a regular furnace takes.", "Default: 120"})
    @net.minecraftforge.common.config.Config.RangeInt(min = 2, max = 72000)
    public static int GOLD_FURNACE_SPEED = 120;

    @net.minecraftforge.common.config.Config.Name("Diamond Furnace Speed")
    @net.minecraftforge.common.config.Config.Comment({"Number of ticks to complete one smelting operation.", "200 is what a regular furnace takes.", "Default: 80"})
    @net.minecraftforge.common.config.Config.RangeInt(min = 2, max = 72000)
    public static int DIAMOND_FURNACE_SPEED = 80;

    @net.minecraftforge.common.config.Config.Name("Emerald Furnace Speed")
    @net.minecraftforge.common.config.Config.Comment({"Number of ticks to complete one smelting operation.", "200 is what a regular furnace takes.", "Default: 40"})
    @net.minecraftforge.common.config.Config.RangeInt(min = 2, max = 72000)
    public static int EMERALD_FURNACE_SPEED = 40;

    @net.minecraftforge.common.config.Config.Name("Obsidian Furnace Speed")
    @net.minecraftforge.common.config.Config.Comment({"Number of ticks to complete one smelting operation.", "200 is what a regular furnace takes.", "Default: 20"})
    @net.minecraftforge.common.config.Config.RangeInt(min = 2, max = 72000)
    public static int OBSIDIAN_FURNACE_SPEED = 20;

    @net.minecraftforge.common.config.Config.Name("Copper Furnace Speed")
    @net.minecraftforge.common.config.Config.Comment({"Number of ticks to complete one smelting operation.", "200 is what a regular furnace takes.", "Default: 180"})
    @net.minecraftforge.common.config.Config.RangeInt(min = 2, max = 72000)
    public static int COPPER_FURNACE_SPEED = 180;

    @net.minecraftforge.common.config.Config.Name("Silver Furnace Speed")
    @net.minecraftforge.common.config.Config.Comment({"Number of ticks to complete one smelting operation.", "200 is what a regular furnace takes.", "Default: 140"})
    @net.minecraftforge.common.config.Config.RangeInt(min = 2, max = 72000)
    public static int SILVER_FURNACE_SPEED = 140;





}
