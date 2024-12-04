package ethos.runehub.ui.impl;

import ethos.model.players.Player;
import ethos.runehub.ui.GameUI;
import ethos.runehub.ui.component.impl.TextComponent;

import java.util.Arrays;

public class BookUI extends GameUI {

    private static final int LINE_CHARACTER_LIMIT = 30; //The total number of characters that fit on a line
    @Override
    protected void onOpen() {
        this.clearUI(); //this clears the UI when it opens to remove any remaining data from last open
        this.updateUI(); //this updates the UI to reflect the expected data
    }

    @Override
    protected void onClose() {
        this.clearUI();//This clears the UI when it's closed
    }

    @Override
    protected void onEvent() {

    }

    /**
     * This method clears all text in the book including the title
     */
    protected void clearUI() {
        titleTextComponent.setText("");
        Arrays.stream(entryTextComponent).forEach(textComponent -> textComponent.setText(""));
    }

    /**
     * This method visually updates the content of the entire book to reflect any line changes
     */
    protected void updateUI() {
        this.writeLine(titleTextComponent);
        Arrays.stream(entryTextComponent).forEach(this::writeLine);
    }

    private void nextPage() {

    }

    private void previousPage() {
        //make sure we can't decrement below 0
        if (currentPage > 0) {
            currentPage--;
        }
    }

    //Use this to fill the first line with sequential numbers to determine the character limit
    //this is a utility method and can be deleted after determining the line limit
    private void fillLine() {
        //65 represents ascii decimal value for 'A'
        for (int i = 65; i < (LINE_CHARACTER_LIMIT + 65); i++) {
            entryTextComponent[0].setText(String.valueOf((char) i));//expected out put is line like abcdefghijklmno
        }
        //line limit = ascii decimal value of last character - 65
        //for example last character = 'r' line value = 114-65 = 49
    }

    private void writeBook() {
        for (TextComponent line:entryTextComponent) {

        }
    }

    public BookUI(Player player) {
        super(69, player);//TODO replace 69 with actual interface ID
        //this component represents the title the lineId
        //TODO change lineId to book title lineId
        this.titleTextComponent = new TextComponent(8144);
        //this represents lines available to write in the book
        //If there are 30 lines between the 2 pages then the number would be 30
        //TODO change count to actual line count in book
        this.entryTextComponent = new TextComponent[51];

        //This instantiates each TextComponent in the entryTextComponent array
        //It is instantiated with the value of a new TextComponent for the given lineId
        //8146 represents the first lines ID
        //TODO change 8146 to the first page line Id in the book ui
        for (int i = 0; i < entryTextComponent.length; i++) {
            entryTextComponent[i] = new TextComponent(8146 + i);
        }

        //This registers a button and an action to the UI
        //59229 is the buttonId close() is the action performed on press
        //TODO replace button ID
        this.registerButton(actionEvent -> close(),59229);//Close button
        //This does the same as above
        //TODO replace button ID
        this.registerButton(actionEvent -> nextPage(),59229);//Forward button
        //This does the same as above
        //TODO replace button ID
        this.registerButton(actionEvent -> close(),59229);//Back button
    }


    //These are all protected so that they may be used in any subclasses of this one
    //subclasses would be used for books with custom functionality
    protected final TextComponent titleTextComponent;
    protected final TextComponent[] entryTextComponent;
    protected int currentPage; //This variable stores the current page number and starts at 0 always
}
