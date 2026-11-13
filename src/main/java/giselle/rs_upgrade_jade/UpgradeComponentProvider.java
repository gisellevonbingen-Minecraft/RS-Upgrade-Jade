package giselle.rs_upgrade_jade;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.TooltipPosition;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElement;
import snownee.jade.api.ui.IElementHelper;

public class UpgradeComponentProvider implements IBlockComponentProvider
{
	public static UpgradeComponentProvider INSTANCE = new UpgradeComponentProvider();

	@Override
	public ResourceLocation getUid()
	{
		return JadePlugin.TOOLTIP_ID;
	}

	@Override
	public int getDefaultPriority()
	{
		return TooltipPosition.BODY;
	}

	@Override
	public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config)
	{
		CompoundTag serverData = accessor.getServerData();

		if (serverData.contains(JadePlugin.TOOLTIP_ID_STRING, Tag.TAG_LIST))
		{
			ListTag data = accessor.getServerData().getList(JadePlugin.TOOLTIP_ID_STRING, Tag.TAG_COMPOUND);
			tooltip.add(Component.empty());

			float iconScale = config.getFloat(JadePlugin.CONFIG_ICON_SCALE);
			boolean textBold = config.get(JadePlugin.CONFIG_TEXT_BOLD);
			int textColor = config.getInt(JadePlugin.CONFIG_TEXT_COLOR);
			IElementHelper elementHelper = IElementHelper.get();

			for (int slot = 0; slot < data.size(); slot++)
			{
				ItemStack stack = ItemStack.of(data.getCompound(slot));

				if (stack.isEmpty())
				{
					continue;
				}

				IElement icon = elementHelper.item(stack, iconScale, "");
				IElement text = elementHelper.text(createCountText(stack.getCount(), textBold, textColor));
				float iconLength = 16.0F * iconScale;
				float textHeight = text.getCachedSize().y;
				icon.size(new Vec2(iconLength, iconLength));
				text.translate(new Vec2(0.0F, Math.max((iconLength - textHeight) / 2.0F, 0.0F)));
				tooltip.append(icon);
				tooltip.append(text);
			}

		}

	}

	private static Component createCountText(int count, boolean textBold, int textColor)
	{
		return Component.literal("x" + count).withStyle(style ->
		{
			if (textBold)
			{
				style = style.withBold(textBold);
			}

			return style.withColor(textColor);
		});
	}

	private UpgradeComponentProvider()
	{

	}

}
