package jp.msfblue1.theanonymous.EventHandler;

import jp.msfblue1.theanonymous.TheAnonymous;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

/**
 * Created by msfblue1 on 2017/11/27.
 */
public class MessageTimerManager {
    public static void register(){
        Bukkit.getScheduler().runTaskTimerAsynchronously(TheAnonymous.getInstance(),()->{
            Bukkit.getOnlinePlayers().forEach(p->{
                if(p.getFirstPlayed() < 900000L){
                    if(!TheAnonymous.getPlayerContainer().notallowkill.contains(p)){
                        Bukkit.getScheduler().runTaskAsynchronously(TheAnonymous.getInstance(),()->{
                            p.sendTitle(ChatColor.GREEN+"あなたはセーフタイム中です","誰もあなたを攻撃することはできません",7,25,7);
                            Bukkit.getScheduler().runTaskLater(TheAnonymous.getInstance(),()->{

                            },45);
                        });
                        TheAnonymous.getPlayerContainer().notallowkill.add(p);
                    }
                }else {
                    if (TheAnonymous.getPlayerContainer().notallowkill.contains(p)){

                    }
                }
            });
        },0,1);
    }
}
