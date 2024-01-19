import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.util.*;


public class AppView extends Pane {

    private ListView<String> storeStock,currentCart,mostPopItems;
    private TextField revenue,numSales,p;
    private StoreButtonPane      buttonPane;

    private LinkedHashMap<String,Integer> current;
    private ArrayList<String> currentString;
    private ArrayList<String> products;
    private ArrayList<String> mostPop;

    private Label label7;

    private double totalCart;

    private int sales;

    private double rev;

    public StoreButtonPane getButtonPane() { return buttonPane; }

    public AppView(){
        current = new <String,Integer>LinkedHashMap();
        products = new ArrayList<String>();
        mostPop = new ArrayList<String>();
        currentString = new ArrayList<String>();

        totalCart = 0;
        sales = 0;
        rev = 0;

        Label label1 = new Label("Store Summary:");
        label1.relocate(30, 2);
        Label label2 = new Label("  # Sales:");
        label2.relocate(25, 25);
        Label label3 = new Label("Revenue:");
        label3.relocate(25, 55);
        Label label4 = new Label(" $ / Sale:");
        label4.relocate(25, 85);
        Label label5 = new Label("Most Popular Items:");
        label5.relocate(60, 109);
        Label label6 = new Label("Store Stock:");
        label6.relocate(295, 2);
        label7 = new Label("Current Cart: 0.00");
        label7.relocate(570, 2);

        revenue = new TextField("0.00");
        revenue.relocate(85, 55);
        revenue.setPrefSize(100,25);
        revenue.setEditable(false);

        numSales = new TextField("0");
        numSales.relocate(85, 25);
        numSales.setPrefSize(100,25);
        numSales.setEditable(false);

        p = new TextField("0");
        p.relocate(85, 85);
        p.setPrefSize(100,25);
        p.setEditable(false);

        storeStock = new ListView<String>();
        storeStock.relocate(235, 20);
        storeStock.setPrefSize(270,305);

        currentCart = new ListView<String>();
        currentCart.relocate(520, 20);
        currentCart.setPrefSize(270,305);

        mostPopItems = new ListView<String>();
        mostPopItems.relocate(20, 125);
        mostPopItems.setPrefSize(200,200);

        buttonPane = new StoreButtonPane();
        buttonPane.relocate(0,340);
        getChildren().addAll(label1,label2,label3,label4,label5,label6,label7,storeStock,currentCart,mostPopItems,revenue,numSales,p,buttonPane);

        setPrefSize(800,400);
    }

    public void update(ElectronicStore store, int selectedItem) {
        Product[] stock = store.getStock();
        products = new ArrayList<String>();




        for (Product p: stock) {
            if(p != null && p.getStockQuantity() != 0){
                products.add(p.toString());
            }


        }



        //Input information into the list views
        storeStock.setItems(FXCollections.observableArrayList(products));
        //tList.getSelectionModel().select(selectedDVD);

        mostPopItems.setItems(FXCollections.observableArrayList(mostPop));
        //tList.getSelectionModel().select(selectedDVD);

        buttonPane.getaddC().disableProperty().bind(storeStock.getSelectionModel().selectedItemProperty().isNull());
        buttonPane.getRemoveC().disableProperty().bind(currentCart.getSelectionModel().selectedItemProperty().isNull());






    }

    public ListView<String> getStoreStock(){return storeStock;}
    public ListView<String> getCurrentCart(){return currentCart;}

    public void updateCartAdd(ElectronicStore store, int selectedItem){
        ArrayList<String> cart = new ArrayList<String>();



        totalCart += store.updateStockAdd(products.get(selectedItem));

        label7.setText(String.format("Current Cart: %.2f",totalCart));


        if(current.containsKey(products.get(selectedItem))){
            current.put(products.get(selectedItem).toString(),current.get(products.get(selectedItem))+1);
        }
        else{
            current.put(products.get(selectedItem),1);

        }

        if(!currentString.contains(products.get(selectedItem))){
            currentString.add(products.get(selectedItem));
        }

        for(String key : current.keySet()){
            cart.add(String.format("%d x %s",current.get(key),key));
        }

        if(cart.size() > 0){
            buttonPane.getCompleteS().setDisable(false);
        }
        else{
            buttonPane.getCompleteS().setDisable(true);
        }

        currentCart.setItems(FXCollections.observableArrayList(cart));



    }

    public void updateCartRemove(ElectronicStore store, int selectedItem){
        ArrayList<String> cart = new ArrayList<String>();




        totalCart -= store.updateStockRemove(currentString.get(selectedItem));

        label7.setText(String.format("Current Cart: %.2f",totalCart));

        if(current.get(currentString.get(selectedItem)) == 1){
            current.remove(currentString.get(selectedItem));
            currentString.remove(currentString.get(selectedItem));


        }
        else{
            current.put(currentString.get(selectedItem),current.get(currentString.get(selectedItem))-1);
        }






        for(String key : current.keySet()){
            cart.add(String.format("%d x %s",current.get(key),key));
        }

        if(cart.size() > 0){
            buttonPane.getCompleteS().setDisable(false);
        }
        else{
            buttonPane.getCompleteS().setDisable(true);
        }

        currentCart.setItems(FXCollections.observableArrayList(cart));

    }

    public void completeSale(ElectronicStore store){

        buttonPane.getCompleteS().setDisable(true);

        mostPop = new ArrayList<String>();
        for(int i =0; i <3; i++){
            try {
                mostPop.add(store.mostPopItem().get(i).toString());
            }
            catch (IndexOutOfBoundsException e){

            }
        }

        currentCart.setItems(FXCollections.observableArrayList(new ArrayList<String>()));
        currentString.clear();



        label7.setText(String.format("Current Cart: %.2f",totalCart));

        current.clear();

        sales += 1;

        rev += totalCart;

        numSales.textProperty().setValue(Integer.toString(sales));
        revenue.textProperty().setValue(Double.toString(rev));
        p.textProperty().setValue(Double.toString(rev/sales));


        totalCart = 0;
    }

    public void reset(){



        numSales.textProperty().setValue("0");
        revenue.textProperty().setValue("0");
        p.textProperty().setValue("0");
        products.clear();
        mostPop.clear();
        currentString.clear();
        current.clear();
        label7.setText(String.format("Current Cart: %.2f",totalCart));
        totalCart = 0;
        sales =0;
        rev = 0;

        storeStock.setItems(FXCollections.observableArrayList(new ArrayList<String>()));
        currentCart.setItems(FXCollections.observableArrayList(new ArrayList<String>()));
        mostPopItems.setItems(FXCollections.observableArrayList(new ArrayList<String>()));




    }




}
