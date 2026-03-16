package com.tagsortlogistics.networking;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public class ModPayloads {
    public record PullMatchingPayload() implements CustomPacketPayload {
        public static final Type<PullMatchingPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("tagsortlogistics", "pull_matching"));
        public static final StreamCodec<RegistryFriendlyByteBuf, PullMatchingPayload> CODEC = StreamCodec.unit(new PullMatchingPayload());
        @Override public Type<? extends CustomPacketPayload> type() { return TYPE; }
    }

    public record SortInventoryPayload() implements CustomPacketPayload {
        public static final Type<SortInventoryPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("tagsortlogistics", "sort_inventory"));
        public static final StreamCodec<RegistryFriendlyByteBuf, SortInventoryPayload> CODEC = StreamCodec.unit(new SortInventoryPayload());
        @Override public Type<? extends CustomPacketPayload> type() { return TYPE; }
    }
}
