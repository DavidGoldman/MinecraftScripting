package scripting.forge;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

import scripting.gui.MainScreen;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;

public class KeyListener {
	
	private final KeyBinding scriptGUI;
	
	public KeyListener() {
		scriptGUI = new KeyBinding("key.script.gui", Keyboard.KEY_F7, "key.categories.misc");
		ClientRegistry.registerKeyBinding(scriptGUI);
	}
	
	@SubscribeEvent
	public void onKeyInput(KeyInputEvent event) {
		if (Minecraft.getMinecraft().currentScreen == null && scriptGUI.isPressed())
			Minecraft.getMinecraft().displayGuiScreen(new MainScreen());
	}
}
