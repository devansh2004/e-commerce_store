import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class StoreButtonPane extends Pane {

    private Button resetS,addC,removeC,completeS;

    public Button getaddC() { return addC; }
    public Button getRemoveC() { return removeC; }
    public Button getCompleteS() { return completeS; }
    public Button getResetS() { return resetS; }
    public StoreButtonPane(){
        Pane innerPane = new Pane();

        resetS = new Button("Reset Store");
        resetS.setStyle("-fx-font: 12 arial;");
        resetS.setPrefSize(90,40);
        resetS.relocate(75,0);

        addC = new Button("Add to Cart");
        addC.setStyle("-fx-font: 12 arial;");
        addC.setPrefSize(90,40);
        addC.relocate(325,0);

        removeC = new Button("Remove from Cart");
        removeC.setStyle("-fx-font: 12 arial;");
        removeC.setPrefSize(125,40);
        removeC.relocate(533,0);

        completeS = new Button("Complete Sale");
        completeS.setStyle("-fx-font: 12 arial;");
        completeS.setPrefSize(125,40);
        completeS.relocate(658,0);
        completeS.setDisable(true);

        innerPane.getChildren().addAll(resetS,addC,removeC,completeS);

        getChildren().addAll(innerPane);
    }
}
