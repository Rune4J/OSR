package ethos.model.players.skills.thieving;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.RandomUtils;

import com.google.common.collect.Lists;

import ethos.Config;
import ethos.Server;
import ethos.model.content.SkillcapePerks;
import ethos.model.content.achievement.AchievementType;
import ethos.model.content.achievement.Achievements;
import ethos.model.content.achievement_diary.ardougne.ArdougneDiaryEntry;
import ethos.model.content.achievement_diary.desert.DesertDiaryEntry;
import ethos.model.content.achievement_diary.falador.FaladorDiaryEntry;
import ethos.model.content.achievement_diary.lumbridge_draynor.LumbridgeDraynorDiaryEntry;
import ethos.model.content.achievement_diary.varrock.VarrockDiaryEntry;
import ethos.model.content.achievement_diary.western_provinces.WesternDiaryEntry;
import ethos.model.content.dailytasks.DailyTasks;
import ethos.model.content.dailytasks.DailyTasks.PossibleTasks;
import ethos.model.items.GameItem;
import ethos.model.items.ItemDefinition;
import ethos.model.npcs.NPC;
import ethos.model.players.Boundary;
import ethos.model.players.Player;
import ethos.model.players.PlayerHandler;
import ethos.model.players.mode.ModeType;
import ethos.model.players.skills.Skill;
import ethos.net.discord.DiscordMessager;
import ethos.util.Location3D;
import ethos.util.Misc;

/**
 * A representation of the thieving skill. Support for both object and npc actions will be supported.
 *
 * @author Jason MacKeigan
 * @date Feb 15, 2015, 7:12:14 PM
 */
public class Thieving {

    private static int[] rogueOutfit = {5553, 5554, 5555, 5556, 5557};

    /**
     * The managing player of this class
     */
    private Player player;

    /**
     * The last interaction that player made that is recorded in milliseconds
     */
    private long lastInteraction;

    /**
     * The constant delay that is required inbetween interactions
     */
    private static final long INTERACTION_DELAY = 1_500L;

    /**
     * The stealing animation
     */
    private static final int ANIMATION = 881;

    /**
     * Constructs a new {@link Thieving} object that manages interactions between players and stalls, as well as players and non playable characters.
     *
     * @param player the visible player of this class
     */
    public Thieving(final Player player) {
        this.player = player;
    }

