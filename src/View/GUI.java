package View;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Observable;
import java.util.Observer;

/**
 * Creates the GUI for the user to interact with the program
 *
 * @author Amber Harding
 */

public class GUI extends Application implements Observer {

    private Client client;
    private VBox textExchanges = new VBox();
    private Insets insets;
    private Stage stage;


    /**
     * Generates the application window*/
    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        this.client = new Client();
        client.addObserver(this);
        client.sendCommand("connect;");

        stage.setTitle("Airline Flight Reservation Server");

        stage.setScene(createScene());
        stage.show();
    }

    private Scene createScene() {
        BorderPane mainPane = new BorderPane();
        mainPane.setPrefHeight(500.0D);
        mainPane.setPrefWidth(500.0D);
        insets = new Insets(30);
        mainPane.setPadding(insets);

        mainPane.setTop(top());
        mainPane.setCenter(center());
        mainPane.setBottom(bottom());

        return new Scene(mainPane);
    }

    /**
     * Method to generate a background
     */
    private Background getBackground(int red, int green, int blue){
        Color color = Color.rgb(red, green, blue);
        return new Background(new BackgroundFill(color,CornerRadii.EMPTY,Insets.EMPTY));
    }

    /**
     * Creates HBox for the top portion of the main pane.
     * Contains event handler for the disconnect button.
     */
    private HBox top() {
        Button connect = new Button("Connect");
        connect.setFont(Font.font("Verdana",20));

        connect.setOnAction(event -> {
            try {
                Stage stage = new Stage();
                GUI newgui = new GUI();
                newgui.start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Button disconnect = new Button("Disconnect");
        disconnect.setFont(Font.font("Verdana",20));

        disconnect.setOnAction(event -> stop());

        HBox hBox = new HBox();
        hBox.getChildren().addAll(connect,disconnect);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(insets);
        hBox.setSpacing(10);
        hBox.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        hBox.setBackground(getBackground(155,200,255));

        return hBox;
    }

    /**
     * Creates VBox for the top portion of the main pane.
     * Contains event handler for the submit button.
     */
    private VBox center() {
        VBox vBox = new VBox();
        vBox.setSpacing(10);

        TextField textField = new TextField();
        textField.setPrefSize(100,100);
        Button submit = new Button("Submit");
        submit.setFont(Font.font("Verdana",20));
        submit.setAlignment(Pos.CENTER);
        submit.setDisable(true);

        textField.textProperty().addListener(observable -> submit.setDisable(false));

        textField.setOnKeyPressed(event -> {
            if(event.getCode().equals(KeyCode.ENTER)){
                submit.fire();
            }
        });

        submit.setOnAction(event -> {
            String command = textField.getText();
            client.sendCommand(command);
            textField.clear();
            submit.setDisable(true);
        });

        vBox.getChildren().addAll(textField,submit);

        vBox.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        vBox.setAlignment(Pos.CENTER);

        return vBox;
    }

    /**
     * Creates VBox for the top portion of the main pane.
     * This VBox will display the responses from the client.
     */
    private VBox bottom() {

        VBox vBox = new VBox();
        Text text = new Text("Response:");
        text.setFont(Font.font ("Verdana", 15));

        ScrollPane scrollPane = new ScrollPane();

        scrollPane.setBackground(getBackground(255,255,255));
        scrollPane.setPrefSize(120, 120);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setContent(textExchanges);

        vBox.getChildren().addAll(text,scrollPane);
        vBox.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.setBackground(getBackground(155,200,255));

        return vBox;
    }

    /**
     * Helper method for when a new client response is generated.
     */
    private void updateTextExchange(String response){
        Text text = new Text(response);
        textExchanges.getChildren().add(text);
    }

    @Override
    public void stop(){
        client.sendCommand("disconnect;");
        client.disconnect();
        stage.close();
    }

    @Override
    public void update(Observable o, Object arg) {
        String response = (String) arg;
        if(response != null){
            updateTextExchange(response);
        }
    }
}