package ethos.runehub.entity.merchant.impl;

import ethos.runehub.entity.merchant.Merchant;
import org.runehub.api.io.load.impl.ItemIdContextLoader;

import java.util.List;

public class WiseOldManMerchant extends Merchant {

    @Override
    public int getPriceMerchantWillSellFor(int itemId) {
        switch (itemId) {
            case 12399:
                return 1000000;
            case 12337:
                return 5000;
            case 2415:
                return 75000;
            case 1409:
                return 45000;
            case 1540:
                return 7500;
        }
        return ItemIdContextLoader.getInstance().read(itemId).getValue();
    }

    public WiseOldManMerchant( ) {
        super(995, 2108, "Wise Old Man", -3688082662207766484L, List.of());
    }
}
