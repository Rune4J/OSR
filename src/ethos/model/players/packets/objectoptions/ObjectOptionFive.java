package ethos.model.players.packets.objectoptions;

import ethos.Server;
import ethos.model.players.Player;
import ethos.model.players.Right;

public class ObjectOptionFive {
	
	public static void handleOption(final Player c, int objectType, int obX, int obY) {
		System.out.println("ObjectOptionFour: " + objectType);
		if (Server.getMultiplayerSessionListener().inAnySession(c)) {
			return;
		}
		c.clickObjectType = 0;
		if (c.getRightGroup().isOrInherits(Right.OWNER))
			c.sendMessage("Clicked Object Option 5:  "+objectType+"");
		
		switch (objectType) {		
		}
	}

}
