package ethos.model.players.skills.woodcutting;

import java.util.Optional;

import ethos.Config;
import ethos.Server;
import ethos.clip.Region;
import ethos.clip.WorldObject;
import ethos.event.Event;
import ethos.model.content.SkillcapePerks;
import ethos.model.content.achievement.AchievementType;
import ethos.model.content.achievement.Achievements;
import ethos.model.content.achievement_diary.desert.DesertDiaryEntry;
import ethos.model.content.achievement_diary.falador.FaladorDiaryEntry;
import ethos.model.content.achievement_diary.fremennik.FremennikDiaryEntry;
import ethos.model.content.achievement_diary.kandarin.KandarinDiaryEntry;
import ethos.model.content.achievement_diary.lumbridge_draynor.LumbridgeDraynorDiaryEntry;
import ethos.model.content.achievement_diary.varrock.VarrockDiaryEntry;
import ethos.model.content.achievement_diary.wilderness.WildernessDiaryEntry;
import ethos.model.content.dailytasks.DailyTasks;
import ethos.model.content.dailytasks.DailyTasks.PossibleTasks;
import ethos.model.players.Boundary;
import ethos.model.players.Player;
import ethos.model.players.PlayerHandler;
import ethos.model.players.Right;
import ethos.model.players.skills.Skill;
import ethos.model.players.skills.firemake.Firemaking;
import ethos.net.discord.DiscordMessager;
import ethos.util.Misc;
import ethos.world.objects.GlobalObject;

public class WoodcuttingEvent extends Event<Player> {
	private Tree tree;
	private Hatchet hatchet;
	private int objectId, x, y, chops;
	
	private int[] lumberjackOutfit = { 10933, 10939, 10940, 10941 };
	
	static Player c;

