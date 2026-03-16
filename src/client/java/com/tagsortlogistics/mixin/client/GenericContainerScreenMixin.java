package com.tagsortlogistics.mixin.client;

import com.tagsortlogistics.networking.ModPayloads;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerScreen.class)
public abstract class GenericContainerScreenMixin extends Screen {

    @Shadow protected int leftPos;
    @Shadow protected int imageWidth;
    @Shadow protected int topPos;

    protected GenericContainerScreenMixin(Component title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void tagsortlogistics$onInit(CallbackInfo ci) {
        if (!((Object) this instanceof ContainerScreen)) return;

        int startX = this.leftPos + this.imageWidth;
        int startY = this.topPos;

        this.addRenderableWidget(Button.builder(Component.literal("Pull"), button -> {
            ClientPlayNetworking.send(new ModPayloads.PullMatchingPayload());
        }).bounds(startX, startY, 40, 20).build());

        this.addRenderableWidget(Button.builder(Component.literal("A-Z"), button -> {
            ClientPlayNetworking.send(new ModPayloads.SortInventoryPayload());
        }).bounds(startX, startY + 22, 40, 20).build());
    }
}
