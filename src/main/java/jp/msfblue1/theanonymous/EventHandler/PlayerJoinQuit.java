package jp.msfblue1.theanonymous.EventHandler;

import jp.msfblue1.theanonymous.TheAnonymous;
import jp.msfblue1.theanonymous.name.Name;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by msfblue1 on 2017/11/16.
 */
public class PlayerJoinQuit implements Listener{


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){

        //TheAnonymous.getPlayerContainer().realname.put(e.getPlayer().getName(),e.getPlayer().getUniqueId());

        if (!e.getPlayer().hasPermission("anonymous.join.nochange")) {
            String name1 = Name.toAnonymousName(e.getPlayer().getAddress().getAddress().getHostAddress()+new SimpleDateFormat("yyyy/MM/dd").format(new Date()));

            e.setJoinMessage("");
            e.getPlayer().setDisplayName(name1);
            e.getPlayer().setCustomNameVisible(true);
            e.getPlayer().setPlayerListName(name1);

            TheAnonymous.getNMSAdapeter().setName(e.getPlayer());

            Bukkit.getScheduler().runTaskAsynchronously(TheAnonymous.getInstance(),()-> TheAnonymous.getNMSAdapeter().setSkin(e.getPlayer()));


        }
    }

}
