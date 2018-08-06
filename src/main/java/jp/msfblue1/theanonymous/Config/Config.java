package jp.msfblue1.theanonymous.Config;

import jp.msfblue1.theanonymous.TheAnonymous;

/**
 * Created by msfblue1 on 2017/11/27.
 */
public class Config {
    private Long firstplayerstime = 0L;

    public Config (TheAnonymous me){
        this.firstplayerstime = Long.valueOf(me.getConfig().getString("firstplayerstime"));
    }
}
