package scripting.forge;

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

import scripting.gui.MainScreen;
import scripting.packet.ScriptPacket;
import scripting.packet.ScriptPacket.PacketType;
import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class KeyListener extends KeyHandler {
	
	public KeyListener() {
		super(new KeyBinding[] { new KeyBinding("key.scriptgui",Keyboard.KEY_F7)}, new boolean[] {false} );
		LanguageRegistry.instance().addStringLocalization("key.scriptgui", "Script GUI");
	}

	@Override
	public String getLabel() {
		return "scripting.keylistener";
	}

	@Override
	public void keyDown(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd, boolean isRepeat) {
		

	}

	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd) {
		if (Minecraft.getMinecraft().currentScreen == null) 
			Minecraft.getMinecraft().displayGuiScreen(new MainScreen());
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.CLIENT);
	}

}
