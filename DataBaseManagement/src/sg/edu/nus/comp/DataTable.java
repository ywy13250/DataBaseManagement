package sg.edu.nus.comp;

import javafx.application.Application;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class DataTable extends Application {
    private TableView table = new TableView();
    private ResultSet resultSet = null;
    public DataTable (ResultSet rs) {
        resultSet = rs;
    }
    @Override
    public void start(Stage stage) {
        Scene scene = new Scene (new Group());
        stage.setTitle("Result");
        stage.setWidth(300);
        stage.setHeight(500);

        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            String[] labels = new String[metaData.getColumnCount()];
            TableColumn[] columns = new TableColumn[metaData.getColumnCount()];
            for (int i = 0; i < labels.length; i++) {
                labels[i] = metaData.getColumnLabel(i + 1);
                columns[i] = new TableColumn(metaData.getColumnName(i + 1));
                columns[i].setResizable(true);
                table.getColumns().add(columns[i]);
            }

        } catch (SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        final VBox vbox = new VBox();
        vbox.getChildren().addAll(table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }


}
