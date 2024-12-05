package ethos.runehub.merchant.impl;

import ethos.runehub.merchant.Merchant;
import java.util.List;

import org.runehub.api.io.load.impl.ItemIdContextLoader;

public class AubreyMerchant extends Merchant {
	
    @Override
    public int getPriceMerchantWillSellFor(int itemId) {
        switch (itemId) {
        //case 1438: //AIR
        //case 1448: //MIND
        //case 1446: //BODY
        //case 1440: //EARTH
        //case 1444: //WATER
        //case 1442: //FIRE
            //return 5000;
        case 5527: //AIR
        case 5529: //MIND
        case 5533: //BODY
        case 5535: //EARTH
        case 5531: //WATER
        case 5537: //FIRE
        	return 12500;
        }
        
        return ItemIdContextLoader.getInstance().read(itemId).getValue();
    }

	  public AubreyMerchant( ) {
	        super(995, 637, "Runecrafting Supplies", 607126714069959094L, List.of());
	    }
	}
