package tema2.ViewModel.Commands;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import tema2.ViewModel.ICommand;

public class PlantaCommands implements ICommand {
    private final Runnable action;
    public BooleanProperty trigger = new SimpleBooleanProperty(false);

    public PlantaCommands(Runnable action) {

        this.action = action;
        trigger.addListener((obs, oldVal, newVal)->
        {
            if(newVal)
            {
                execute();
            }
        });
    }

    @Override
    public void execute() {
        action.run();
    }
}
