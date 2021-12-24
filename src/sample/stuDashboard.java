package sample;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class stuDashboard implements Initializable {
  private Stage stage;
  private Scene scene;
  private Parent root;

  @FXML
  private Label backMenu;

  @FXML
  private Label onMenu;

  @FXML
  private AnchorPane sideNav;

  public void switchToLogOut(MouseEvent event) throws IOException {
    root = FXMLLoader.load(getClass().getResource("logout.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    sideNav.setTranslateX(-350);
    onMenu.setOnMouseClicked(event -> {
      TranslateTransition slide = new TranslateTransition();
      slide.setDuration(Duration.seconds(0.4));
      slide.setNode(sideNav);

      slide.setToX(0);
      slide.play();

      sideNav.setTranslateX(-350);

      slide.setOnFinished((ActionEvent e) -> {
        onMenu.setVisible(false);
        backMenu.setVisible(true);
      });
    });

    backMenu.setOnMouseClicked(event -> {
      TranslateTransition slide = new TranslateTransition();
      slide.setDuration(Duration.seconds(0.4));
      slide.setNode(sideNav);

      slide.setToX(-350);
      slide.play();

      sideNav.setTranslateX(0);

      slide.setOnFinished((ActionEvent e) -> {
        onMenu.setVisible(true);
        backMenu.setVisible(false);
      });
    });
  }
}
