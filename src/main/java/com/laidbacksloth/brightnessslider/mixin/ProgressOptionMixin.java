package com.laidbacksloth.brightnessslider.mixin;

import com.laidbacksloth.brightnessslider.BetterBrightnessSlider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.ProgressOption;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.FormattedCharSequence;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

@Mixin(ProgressOption.class)
public class ProgressOptionMixin {
    @Shadow
    @Final
    @Mutable
    protected double minValue;

    @Shadow
    @Mutable
    protected double maxValue;

    @Shadow
    @Final
    @Mutable
    private BiConsumer<Options, Double> setter;

    @Shadow
    @Final
    @Mutable
    private BiFunction<Options, ProgressOption, Component> toString;

    @Inject(method = "<init>*", at = @At("RETURN"), remap = false)
    protected void init(String pCaptionKey, double pMinValue, double pMaxValue, float pSteps, Function<Options, Double> pGetter, BiConsumer<Options, Double> pSetter, BiFunction<Options, ProgressOption, Component> pToString, Function<Minecraft, List<FormattedCharSequence>> pTooltipSupplier, CallbackInfo info) {
        if (pCaptionKey.equals("options.gamma")) {
            this.minValue = BetterBrightnessSlider.min;
            this.maxValue = BetterBrightnessSlider.max;
            this.setter = this::setter;
            this.toString = this::toString;
        }
    }

    private void setter(Options options, Double brightness) {
        brightness = Math.round(brightness / BetterBrightnessSlider.interval) * BetterBrightnessSlider.interval;
        Minecraft.getInstance().options.gamma = brightness;
    }

    private Component toString(Options options, ProgressOption progressOption) {
        return new TranslatableComponent("options.gamma").append(": ").append(new TextComponent(Math.round(options.gamma * 100) + "%"));
    }
}
