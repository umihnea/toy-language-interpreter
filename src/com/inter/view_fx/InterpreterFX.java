package com.inter.view_fx;

import com.inter.controller.Controller;
import com.inter.repository.IRepository;
import com.inter.repository.Repository;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class InterpreterFX extends Application {
    private IRepository repository;
    private Controller controller;
    private Mediator mediator;

    @Override
    public void start(Stage stage) {
        repository = new Repository();
        controller = new Controller(repository);
        mediator = new Mediator(controller);

        Scene mainScene = new Scene(getMainView(stage), 600, 600);
        stage.setScene(mainScene);

        stage.show();
    }

    private BorderPane getMainView(Stage stage) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("resources/main.fxml"));

        BorderPane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        MainView mainView = loader.getController();
        mainView.setMediator(mediator);
        mediator.registerMain(mainView);

        mainView.setPrimaryStage(stage);

        return root;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
