package jp.msfblue1.theanonymous;

import jp.msfblue1.theanonymous.Config.Config;
import jp.msfblue1.theanonymous.EventHandler.PlayersContainer;
import jp.msfblue1.theanonymous.EventHandler.PlayerJoinQuit;
import jp.msfblue1.theanonymous.NMS.NMSAdapter;
import jp.msfblue1.theanonymous.NMS.NMSManager;
import jp.msfblue1.theanonymous.api.MultiverseCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;


public class TheAnonymous extends JavaPlugin {

	private static NMSManager nmsmanager;
	//private static PlayersContainer playercontainer;
	private static Config config;
	private static TheAnonymous instance;
	public static TheAnonymous getInstance() {
		return instance;
	}

	//private SkinFactory factory;

	public static void log(String msg){
		String prefix = ChatColor.DARK_GRAY+"["+ChatColor.BLUE+"TheAnonymous"+ChatColor.DARK_GRAY+"] "+ChatColor.RESET;
		Bukkit.getConsoleSender().sendMessage(prefix+msg);
	}

	//public SkinFactory getFactory() {
	//	return factory;
	//}


	@Override
	public void onEnable() {
		config = new Config(this);
		//playercontainer = new PlayersContainer();

		instance = this;
		//factory = new UniversalSkinFactory();

		nmsmanager = new NMSManager(Bukkit.getBukkitVersion());

		MultiverseCore.init();
		if (MultiverseCore.check()){
			log("Multiverse-Coreが検出されました。");
		}

		Bukkit.getPluginManager().registerEvents(new PlayerJoinQuit(),this);

	}

	public static Config getConfigData() {
		return config;
	}

//	public static PlayersContainer getPlayerContainer(){
//		return playercontainer;
//	}

	public static NMSAdapter getNMSAdapeter(){
		if(nmsmanager != null && nmsmanager.getNMSAdapter() != null){
			return nmsmanager.getNMSAdapter();
		}else{
			return null;
		}
	}

}
