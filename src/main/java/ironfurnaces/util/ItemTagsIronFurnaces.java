package ironfurnaces.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;

import java.util.Collection;

public class ItemTagsIronFurnaces {

    //Credit: Elrol_Arrowsend
    public static Item getOreDict(String oreDic) {
        ResourceLocation tag = new ResourceLocation("forge", oreDic);
        Tag<Item> t = net.minecraft.tags.ItemTags.getAllTags().getTag(tag);
        if (t == null) {
            return null;
        }
        Collection<Item> tagCollection = t.getValues();
        for (Item item : tagCollection) {
            return item;
        }
        return null;
    }
}
