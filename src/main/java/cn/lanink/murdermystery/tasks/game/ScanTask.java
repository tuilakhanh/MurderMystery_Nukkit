package cn.lanink.murdermystery.tasks.game;

import cn.lanink.murdermystery.MurderMystery;
import cn.lanink.murdermystery.entity.EntityText;
import cn.lanink.murdermystery.room.BaseRoom;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.scheduler.AsyncTask;
import cn.nukkit.scheduler.Task;

import java.util.LinkedList;
import java.util.Map;

public class ScanTask extends AsyncTask {

    private final BaseRoom room;
    private final Player player;

    public ScanTask(BaseRoom room , Player player) {
        this.room = room;
        this.player = player;
    }

    @Override
    public void onRun() {
        LinkedList<EntityText> texts = new LinkedList<>();
        for (Map.Entry<Player, Integer> entry : this.room.getPlayers().entrySet()) {
            if (entry.getValue() == 1 || entry.getValue() == 2) {
                EntityText text = new EntityText(entry.getKey().getChunk(), EntityText.getDefaultNBT(entry.getKey()), entry.getKey());
                text.spawnTo(player);
                texts.add(text);
            }
        }
        this.player.sendMessage(MurderMystery.getInstance().getLanguage().useItemScan);
        Server.getInstance().getScheduler().scheduleDelayedTask(MurderMystery.getInstance(), new Task() {
            @Override
            public void onRun(int i) {
                if (texts.size() > 0) {
                    for (EntityText text : texts) {
                        text.close();
                    }
                }
            }
        }, 100);
    }

}
