package jp.msfblue1.theanonymous.NMS;

import jp.msfblue1.theanonymous.TheAnonymous;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

/**
 * Created by msfblue1 on 2017/11/18.
 */
public class  NMSManager {

    private NMSAdapter adapter;

    public NMSAdapter getNMSAdapter(){
        return this.adapter;
    }

    public NMSManager(String BukkitVer) {
        switch (BukkitVer) {
            case "1.8-R0.1-SNAPSHOT":
            case "1.8.3-R0.1-SNAPSHOT":
            case "1.8.8-R0.1-SNAPSHOT":
                TheAnonymous.log("次のモードで起動されました。");
                TheAnonymous.log("モード : "+BukkitVer);
                this.adapter = new CB1_8();
                break;
            case "1.9-R0.1-SNAPSHOT":
            case "1.9.2-R0.1-SNAPSHOT":
            case "1.9.4-R0.1-SNAPSHOT":
                TheAnonymous.log("次のモードで起動されました。");
                TheAnonymous.log("モード : "+BukkitVer);
                this.adapter = new CB1_9();
                break;
            case "1.10.2-R0.1-SNAPSHOT":
                TheAnonymous.log("次のモードで起動されました。");
                TheAnonymous.log("モード : "+BukkitVer);
                this.adapter = new CB1_10();
                break;
            case "1.11-R0.1-SNAPSHOT":
            case "1.11.2-R0.1-SNAPSHOT":
                TheAnonymous.log("次のモードで起動されました。");
                TheAnonymous.log("モード : "+BukkitVer);
                this.adapter = new CB1_11();
                break;
            case "1.12-R0.1-SNAPSHOT":
            case "1.12.1-R0.1-SNAPSHOT":
            case "1.12.2-R0.1-SNAPSHOT":
                TheAnonymous.log("次のモードで起動されました。");
                TheAnonymous.log("モード : "+BukkitVer);
                this.adapter = new CB1_12();
                break;
            default:
                TheAnonymous.log(ChatColor.RED+"プラグインがNMSとのコンタクトに失敗しました。");
                TheAnonymous.log("理由: バージョンが1.8 - 1.12の範囲を超えているためクラスにアクセスできません。");
                TheAnonymous.log("マネージャーにはNullが返却されました。このプラグインはNPE例外を起こす可能性があります。");
                this.adapter = null;
                break;
        }
    }
}
