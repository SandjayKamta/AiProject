package com.example.chatbot42_project2_groep_7a.ui;

import com.example.chatbot42_project2_groep_7a.ui.Settings.Settings;
import com.example.chatbot42_project2_groep_7a.util.ChatManager;
import com.example.chatbot42_project2_groep_7a.util.ChatManagerImages;
import com.example.chatbot42_project2_groep_7a.util.queryResolution.QueryResolutionForm;
import com.example.chatbot42_project2_groep_7a.util.queryResolution.QueryResolutionResult;
import com.example.chatbot42_project2_groep_7a.util.queryResolution.QueryResolutionStrategy;
import com.example.chatbot42_project2_groep_7a.util.queryResolution.QueryResolutionStrategyFactory;
import com.example.chatbot42_project2_groep_7a.util.queryResolution.imageToText.ImageToTextResolutionStrategyFactory;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ImageToText {
    private Stage primaryStage;
    private TextField textField;
    public static String account;
    private VBox chatBox = new VBox();
    private ScrollPane scrollPaneChats;
    private ScrollPane scrollPaneMessages;
    private ImageView imageView;
    private VBox messageBox = new VBox();
    private HBox hBoxBottom = new HBox();
    private Button newChat = new Button("+ Nieuwe Chat");
    private Button textChat = new Button("Text Chat");
    private Button textImageChat = new Button("Tekst naar Foto Chat");

    private Button deleteChats = new Button("Verwijder Chats");
    private Button instellingen = new Button("Instellingen");
    private Button signOut = new Button("Log Uit");
    private TextArea textArea;
    private int currentChatId = 0;
    private final ChatManagerImages chatManager = ChatManagerImages.getInstance();
    private final HashMap<Integer, Map<ImageView, Label>> chatMap = chatManager.getChatMapImagesToText();
    private Dragboard dragboard;
    private Map<Integer, String> chatTopics = new HashMap<>();



    public ImageToText(Stage primaryStage, String account) {
        initializeVariables(primaryStage);
        this.account = account;
        chatTopics.putAll(ChatManager.chatTopicMap.get("TextToText"));
    }


    private void initializeVariables(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.textField = new TextField();
        this.imageView = new ImageView();
        this.textArea = new TextArea();
//        this.queryResolutionFactory = new ImageToTextFactory();
    }

    private void setImageOnImageView(ImageView imageView, Image imagePath) {
        imageView.setImage(imagePath);
        imageView.setFitWidth(400);
        imageView.setFitHeight(400);
    }


    private QueryResolutionResult<String> resolveQuery(Image prompt) {
        QueryResolutionStrategyFactory factory = new ImageToTextResolutionStrategyFactory();
        QueryResolutionStrategy strategy = factory.createQueryResolutionStrategy();
        QueryResolutionForm<Image> form = new QueryResolutionForm<>(prompt);
        return strategy.resolve(form);
    }
    private void handleInput(Image input) {
        ChatManagerImages chatManager = ChatManagerImages.getInstance();
        Map<ImageView, Label> chatMessages = chatManager.getChatMessagesImagesToText(currentChatId);
        HashMap<Integer, Map<ImageView, Label>> chatMap = chatManager.getChatMapImagesToText();

        QueryResolutionResult<String> result = resolveQuery(input);
        String resultString = result.resultData();

        ImageView imageView = createImageView(input);
        setImageOnImageView(imageView, input);
        Label inputLabel = new Label(resultString);
        inputLabel.setWrapText(true);
        inputLabel.setBackground(Background.fill(Color.WHITE));
        inputLabel.setScaleX(1.5);
        inputLabel.setScaleY(1.5);


        messageBox.getChildren().addAll(imageView, inputLabel);

        chatMessages = chatMessages != null ? chatMessages : new LinkedHashMap<>();
        chatMessages.put(imageView, inputLabel);
        chatManager.saveChatMessagesImagesToText(currentChatId, chatMessages);

        loadLastChats(chatMap);
        System.out.println(chatManager.getChatMessagesImagesToText(currentChatId));
    }

    /**
     * Haalt alle opgeslagen Chats op en voegt de Buttons toe.
     */
    public ArrayList<Button> loadLastChats(Map<Integer, Map<ImageView, Label>> chatMap) {
        clearChatChildren();
        addNewChatButton();

        ArrayList<Button> buttons = createChatButtons(chatMap);
        addButtonsToChats(buttons);

        return buttons;
    }

    public void show() {
        // Hoofdcontainer
        BorderPane root = new BorderPane();
        loadLastChats(chatMap);

        // Linkercontainer voor chats
        chatBox.setSpacing(10);
        chatBox.setPadding(new Insets(10));
        chatBox.setPrefHeight(600);
        chatBox.setPrefWidth(300);

        // Scrollpane voor chats
        scrollPaneChats = new ScrollPane(chatBox);
        scrollPaneChats.setFitToWidth(true);
        scrollPaneChats.setPrefViewportHeight(600);

        // Scrollpane voor chatberichten
        scrollPaneMessages = new ScrollPane(messageBox);
        scrollPaneMessages.setFitToWidth(true);
        scrollPaneMessages.setFitToHeight(true);
        scrollPaneMessages.vvalueProperty().bind(messageBox.heightProperty());

        scrollPaneMessages.setOnDragOver(event -> {
            dragboard = event.getDragboard();
            if (dragboard.hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY);
            } else {
                event.consume();
            }
        });

        // Haal de PATH + NAAM van de afbeelding op en geef deze mee aan de handleImageDrop() methode
        scrollPaneMessages.setOnDragDropped(event -> {
            dragboard = event.getDragboard();
            boolean success = false;
            if (dragboard.hasFiles()) {
                success = true;
                File imageFile = dragboard.getFiles().get(0);
                Image image = new Image(imageFile.toURI().toString());
                handleInput(image);
            }
            event.setDropCompleted(success);
            event.consume();
        });



        // Tekstgebied voor chatberichten
//        setImageOnImageView(imageView, ImagePaths.START_IMAGE);
        imageView.setFitHeight(300);
        imageView.setFitWidth(400);

        // Container voor chatberichten
        textArea.setWrapText(true);
        textArea.setEditable(false);
        textArea.setPrefHeight(300);
        textArea.setPrefWidth(300);


        // Invoerveld voor prompts
        TextField promptInput = new TextField();
        promptInput.setPromptText("Explain quantum computing to me.");
        promptInput.setPrefWidth(900);

        // Knop voor het versturen van de prompt
        Button submitButton = new Button();
        submitButton.setMinSize(100, 20);

        // Container voor invoerveld en knop van de prompt
//        HBox inputContainer = new HBox(promptInput, submitButton);
//        inputContainer.setSpacing(10);



        // Container voor chatberichten en prompt-invoer
        messageBox.setFillWidth(true);
        messageBox.getChildren().addAll(scrollPaneMessages);
        messageBox.setAlignment(Pos.CENTER);
        messageBox.setSpacing(10);

        // Onderste HBox met knoppen
        hBoxBottom.setSpacing(10);
        hBoxBottom.setPadding(new Insets(10));
        hBoxBottom.setPrefHeight(120);
        hBoxBottom.getChildren().addAll(textChat, textImageChat, deleteChats, instellingen, signOut);
        hBoxBottom.setAlignment(Pos.BOTTOM_CENTER);

        // textChatknop
        textChat.setOnMouseClicked(event -> {
            Chat chatInterface = new Chat(primaryStage, account);
            chatInterface.show();
        });

        // text naar image knop
        textImageChat.setOnMouseClicked(event -> {
            TextToImage imageInterface = new TextToImage(primaryStage, account);
            imageInterface.show();
        });

        // Instellingenknop
        instellingen.setOnMouseClicked(event -> {
            Settings settingsInterface = new Settings(primaryStage);
            settingsInterface.show();
        });

        // Uitlogknop
        signOut.setOnMouseClicked(event -> {
            Login loginInterface = new Login(primaryStage);
            loginInterface.show();
        });

        // Nieuwe chatknop
        newChat.setOnMouseClicked(event -> {
            currentChatId++;
            messageBox.getChildren().clear();
            System.out.println(currentChatId);
            addChat();
        });

        //Stijl van alle buttons etc.

        // Plaatsing van elementen in de root-container
        root.setLeft(scrollPaneChats);
        root.setCenter(scrollPaneMessages);
        root.setBottom(hBoxBottom);

//        // Event handlers voor het invoerveld en de knop
//        promptInput.setOnKeyPressed(keyEvent -> {
//            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
//                String input = promptInput.getText().toLowerCase();
//                handleInput(input);
//                promptInput.clear();
//            }
//        });
//
//        submitButton.setOnAction(event -> {
//            String input = promptInput.getText().toLowerCase();
//            handleInput(input);
//            promptInput.clear();
//        });





        // Creëer en toon de scène
        Scene scene = new Scene(root, 1280, 720);
        if (Settings.isDarkMode){
            scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        }
        else{
            scene.getStylesheets().add(getClass().getResource("/styles-light.css").toExternalForm());
        }
        primaryStage.setTitle("ChatBot42");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private ImageView createImageView(Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(400);
        imageView.setFitWidth(400);
        setImageOnImageView(imageView, image);
        return imageView;
    }

    public void addChat() {
        Button chat = new Button("Chat" + chatBox.getChildren().size());
        if (Settings.isDarkMode) {
            chat.setStyle("-fx-border-color: #385CA9;" +
                    "-fx-background-color: #232323;" +
                    "-fx-text-fill: #FFFFFF");
            chatBox.getChildren().add(chat);
        } else {
            chat.setStyle("-fx-border-color: #385CA9;" +
                    "-fx-background-color: #DBE7FE;" +
                    "-fx-text-fill: #000000;");
            chatBox.getChildren().add(chat);
        }

        chat.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                ChatManager chatManager = ChatManager.getInstance();
                chat.setText(chatManager.changeTopic("TextToText", currentChatId));
                Map<Integer, String> currentChat = new HashMap<>();
                currentChat.put(currentChatId, chat.getText());
                ChatManager.chatTopicMap.put("TextToText", currentChat);
            }
        });
    }

    private void clearChatChildren() {
        chatBox.getChildren().clear();
    }

    private void addNewChatButton() {
        chatBox.getChildren().add(newChat);
    }
    private ArrayList<Button> createChatButtons(Map<Integer, Map<ImageView, Label>> chatMap) {
        ArrayList<Button> buttons = new ArrayList<>();

        for (Integer chatId : chatMap.keySet()) {
            Map<ImageView, Label> chatMessages = new LinkedHashMap<>(chatMap.get(chatId));
            System.out.println("Chat ID: " + chatId);

            Button button = createChatButton(chatId, chatMessages);
            buttons.add(button);
        }

        return buttons;
    }

    private Button createChatButton(Integer chatId, Map<ImageView, Label> chatMessages) {
        Button button;
        if (chatTopics == null || chatTopics.get(chatId) == null) {
            button = new Button("Chat ID: " + chatId);
        } else {
            Map <Integer, String> topic = new LinkedHashMap<>(ChatManager.chatTopicMap.get("TextToText"));

            button = new Button(topic.get(chatId));
        }

        button.setOnMouseClicked(event -> {
            messageBox.getChildren().clear();
            currentChatId = chatId;
            displayChatMessage(chatMessages);
            System.out.println("Clicked on Chat ID: " + chatId);
            System.out.println("Chat Messages: " + chatMessages);

            if (event.getButton() == MouseButton.SECONDARY) {
                ChatManager chatManager = ChatManager.getInstance();
                button.setText(chatManager.changeTopic("TextToText", currentChatId));
                Map<Integer, String> currentChat = new HashMap<>();
                currentChat.put(currentChatId, button.getText());
                ChatManager.chatTopicMap.put("TextToText", currentChat);
                chatTopics.putAll(ChatManager.chatTopicMap.get("TextToText"));
            }
        });

        String style = Settings.isDarkMode ? "-fx-border-color: #385CA9; -fx-background-color: #232323; -fx-text-fill: #FFFFFF"
                : "-fx-border-color: #385CA9; -fx-background-color: #dbe7fe; -fx-text-fill: #FFFFFF";
        button.setStyle(style);

        return button;
    }


    private void addButtonsToChats(ArrayList<Button> buttons) {
        chatBox.getChildren().addAll(buttons);
    }

    /**
     * Vult de chatbox met de message van de geselecteerde chat.
     */
    private void displayChatMessage(Map<ImageView, Label> chatMessages) {
        for (Map.Entry<ImageView, Label> entry : chatMessages.entrySet()) {
            ImageView a = entry.getKey();
            Label b = entry.getValue();
            messageBox.getChildren().addAll(a, b);
        }
    }
}