    /**
     * A method for stealing from a stall
     *
     * @param stall    the stall being stolen from
     * @param objectId the object id value of the stall
     * @param location the location of the stall
     */
    public void steal(Stall stall, int objectId, Location3D location) {
        double osrsExperience;
        double regExperience;
        int pieces = 0;
        for (int aRogueOutfit : rogueOutfit) {
            if (player.getItems().isWearingItem(aRogueOutfit)) {
                pieces += 2;
            }
        }
        if (System.currentTimeMillis() - lastInteraction < INTERACTION_DELAY) {
            //player.sendMessage("You must wait a few more seconds before you can steal again.");
            return;
        }
        if (player.getItems().freeSlots() == 0) {
            player.sendMessage("You need at least one free slot to steal from this.");
            return;
        }
        /* if (!Server.getGlobalObjects().exists(objectId, location.getX(), location.getY()) || Server.getGlobalObjects().exists(4797, location.getX(), location.getY())) {
            player.sendMessage("The stall has been depleted.");
            return;
        } */
        if (player.playerLevel[Skill.THIEVING.getId()] < stall.level) {
            player.sendMessage("You need a thieving level of " + stall.level + " to steal from this.");
            return;
        }
        if (Misc.random(50) == 0 && player.getInterfaceEvent().isExecutable()) {
            player.getInterfaceEvent().execute();
            return;
        }
        switch (stall) {
            case Food:
              //  player.getDiaryManager().getVarrockDiary().progress(VarrockDiaryEntry.TEA_STALL);
                break;
            case Crafting:
                if (Boundary.isIn(player, Boundary.ARDOUGNE_BOUNDARY)) {
                    player.getDiaryManager().getArdougneDiary().progress(ArdougneDiaryEntry.STEAL_CAKE);
                }
                break;
            case Magic:
                if (Boundary.isIn(player, Boundary.ARDOUGNE_BOUNDARY)) {
                    player.getDiaryManager().getArdougneDiary().progress(ArdougneDiaryEntry.STEAL_GEM_ARD);
                }
                if (Boundary.isIn(player, Boundary.FALADOR_BOUNDARY)) {
                    player.getDiaryManager().getFaladorDiary().progress(FaladorDiaryEntry.STEAL_GEM_FAL);
                }
                break;
            case General:
                DailyTasks.increase(player, PossibleTasks.SILVER_SICKLES);
                break;
            case Scimitar:
                break;
            case Wine:
                break;
            case Fish:
                break;
            case Gem:
                break;
            case Toys:
                break;
            case Silver:
                break;
            case Silk:
                break;
            case Seeds:
                break;
            case Fur:
                if (Boundary.isIn(player, Boundary.ARDOUGNE_BOUNDARY)) {
                    player.getDiaryManager().getArdougneDiary().progress(ArdougneDiaryEntry.STEAL_FUR);
                }
                break;
            default:
                break;
        }
        player.turnPlayerTo(location.getX(), location.getY());
/**        if (Misc.random(stall.depletionProbability) == 0) {
 GlobalObject stallObj = Server.getGlobalObjects().get(objectId, location.getX(), location.getY(), location.getZ());
 if (stallObj != null) {
 Server.getGlobalObjects().add(new GlobalObject(4797, location.getX(), location.getY(), location.getZ(), stallObj.getFace(), 10, 8, stallObj.getObjectId()));
 }
 }
 */
        GameItem item = stall.item;
        ItemDefinition definition = ItemDefinition.forId(item.getId());
        if (Misc.random(stall.petChance) == 20 && player.getItems().getItemCount(20663, false) == 0 && player.summonId != 20663) {
            PlayerHandler.executeGlobalMessage("[<col=CC0000>News</col>] @cr20@ <col=255>" + player.playerName + "</col> now goes hand in hand with a <col=CC0000>Rocky</col> pet!");
            DiscordMessager.sendLootations("[News] " + player.playerName + " now goes hand in hand with a Rocky pet!");
            player.getItems().addItemUnderAnyCircumstance(20663, 1);
        }
        int experience = (int) stall.experience;

        /**
         * Experience calculation
         */
        /**
         * 10 + 10 / 10 * 4
         * 10 * 40 + 10 * 40 / 10 * 4
         */
        osrsExperience = experience + experience / 10 * pieces;
        regExperience = experience * (Config.THIEVING_EXPERIENCE * player.prestige()) + experience * (Config.THIEVING_EXPERIENCE * player.prestige()) / 10 * pieces;

        player.startAnimation(ANIMATION);

//        final LootTableContainer container = LootContainerLoader.getInstance().getLootContainer
//                (objectId, LootContainerType.OBJECT);
//        if (container != null && container.getLootTables().size() > 0) {
//            final LootTable lootTable = container.roll(player.getContext().getMagicFind().value());
//            final long metricId = LootContainerContextProducer.getInstance(LootContainerType.OBJECT).get(container.getId()).getMetricId();
//            final AdjustableNumber<Long> rolls = new AdjustableLong(
//                    LootMetricProducer.getInstance().get(metricId) != null ?
//                            LootMetricProducer.getInstance().get(metricId).getRolls().value() :
//                            0L
//            );
//            rolls.increment();
//            lootTable.roll(player.getContext().getMagicFind().value())
//                    .forEach(loot -> {
//                        final double tier = lootTable.getPotentialItems().stream().filter(potentialItem ->
//                                potentialItem.getItemId() == loot.getItemId()).findAny().orElseThrow(() -> new NullPointerException("Error")).getRoll();
//                        switch (Tier.getRarityForValue(tier)) {
//                            case VERY_RARE:
//                                break;
//                            case LEGENDARY:
//                                break;
//                            case MYTHIC:
//                                break;
//                        }
//                        Server.getLootMetrics().add(
//                                new LootMetric(
//                                        metricId,
//                                        System.currentTimeMillis(),
//                                        player.getName(),
//                                        container.getId(),
//                                        lootTable.getTableId(),
//                                        loot.getItemId(),
//                                        loot.getAmount(),
//                                        tier,
//                                        rolls.value(),
//                                        LootContainerType.NPC.ordinal(),
//                                        player.getContext().getMagicFind().value() //TODO replace with mf variable
//                                )
//                        );
//                        player.getItems().addItem(loot.getItemId(), loot.getAmount());
//                    });
//
//            player.getPA().addSkillXP((int) (player.getMode().getType().equals(ModeType.OSRS) ? osrsExperience : regExperience), Skill.THIEVING.getId(), true);
//            Achievements.increase(player, AchievementType.THIEV, 1);
//            lastInteraction = System.currentTimeMillis();
//        } else {
//            player.sendMessage("Missing Stall Data");
//        }
    }

