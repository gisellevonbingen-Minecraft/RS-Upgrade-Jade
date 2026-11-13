package giselle.rs_upgrade_jade;

import mcp.mobius.waila.api.BlockAccessor;
import mcp.mobius.waila.api.IComponentProvider;
import mcp.mobius.waila.api.ITooltip;
import mcp.mobius.waila.api.config.IPluginConfig;
import mcp.mobius.waila.api.ui.IElement;
import mcp.mobius.waila.api.ui.IElementHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;

public class UpgradeComponentProvider implements IComponentProvider
{
	public static UpgradeComponentProvider INSTANCE = new UpgradeComponentProvider();

	@Override
	public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config)
	{
		CompoundTag serverData = accessor.getServerData();

		if (serverData.contains(JadePlugin.TOOLTIP_ID_STRING, Tag.TAG_LIST))
		{
			ListTag data = accessor.getServerData().getList(JadePlugin.TOOLTIP_ID_STRING, Tag.TAG_COMPOUND);
			tooltip.add(TextComponent.EMPTY);

			float iconScale = ClientConfig.ICON_SCALE.get().floatValue();
			boolean textBold = ClientConfig.TEXT_BOLD.get();
			int textColor = ClientConfig.TEXT_COLOR.get();
			IElementHelper elementHelper = tooltip.getElementHelper();

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
		return new TextComponent("x" + count).withStyle(style ->
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
