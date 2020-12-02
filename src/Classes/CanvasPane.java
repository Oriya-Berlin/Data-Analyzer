package Classes;


import Controllers.GraphController;
import javafx.scene.Node;
import javafx.scene.chart.Chart;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;



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
                        // TODO : make every chart his own  chart window interface
                        GraphController graphController = GraphController.getInstance();
                        graphController.graphUIstage.close();
                        System.out.println(graphController.graphUIstage);

                    }

                    // if we close some table, it will remove it from 'tables' array and from canvas
                    if (node instanceof TableView)
                    {
                        // TODO : handle that later
                    }

                    this.getChildren().remove(node);
                }
            }
        });

        DragResizeMod.makeResizable(node);
        super.getChildren().add(node);

    }
}
