package jp.msfblue1.theanonymous.api;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

import com.onarandombox.MultiverseCore.MultiverseCore;

public class MultiverseCore {

	private static com.onarandombox.MultiverseCore.MultiverseCore mcore = null;

	public static boolean check() {
		if (mcore != null)
			return true;
		return false;
	}

	//Multiverse-Core dimensions transfer :D
	public static int dimension(World world) {
		if (getWorldScale(world) == 1||getWorldScale(world) == 14)
			return 0;
		if (getWorldScale(world) == 8||getWorldScale(world) == 13)
			return -1;
		if (getWorldScale(world) == 16||getWorldScale(world) == 12)
			return 1;
		return 0;
	}

	public static double getWorldScale(World world) {
		return mcore.getMVWorldManager().getMVWorld(world).getScaling();
	}

	public static void init() {
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
		if (plugin instanceof com.onarandombox.MultiverseCore.MultiverseCore)
			mcore = (com.onarandombox.MultiverseCore.MultiverseCore) plugin;
	}
}
