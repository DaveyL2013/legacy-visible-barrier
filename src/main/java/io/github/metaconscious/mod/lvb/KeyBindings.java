package io.github.metaconscious.mod.lvb;

import net.minecraft.client.Minecraft;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.text.TranslatableText;
import net.ornithemc.osl.keybinds.api.KeyBindingEvents;
import net.ornithemc.osl.lifecycle.api.client.MinecraftClientEvents;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

public final class KeyBindings {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final TranslatableText TO_VISIBLE = new TranslatableText("lvb.to_visible");
    private static final TranslatableText TO_INVISIBLE = new TranslatableText("lvb.to_invisible");

    private final KeyBinding barrierVisibilityTogglingKey = new KeyBinding(
            "key.toggle_barrier_visibility",
            Keyboard.KEY_B,
            "key.category.legacy_visible_barriers"
    );

    private final VisibilityController controller = new VisibilityController();

    public void init() {
        KeyBindingEvents.REGISTER_KEYBINDS.register(registry -> {
            KeyBinding barrierVisibilityTogglingKey = registry.register(this.barrierVisibilityTogglingKey);
            LOGGER.info(LegacyVisibleBarrierMod.MOD_ID + " key binding registered.");
        });
        MinecraftClientEvents.TICK_END.register(this::onBarrierVisibilityToggled);
        LOGGER.info(LegacyVisibleBarrierMod.MOD_ID + " keypress event listener registered.");
    }

    public Visibility getVisibilityViewer() {
        return controller;
    }

    private void onBarrierVisibilityToggled(Minecraft minecraftClient) {
        while (barrierVisibilityTogglingKey.consumeClick()) {
            boolean visibleNow = controller.toggle();
            if (visibleNow) {
                minecraftClient.player.sendMessage(TO_VISIBLE);
            } else {
                minecraftClient.player.sendMessage(TO_INVISIBLE);
            }
            minecraftClient.worldRenderer.reload();
            LOGGER.debug("Barriers should now be '{}'.", visibleNow ? "visible" : "invisible");
        }
    }

    public interface Visibility {
        boolean isVisible();
    }

    private static final class VisibilityController implements Visibility {

        private boolean visible = false;

        public boolean toggle() {
            this.visible = !this.visible;
            return this.visible;
        }

        @Override
        public boolean isVisible() {
            return visible;
        }
    }
}
