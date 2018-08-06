package jp.msfblue1.theanonymous.NMS;

import org.bukkit.entity.Player;

/**
 * Created by msfblue1 on 2017/11/18.
 */
public interface NMSAdapter {
    void setName(Player p);
    void sendNavTitle(Player p,String Mes);
    void setSkin(Player player);
    String encryptName(String value);
}
