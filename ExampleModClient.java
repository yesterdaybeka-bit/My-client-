package com.example;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ExampleModClient implements ClientModInitializer {
    private static KeyBinding flyKey;
    private static boolean flyOn = false;

    @Override
    public void onInitializeClient() {
        flyKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.myclient.fly", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_G, "category.myclient"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;
            while (flyKey.wasPressed()) {
                flyOn = !flyOn;
                if (!flyOn && !client.player.isCreative() && !client.player.isSpectator()) {
                    client.player.getAbilities().allowFlying = false;
                    client.player.getAbilities().flying = false;
                }
            }
            if (flyOn) client.player.getAbilities().allowFlying = true;
        });
    }
}
