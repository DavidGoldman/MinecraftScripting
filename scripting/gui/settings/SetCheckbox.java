package scripting.gui.settings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import scripting.wrapper.settings.SettingBoolean;

import com.mcf.davidee.guilib.core.Checkbox;
import com.mcf.davidee.guilib.core.Scrollbar.Shiftable;

public class SetCheckbox extends Checkbox implements ISetting, Shiftable {
	

	private static final ResourceLocation TEXTURE = new ResourceLocation("guilib", "textures/gui/checkbox.png");
	public static final int SIZE = 10;

	private final SettingBoolean setting;
	
	public SetCheckbox(SettingBoolean setting) {
		super(SIZE + 8 + Minecraft.getMinecraft().fontRenderer.getStringWidth(setting.display), SIZE, setting.display);
		
		this.check = setting.enabled;
		this.setting = setting;
	}
	
	@Override
	public void setPosition(int x, int y) {
		//X is the center
		super.setPosition(x - width/2, y);
	}
	
	@Override
	public void applySetting() {
		setting.enabled = check;
	}

	@Override
	public void shiftY(int dy) {
		this.y += dy;
	}
	
	@Override
	public void handleClick(int mx, int my) {
		mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
		super.handleClick(mx, my);
	}


	@Override
	public void draw(int mx, int my) {
		mc.renderEngine.bindTexture(TEXTURE);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		drawTexturedModalRect(x + width - SIZE - 1, y, 0, check ? SIZE : 0, SIZE, SIZE);
		mc.fontRenderer.drawStringWithShadow(str, x + 2, y + 1, (inBounds(mx, my)) ? 16777120 : 0xffffff);
	}

}
