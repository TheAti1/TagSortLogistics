package com.tagsortlogistics.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.stream.Collectors;

public class TagHelper {

    /**
     * Finds item frames attached to or near a specific block position (like a chest).
     */
    public static List<ItemFrame> getAttachedItemFrames(Level world, BlockPos pos) {
        AABB searchBox = new AABB(pos).inflate(1.0);
        return world.getEntitiesOfClass(ItemFrame.class, searchBox, entity -> true);
    }

    /**
     * Gets all item tags associated with the items in the item frames around a chest.
     */
    public static List<TagKey<Item>> getTagsFromFrames(Level world, BlockPos pos) {
        return getAttachedItemFrames(world, pos).stream()
                .map(ItemFrame::getItem)
                .filter(stack -> !stack.isEmpty())
                .flatMap(stack -> stack.getTags())
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Checks if a given ItemStack matches any of the tags found in the item frames around the chest.
     */
    public static boolean doesItemMatchChestTags(Level world, BlockPos chestPos, ItemStack stackToCheck) {
        if (stackToCheck.isEmpty()) return false;

        List<TagKey<Item>> chestTags = getTagsFromFrames(world, chestPos);

        if (chestTags.isEmpty()) return false;

        for (TagKey<Item> tag : chestTags) {
            if (stackToCheck.is(tag)) {
                return true;
            }
        }
        return false;
    }
}
