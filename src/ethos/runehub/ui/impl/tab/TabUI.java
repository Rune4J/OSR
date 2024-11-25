package ethos.runehub.ui.impl.tab;

import ethos.model.players.Player;
import ethos.runehub.ui.GameUI;
import ethos.runehub.ui.component.button.Button;
import ethos.runehub.ui.event.action.ActionListener;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public abstract class TabUI extends GameUI {

    protected abstract void refresh();

    @Override
    public void open() {
        this.onOpen();
        this.setShowing(true);
        this.setState(State.ACTIVE);
        this.getPlayer().getAttributes().setTabUI(this);
        this.getPlayer().setSidebarInterface(sideBarId, this);
    }

    public void action(int buttonId) {
        Logger.getGlobal().fine("Button Press on Tab: " + sideBarId + " - ActionID: " + buttonId);
        this.onAction(buttonId);
    }





    public TabUI(int id, Player player, int sideBarId) {
        super(id, player);
        this.sideBarId = sideBarId;

    }

    private final int sideBarId;

    private int currentPage = 0; // Assuming pages are 0-indexed
    private final int totalPages = 10; // Example total pages, adjust as needed

    public void prevPage() {
        if (currentPage > 0) {
            currentPage--;
            updateUI();
        } else {
            // Optional: Notify the user that they're already on the first page
            System.out.println("You are already on the first page.");
        }
    }

    public void nextPage() {
        if (currentPage < totalPages - 1) {
            currentPage++;
            updateUI();
        } else {
            // Optional: Notify the user that they're already on the last page
            System.out.println("You are already on the last page.");
        }
    }

    private void updateUI() {
        // Code to update the interface to show the content of the currentPage
        System.out.println("UI updated to show page: " + currentPage);
    }
}
