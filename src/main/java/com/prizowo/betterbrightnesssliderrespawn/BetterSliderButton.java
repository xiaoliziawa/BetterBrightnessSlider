package com.prizowo.betterbrightnesssliderrespawn;

import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.AbstractOptionSliderButton;
import net.minecraft.network.chat.Component;

import java.util.function.Consumer;

public class BetterSliderButton extends AbstractOptionSliderButton {
    private final OptionInstance<Double> instance;
    private final OptionInstance.TooltipSupplier<Double> tooltipSupplier;
    private final Consumer<Double> onValueChanged;

    public BetterSliderButton(Options options, int x, int y, int width, int height, OptionInstance<Double> instance, OptionInstance.TooltipSupplier<Double> tooltipSupplier, Consumer<Double> onValueChanged) {
        super(options, x, y, width, height, BetterSlider.INSTANCE.toSliderValue(instance.get()));
        this.instance = instance;
        this.tooltipSupplier = tooltipSupplier;
        this.onValueChanged = onValueChanged;
        this.updateMessage();
    }

    @Override
    protected void updateMessage() {
        double value = BetterSlider.INSTANCE.fromSliderValue(this.value);
        this.setMessage(Component.literal(String.format("%.2f", value)));
        this.setTooltip(this.tooltipSupplier.apply(value));
    }

    @Override
    protected void applyValue() {
        double newValue = BetterSlider.INSTANCE.fromSliderValue(this.value);
        this.instance.set(newValue);
        this.onValueChanged.accept(newValue);
    }
}
