package com.boatmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class BoatModClient implements ClientModInitializer {
    private static KeyMapping boostKey;

    @Override
    public void onInitializeClient() {
        boostKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.boatmod.boost",
                GLFW.GLFW_KEY_G,
                "category.boatmod"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;
            if (boostKey.consumeClick() && client.player.getVehicle() instanceof net.minecraft.world.entity.vehicle.Boat) {
                ClientPlayNetworking.send(BoatModFabric.BOOST_PACKET, PacketByteBufs.empty());
            }
        });
    }
}
