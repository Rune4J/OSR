package ethos.runehub.merchant.impl;

import ethos.runehub.merchant.Merchant;
import java.util.List;

import org.runehub.api.io.load.impl.ItemIdContextLoader;

public class HomeWeaponMerchant extends Merchant {
	
    @Override
    public int getPriceMerchantWillSellFor(int itemId) {
        switch (itemId) {

        }
        
        return ItemIdContextLoader.getInstance().read(itemId).getValue();
    }

	  public HomeWeaponMerchant( ) {
	        super(995, 2471, "Weapons (Class-1)", -8119807085512275586L, List.of());
	    }
	}
