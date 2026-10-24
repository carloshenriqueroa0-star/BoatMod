package com.boatmod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BoatModFabric implements ModInitializer {
    public static final String MODID = "boatmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
    public static final ResourceLocation BOOST_PACKET = new ResourceLocation(MODID, "boost");

    @Override
    public void onInitialize() {
        LOGGER.info("[BoatMod] Inicializando - Barco com Remos e Turbo de Baiacu!");

        // Registro do item Remo
        ModItems.register();

        // Evento: Clique direito em barco com Remo/Baiacu
        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (!(entity instanceof Boat boat)) return InteractionResult.PASS;
            if (world.isClientSide) return InteractionResult.PASS;

            var stack = player.getItemInHand(hand);
            var data = boat.getPersistentData();

            // === ADICIONAR REMO ===
            // Aceita nosso item custom ou graveto/pá vanilla para facilitar teste
            boolean isOar = stack.is(ModItems.OAR) || stack.is(Items.STICK) || stack.is(Items.WOODEN_SHOVEL);
            if (isOar && !player.isShiftKeyDown()) {
                int oars = data.getInt("boatmod_oars");
                if (oars == 0) oars = 1;
                if (oars >= 12) {
                    player.displayClientMessage(Component.literal("§cJá tem 12 remos (máximo)!"), true);
                    return InteractionResult.FAIL;
                }
                if (!player.isCreative()) stack.shrink(1);
                data.putInt("boatmod_oars", oars + 1);
                world.playSound(null, boat.blockPosition(), SoundEvents.WOOD_PLACE, SoundSource.PLAYERS, 1f, 1.2f);
                player.displayClientMessage(Component.literal("§a+1 Remo! Total: " + (oars+1) + "/12 §7Velocidade +" + ((oars)*15) + "%"), true);
                return InteractionResult.SUCCESS;
            }

            // === ADICIONAR BAIACU ===
            if (stack.is(Items.PUFFERFISH)) {
                int puffers = data.getInt("boatmod_puffers");
                if (puffers >= 4) {
                    player.displayClientMessage(Component.literal("§cMáximo de 4 Baiacus!"), true);
                    return InteractionResult.FAIL;
                }
                if (!player.isCreative()) stack.shrink(1);
                data.putInt("boatmod_puffers", puffers + 1);
                world.playSound(null, boat.blockPosition(), SoundEvents.PUFFER_FISH_BLOW_UP, SoundSource.PLAYERS, 1f, 1f);
                player.displayClientMessage(Component.literal("§e+1 Baiacu Propulsor! Total: " + (puffers+1) + "/4 §b[Aperte G para turbo]"), true);
                return InteractionResult.SUCCESS;
            }

            // === REMOVER REMO (Shift + mão vazia) ===
            if (player.isShiftKeyDown() && stack.isEmpty()) {
                int oars = data.getInt("boatmod_oars");
                if (oars == 0) oars = 1;
                if (oars > 1) {
                    data.putInt("boatmod_oars", oars - 1);
                    player.getInventory().add(new net.minecraft.world.item.ItemStack(ModItems.OAR));
                    player.displayClientMessage(Component.literal("§7-1 Remo removido. Total: " + (oars-1)), true);
                    return InteractionResult.SUCCESS;
                }
            }

            return InteractionResult.PASS;
        });

        // Rede: recebe pedido de turbo do cliente (tecla G)
        ServerPlayNetworking.registerGlobalReceiver(BOOST_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> tryBoost(player));
        });
    }

    // Lógica de velocidade (+15% por remo) - chamada todo tick via Mixin
    public static void applyOarSpeed(Boat boat) {
        if (boat.getControllingPassenger() == null || !boat.isInWater()) return;
        var data = boat.getPersistentData();
        int oars = data.getInt("boatmod_oars");
        if (oars == 0) oars = 1;
        if (oars <= 1) return;
        Vec3 motion = boat.getDeltaMovement();
        if (motion.lengthSqr() < 0.001) return;
        double bonus = 1.0 + (oars - 1) * 0.15;
        Vec3 boosted = new Vec3(motion.x * bonus, motion.y, motion.z * bonus);
        boat.setDeltaMovement(boosted);
    }

    // Turbo de baiacu
    public static void tryBoost(ServerPlayer player) {
        if (!(player.getVehicle() instanceof Boat boat)) return;
        var data = boat.getPersistentData();
        int puffers = data.getInt("boatmod_puffers");
        if (puffers <= 0) {
            player.displayClientMessage(Component.literal("§cAdicione Baiacu no barco primeiro! (segure baiacu + clique direito)"), true);
            return;
        }
        ServerLevel level = player.serverLevel();
        double power = 0.8 + puffers * 0.6; // 1.4 a 3.2
        Vec3 look = boat.getLookAngle();
        Vec3 boost = new Vec3(look.x * power, 0.2, look.z * power);
        boat.setDeltaMovement(boat.getDeltaMovement().add(boost));
        boat.hasImpulse = true;

        for (int i = 0; i < 25 + puffers * 12; i++) {
            double x = boat.getX() - look.x * 1.2 + (level.random.nextDouble() - 0.5);
            double y = boat.getY() + 0.9;
            double z = boat.getZ() - look.z * 1.2 + (level.random.nextDouble() - 0.5);
            level.sendParticles(ParticleTypes.CLOUD, x, y, z, 1, -look.x * 0.4, 0.05, -look.z * 0.4, 0.15);
        }
        level.playSound(null, boat.blockPosition(), SoundEvents.FIREWORK_ROCKET_LAUNCH, net.minecraft.sounds.SoundSource.PLAYERS, 1f, 1.2f);
    }
}
