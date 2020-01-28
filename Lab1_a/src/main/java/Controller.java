import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class Controller {
    @FXML
    public void onClick(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Calculated");
        alert.setHeaderText("Love calculated:");
        alert.setContentText("Button clicked");
        alert.showAndWait();
    }
}
