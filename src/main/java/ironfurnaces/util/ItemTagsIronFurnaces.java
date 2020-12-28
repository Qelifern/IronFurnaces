package ironfurnaces.util;

import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;

import java.util.Collection;

public class ItemTagsIronFurnaces {

    //Credit: Elrol_Arrowsend
    public static Item getOreDict(String oreDic) {
        ResourceLocation tag = new ResourceLocation("forge", oreDic);
        ITag<Item> t = net.minecraft.tags.ItemTags.getCollection().get(tag);
        if (t == null) {
            return null;
        }
        Collection<Item> tagCollection = t.getAllElements();
        for (Item item : tagCollection) {
            return item;
        }
        return null;
    }
}
