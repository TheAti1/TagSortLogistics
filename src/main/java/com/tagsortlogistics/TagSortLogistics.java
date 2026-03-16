package com.tagsortlogistics;

import com.tagsortlogistics.logic.InventoryLogic;
import com.tagsortlogistics.logic.LastOpenedTracker;
import com.tagsortlogistics.networking.ModPayloads;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TagSortLogistics implements ModInitializer {
	public static final String MOD_ID = "tagsortlogistics";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		PayloadTypeRegistry.playC2S().register(ModPayloads.PullMatchingPayload.TYPE, ModPayloads.PullMatchingPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(ModPayloads.SortInventoryPayload.TYPE, ModPayloads.SortInventoryPayload.CODEC);

		InventoryLogic.init();

		UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
			if (!world.isClientSide()) {
				BlockEntity be = world.getBlockEntity(hitResult.getBlockPos());
				if (be instanceof Container) {
					LastOpenedTracker.lastOpenedPos.put(player.getUUID(), hitResult.getBlockPos().immutable());
				}
			}
			return InteractionResult.PASS;
		});

		LOGGER.info("TagSort Logistics initialized!");
	}
}
