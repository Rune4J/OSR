package ethos.runehub.ui;

import ethos.model.items.ItemAssistant;
import ethos.model.players.Player;
import ethos.runehub.entity.item.GameItem;
import ethos.runehub.skill.artisan.crafting.TanningItem;
import ethos.runehub.ui.component.button.Button;
import ethos.runehub.ui.component.impl.TextComponent;
import org.runehub.api.io.load.impl.ItemIdContextLoader;

import java.text.NumberFormat;
import java.util.Arrays;

public class TanningUI extends GameUI {


    @Override
    protected void onOpen() {
        this.render();
        this.registerButton(new UICloseActionListener(), 148114);
    }

    @Override
    protected void onClose() {
        this.clearUI();
    }

    @Override
    protected void onEvent() {

    }

    // Render the interface
    private void render() {
        //Ensure tanning items is no more than 8
        if (tanningItems.length > 8) {
            throw new IllegalArgumentException("Tanning items must be no more than 8");
        }
        writeItems();
        writeItemNames();
        writeItemPrices();
        registerButtons();

        Arrays.stream(itemNameLabels).forEach(this::writeLine);
        Arrays.stream(priceLabels).forEach(this::writeLine);
    }

    // Clear the interface
    private void clearUI() {
        clearItemsFromInterface();
        clearItemNames();
        clearButtons();
    }

    //Write the tanning item names to the itemNameLabels
    private void writeItemNames() {
        for (int i = 0; i < tanningItems.length; i++) {
            if (tanningItems[i] != null) {
                itemNameLabels[i] = new TextComponent(14777 + i);
                itemNameLabels[i].setText(ItemIdContextLoader.getInstance().read(tanningItems[i].getHideId()).getName());
            }
        }
    }

    //Write the tanning item prices to the priceLabels
    private void writeItemPrices() {
        for (int i = 0; i < tanningItems.length; i++) {
            if (tanningItems[i] != null) {
                priceLabels[i] = new TextComponent(14785 + i);
                priceLabels[i].setText("@gre@Price: " + tanningItems[i].getPrice());
            }
        }
    }

    // Write the tanning items to the interface
    private void writeItems() {
        for (int i = 0; i < tanningItems.length; i++) {
            if (tanningItems[i] != null) {
                this.getPlayer().getPA().itemOnInterface(14769 + i, 250, tanningItems[i].getLeatherId());
            }
        }
    }

    // Register the buttons
    private void registerButtons() {
        for (int i = 0; i < buttons.length; i++) {
            if (i < 8) {
                buttons[i] = new Button(57201 + i);
            } else {
                buttons[i] = new Button(57209 + i);
            }
        }

        buttons[0].addActionListener(e -> tanHide(tanningItems[0],getTotalHidesInInventory(tanningItems[0])));
        buttons[1].addActionListener(e -> tanHide(tanningItems[1],getTotalHidesInInventory(tanningItems[1])));
        buttons[2].addActionListener(e -> tanHide(tanningItems[2],getTotalHidesInInventory(tanningItems[2])));
        buttons[3].addActionListener(e -> tanHide(tanningItems[3],getTotalHidesInInventory(tanningItems[3])));
        buttons[4].addActionListener(e -> tanHide(tanningItems[4],getTotalHidesInInventory(tanningItems[4])));
        buttons[5].addActionListener(e -> tanHide(tanningItems[5],getTotalHidesInInventory(tanningItems[5])));
        buttons[6].addActionListener(e -> tanHide(tanningItems[6],getTotalHidesInInventory(tanningItems[6])));
        buttons[7].addActionListener(e -> tanHide(tanningItems[7],getTotalHidesInInventory(tanningItems[7])));

        buttons[8].addActionListener(e -> tanHide(tanningItems[0],5));
        buttons[9].addActionListener(e -> tanHide(tanningItems[1],5));
        buttons[10].addActionListener(e -> tanHide(tanningItems[2],5));
        buttons[11].addActionListener(e -> tanHide(tanningItems[3],5));
        buttons[12].addActionListener(e -> tanHide(tanningItems[4],5));
        buttons[13].addActionListener(e -> tanHide(tanningItems[5],5));
        buttons[14].addActionListener(e -> tanHide(tanningItems[6],5));
        buttons[15].addActionListener(e -> tanHide(tanningItems[7],5));

        buttons[16].addActionListener(e -> tanHide(tanningItems[0],1));
        buttons[17].addActionListener(e -> tanHide(tanningItems[1],1));
        buttons[18].addActionListener(e -> tanHide(tanningItems[2],1));
        buttons[19].addActionListener(e -> tanHide(tanningItems[3],1));
        buttons[20].addActionListener(e -> tanHide(tanningItems[4],1));
        buttons[21].addActionListener(e -> tanHide(tanningItems[5],1));
        buttons[22].addActionListener(e -> tanHide(tanningItems[6],1));
        buttons[23].addActionListener(e -> tanHide(tanningItems[7],1));

        for (Button button : buttons) {
            this.registerButton(button);
        }
    }

