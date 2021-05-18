package mvc;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import mvc.Controleur;
import mvc.Modele;

import java.io.IOException;

/*
 * Dans cette classe nous definissons les éléments graphiques de notre
 * application.
 *
 * NB: voir aussi lignes 64-74!
 */
public class Vue extends Application {

    private static Controleur controleur;

    @Override
    public void start(Stage primaryStage) {

        try {
            // Scene
            BorderPane root = new BorderPane();
            Insets borderPadding = new Insets(15, 15, 15, 15);
            root.setPadding(borderPadding);

            Scene scene = new Scene(root, 670, 390, Color.rgb(40, 60, 79));


            // Compteurs
            HBox textBox = new HBox();
            VBox numberBox1 = new VBox();
            VBox numberBox2 = new VBox();

            // Valeurs des compteurs
            Text textValeur1 = new Text("0");
            textStyle(textValeur1);
            Text textValeur2 = new Text("0");
            textStyle(textValeur2);

            // Selection de compteur
            final ToggleGroup group = new ToggleGroup();
            RadioButton select1 = new RadioButton("Compteur 1: \n");
            radioGroupAndStyle(group, select1, true);
            RadioButton select2 = new RadioButton("Compteur 2: \n");
            radioGroupAndStyle(group, select2, false);

            // Bouttons des controle
            Button inc = new Button("+1");
            stylizeButton(inc);
            Button dub = new Button("*2");
            stylizeButton(dub);
            Button div = new Button("/2");
            stylizeButton(div);
            Button dec = new Button("-1");
            stylizeButton(dec);

            BorderPane.setAlignment(inc, Pos.CENTER);
            BorderPane.setAlignment(dub, Pos.CENTER);
            BorderPane.setAlignment(div, Pos.CENTER);
            BorderPane.setAlignment(dec, Pos.CENTER);

            // On met les radios et leurs valeurs dans leur respectives boites.
            Insets padding1 = new Insets(0, 50, 0, 0);
            Insets padding2 = new Insets(0, 0, 0, 50);
            setNumberBox(numberBox1, select1, textValeur1, padding1);
            setNumberBox(numberBox2, select2, textValeur2, padding2);

            // On met les boites des nombres dans une boite contennant tous les nombres et selecteurs.
            textBox.getChildren().addAll(numberBox1, numberBox2);
            textBox.setAlignment(Pos.CENTER);
            textBox.setPadding(new Insets(10, 10, 10, 10));

            // Menu bar et exporter historique
            VBox topDisplay = new VBox();
            Menu menu = new Menu("Menu");
            MenuItem exporterHistorique = new MenuItem("Exporter historique");
            menu.getItems().add(exporterHistorique);
            MenuBar menuBar = new MenuBar();
            menuBar.getMenus().addAll(menu);


            // Redo undo et inc vont dans le top
            Button undo = new Button("Undo");
            stylizeButton(undo);
            undo.setAlignment(Pos.CENTER);
            Button redo = new Button("Redo");
            stylizeButton(redo);
            redo.setAlignment(Pos.CENTER);

            // Boite pour undo
            VBox undoBox = new VBox();
            undoBox.getChildren().add(undo);
            undoBox.setAlignment(Pos.TOP_LEFT);
            undoBox.setPadding(new Insets(0, 80, 0, 0));

            // pour inc
            VBox incBox = new VBox();
            incBox.getChildren().add(inc);
            incBox.setAlignment(Pos.TOP_CENTER);

            // pour redo
            VBox redoBox = new VBox();
            redoBox.getChildren().add(redo);
            redoBox.setAlignment(Pos.TOP_RIGHT);
            redoBox.setPadding(new Insets(0, 0, 0, 80));

            // contient les boites des bouttons
            HBox topButtons = new HBox();
            topButtons.getChildren().addAll(undoBox, incBox, redoBox);
            topButtons.setAlignment(Pos.CENTER);
            topButtons.setPadding(new Insets(10, 0, 0, 0));

            // on met le menuBar puis les bouttons
            topDisplay.getChildren().addAll(menuBar, topButtons);
            topDisplay.setAlignment(Pos.CENTER);


            // Background
            Image image = new Image("/Background.gif");
            Background background = setBackground(image);


            // On insert le tout dans notre scene
            root.setTop(topDisplay);
            root.setBottom(dec);
            root.setLeft(dub);
            root.setRight(div);
            root.setCenter(textBox);

            root.setBackground(background);

            // Le controleur manipule tout evenement.

            inc.setOnAction((action) -> {
                controleur.inc(select1.isSelected());
            });

            dec.setOnAction((action) -> {
                controleur.dec(select1.isSelected());
            });

            dub.setOnAction((action) -> {
                controleur.dub(select1.isSelected());
            });

            div.setOnAction((action) -> {
                controleur.div(select1.isSelected());
            });

            exporterHistorique.setOnAction((action) -> {
                try {
                    controleur.historique();
                } catch (IOException exception) {
                    System.out.println("Erreur pendant la creation du fichier historique.txt");
                }
            });

            undo.setOnAction((action) -> {
                controleur.undo();
            });

            redo.setOnAction((action) -> {
                controleur.redo();
            });

            /*
             * En raison de la conception des applications JavaFX, nous sommes obligés de
             * créer le modèle et le controleur ici.
             *
             * Notez cependant que nous passons l'instance du modèle directement dans le
             * constructeur du controleur; nous n'y avons pas d'autre accès.
             *
             * Pour faciliter les choses, ici le constructeur ne prend pas la mvc. Vue entière,
             * mais juste le sous-ensemble de la mvc.Vue (l'objet Text) qu'il doit manipuler.
             */
            controleur = new Controleur(new Modele(), new Modele(), textValeur1, textValeur2);

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stylizeButton(Button button) {
        Double width = (double) 40;
        Double height = (double) 35;
        String style = "-fx-background-color: white; -fx-text-fill: rgb(40,60,79); -fx-font-weight: normal; -fx-font-size:15";
        String hoverStyle = "-fx-background-color: #32965D; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size:15";
        button.setStyle(style);
        // Quand le souris rentre, on change le style
        button.setOnMouseEntered(e -> button.setStyle(hoverStyle));
        // On le change a nouveau en sortant, donnant l'effet de hover
        button.setOnMouseExited(e -> button.setStyle(style));
        // On met la taille
        button.setMinWidth(width);
        button.setMinHeight(height);
    }

    private void radioGroupAndStyle(ToggleGroup group, RadioButton radio, boolean mainRadio) {
        String style = "-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size:15";
        radio.setStyle(style);
        radio.setToggleGroup(group);
        // Si c'est notre boutton principal, le selectionner par defaut.
        radio.setSelected(mainRadio);
    }

    private void textStyle(Text text) {
        Color color = Color.WHITE;
        Font font = Font.font(20);
        String style = "-fx-font-weight: bold;";

        text.setFill(color);
        text.setFont(font);
        text.setStyle(style);
    }

    private void setNumberBox(VBox box, RadioButton radio, Text text, Insets padding) {
        Pos position = Pos.CENTER;

        box.getChildren().add(radio);
        box.getChildren().add(text);
        box.setAlignment(position);
        box.setPadding(padding);
    }

    private Background setBackground(Image image) {
        // Determiner les dimensions automatiquement, n'occupe pas tout la fenetre pour preserver la qualitee de l'image
        // mais
        BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false,
                false, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        return new Background(backgroundImage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}