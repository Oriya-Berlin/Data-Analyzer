package Classes;

import javafx.scene.control.Alert;

public class AlertsMaker {

    /* this class will contains all alert templates windows  */

    public static boolean showSimpleAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();

        if(alert.getResult().getButtonData().isDefaultButton())
            return true;
        else
            return false;
    }


    public static void showErrorMessage(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }


}
