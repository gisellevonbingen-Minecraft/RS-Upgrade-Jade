package giselle.rs_upgrade_jade;

import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;

@snownee.jade.api.WailaPlugin
public class JadePlugin implements IWailaPlugin
{
	public static final ResourceLocation TOOLTIP_ID = RS_UpgradeJade.rl("tooltip");
	public static final String TOOLTIP_ID_STRING = TOOLTIP_ID.toString();

	public static final ResourceLocation CONFIG_ICON_SCALE = RS_UpgradeJade.rl("icon_scale");
	public static final ResourceLocation CONFIG_TEXT_BOLD = RS_UpgradeJade.rl("text_bold");
	public static final ResourceLocation CONFIG_TEXT_COLOR = RS_UpgradeJade.rl("text_color");

	@Override
	public void register(IWailaCommonRegistration registrar)
	{
		registrar.registerBlockDataProvider(UpgradeServerDataProvider.INSTANCE, BlockEntity.class);
	}

	@Override
	public void registerClient(IWailaClientRegistration registrar)
	{
		registrar.addConfig(CONFIG_ICON_SCALE, 0.5F, 0.5F, 1.0F, true);
		registrar.addConfig(CONFIG_TEXT_BOLD, false);
		registrar.addConfig(CONFIG_TEXT_COLOR,0x9E9E9E, 0x000000, 0xFFFFFF, false);
		
		registrar.registerBlockComponent(UpgradeComponentProvider.INSTANCE, Block.class);
	}

}
