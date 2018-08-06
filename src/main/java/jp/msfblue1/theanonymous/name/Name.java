package jp.msfblue1.theanonymous.name;

import jp.msfblue1.theanonymous.NMS.NMSManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by msfblue1 on 2017/11/16.
 */
public class Name {

    private static String encrypt(String value) {
        String text = value;
        byte[] cipher_byte;
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(text.getBytes());
            cipher_byte = md.digest();
            StringBuilder sb = new StringBuilder(2 * cipher_byte.length);
            for(byte b: cipher_byte) {
                sb.append(String.format("%02x", b&0xff) );
            }
            String substring = sb.toString().substring(0, 8);
            return substring;
        } catch (Exception e) {
            e.printStackTrace();
            return "0000x0000";
        }
    }

    public static String toAnonymousName(String value){
        return encrypt(value);
    }

    public static void setName(Player p,String name){
        //NMSManager.getNMSAdapter(Bukkit.getBukkitVersion()).setName(p,name);
    }
}
