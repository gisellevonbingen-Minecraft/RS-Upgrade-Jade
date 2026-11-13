package giselle.rs_upgrade_jade;

import java.awt.Dimension;

import com.mojang.blaze3d.matrix.MatrixStack;

import mcp.mobius.waila.api.ICommonAccessor;
import mcp.mobius.waila.api.ITooltipRenderer;
import mcp.mobius.waila.api.RenderContext;
import mcp.mobius.waila.overlay.DisplayUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.util.Constants.NBT;

public class UpgradesTooltipRenderer implements ITooltipRenderer
{
	public static final UpgradesTooltipRenderer INSTANCE = new UpgradesTooltipRenderer();

	@Override
	public void draw(CompoundNBT compound, ICommonAccessor accessor, int x, int y)
	{
		MatrixStack matrixStack = RenderContext.matrixStack;
		FontRenderer font = Minecraft.getInstance().font;
		ListNBT data = compound.getList("data", NBT.TAG_COMPOUND);
		float iconScale = compound.getFloat("iconScale");
		boolean textBold = compound.getBoolean("textBold");
		int textColor = compound.getInt("textColor");
		float iconX = x;

		for (int slot = 0; slot < data.size(); slot++)
		{
			ItemStack stack = ItemStack.of(data.getCompound(slot));

			if (stack.isEmpty())
			{
				continue;
			}

			ITextComponent text = createCountText(stack.getCount(), textBold, textColor);
			float iconLength = 16.0F * iconScale;
			float textHeight = font.lineHeight + 1;
			float textYOffset = Math.max((iconLength - textHeight) / 2.0F, 0.0F);
			stack.setCount(1);
			DisplayUtil.renderStack(matrixStack, (int) iconX, y, stack, iconScale);
			font.draw(matrixStack, text, iconX + iconLength, y + textYOffset, textColor);
			iconX += iconLength + font.width(text);
		}

	}

	@Override
	public Dimension getSize(CompoundNBT compound, ICommonAccessor accessor)
	{
		FontRenderer font = Minecraft.getInstance().font;
		ListNBT data = compound.getList("data", NBT.TAG_COMPOUND);
		float iconScale = compound.getFloat("iconScale");
		boolean textBold = compound.getBoolean("textBold");
		int textColor = compound.getInt("textColor");
		float width = 0.0F;
		float height = 0.0F;

		for (int slot = 0; slot < data.size(); slot++)
		{
			ItemStack stack = ItemStack.of(data.getCompound(slot));

			if (stack.isEmpty())
			{
				continue;
			}

			ITextComponent text = createCountText(stack.getCount(), textBold, textColor);
			float iconLength = 16.0F * iconScale;
			float textHeight = font.lineHeight + 1;
			width += iconLength + font.width(text);
			height = Math.max(height, Math.max(iconLength, textHeight));
		}

		return new Dimension((int) Math.ceil(width), (int) Math.ceil(height));
	}

	private static ITextComponent createCountText(int count, boolean textBold, int textColor)
	{
		return new StringTextComponent("x" + count).withStyle(style ->
		{
			if (textBold)
			{
				style = style.withBold(textBold);
			}

			return style.withColor(Color.fromRgb(textColor));
		});
	}

	private UpgradesTooltipRenderer()
	{

	}

}
