package com.jiangzuomeng.module;

/**
 * Created by ekuri-PC on 2015/11/21.
 */
public class Travel {
    public int id;
    public int mainTravelItemId;
    public int firstTravelItemId;
    public int lastTravelId;
    public int nextTravelId;

    public Travel (int id, int mainTravelItemId, int firstTravelItemId, int lastTravelId,
                   int nextTravelId) {
        this.id = id;
        this.mainTravelItemId = mainTravelItemId;
        this.firstTravelItemId = firstTravelItemId;
        this.lastTravelId = lastTravelId;
        this.nextTravelId = nextTravelId;
    }
}