	public WoodcuttingEvent(Player player, Tree tree, Hatchet hatchet, int objectId, int x, int y) {
		super("skilling", player, 1);
		this.tree = tree;
		this.hatchet = hatchet;
		this.objectId = objectId;
		this.x = x;
		this.y = y;
	}
	@Override
	public void execute() {
		double osrsExperience;
		double experience;
		int pieces = 0;
		pieces=handleOutfit(pieces);
		osrsExperience = tree.getExperience() + tree.getExperience() / 10 * pieces;
		experience = tree.getExperience() * (Config.WOODCUTTING_EXPERIENCE * attachment.prestige()) + tree.getExperience() * (Config.WOODCUTTING_EXPERIENCE * attachment.prestige()) / 10 * pieces;
		if (canChop()) return;
		if (Misc.random(300) == 0 && attachment.getInterfaceEvent().isExecutable()) {
			attachment.getInterfaceEvent().execute();
			super.stop();
			return;
		}
		chops++;
		int chopChance = 1 + (int) (tree.getChopsRequired() * hatchet.getChopSpeed());
		if (Boundary.isIn(attachment, Boundary.WOODCUTTING_GUILD_BOUNDARY)){
			chopChance *= 1.5;
		}
		if (Misc.random(tree.getChopdownChance()) == 0 || tree.equals(Tree.NORMAL) && Misc.random(chopChance) == 0) {
			int face = 0;
			Optional<WorldObject> worldObject = Region.getWorldObject(objectId, x, y, 0);
			if (worldObject.isPresent()) {
				face = worldObject.get().getFace();
			}
			int stumpId = 0;
			if (tree.equals(Tree.REDWOOD)) {
				face = (attachment.absX < 1568) ? 1 : (attachment.absX > 1573) ? 3 : (attachment.absY < 3480) ? 0 : 2;
				attachment.sendMessage("objectId: "+objectId);
				if (objectId == 29668)
					stumpId = 29669;
				else if (objectId == 29670)
					stumpId = 29671;
			}
			Server.getGlobalObjects().add(new GlobalObject(tree.equals(Tree.REDWOOD) ? stumpId : tree.getStumpId(), x, y, attachment.heightLevel, face, 10, tree.getRespawnTime(), objectId));
			attachment.getItems().addItem(tree.getWood(), 1);
			attachment.getPA().addSkillXP((int) (attachment.getRightGroup().isOrInherits(Right.OSRS) ? osrsExperience : experience) , Skill.WOODCUTTING.getId(), true);
			Achievements.increase(attachment, AchievementType.WOODCUT, 1);
			handleRewards();
			attachment.prestigeNumber = 8;
			attachment.currentPrestigeLevel = attachment.prestigeLevel[attachment.prestigeNumber];
			
	        if (attachment.currentPrestigeLevel == 1) {
	    		if (Misc.random(30) == 0) {
	    			attachment.getItems().addItem(tree.getWood(), 1);
	    		}
	        }
	        
	        if (attachment.currentPrestigeLevel == 2) {
	    		if (Misc.random(15) == 0) {
	    			attachment.getItems().addItem(tree.getWood(), 1);
	    			attachment.sendMessage("@blu@You have chopped an extra log!");
	    		}
	        }
	        
	        if (attachment.currentPrestigeLevel == 3) {
	    		if (Misc.random(15) == 0) {
	    			attachment.getItems().addItem(tree.getWood(), 1);
	    			attachment.getItems().addItem(995, Misc.random(500));
	    			attachment.sendMessage("@blu@You have chopped an extra log!");
	    		}
	        }
	        
	        if (attachment.currentPrestigeLevel == 4) {
	    		if (Misc.random(15) == 0) {
	    			attachment.getItems().addItem(tree.getWood(), 1);
	    			attachment.getItems().addItem(995, Misc.random(1000));
	    			attachment.sendMessage("@blu@You have chopped an extra log!");
	    		}
	        }
	        
	        if (attachment.currentPrestigeLevel == 5) {
	    		if (Misc.random(15) == 0) {
	    			attachment.getItems().addItem(tree.getWood(), 1);
	    			attachment.getItems().addItem(995, Misc.random(2000));
	    			attachment.sendMessage("@blu@You have chopped an extra log!");
	    		}
	        }
	        
	        if (attachment.currentPrestigeLevel == 6) {
	    		if (Misc.random(15) == 0) {
	    			attachment.getItems().addItem(tree.getWood(), 1);
	    			attachment.getItems().addItem(995, Misc.random(3000));
	    			attachment.sendMessage("@blu@You have chopped an extra log!");
	    		}
	        }
	        
	        if (attachment.currentPrestigeLevel == 7) {
	    		if (Misc.random(15) == 0) {
	    			attachment.getItems().addItem(tree.getWood(), 1);
	    			attachment.getItems().addItem(995, Misc.random(4000));
	    			attachment.sendMessage("@blu@You have chopped an extra log!");
	    		}
	        }
	        
	        if (attachment.currentPrestigeLevel == 8) {
	    		if (Misc.random(15) == 0) {
	    			attachment.getItems().addItem(tree.getWood(), 1);
	    			attachment.getItems().addItem(995, Misc.random(5000));
	    			attachment.sendMessage("@blu@You have chopped an extra log!");
	    		}
	        }
	        
	        if (attachment.currentPrestigeLevel == 9) {
	    		if (Misc.random(15) == 0) {
	    			attachment.getItems().addItem(tree.getWood(), 1);
	    			attachment.getItems().addItem(995, Misc.random(6000));
	    			attachment.sendMessage("@blu@You have chopped an extra log!");
	    		}
	        }
	        
	        if (attachment.currentPrestigeLevel >= 10) {
	    		if (Misc.random(15) == 0) {
	    			attachment.getItems().addItem(tree.getWood(), 1);
	    			attachment.getItems().addItem(995, Misc.random(7000));
	    			attachment.sendMessage("@blu@You have chopped an extra log!");
	    		}
	        }
			super.stop();
			return;
		}
		if (!tree.equals(Tree.NORMAL)) {
			if (Misc.random(chopChance) == 0 || chops >= tree.getChopsRequired()) {
				chops = 0;
				int random = Misc.random(4);
				attachment.getPA().addSkillXP((int) (attachment.getRightGroup().isOrInherits(Right.OSRS) ? osrsExperience : experience) , Skill.WOODCUTTING.getId(), true);
				Achievements.increase(attachment, AchievementType.WOODCUT, 1);
				if ((attachment.getItems().isWearingItem(13241) || attachment.getItems().playerHasItem(13241)) && random == 2) {
					Firemaking.lightFire(attachment, tree.getWood(), "infernal_axe");
					return;
				}
				handleDiary(tree);
				handleWildernessRewards();
				attachment.getItems().addItem(tree.getWood(), SkillcapePerks.WOODCUTTING.isWearing(attachment) || (SkillcapePerks.isWearingMaxCape(attachment) && attachment.getWoodcuttingEffect()) && Misc.random(2) == 1 ? 2 : 1);
			}
		}
		if (tree.equals(Tree.ARCTIC_PINE) || tree.equals(Tree.MAGIC) || tree.equals(Tree.MAPLE) || tree.equals(Tree.YEW)
				|| tree.equals(Tree.REDWOOD) || tree.equals(Tree.NORMAL) || tree.equals(Tree.OAK) || tree.equals(Tree.SAPLING)
				|| tree.equals(Tree.TEAK) || tree.equals(Tree.WILLOW)) {
			
		}
		
		if (super.getElapsedTicks() % 4 == 0) {
			attachment.startAnimation(hatchet.getAnimation());
		}
	}

	private int handleOutfit(int pieces) {

		for (int aLumberjackOutfit : lumberjackOutfit) {
			if (attachment.getItems().isWearingItem(aLumberjackOutfit)) {
				pieces+=2;
			}
		}
		return pieces;
	}

	private boolean canChop() {

		if (attachment == null || attachment.disconnected || attachment.getSession() == null) {
			super.stop();
			return true;
		}
		if (!attachment.getItems().playerHasItem(hatchet.getItemId()) && !attachment.getItems().isWearingItem(hatchet.getItemId())) {
			attachment.sendMessage("Your axe has disappeared.");
			super.stop();
			return true;
		}
		if (attachment.playerLevel[attachment.playerWoodcutting] < hatchet.getLevelRequired()) {
			attachment.sendMessage("You no longer have the level required to operate this hatchet.");
			super.stop();
			return true;
		}
		if (attachment.getItems().freeSlots() == 0) {
			attachment.sendMessage("You have run out of free inventory space.");
			super.stop();
			return true;
		}
		return false;
	}

