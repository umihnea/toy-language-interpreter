package com.inter.view_fx;

import com.inter.controller.Controller;
import com.inter.model.ProgramState;
import com.inter.model.statements.Statement;
import com.inter.repository.IRepository;
import com.inter.utils.FilePair;
import com.inter.utils.HeapPair;
import com.inter.utils.SymbolPair;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

@SuppressWarnings("WeakerAccess")
public class Mediator {
    private Controller controller;
    private MainView mainView;
    private SelectView selectView;
    private ProgramState currentState;

    public Mediator(Controller controller) {
        this.controller = controller;
        this.currentState = null;
    }

    public void registerMain(MainView mainView) {
        this.mainView = mainView;
    }

    public void setProgram(Statement program) {
        controller.setProgram(program);
        setCurrentState(controller.getRepository().getProgramList().get(0));
        updateControls();
    }

    private void updateControls() {
        mainView.updateControls();
    }

    public IRepository getRepository() {
        return controller.getRepository();
    }

    public ProgramState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(ProgramState state) {
        currentState = state;
    }

    public String getNumberOfStates() {
        return String.valueOf(controller.getRepository().getProgramList().size());
    }

    public ObservableList<ProgramState> getProgramStates() {
        return FXCollections.observableArrayList(controller.getRepository().getProgramList());
    }

    public ObservableList<Statement> getStack() {
        assert currentState != null;
        return FXCollections.observableArrayList(currentState.getStack().getContainer());
    }

    public ObservableList<String> getOutput() {
        assert currentState != null;
        return FXCollections.observableArrayList(currentState.getBuffer().getContainer());
    }

    public ObservableList<SymbolPair> getSymbolTable() {
        ArrayList<SymbolPair> pairs = (ArrayList<SymbolPair>) currentState.getSymbolTable().entrySet().stream()
                .map(e -> new SymbolPair(e.getKey(), e.getValue()))
                .collect(Collectors.toList());

        return FXCollections.observableArrayList(pairs);
    }

    public ObservableList<HeapPair> getHeapTable() {
        ArrayList<HeapPair> pairs = (ArrayList<HeapPair>) currentState.getHeap().entrySet().stream()
                .map(e -> new HeapPair(e.getKey(), e.getValue()))
                .collect(Collectors.toList());

        return FXCollections.observableArrayList(pairs);
    }

    public ObservableList<FilePair> getFileTable() {
        ArrayList<FilePair> pairs = (ArrayList<FilePair>) currentState.getFileTable().entrySet().stream()
                .map(e -> new FilePair(e.getKey(), e.getValue().getFilename()))
                .collect(Collectors.toList());

        return FXCollections.observableArrayList(pairs);
    }

    public void stepOnce() {
        if (!controller.hasProgram()) {
            showErrorDialog("Execution finished or no program was loaded.");
            return;
        }

        try {
            controller.stepOnce();

            if (controller.getRepository().getProgramList().isEmpty()) {
                showErrorDialog("Execution finished or no program was loaded.");
                return;
            }

            setCurrentState(controller.getRepository().getProgramList().get(0));
            updateControls();

        } catch (Exception e) {
            showErrorDialog(e.getMessage());
            e.printStackTrace();
        }
    }

    public void runToCompletion() {
        if (!controller.hasProgram()) {
            showErrorDialog("No program loaded.");
            return;
        }

        try {
            controller.runToCompletion();
            updateControls();
        } catch (InterruptedException e) {
            showErrorDialog(e.getMessage());
            // e.printStackTrace();
        }
    }

    private void showErrorDialog(String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(content);
        alert.showAndWait();
    }

    /* Select */
    public void showSelect() {
        Stage selectWindow = new Stage();
        selectWindow.setX(mainView.getStage().getX() + 100);
        selectWindow.setY(mainView.getStage().getY() + 100);
        selectWindow.initModality(Modality.WINDOW_MODAL);
        selectWindow.initOwner(mainView.getStage());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("resources/select.fxml"));
        Pane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        selectView = loader.getController();
        selectView.setMediator(this);
        selectView.setStage(selectWindow);

        assert root != null;
        Scene selectScene = new Scene(root, 300, 300);
        selectWindow.setScene(selectScene);

        selectWindow.show();
    }

    public void makeSelection(Statement selected) {
        setProgram(selected);
        selectView.getStage().hide();
    }
}
