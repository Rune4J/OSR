package ethos.runehub.entity.merchant.impl;

import ethos.runehub.entity.merchant.Merchant;
import java.util.List;

import org.runehub.api.io.load.impl.ItemIdContextLoader;

public class RationsMerchant extends Merchant {
	
    @Override
    public int getPriceMerchantWillSellFor(int itemId) {
        switch (itemId) {
        case 7934:
            return 1750;
        case 6656:
            return 2500;
        case 6654:
            return 7500;
        case 6655:
            return 5000;
        case 10865:
            return 3250;
        }
        
        return ItemIdContextLoader.getInstance().read(itemId).getValue();
    }

	  public RationsMerchant( ) {
	        super(995, 4875, "Field Rations & Military Goods", 1971830990604225925L, List.of());
	    }
	}