    /**
     * A method for pick pocketing npc's
     *
     * @param pickpocket the pickpocket type
     * @param npc        the npc being pick pocketed
     */
    public void steal(Pickpocket pickpocket, NPC npc) {
        double multiplier = 0;
        for (int aRogueOutfit : rogueOutfit) {
            if (player.getItems().isWearingItem(aRogueOutfit)) {
                multiplier += 0.625;
            }
        }
        if (System.currentTimeMillis() - lastInteraction < INTERACTION_DELAY) {
            //player.sendMessage("You must wait a few more seconds before you can steal again.");
            return;
        }
        if (player.getItems().freeSlots() == 0) {
            player.sendMessage("You need at least one free slot to steal from this npc.");
            return;
        }
        if (player.playerLevel[Skill.THIEVING.getId()] < pickpocket.level) {
            player.sendMessage("You need a thieving level of " + pickpocket.level + " to steal from this npc.");
            return;
        }
        if (Misc.random(100) == 0 && player.getInterfaceEvent().isExecutable()) {
            player.getInterfaceEvent().execute();
            return;
        }
        /**
         * Incorporate chance for failure
         */
        switch (pickpocket) {
            case FARMER:
                if (Boundary.isIn(player, Boundary.ARDOUGNE_BOUNDARY)) {
                    player.getDiaryManager().getArdougneDiary().progress(ArdougneDiaryEntry.PICKPOCKET_ARD);
                }
                if (Boundary.isIn(player, Boundary.FALADOR_BOUNDARY)) {
                    player.getDiaryManager().getFaladorDiary().progress(FaladorDiaryEntry.PICKPOCKET_MASTER_FARMER_FAL);
                }
                if (Boundary.isIn(player, Boundary.DRAYNOR_BOUNDARY)) {
                    player.getDiaryManager().getLumbridgeDraynorDiary().progress(LumbridgeDraynorDiaryEntry.PICKPOCKET_FARMER_DRAY);
                }
                DailyTasks.increase(player, PossibleTasks.MASTER_FARMER);
                break;
            case MAN:
                if (Boundary.isIn(player, Boundary.FALADOR_BOUNDARY)) {
                    player.getDiaryManager().getFaladorDiary().progress(FaladorDiaryEntry.PICKPOCKET_MAN);
                }
                if (Boundary.isIn(player, Boundary.LUMRIDGE_BOUNDARY)) {
                    player.getDiaryManager().getLumbridgeDraynorDiary().progress(LumbridgeDraynorDiaryEntry.PICKPOCKET_MAN_LUM);
                }
                break;
            case GNOME:
                player.getDiaryManager().getWesternDiary().progress(WesternDiaryEntry.PICKPOCKET_GNOME);
                break;
            case HERO:
                player.getDiaryManager().getArdougneDiary().progress(ArdougneDiaryEntry.PICKPOCKET_HERO);
                break;
            case MENAPHITE_THUG:
                player.getDiaryManager().getDesertDiary().progress(DesertDiaryEntry.PICKPOCKET_THUG);
                break;
            default:
                break;

        }
        player.turnPlayerTo(npc.getX(), npc.getY());
        player.startAnimation(ANIMATION);
        GameItem item = pickpocket.getRandomItem();
        double percentOfXp = pickpocket.experience * (player.getMode().getType().equals(ModeType.OSRS) ? 1 : Config.THIEVING_EXPERIENCE * player.prestige()) / 100 * multiplier;
        boolean maxCape = SkillcapePerks.THIEVING.isWearing(player) || SkillcapePerks.isWearingMaxCape(player);
        if (item != null) {
            player.getItems().addItem(item.getId(), maxCape ? item.getAmount() * 2 : item.getAmount());
        } else {
            player.sendMessage("You were unable to find anything useful.");
        }
        if (Misc.random(pickpocket.petChance) == 20 && player.getItems().getItemCount(20663, false) == 0 && player.summonId != 20663) {
            PlayerHandler.executeGlobalMessage("[<col=CC0000>News</col>] @cr20@ <col=255>" + player.playerName + "</col> now goes hand in hand with a <col=CC0000>Rocky</col> pet!");
            DiscordMessager.sendLootations("[News] " + player.playerName + " now goes hand in hand with a Rocky pet!");
            player.getItems().addItemUnderAnyCircumstance(20663, 1);
        }
        Achievements.increase(player, AchievementType.THIEV, 1);
        player.getPA().addSkillXP((int) (pickpocket.experience * (player.getMode().getType().equals(ModeType.OSRS) ? 1 : Config.THIEVING_EXPERIENCE * player.prestige()) + percentOfXp), Skill.THIEVING.getId(), true);
        lastInteraction = System.currentTimeMillis();
    }