	private void handleWildernessRewards() {

		if (Boundary.isIn(attachment, Boundary.RESOURCE_AREA)) {
			if (Misc.random(20) == 5) {
				int randomAmount = Misc.random(3) + 1;
				attachment.sendMessage("You received " + randomAmount + " blood money while woodcutting!");
				attachment.getItems().addItem(13307, randomAmount);
			}
		}
	}

	private void handleDiary(Tree tree) {
		switch (tree) {
			case MAGIC:
				if (Boundary.isIn(attachment, Boundary.AL_KHARID_BOUNDARY)) {
					attachment.getDiaryManager().getLumbridgeDraynorDiary().progress(LumbridgeDraynorDiaryEntry.CHOP_MAGIC_AL);
				}
				if (Boundary.isIn(attachment, Boundary.RESOURCE_AREA_BOUNDARY)) {
					attachment.getDiaryManager().getWildernessDiary().progress(WildernessDiaryEntry.MAGIC_LOG_WILD);
				}
				if (Boundary.isIn(attachment, Boundary.SEERS_BOUNDARY)) {
					attachment.getDiaryManager().getKandarinDiary().progress(KandarinDiaryEntry.CUT_MAGIC_SEERS);
				}
				DailyTasks.increase(attachment, PossibleTasks.MAGIC_LOGS);
				break;
			case MAPLE:
				break;
			case NORMAL:
				break;
			case OAK:
				if (Boundary.isIn(attachment, Boundary.RELLEKKA_BOUNDARY)) {
					attachment.getDiaryManager().getFremennikDiary().progress(FremennikDiaryEntry.CHOP_OAK_FREM);
				}
				break;
			case WILLOW:
				if (Boundary.isIn(attachment, Boundary.FALADOR_BOUNDARY)) {
					attachment.getDiaryManager().getFaladorDiary().progress(FaladorDiaryEntry.CHOP_WILLOW);
				}
				if (Boundary.isIn(attachment, Boundary.DRAYNOR_BOUNDARY)) {
					attachment.getDiaryManager().getLumbridgeDraynorDiary().progress(LumbridgeDraynorDiaryEntry.CHOP_WILLOW_DRAY);
				}
				break;
			case YEW:
				if (Boundary.isIn(attachment, Boundary.FALADOR_BOUNDARY)) {
					attachment.getDiaryManager().getFaladorDiary().progress(FaladorDiaryEntry.CHOP_YEW);
				}
				if (Boundary.isIn(attachment, Boundary.VARROCK_BOUNDARY)) {
					//attachment.getDiaryManager().getVarrockDiary().progress(VarrockDiaryEntry.YEWS_AND_BURN);
				}
				DailyTasks.increase(attachment, PossibleTasks.YEW_LOGS);
				break;
			case TEAK:
				if (Boundary.isIn(attachment, Boundary.DESERT_BOUNDARY)) {
					attachment.getDiaryManager().getDesertDiary().progress(DesertDiaryEntry.CHOP_TEAK);
				}
				break;
			default:
				break;

		}
	}

	private void handleRewards() {
		if (Misc.random(tree.getPetChance() / 100) == 10) {
			switch (Misc.random(1)) {
			case 0:
				attachment.getItems().addItemUnderAnyCircumstance(19712, 1);
				break;

			case 1:
				attachment.getItems().addItemUnderAnyCircumstance(19714, 1);
				break;
			}
			attachment.sendMessage("@blu@You appear to see a clue nest fall from the tree, and pick it up.");
		}
		if (Misc.random(12000) == 5555) {
			attachment.getItems().addItemUnderAnyCircumstance(lumberjackOutfit[Misc.random(lumberjackOutfit.length - 1)], 1);
			attachment.sendMessage("You notice a lumberjack piece falling from the tree and pick it up.");
		}
		if (Misc.random(tree.getPetChance()) / 75 == 10) {
			attachment.getItems().addItemUnderAnyCircumstance(19716, 1);
			attachment.sendMessage("@blu@You appear to see a clue nest fall from the tree, and pick it up.");
		}
		if (Misc.random(tree.getPetChance()) == 2 && attachment.getItems().getItemCount(13322, false) == 0 && attachment.summonId != 13322) {
			PlayerHandler.executeGlobalMessage("[<col=CC0000>News</col>] @cr20@ <col=255>" + attachment.playerName + "</col> chopped down the nest for <col=CC0000>Beaver</col> pet!");
			DiscordMessager.sendLootations("[News] " + attachment.playerName + " chopped down the nest for Beaver pet!");
			attachment.getItems().addItemUnderAnyCircumstance(13322, 1);
		}
	}

	@Override
	public void stop() {
		super.stop();
		if (attachment != null) {
			attachment.startAnimation(65535);
		}
	}

}