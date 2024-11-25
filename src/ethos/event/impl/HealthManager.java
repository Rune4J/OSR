package ethos.event.impl;

import ethos.model.players.Player;
import ethos.model.players.combat.Hitmark;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Set;
import java.util.HashSet;

public class HealthManager {

    private static final Set<Integer> ITEM_IDS = new HashSet<>();
    private final Player player;
    private Timer loginHealthTimer;
    private Timer reductionTimer;
    private Timer loginReductionTimer;
    private static final int DAMAGE = 2;

    static {
        ITEM_IDS.add(7934);
        // ITEM_IDS.add(####);
        // ITEM_IDS.add(####);
        // ITEM_IDS.add(####);
        // ITEM_IDS.add(####);
    }

    public HealthManager(Player player) {
        this.player = player;
    }

    public void startLoginHealthTimer() {
        //System.out.println("[HealthManager] Starting login health timer...");
        stopLoginHealthTimer();
        loginHealthTimer = new Timer();
        loginHealthTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                //System.out.println("[HealthManager] 15 seconds elapsed. Starting health reduction timer...");
                stopLoginHealthTimer();
                startReductionTimer();
            }
        }, 2700000); // 45 MINUTE DELAY AFTER A PLAYER LOGS IN - MICHAEL
    }

    public void stopLoginHealthTimer() {
        if (loginHealthTimer != null) {
            loginHealthTimer.cancel();
            loginHealthTimer = null;
        }
    }

    private boolean isReductionTimerRunning = false;

    private void startReductionTimer() {
    	player.sendMessage("@red@You feel hungry... and a bit fatigued. @bla@You can stop hunger by eating @blu@Rations.");
        synchronized (this) {
            if (isReductionTimerRunning) {
                return; 
            }
            isReductionTimerRunning = true;

            if (reductionTimer != null) {
                reductionTimer.cancel(); 
            }
            reductionTimer = new Timer();
            reductionTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    //System.out.println("[HealthManager] Reduction timer triggered.");
                    reduceHealth(); 
                }
            }, 0, 7000); // YOU GET HIT 2HP EVERY 7 SECONDS - MICHAEL
        }
    }

    public void stopReductionTimer() {
        synchronized (this) {
            if (reductionTimer != null) {
                reductionTimer.cancel();
                reductionTimer = null;
            }
            isReductionTimerRunning = false; 
        }
    }

    private void reduceHealth() {
        //System.out.println("[HealthManager] Calling reduceHealth()");
        if (player != null && !player.disconnected) {
            int initialHealth = player.getHealthAmount(); // Directly get the player's health amount
            //System.out.println("[HealthManager] Initial health = " + initialHealth);

            if (initialHealth <= 0) {
                //System.out.println("[HealthManager] Health is 0 or less, stopping reduction timer.");
                stopReductionTimer();
                return;
            }

            int newHealth = initialHealth - DAMAGE;
            if (newHealth < 0) {
                newHealth = 0;
            }
            player.setHealthAmount(newHealth);
            player.appendDamage(DAMAGE, Hitmark.HIT);

            //System.out.println("[HealthManager] Health reduced by " + DAMAGE + ". New health = " + newHealth);
        } else {
            //System.out.println("[HealthManager] Player is null or disconnected, stopping reduction timer.");
            stopReductionTimer();
        }
    }

    public void resetCycle() {
        //System.out.println("[HealthManager] Resetting health cycle.");
        stopReductionTimer();
        startLoginHealthTimer();
    }

    public void handleItemConsumed(int itemId) {
        if (ITEM_IDS.contains(itemId)) {
            //System.out.println("[HealthManager] Item " + itemId + " consumed. Resetting health cycle.");
            resetCycle();
        }
    }
}
