package com.boatmod;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ModItems {
    public static final Item OAR = Registry.register(
            BuiltInRegistries.ITEM,
            new ResourceLocation(BoatModFabric.MODID, "oar"),
            new Item(new Item.Properties().stacksTo(64))
    );

    public static void register() {
        // Registro já feito no campo estático
        BoatModFabric.LOGGER.info("[BoatMod] Item Remo registrado: boatmod:oar");
    }
}
