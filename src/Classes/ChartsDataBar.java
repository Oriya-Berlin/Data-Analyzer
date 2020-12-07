package Classes;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

public class ChartsDataBar {

    private HBox DataBarWindow = new HBox();
    private ListView textField = new ListView();
    private Button DataBarOKbtn = new Button();


    public ChartsDataBar(HBox DataBarWindow, ListView textField, Button DataBarOKbtn) {
        this.DataBarWindow = DataBarWindow;
        this.textField = textField;
        this.DataBarOKbtn = DataBarOKbtn;

    }


        public HBox getDataBarWindow() {
        return DataBarWindow;
    }

    public ListView getDataBarTextField() {
        return textField;
    }

    public Button getDataBarOKbtn() {
        return DataBarOKbtn;
    }



}
