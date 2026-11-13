package giselle.rs_upgrade_jade;

import java.util.List;

import com.refinedmods.refinedstorage.common.api.configurationcard.ConfigurationCardTarget;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.neoforged.neoforge.items.ItemStackHandler;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IServerDataProvider;

public class UpgradeServerDataProvider implements IServerDataProvider<BlockAccessor>
{
	public static UpgradeServerDataProvider INSTANCE = new UpgradeServerDataProvider();

	@Override
	public ResourceLocation getUid()
	{
		return JadePlugin.TOOLTIP_ID;
	}

	@Override
	public void appendServerData(CompoundTag compound, BlockAccessor accessor)
	{
		BlockEntity blockEntity = accessor.getBlockEntity();

		if (blockEntity instanceof ConfigurationCardTarget target)
		{
			List<ItemStack> upgrades = target.getUpgrades();

			if (upgrades == null)
			{
				return;
			}

			int slots = upgrades.size();
			ItemStackHandler merged = new ItemStackHandler(slots);

			for (int slot = 0; slot < slots; slot++)
			{
				ItemStack stack = upgrades.get(slot);

				if (stack.isEmpty())
				{
					continue;
				}

				ItemHandlerHelper.insertItemStacked(merged, stack.copy(), false);
			}

			ListTag data = new ListTag();

			for (int slot = 0; slot < slots; slot++)
			{
				ItemStack stack = merged.getStackInSlot(slot);

				if (stack.isEmpty())
				{
					continue;
				}

				data.add(stack.save(accessor.getLevel().registryAccess()));
			}

			if (data.size() > 0)
			{
				compound.put(JadePlugin.TOOLTIP_ID_STRING, data);
			}

		}

	}

	private UpgradeServerDataProvider()
	{

	}

}
