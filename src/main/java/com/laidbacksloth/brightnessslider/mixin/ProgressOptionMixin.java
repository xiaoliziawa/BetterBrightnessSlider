package com.laidbacksloth.brightnessslider.mixin;

import com.laidbacksloth.brightnessslider.BetterBrightnessSlider;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.SliderPercentageOption;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

@Mixin(SliderPercentageOption.class)
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
    private BiConsumer<GameSettings, Double> setter;

    @Shadow
    @Final
    @Mutable
    private BiFunction<GameSettings, SliderPercentageOption, ITextComponent> toString;

    @Inject(method = "<init>*", at = @At("RETURN"), remap = false)
    protected void init(String pCaptionKey, double pMinValue, double pMaxValue, float pSteps, Function<GameSettings, Double> pGetter, BiConsumer<GameSettings, Double> pSetter, BiFunction<GameSettings, SliderPercentageOption, ITextComponent> pToString, CallbackInfo info) {
        if (pCaptionKey.equals("options.gamma")) {
            this.minValue = BetterBrightnessSlider.min;
            this.maxValue = BetterBrightnessSlider.max;
            this.setter = this::setter;
            this.toString = this::toString;
        }
    }

    private void setter(GameSettings options, Double brightness) {
        brightness = Math.round(brightness / BetterBrightnessSlider.interval) * BetterBrightnessSlider.interval;
        Minecraft.getInstance().options.gamma = brightness;
    }

    private ITextComponent toString(GameSettings options, SliderPercentageOption progressOption) {
        return new TranslationTextComponent("options.gamma").append(": ").append(Math.round(options.gamma * 100) + "%");
    }
}
