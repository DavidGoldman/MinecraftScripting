package scripting;

import java.lang.reflect.Field;
import java.util.Map;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;

public class ReflectionHelper {

	private static final int POTION_FIELD_INDEX = 51;

	public static Map<Class<?>, String> tileClassToString = null;
	public static Field potionsNeedUpdate = null;

	public static void init() throws RuntimeException {
		try {
			potionsNeedUpdate = EntityLivingBase.class.getDeclaredFields()[POTION_FIELD_INDEX];
			potionsNeedUpdate.setAccessible(true);
			tileClassToString = cpw.mods.fml.relauncher.ReflectionHelper.getPrivateValue(TileEntity.class, null, 1);
		}
		catch(RuntimeException e) { throw e; }
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
}
