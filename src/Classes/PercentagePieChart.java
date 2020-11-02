package Classes;

import Controllers.GraphController;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.text.Text;

import java.util.Optional;

public class PercentagePieChart extends PieChart {

    @Override
    protected void layoutChartChildren(double top, double left, double contentWidth, double contentHeight) {

        GraphController graphController = GraphController.getInstance();
        Double sum = null;

        if(graphController.row_or_column.getSelectedToggle() == graphController.rowRadio)
            sum = ChartsDataHandling.row_sum;
        if(graphController.row_or_column.getSelectedToggle() == graphController.colRadio)
            sum = ChartsDataHandling.column_sum;


        if (getLabelsVisible()) {
            Double finalSum = sum;

            getData().forEach(d -> {
                Optional<Node> opTextNode = this.lookupAll(".chart-pie-label").stream().filter(n -> n instanceof Text && ((Text) n).getText().contains(d.getName())).findAny();

                if (opTextNode.isPresent()) {
                    if(finalSum != null)
                        ((Text) opTextNode.get()).setText(d.getPieValue() + " " + ChartsDataHandling.calculatePercent(finalSum, d.getPieValue()));
                    else
                        ((Text) opTextNode.get()).setText(d.getPieValue() + " (20%)" );
                }
            });
        }
        super.layoutChartChildren(top, left, contentWidth, contentHeight);

    }


}
