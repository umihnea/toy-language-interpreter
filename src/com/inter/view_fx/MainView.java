package com.inter.view_fx;

import com.inter.model.ProgramState;
import com.inter.model.statements.Statement;
import com.inter.utils.FilePair;
import com.inter.utils.HeapPair;
import com.inter.utils.SymbolPair;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

@SuppressWarnings({"WeakerAccess", "FieldCanBeLocal"})
public class MainView {
    private Stage primaryStage;
    private Mediator mediator;

    @FXML
    public TableView<SymbolPair> tblSymbols;
    @FXML
    public TableColumn<SymbolPair, String> colSymbolKey;
    @FXML
    public TableColumn<SymbolPair, String> colSymbolValue;

    @FXML
    public TableView<HeapPair> tblHeap;
    @FXML
    public TableColumn<HeapPair, String> colHeapKey;
    @FXML
    public TableColumn<HeapPair, String> colHeapValue;

    @FXML
    public TableView<FilePair> tblFiles;
    @FXML
    public TableColumn<FilePair, String> colFileKey;
    @FXML
    public TableColumn<FilePair, String> colFileValue;

    public TableView<HeapPair> tblLatches;
    public TableColumn<HeapPair, String> colLatchKey;
    public TableColumn<HeapPair, String> colLatchValue;

    @FXML
    public ListView<ProgramState> lstProgramStates;
    @FXML
    public ListView<Statement> lstStack;
    @FXML
    public ListView<String> lstOutput;

    @FXML
    public TextField txtNoStates;

    @FXML
    public Button btnStepOnce;
    @FXML
    public Button btnRunToFinish;
    @FXML
    public Button btnLoadProgram;

    public Stage getStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @FXML
    public void initialize() {
        lstProgramStates.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        lstProgramStates.getSelectionModel().selectIndices(1);

        colSymbolKey.setCellValueFactory(new PropertyValueFactory<>("key"));
        colSymbolValue.setCellValueFactory(new PropertyValueFactory<>("value"));

        colHeapKey.setCellValueFactory(new PropertyValueFactory<>("key"));
        colHeapValue.setCellValueFactory(new PropertyValueFactory<>("value"));

        colFileKey.setCellValueFactory(new PropertyValueFactory<>("key"));
        colFileValue.setCellValueFactory(new PropertyValueFactory<>("value"));

        colLatchKey.setCellValueFactory(new PropertyValueFactory<>("key"));
        colLatchValue.setCellValueFactory(new PropertyValueFactory<>("value"));
    }

    public void updateControls() {
        lstProgramStates.setItems(mediator.getProgramStates());
        txtNoStates.setText(mediator.getNumberOfStates());
        lstStack.setItems(mediator.getStack());
        lstOutput.setItems(mediator.getOutput());
        tblSymbols.setItems(mediator.getSymbolTable());
        tblHeap.setItems(mediator.getHeapTable());
        tblFiles.setItems(mediator.getFileTable());
        tblLatches.setItems(mediator.getLatchTable());
    }

    public void changeProgramState() {
        /* On lstProgramStates->onMouseClicked */
        if (!lstProgramStates.getItems().isEmpty() && !lstProgramStates.getSelectionModel().getSelectedItems().isEmpty()) {
            mediator.setCurrentState(lstProgramStates.getSelectionModel().getSelectedItems().get(0));
            updateControls();
        }
    }

    public void stepOnce(ActionEvent actionEvent) {
        mediator.stepOnce();
    }

    public void runToCompletion(ActionEvent actionEvent) {
        mediator.runToCompletion();
    }

    public void openSelectWindow(ActionEvent actionEvent) {
        mediator.showSelect();
    }
}