    // Clear the interface
    private void clearItemsFromInterface() {
        for (int i = 0; i < 8; i++) {
            this.getPlayer().getPA().itemOnInterface(14769 + i, 250, -1);
        }
    }

    // Clear the interface
    private void clearItemNames() {
        for (int i = 0; i < 8; i++) {
            itemNameLabels[i].setText("");
            priceLabels[i].setText("");
        }
    }

    // Unregister the buttons
    private void clearButtons() {
        for (Button button : buttons) {
            button.getActionListeners().clear();
        }
    }

    private int getTotalHidesInInventory(TanningItem tanningItem) {

        if (tanningItem == null) {
            return 0;
        }

        int unnotedHidesInInventory = this.getPlayer().getItems().getItemCount(tanningItem.getHideId());
        int hidesInInventory = unnotedHidesInInventory;

        // If player is a member include noted version of hides
        if (this.getPlayer().getAttributes().isMember()) {
            hidesInInventory += this.getPlayer().getItems().getItemCount(tanningItem.getHideId() + 1);
        }

        return hidesInInventory;
    }

    // TODO - Move this to a better location
    private void tanHide(TanningItem tanningItem, int amount) {
        if (tanningItem == null) {
            return;
        }

        if (amount <= 0) {
            this.getPlayer().sendMessage("You do not have any " + ItemIdContextLoader.getInstance().read(tanningItem.getHideId()).getName() + " to tan.");
            return;
        }

        int unnotedHidesInInventory = this.getPlayer().getItems().getItemCount(tanningItem.getHideId());
        int hidesInInventory = unnotedHidesInInventory;
        int coinsInInventory = this.getPlayer().getItems().getItemCount(995);
        int tanningTotalCost = tanningItem.getPrice() * amount;

        String tanningAmount = NumberFormat.getInstance().format(amount);
        String tanningCost = NumberFormat.getInstance().format(tanningTotalCost);
        String hideName = ItemIdContextLoader.getInstance().read(tanningItem.getHideId()).getName();

        // If player is a member include noted version of hides
        if (this.getPlayer().getAttributes().isMember()) {
            hidesInInventory += this.getPlayer().getItems().getItemCount(tanningItem.getHideId() + 1);
        }

        // Check if player has enough hides
        if (hidesInInventory < amount) {
            this.getPlayer().sendMessage("You do not have " + tanningAmount + " " + hideName + " to tan.");
            return;
        }

        // Check if player has enough coins
        if (coinsInInventory < tanningTotalCost) {
            this.getPlayer().sendMessage("You do not have $" + tanningCost + " coins to tan $" + tanningAmount + " " + hideName + ".");
            return;
        }

        // Delete unnoted hides from inventory
        this.getPlayer().getItems().deleteItem(tanningItem.getHideId(), amount);

        // If player is a member and the unnoted hides is less than the amount delete the difference from noted hides from inventory
        if (this.getPlayer().getAttributes().isMember() && unnotedHidesInInventory < amount) {
            int notedHidesToDelete = amount - unnotedHidesInInventory;
            this.getPlayer().getItems().deleteItem(tanningItem.getHideId() + 1, notedHidesToDelete);
        }

        // Delete coins from inventory
        this.getPlayer().getItems().deleteItem(995, tanningTotalCost);

        // Add leather to inventory or add noted leather to inventory if player is a member
        if (this.getPlayer().getAttributes().isMember()) {
            this.getPlayer().getItems().addItem(tanningItem.getLeatherId() + 1, amount);
        } else {
            this.getPlayer().getItems().addItem(tanningItem.getLeatherId(), amount);
        }

        this.getPlayer().sendMessage("The tanner tans the hides for you.");

    }

    public TanningUI(Player player, TanningItem[] tanningItems) {
        super(14670, player);
        this.tanningItems = tanningItems;
    }

    private final TanningItem[] tanningItems;
    private final TextComponent[] priceLabels = new TextComponent[8];
    private final TextComponent[] itemNameLabels = new TextComponent[8];
    private final Button[] buttons = new Button[24];
}
