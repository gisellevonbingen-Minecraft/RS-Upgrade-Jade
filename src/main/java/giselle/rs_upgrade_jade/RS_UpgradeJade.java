package giselle.rs_upgrade_jade;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;

@Mod(RS_UpgradeJade.MODID)
public class RS_UpgradeJade
{
	public static final String MODID = "rs_upgrade_jade";
	public static final Logger LOGGER = LogManager.getLogger();

	public RS_UpgradeJade()
	{

	}

	public static ResourceLocation rl(String path)
	{
		return new ResourceLocation(MODID, path);
	}

}
