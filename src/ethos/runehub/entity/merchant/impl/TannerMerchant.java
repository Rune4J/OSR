package ethos.runehub.entity.merchant.impl;

import ethos.runehub.entity.merchant.MerchandiseSlot;
import ethos.runehub.entity.merchant.Merchant;
import org.runehub.api.io.load.impl.ItemIdContextLoader;

import java.util.List;

public class TannerMerchant extends Merchant {


    @Override
    public int getPriceMerchantWillSellFor(int itemId) {
        int baseValue = ItemIdContextLoader.getInstance().read(itemId).getValue();
        switch (itemId) {
            case 1733, 1734:
                return 10;
            case 1755:
                return 15;
            case 1592, 1597, 1595, 1599:
                return 500;
        }
        return baseValue;
    }

    public TannerMerchant() {
        super(List.of(
                new MerchandiseSlot(1733, 100, false, 0.0D, false),
                new MerchandiseSlot(1734, 500, false, 0.0D, false),
                new MerchandiseSlot(1755, 100, false, 0.0D, false),
                new MerchandiseSlot(1592, 100, false, 0.0D, false), // ring mould
                new MerchandiseSlot(1595, 100, false, 0.0D, false), // necklace mould
                new MerchandiseSlot(1597, 100, false, 0.0D, false), // amulet mould
                new MerchandiseSlot(1599, 100, false, 0.0D, false) // holy mould
        ),
                995,
                5809,
                "Crafting Supplies",
                -1L,
                List.of()
        );
    }
}
