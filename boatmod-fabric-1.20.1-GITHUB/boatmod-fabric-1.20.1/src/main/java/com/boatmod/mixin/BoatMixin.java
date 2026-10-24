package com.boatmod.mixin;

import com.boatmod.BoatModFabric;
import net.minecraft.world.entity.vehicle.Boat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Injeta no tick do barco para aplicar bônus de velocidade dos remos
@Mixin(Boat.class)
public class BoatMixin {
    @Inject(method = "tick", at = @At("TAIL"))
    private void boatmod$applyOarSpeed(CallbackInfo ci) {
        Boat boat = (Boat) (Object) this;
        BoatModFabric.applyOarSpeed(boat);
    }
}
