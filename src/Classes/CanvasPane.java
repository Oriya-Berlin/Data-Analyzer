package Classes;


import javafx.scene.Node;
import javafx.scene.chart.Chart;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import Controllers.*;



public class CanvasPane extends AnchorPane {

    public void addChildren(Node node) {

        node.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.DELETE) && node.isFocused())
            {
                boolean user_choise = AlertsMaker.showSimpleAlert("Confirm Action", "Are you sure you want do delete this item permanently?");
                if(user_choise)
                {
                    // if we close some graph, it will close also he's graph interface window
                    if (node instanceof Chart){
                        // TODO : make every chart his own chart window interface
                        GraphController graphController = GraphController.getInstance();
                        graphController.graphUIstage.close();
                    }

                    // if we close some table, it will remove it from 'tables' array and from canvas
                    if (node instanceof AnalyzerTableView)
                    {
                        TablesRegistry tables = TablesRegistry.getInstance();
                        tables.remove(node.toString());

                        // remove table name from combo box
                        ExecuteCommandController instance = ExecuteCommandController.getInstance();
                        ComboBox<String> cb = instance.getComboBox();
                        cb.getItems().remove(node.toString());
                    }

                    this.getChildren().remove(node);
                }
            }
        });

        DragResizeMod.makeResizable(node);
        super.getChildren().add(node);

    }
}
