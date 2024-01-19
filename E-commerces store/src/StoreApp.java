import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class StoreApp extends Application{

    ElectronicStore store;
    public StoreApp(){
        store = ElectronicStore.createStore();
    }


    public void start(Stage primaryStage){
        Pane pane = new Pane();

        AppView view = new AppView();

        view.update(store,1);

        pane.getChildren().add(view);

        primaryStage.setTitle("Walmart");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(pane));
        primaryStage.show();


        view.getButtonPane().getaddC().setOnAction(new EventHandler<ActionEvent>() { //Create a event handler for the delete button
            @Override
            public void handle(ActionEvent actionEvent) {


                view.updateCartAdd(store, view.getStoreStock().getSelectionModel().getSelectedIndex());
                view.update(store,0);

            }
        });

        view.getButtonPane().getRemoveC().setOnAction(new EventHandler<ActionEvent>() { //Create a event handler for the delete button
            @Override
            public void handle(ActionEvent actionEvent) {


                view.updateCartRemove(store, view.getCurrentCart().getSelectionModel().getSelectedIndex());
                view.update(store,0);

            }
        });

        view.getButtonPane().getCompleteS().setOnAction(new EventHandler<ActionEvent>() { //Create a event handler for the delete button
            @Override
            public void handle(ActionEvent actionEvent) {


                view.completeSale(store);
                view.update(store,0);

            }
        });

        view.getButtonPane().getResetS().setOnAction(new EventHandler<ActionEvent>() { //Create a event handler for the delete button
            @Override
            public void handle(ActionEvent actionEvent) {

                store = ElectronicStore.createStore();
                view.reset();
                view.update(store,0);



            }
        });

    }

    public static void main(String[] args) {
        launch(args);
    }
}
