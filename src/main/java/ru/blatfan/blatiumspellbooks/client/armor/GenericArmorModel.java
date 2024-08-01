package ru.blatfan.blatiumspellbooks.client.armor;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import net.minecraft.resources.ResourceLocation;
import ru.blatfan.blatiumspellbooks.BlatiumSpellbooks;
import ru.blatfan.blatiumspellbooks.item.WizardArmorItem;
import software.bernie.geckolib.model.DefaultedItemGeoModel;

public class GenericArmorModel<T extends WizardArmorItem> extends DefaultedItemGeoModel<T> {

    public GenericArmorModel() {
        super(new ResourceLocation(BlatiumSpellbooks.MODID, ""));
    }

    @Override
    public ResourceLocation getModelResource(T object) {
        return new ResourceLocation(IronsSpellbooks.MODID, "geo/netherite_armor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(T object) {
        var identifier = object.getMaterial().getName();
        return new ResourceLocation(BlatiumSpellbooks.MODID, "textures/models/armor/" + identifier + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return new ResourceLocation(IronsSpellbooks.MODID, "animations/wizard_armor_animation.json");
    }
}