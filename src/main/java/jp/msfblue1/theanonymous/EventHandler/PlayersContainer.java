package jp.msfblue1.theanonymous.EventHandler;

import org.bukkit.entity.Player;

import java.util.*;

/**
 * Created by msfblue1 on 2017/11/27.
 */
public class PlayersContainer {
    public List<Player> notallowkill = new ArrayList<>();
    public Map<String,UUID> realname = new LinkedHashMap<>();
    public Map<Player,Message> messagelist = new HashMap<>();

    public class Message{
        List<String> messages = new LinkedList<>();
    }
}
