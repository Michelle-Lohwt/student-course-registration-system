package sample;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class stuDashboard extends Controller implements Initializable {

  @FXML
  private Label backMenu;

  @FXML
  private Label onMenu;

  @FXML
  private AnchorPane sideNav;

  public void LogOut(MouseEvent event) throws IOException {
    switchTo(event, "logout.fxml");
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
