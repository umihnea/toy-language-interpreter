import com.inter.controller.Controller;
import com.inter.repository.IRepository;
import com.inter.repository.Repository;
import com.inter.view.ConsoleView;
import com.inter.view.commands.ExitCommand;
import com.inter.view.commands.LoadExampleCommand;
import com.inter.view.commands.RunProgramCommand;
import com.inter.view.commands.StepOnceCommand;

public class Interpreter {

    public static void main(String[] args) {

        IRepository repository = new Repository();
        Controller controller = new Controller(repository);
        ConsoleView ui = new ConsoleView();

        ui.addCommand(new ExitCommand("0", "Exit"));

        ui.addCommand(new LoadExampleCommand("1", "Load Example Program", controller));
        /* Loads a predefined statement into controller.
         * If already loaded, will overwrite. */

        ui.addCommand(new StepOnceCommand("2", "Step Once", controller, true));
        /* Controller fetches the current program state.
         * The program state is advanced by one step.
         * A step trace is printed to screen (if displayFlag is set).
         * Program state is put back. */

        ui.addCommand(new RunProgramCommand("3", "Run to Completion", controller));
        /* Controller fetches the current state.
         * The program state is completed.
         * Results displayed.
         * Program state is put back. */

        ui.render();
    }
}
