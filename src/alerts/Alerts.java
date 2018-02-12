package alerts;

import javafx.scene.control.Alert.AlertType;
import resources.Resources;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.io.File;
import java.util.Optional;

/**
 * Class created to make specific alerts for errors that may be thrown throughout the program
 */

public class Alerts {

    /**
     * Private constructor to avoid use of implicit public constructor
     */
    private Alerts(){
    }

    /**
     * Alert to notify user that the XML file has been successfully created and saved
     * @param filePath
     */
    public static void XMLCreated(String filePath) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(Resources.getString("XMLSavedTitle"));
        alert.setHeaderText(Resources.getString("XMLSavedHeader"));
        alert.setContentText(Resources.getString("XMLSavedMessage"));
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            alert.close();
        }
    }

    /**
     * Alert to notify the user that the file they have selected is improperly formatted
     * @param e
     * @param file
     */
    public static void XMLError(Exception e, File file) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(Resources.getString("ErrorTitle"));
        alert.setHeaderText(Resources.getString("XMLHeaderError"));
        alert.setContentText(String.format(Resources.getString("XMLMessageError"), e.getMessage(), file.getName()));
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            alert.close();
        }
    }

    /**
     * Alert to notify the user that the file they have selected has an inputType that is not one of the three supported by
     * the program (PROBABILITY, RANDOM, and SPECIFIC)
     * @param e
     */
    public static void XMLWrongInputType(Exception e) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(Resources.getString("ErrorTitle"));
        alert.setHeaderText(Resources.getString("XMLHeaderError"));
        alert.setContentText(String.format(Resources.getString("InvalidInputTypeMessage"), e.getMessage()));
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            alert.close();
        }
    }

    /**
     * Alert to notify the user that the file they have selected has a simulationType that is not one of the five
     * supported by the program (CONWAY, FIRE, SEGREGATION, PRED_PREY, and RPS)
     * @param e
     */
    public static void XMLWrongSimulationType(Exception e) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(Resources.getString("ErrorTitle"));
        alert.setHeaderText(Resources.getString("XMLHeaderError"));
        alert.setContentText(String.format(Resources.getString("InvalidSimulationError"), e.getMessage()));
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            alert.close();
        }
    }

    /**
     * Alert to notify the user that the file they have selected has missing data for one or more cells
     * @param e
     */
    public static void CellDataError(Exception e) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(Resources.getString("ErrorTitle"));
        alert.setHeaderText(Resources.getString("XMLHeaderError"));
        alert.setContentText(String.format(Resources.getString("InvalidCellDataMessage"), e.getMessage()));
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            alert.close();
        }
    }

    /**
     * Alert to notify the user that the file they have selected specifies a state that is not defined in
     * the game type's rules
     * @param e
     */
    public static void InvalidStateValue(Exception e) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(Resources.getString("ErrorTitle"));
        alert.setHeaderText(Resources.getString("XMLHeaderError"));
        alert.setContentText(String.format(Resources.getString("InvalidStateValue"), e.getMessage()));
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            alert.close();
        }
    }
}
