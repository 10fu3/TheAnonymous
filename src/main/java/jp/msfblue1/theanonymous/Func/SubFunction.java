package jp.msfblue1.theanonymous.Func;

import jp.msfblue1.theanonymous.TheAnonymous;
import jp.msfblue1.theanonymous.name.Name;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by msfblue1 on 2017/12/18.
 */
public class SubFunction {
    public static void setAnonymous(Player p , PlayerJoinEvent e){
        String name1 = Name.toAnonymousName(e.getPlayer().getAddress().getAddress().getHostAddress()+new SimpleDateFormat("yyyy/MM/dd").format(new Date()));

        if(e != null){
            e.setJoinMessage("");
        }
        p.setDisplayName(name1);
        p.setCustomNameVisible(true);
        p.setPlayerListName(name1);

        TheAnonymous.getNMSAdapeter().setName(e.getPlayer());

        Bukkit.getScheduler().runTaskAsynchronously(TheAnonymous.getInstance(),()-> TheAnonymous.getNMSAdapeter().setSkin(e.getPlayer()));


    }
}
