package com.tagsortlogistics.logic;

import com.tagsortlogistics.networking.ModPayloads.*;
import com.tagsortlogistics.util.TagHelper;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class InventoryLogic {
    public static void init() {
        ServerPlayNetworking.registerGlobalReceiver(PullMatchingPayload.TYPE, (payload, context) -> {
            context.server().execute(() -> {
                handlePullMatching(context.player());
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(SortInventoryPayload.TYPE, (payload, context) -> {
            context.server().execute(() -> {
                handleSortInventory(context.player());
            });
        });
    }

    private static void handlePullMatching(ServerPlayer player) {
        AbstractContainerMenu handler = player.containerMenu;
        if (handler instanceof ChestMenu chestMenu) {
            Container chestInv = chestMenu.getContainer();
            Level world = player.level();
            BlockPos chestPos = LastOpenedTracker.lastOpenedPos.get(player.getUUID());

            if (chestPos != null && chestInv != null) {
                Inventory playerInv = player.getInventory();
                for (int i = 0; i < playerInv.items.size(); i++) {
                    ItemStack playerStack = playerInv.items.get(i);
                    if (!playerStack.isEmpty() && TagHelper.doesItemMatchChestTags(world, chestPos, playerStack)) {
                        ItemStack remaining = transferStack(playerStack, chestInv);
                        playerInv.items.set(i, remaining);
                    }
                }
            }
        }
    }

    private static void handleSortInventory(ServerPlayer player) {
        AbstractContainerMenu handler = player.containerMenu;
        if (handler instanceof ChestMenu chestMenu) {
            Container chestInv = chestMenu.getContainer();
            sortInventory(chestInv);
        }
    }

    private static void sortInventory(Container inv) {
        List<ItemStack> items = new ArrayList<>();
        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);
            if (!stack.isEmpty()) {
                items.add(stack.copy());
                inv.setItem(i, ItemStack.EMPTY);
            }
        }

        // Stack items together
        List<ItemStack> stacked = new ArrayList<>();
        for (ItemStack item : items) {
            for (ItemStack existing : stacked) {
                if (ItemStack.isSameItemSameComponents(item, existing)) {
                    int space = existing.getMaxStackSize() - existing.getCount();
                    if (space > 0) {
                        int toAdd = Math.min(space, item.getCount());
                        existing.grow(toAdd);
                        item.shrink(toAdd);
                        if (item.isEmpty()) break;
                    }
                }
            }
            if (!item.isEmpty()) {
                stacked.add(item);
            }
        }

        stacked.sort(Comparator.comparing(stack -> stack.getHoverName().getString()));

        for (int i = 0; i < stacked.size() && i < inv.getContainerSize(); i++) {
            inv.setItem(i, stacked.get(i));
        }
    }

    private static ItemStack transferStack(ItemStack stack, Container dest) {
        ItemStack remaining = stack.copy();
        for (int i = 0; i < dest.getContainerSize(); i++) {
            ItemStack destStack = dest.getItem(i);
            if (!destStack.isEmpty() && ItemStack.isSameItemSameComponents(remaining, destStack)) {
                int space = destStack.getMaxStackSize() - destStack.getCount();
                if (space > 0) {
                    int toAdd = Math.min(space, remaining.getCount());
                    destStack.grow(toAdd);
                    remaining.shrink(toAdd);
                    if (remaining.isEmpty()) return ItemStack.EMPTY;
                }
            }
        }
        for (int i = 0; i < dest.getContainerSize(); i++) {
            if (dest.getItem(i).isEmpty()) {
                dest.setItem(i, remaining.copy());
                return ItemStack.EMPTY;
            }
        }
        return remaining;
    }
}