    private enum Rarity {
        ALWAYS(0), COMMON(5), UNCOMMON(10), RARE(15), VERY_RARE(25);

        /**
         * The rarity
         */
        private final int rarity;

        /**
         * Creates a new rarity
         *
         * @param rarity the rarity
         */
        Rarity(int rarity) {
            this.rarity = rarity;
        }
    }

    @SuppressWarnings("serial")
    public enum Pickpocket {
        MAN(1, 8, 55000, new HashMap<Rarity, List<GameItem>>() {
            {
                put(Rarity.ALWAYS, Arrays.asList(new GameItem(995, 750), new GameItem(995, 1000), new GameItem(995, 1250)));
            }
        }), FARMER(60, 65, 52000, new HashMap<Rarity, List<GameItem>>() {
            {
                put(Rarity.ALWAYS, Arrays.asList(new GameItem(5291), new GameItem(5292), new GameItem(5293)));
                put(Rarity.COMMON, Arrays.asList(new GameItem(5294), new GameItem(5297), new GameItem(5296)));
                put(Rarity.UNCOMMON, Arrays.asList(new GameItem(5295), new GameItem(5298), new GameItem(5301), new GameItem(5302)));
                put(Rarity.RARE, Arrays.asList(new GameItem(5299), new GameItem(5300), new GameItem(5303)));
                put(Rarity.VERY_RARE, Collections.singletonList(new GameItem(5304)));
            }
        }), MENAPHITE_THUG(65, 75, 41000, new HashMap<Rarity, List<GameItem>>() {
            {
                put(Rarity.ALWAYS, Arrays.asList(new GameItem(995, 1000), new GameItem(995, 800), new GameItem(995, 950)));
            }
        }), GNOME(75, 85, 45000, new HashMap<Rarity, List<GameItem>>() {
            {
                put(Rarity.ALWAYS, Arrays.asList(new GameItem(995, 1200), new GameItem(995, 800), new GameItem(995, 1250)));
                put(Rarity.UNCOMMON, Arrays.asList(new GameItem(444), new GameItem(557), new GameItem(13431, 5)));
            }
        }), HERO(80, 100, 38000, new HashMap<Rarity, List<GameItem>>() {
            {
                put(Rarity.ALWAYS, Arrays.asList(new GameItem(995, 1500), new GameItem(995, 1800), new GameItem(995, 3500)));
                put(Rarity.UNCOMMON, Arrays.asList(new GameItem(560, 2), new GameItem(565), new GameItem(444), new GameItem(1601)));
            }
        });

