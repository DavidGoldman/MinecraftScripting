package scripting;

import java.lang.reflect.Field;
import java.util.Map;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;

public class ReflectionHelper {

	private static final int POTION_FIELD_INDEX = 51;
	private static final int CLASS_TO_STRING_INDEX = 2;

	public static Map<Class<?>, String> tileClassToString = null;
	public static Field potionsNeedUpdate = null;

	public static void init() throws RuntimeException {
		try {
			potionsNeedUpdate = EntityLivingBase.class.getDeclaredFields()[POTION_FIELD_INDEX];
			potionsNeedUpdate.setAccessible(true);
			tileClassToString = cpw.mods.fml.relauncher.ReflectionHelper.getPrivateValue(TileEntity.class, null, CLASS_TO_STRING_INDEX);
		}
		catch(RuntimeException e) { throw e; }
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
}
