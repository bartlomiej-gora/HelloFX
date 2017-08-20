package pl.bgora;

import com.github.bgora.rpnlibrary.Calculator;
import com.github.bgora.rpnlibrary.exceptions.RPNException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.net.URL;
import java.util.ResourceBundle;

public class CalculatorController implements Initializable {

    @FXML
    private Button calculateBtn;

    @FXML
    private ListView<String> resultList;

    @FXML
    private TextField textField1;

    @FXML
    private MenuItem close;

    @FXML
    private MenuItem about;

    private Calculator calc;
    private ObservableList<String> list;

    public CalculatorController() {
        calc = Calculator.createDefaultCalculator();
        list = FXCollections.observableArrayList();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        calculateBtn.setOnAction(event ->
        {
            if (textField1.getText().length() > 0) {
                try {
                    execute();
                } catch (RPNException e) {
                    showError(e);
                }
            }
        });

        textField1.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER && textField1.getText().length() > 0) {
                try {
                    execute();
                } catch (RPNException e) {
                    showError(e);
                }
            }
        });

        close.setOnAction(event -> {
            System.exit(0);
        });

        about.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("About");
            alert.setContentText("RPN Library demo app\nRPNLibrary version 3.2.2");
            alert.show();
        });


    }

    private void showError(RPNException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Błąd!");
        alert.setContentText(e.getMessage());
        alert.show();
    }

    private void execute() throws RPNException {
        String result = calc.calculate(textField1.getText()).toString();
        list.add(result);
        resultList.setItems(list);
        textField1.clear();
    }
}
