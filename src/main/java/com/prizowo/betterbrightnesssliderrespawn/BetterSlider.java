package com.prizowo.betterbrightnesssliderrespawn;

import com.mojang.serialization.Codec;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.Options;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class BetterSlider implements OptionInstance.ValueSet<Double> {
    public static final BetterSlider INSTANCE = new BetterSlider();

    private BetterSlider() {}

    @Override
    public Function<OptionInstance<Double>, AbstractWidget> createButton(OptionInstance.TooltipSupplier<Double> tooltipSupplier, Options options, int x, int y, int width, Consumer<Double> onValueChanged) {
        return (optionInstance) -> new BetterSliderButton(options, x, y, width, 20, optionInstance, tooltipSupplier, onValueChanged);
    }

    @Override
    public Optional<Double> validateValue(Double value) {
        return value >= Betterbrightnesssliderrespawn.min && value <= Betterbrightnesssliderrespawn.max ? Optional.of(value) : Optional.empty();
    }

    @Override
    public Codec<Double> codec() {
        return Codec.DOUBLE.xmap(
                value -> Math.max(Betterbrightnesssliderrespawn.min, Math.min(Betterbrightnesssliderrespawn.max, value)),
                value -> value
        );
    }

    public double toSliderValue(Double value) {
        return (value - Betterbrightnesssliderrespawn.min) / (Betterbrightnesssliderrespawn.max - Betterbrightnesssliderrespawn.min);
    }

    public Double fromSliderValue(double value) {
        return value * (Betterbrightnesssliderrespawn.max - Betterbrightnesssliderrespawn.min) + Betterbrightnesssliderrespawn.min;
    }
}
