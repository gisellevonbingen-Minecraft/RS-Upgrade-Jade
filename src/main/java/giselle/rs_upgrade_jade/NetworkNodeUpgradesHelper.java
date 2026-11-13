package giselle.rs_upgrade_jade;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.refinedmods.refinedstorage.api.network.node.INetworkNode;

import net.minecraftforge.items.IItemHandler;

public final class NetworkNodeUpgradesHelper
{
	private static final Map<Class<? extends INetworkNode>, Optional<Method>> UPGRADES_METHODS = new HashMap<>();
	private static final Map<Class<? extends INetworkNode>, Optional<Field>> UPGRADES_FIELDS = new HashMap<>();

	public static IItemHandler getUpgrades(INetworkNode node)
	{
		Class<? extends INetworkNode> clazz = node.getClass();
		Optional<Method> method = UPGRADES_METHODS.computeIfAbsent(clazz, NetworkNodeUpgradesHelper::findUpgradesMethod);

		if (method.isPresent())
		{
			try
			{
				return (IItemHandler) method.get().invoke(node);
			}
			catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
			{

			}

		}

		Optional<Field> field = UPGRADES_FIELDS.computeIfAbsent(clazz, NetworkNodeUpgradesHelper::findUpgradesField);

		if (field.isPresent())
		{
			try
			{
				return (IItemHandler) field.get().get(node);
			}
			catch (IllegalAccessException | IllegalArgumentException e)
			{

			}

		}

		return null;
	}

	private static Optional<Method> findUpgradesMethod(Class<? extends INetworkNode> clazz)
	{
		try
		{
			return Optional.of(clazz.getMethod("getUpgrades"));
		}
		catch (NoSuchMethodException | SecurityException e)
		{
			return Optional.empty();
		}

	}

	private static Optional<Field> findUpgradesField(Class<? extends INetworkNode> clazz)
	{
		try
		{
			Field field = clazz.getDeclaredField("upgrades");
			field.setAccessible(true);
			return Optional.of(field);
		}
		catch (NoSuchFieldException | SecurityException e)
		{
			return Optional.empty();
		}

	}

	private NetworkNodeUpgradesHelper()
	{

	}

}