        /**
         * The level required to pickpocket
         */
        private final int level;

        /**
         * The experience gained from the pick pocket
         */
        private final int experience;

        /**
         * The chance of receiving a pet
         */
        private final int petChance;

        /**
         * The list of possible items received from the pick pocket
         */
        private Map<Rarity, List<GameItem>> items = new HashMap<>();

        /**
         * Creates a new pickpocket level requirement and experience gained
         *
         * @param level      the level required to steal from
         * @param experience the experience gained from stealing
         */
        Pickpocket(int level, int experience, int petChance, Map<Rarity, List<GameItem>> items) {
            this.level = level;
            this.experience = experience;
            this.petChance = petChance;
            this.items = items;
        }

        GameItem getRandomItem() {
            for (Entry<Rarity, List<GameItem>> entry : items.entrySet()) {
                final Rarity rarity = entry.getKey();

                if (rarity == Rarity.ALWAYS) {
                    continue;
                }
                final List<GameItem> items = entry.getValue();

                if (items.isEmpty()) {
                    continue;
                }

                if (RandomUtils.nextInt(1, rarity.rarity) == 1) {
                    return Misc.getItemFromList(items).randomizedAmount();
                }
            }

            List<GameItem> always = items.getOrDefault(Rarity.ALWAYS, Lists.newArrayList());

            if (!always.isEmpty()) {
                return Misc.getItemFromList(always).randomizedAmount();
            }

            return null;
        }
    }

    public enum Stall {
    	//
        Crafting(new GameItem(995, Misc.random(250) + 500), 1, 16, 20, 45000),
        //
        Food(new GameItem(995, Misc.random(750) + 1000), 1, 30, 10, 42500),
        //
        General(new GameItem(995, Misc.random(1000) + 1500), 50, 54, 10, 40000),
        //
        Magic(new GameItem(995, Misc.random(1250) + 2000), 75, 80, 10, 38000),
        //
        Scimitar(new GameItem(995, Misc.random(1500) + 2500), 90, 100, 10, 36500),
        //
        Wine(new GameItem(1), 30, 23, 0, 0),
        //
        Spice(new GameItem(1), 30, 23, 0, 0),
        //
        Toys(new GameItem(1), 99, 1000, 0, 0),
        //
        Silk(new GameItem(1), 45, 500, 0, 0),
        //
        Silver(new GameItem(1), 25, 42, 0, 0),
        //
        Gem(new GameItem(1), 82, 780, 0, 0),
        //
        Fish(new GameItem(1), 1, 16, 0, 0),
        //
        Seeds(new GameItem(1), 30, 23, 0, 0),
        //
        Baker(new GameItem(1), 30, 23, 0, 0),
        //
        Fur(new GameItem(995, Misc.random(1000) + 1000), 50, 54, 10, 40000);
    	//
        /**
         * The item received from the stall
         */
        private final GameItem item;

        /**
         * The experience gained in thieving from a single stall thieve
         */
        private final double experience;

        /**
         * The level required to steal from the stall
         */
        private final int level;

        /**
         * The chance of receiving a pet
         */
        private final int petChance;

        /**
         * Constructs a new {@link Stall} object with a single parameter, {@link GameItem} which is the item received when interacted with.
         *
         * @param item the item received upon interaction
         */
        Stall(GameItem item, int level, int experience, int depletionProbability, int petChance) {
            this.item = item;
            this.level = level;
            this.experience = experience;
            this.petChance = petChance;
        }
    }

}
