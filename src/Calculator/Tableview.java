   package Calculator;

import Calculator.Entries;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class Tableview extends Application {

    private File file;

    private TableColumn Measure, Error, Units, Name, Type;

    private final TableView table = new TableView<>();

    @Override
    public void start(Stage stage) {
//        Callback<TableColumn<Person, String>, TableCell<Person, String>> cellFactory = (
//                TableColumn<Person, String> p) -> new EditingCell();

        Scene scene = new Scene(new Group());
        stage.setWidth(480);
        stage.setHeight(550);
        final HBox hb = new HBox();

        table.setEditable(true);

        Name = new TableColumn<>("Name");
        Name.setMinWidth(100);

        Type = new TableColumn<>("Type");
        Type.setMinWidth(100);


//        table.getColumns().addAll(Name, Value, Type);
        Measure = new TableColumn("Measure Value");
        Error = new TableColumn("Error Term");
        Units = new TableColumn("Units");
     

        table.getColumns().addAll(Name, Measure, Error, Units, Type);

        final Button addButton = new Button("Add");
        final Button searchButton = new Button("Search");
        hb.getChildren().addAll( searchButton,addButton);
        hb.setSpacing(3);
        
        
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(table,hb);
        
        
        
        ;

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();

        file = new File(System.getProperty("user.dir") + "//repo.txt");

        addData();
    }

    private void addData() {
        Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        String name = "aak";

        Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        Measure.setCellValueFactory(new PropertyValueFactory<>("measuredValue"));
        Error.setCellValueFactory(new PropertyValueFactory<>("errorValue"));
        Units.setCellValueFactory(new PropertyValueFactory<>("unit"));
        Type.setCellValueFactory(new PropertyValueFactory<>("type"));

        ArrayList<Entries> entries = readRepo();
        for (int i = 0; i <= entries.size()-1; i++) {
            table.getItems().add(entries.get(i));
        }
        table.getItems().add(new Entries("abc","213.23","0.9","m","Constant"));

    }

    public ArrayList<Entries> readRepo() {
        ArrayList<Entries> toReturn = new ArrayList<>();
        try {
            file = new File(System.getProperty("user.dir") + "//repo.txt");
            FileReader reader = new FileReader(file);
            int i;
            String[] arr = new String[5];
            arr[0] = ""; arr[1] = ""; arr[2] = ""; arr[3] = ""; arr[4] = "";
            int arrIndx = 0;
            while ((i = reader.read()) != -1) {
                if ((char)i == ',') {
                    arrIndx++;
                    continue;
                }
                if ((char)i == '\n') {
                    arrIndx = 0;
                    toReturn.add(new Entries(arr[0], arr[1], arr[2], arr[3], arr[4]));
                    arr = new String[5];
                    arr[0] = ""; arr[1] = ""; arr[2] = ""; arr[3] = ""; arr[4] = "";
                    continue;
                }
                arr[arrIndx] += "" + (char)i;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toReturn;
    }
}
