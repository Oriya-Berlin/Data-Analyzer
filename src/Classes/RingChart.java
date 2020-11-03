package Classes;


import Controllers.GraphController;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.Optional;


public class RingChart extends PieChart {

    private final Circle innerCircle;



    public RingChart(ObservableList<Data> pieData) {
        super(pieData);

        innerCircle = new Circle();

        // just styled in code for demo purposes,
        // use a style class instead to style via css.
        innerCircle.setFill(Color.WHITESMOKE);
        innerCircle.setStroke(Color.WHITE);
        innerCircle.setStrokeWidth(3);
    }


    public RingChart() {
        super();

        innerCircle = new Circle();

        // just styled in code for demo purposes,
        // use a style class instead to style via css.
        innerCircle.setFill(Color.WHITESMOKE);
        innerCircle.setStroke(Color.WHITE);
        innerCircle.setStrokeWidth(3);
    }



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

        addInnerCircleIfNotPresent();
        updateInnerCircleLayout();
    }



    public void addInnerCircleIfNotPresent() {
        if (getData().size() > 0) {
            Node pie = getData().get(0).getNode();
            if (pie.getParent() instanceof Pane) {
                Pane parent = (Pane) pie.getParent();

                if (!parent.getChildren().contains(innerCircle)) {
                    parent.getChildren().add(innerCircle);
                }
            }
        }
    }


    public void updateInnerCircleLayout() {
        double minX = Double.MAX_VALUE, minY = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE, maxY = Double.MIN_VALUE;
        for (Data data: getData()) {
            Node node = data.getNode();

            Bounds bounds = node.getBoundsInParent();
            if (bounds.getMinX() < minX) {
                minX = bounds.getMinX();
            }
            if (bounds.getMinY() < minY) {
                minY = bounds.getMinY();
            }
            if (bounds.getMaxX() > maxX) {
                maxX = bounds.getMaxX();
            }
            if (bounds.getMaxY() > maxY) {
                maxY = bounds.getMaxY();
            }
        }

        innerCircle.setCenterX(minX + (maxX - minX) / 2);
        innerCircle.setCenterY(minY + (maxY - minY) / 2);

        innerCircle.setRadius((maxX - minX) / 4);
    }
}