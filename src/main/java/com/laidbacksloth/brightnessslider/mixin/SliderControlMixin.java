package com.laidbacksloth.brightnessslider.mixin;

import com.laidbacksloth.brightnessslider.BetterBrightnessSlider;
import me.jellysquid.mods.sodium.client.gui.options.Option;
import me.jellysquid.mods.sodium.client.gui.options.control.ControlValueFormatter;
import me.jellysquid.mods.sodium.client.gui.options.control.SliderControl;
import net.minecraft.util.text.TranslationTextComponent;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Pseudo
@Mixin(SliderControl.class)
public class SliderControlMixin {
    @Shadow
    @Final
    @Mutable
    private int min;

    @Shadow
    @Final
    @Mutable
    private int max;

    @Shadow
    @Final
    @Mutable
    private int interval;

    @Inject(method = "<init>", at = @At("RETURN"), remap = false)
    public void init(Option<Integer> option, int min, int max, int interval, ControlValueFormatter mode, CallbackInfo info) {
        if (Objects.equals(option.getName(), new TranslationTextComponent("options.gamma").getString())) {
            this.min = (int) (BetterBrightnessSlider.min * 100);
            this.max = (int) (BetterBrightnessSlider.max * 100);
            this.interval = (int) (BetterBrightnessSlider.interval * 100);
        }
    }
}
