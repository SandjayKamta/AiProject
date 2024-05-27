package com.example.chatbot42_project2_groep_7a.ui;

import com.example.chatbot42_project2_groep_7a.ui.Settings.Settings;
import com.example.chatbot42_project2_groep_7a.util.ChatManager;
import com.example.chatbot42_project2_groep_7a.util.ChatManagerImages;
import com.example.chatbot42_project2_groep_7a.util.queryResolution.QueryResolutionForm;
import com.example.chatbot42_project2_groep_7a.util.queryResolution.QueryResolutionResult;
import com.example.chatbot42_project2_groep_7a.util.queryResolution.QueryResolutionStrategy;
import com.example.chatbot42_project2_groep_7a.util.queryResolution.QueryResolutionStrategyFactory;
import com.example.chatbot42_project2_groep_7a.util.queryResolution.textToImage.TextToImageResolutionStrategyFactory;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.*;


public class TextToImage implements eventhandler{

    private final Stage primaryStage;
    private final BorderPane root = new BorderPane();
    public static String account;
    private final TextField promptInput = new TextField();
    private final Button submitButton = new Button();
    private final VBox chats = new VBox();
    private final VBox chatBox = new VBox();
    private final VBox messageBox = new VBox();
    private ScrollPane scrollPaneChats = new ScrollPane();
    private final HBox inputContainer = new HBox(promptInput, submitButton);
    private final HBox hBoxBottom = new HBox();
    private final Button newChat = new Button("+ Nieuwe Chat");
    private final Button textChat = new Button("Text Chat");
    private final Button imageTextChat = new Button("Foto naar Text Chat");
    private final Button deleteChats = new Button("Verwijder Chats");
    private final Button instellingen = new Button("Instellingen");
    private final Button signOut = new Button("Log Uit");
    private final Scene scene = new Scene(root, 1280, 720);
    private ScrollPane scrollPane = new ScrollPane();
    private int currentChatId = 0;
    private final ChatManagerImages chatManager = ChatManagerImages.getInstance();
    private final HashMap<Integer, Map<Label, ImageView>> chatMap = chatManager.getChatMapImages();

    public TextToImage(Stage primaryStage, String account){
        TextToImage.account = account;
        this.primaryStage = primaryStage;
    }

    private void setImageOnImageView(ImageView imageView, Image imagePath) {
        imageView.setImage(imagePath);
    }

    /**
     *  QueryResolutionStrategy wordt gebruikt voor TextToImage
     */
    private QueryResolutionResult<Image> resolveQuery(String prompt) {
        QueryResolutionStrategyFactory factory = new TextToImageResolutionStrategyFactory();
        QueryResolutionStrategy strategy = factory.createQueryResolutionStrategy();
        QueryResolutionForm<String> form = new QueryResolutionForm<>(prompt);
        return strategy.resolve(form);
    }

    /**
     * Prompt input wordt hier meegegeven en slaat de prompt + resolveQuery op in de ChatManager
     */
    private void handleInput(String input) {
        ChatManagerImages chatManager = ChatManagerImages.getInstance();
        Map<Label, ImageView> chatMessages = chatManager.getChatMessagesImages(currentChatId);
        HashMap<Integer, Map<Label, ImageView>> chatMap = chatManager.getChatMapImages();

        QueryResolutionResult<Image> result = resolveQuery(input);
        Image resultImage = result.resultData();

        Label inputLabel = new Label(input);
        ImageView imageView = createImageView(resultImage);

        messageBox.getChildren().addAll(inputLabel, imageView);

        chatMessages = chatMessages != null ? chatMessages : new LinkedHashMap<>();
        chatMessages.put(inputLabel, imageView);
        chatManager.saveChatMessagesImages(currentChatId, chatMessages);

        loadLastChats(chatMap);
        System.out.println(chatManager.getChatMessagesImages(currentChatId));
    }

    /**
     * Haalt alle opgeslagen Chats op en voegt de Buttons toe.
     */
    public ArrayList<Button> loadLastChats(Map<Integer, Map<Label, ImageView>> chatMap) {
        clearChatChildren();
        addNewChatButton();

        ArrayList<Button> buttons = createChatButtons(chatMap);
        addButtonsToChats(buttons);

        return buttons;
    }

    /**
     * Actie bij klikken van enter in invoerveld
     */
    public void Event1(){
        // Event handlers voor het invoerveld
        promptInput.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                String input = promptInput.getText().toLowerCase();
                handleInput(input);
                promptInput.clear();
            }
        });
    }

    /**
     * Actie bij het klikken op de submit knop
     */
    public void Event2(){
        //Event handler voor sumbit knop
        submitButton.setOnAction(event -> {
            String input = promptInput.getText().toLowerCase();
            handleInput(input);
            promptInput.clear();
        });
    }

    public void setScrollPaneChats(){
        scrollPaneChats = new ScrollPane(chats);
        scrollPaneChats.setFitToWidth(true);
        scrollPaneChats.setPrefViewportHeight(600);
    }

    // Opgeslagen Chats aan linkerzijde van het scherm
    public void containerChats(){
        chats.setSpacing(10);
        chats.setPrefHeight(600);
        chats.setPrefWidth(300);
    }

    // Vbox midden van het scherm
    public void container(){
        chatBox.setFillWidth(true);
        chatBox.getChildren().addAll(scrollPane, messageBox, inputContainer);
        chatBox.setAlignment(Pos.CENTER);
    }


    // ScrollPane binnen boven staande Vbox
    public void setScrollPaneMessages(){
        scrollPane = new ScrollPane(messageBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.vvalueProperty().bind(messageBox.heightProperty());
    }


    // Vbox binnen boven staande ScrollPane
    public void messageBox(){
        messageBox.setSpacing(10);
        messageBox.setPrefHeight(1100);
        messageBox.setPrefWidth(600);
    }

    public void hbox(){
        hBoxBottom.setSpacing(10);
        hBoxBottom.setPadding(new Insets(10));
        hBoxBottom.setPrefHeight(120);
        hBoxBottom.getChildren().addAll(textChat, imageTextChat, deleteChats, instellingen, signOut);
        hBoxBottom.setAlignment(Pos.BOTTOM_CENTER);
        inputContainer.setSpacing(10);
        submitButton.setMinSize(100, 20);
        promptInput.setPromptText("Explain quantum computing to me.");
        promptInput.setPrefWidth(900);
    }

    public void layout(){
        root.setLeft(scrollPaneChats);
        root.setCenter(chatBox);
        root.setBottom(hBoxBottom);
    }

    public void stijl(){
        if (Settings.isDarkMode){
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm());
        }
        else{
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles-light.css")).toExternalForm());
        }        primaryStage.setTitle("ChatBot42");
    }

    public void language(){
        //Taal
        Locale currentLocale = Settings.isNederlandseTaal ? new Locale("nl") : Locale.ENGLISH;
        ResourceBundle messages = ResourceBundle.getBundle("messages", currentLocale);

        newChat.setText(messages.getString("newChat"));
        textChat.setText(messages.getString("textChat"));
        imageTextChat.setText(messages.getString("imageTextChat"));
        deleteChats.setText(messages.getString("deleteChats"));
        submitButton.setText(messages.getString("submitButton"));
        signOut.setText(messages.getString("signOut"));
        instellingen.setText(messages.getString("settings"));
    }

    public void alleButtons(){
        // textChatknop
        textChat.setOnMouseClicked(event -> {
            chats.getChildren().clear();
            Chat chatInterface = new Chat(primaryStage, account);
            chatInterface.show();
        });

        // imageTextChatknop
        imageTextChat.setOnMouseClicked(event -> {
            chats.getChildren().clear();
            ImageToText imageToTextInterface = new ImageToText(primaryStage, account);
            imageToTextInterface.show();
        });

        // Instellingenknop
        instellingen.setOnMouseClicked(event -> {
            chats.getChildren().clear();
            Settings settingsInterface = new Settings(primaryStage);
            settingsInterface.show();
        });

        // Uitlogknop
        signOut.setOnMouseClicked(event -> {
            chats.getChildren().clear();
            Login login = new Login(primaryStage);
            login.show();
        });

        // Verwijder chats knop
        deleteChats.setOnMouseClicked(event -> messageBox.getChildren().clear());

        // Nieuwe chatknop
        newChat.setOnMouseClicked(event -> {
            currentChatId++;
            messageBox.getChildren().clear();
            System.out.println(currentChatId);
            addChat();
        });
    }

    public void show() {
        initializeComponents();
        configureStyles();
        setupScene();
    }

    private void initializeComponents() {
        ArrayList<Button> buttons = loadLastChats(chatMap);
        hbox();
        containerChats();
//        chats.getChildren().addAll(buttons);
        setScrollPaneChats();
        setScrollPaneMessages();
        container();
        messageBox();
        layout();
        Event1();
        Event2();
        alleButtons();
        language();
        stijl();
    }

    private void configureStyles() {
        messageBox.setStyle("-fx-border-color: transparent; -fx-background-color: transparent; -fx-border-width: 0px; -fx-border-radius: 0px; -fx-background-radius: 0px; -fx-padding: 0px;");
    }

    private void setupScene() {
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void addChat() {
        Button chat = new Button("Chat" + (chats.getChildren().size() - 1));
        if (Settings.isDarkMode) {
            chat.setStyle("-fx-border-color: #385CA9;" +
                    "-fx-background-color: #232323;" +
                    "-fx-text-fill: #FFFFFF");
            chats.getChildren().add(chat);
        } else {
            chat.setStyle("-fx-border-color: #385CA9;" +
                    "-fx-background-color: #DBE7FE;" +
                    "-fx-text-fill: #000000;");
            chats.getChildren().add(chat);
        }

        chat.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                ChatManager chatManager = ChatManager.getInstance();
                chat.setText(chatManager.changeTopic("TextToImage", currentChatId));
            }
        });
    }

    private ImageView createImageView(Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(400);
        imageView.setFitWidth(400);
        setImageOnImageView(imageView, image);
        return imageView;
    }

    private void clearChatChildren() {
        chats.getChildren().clear();
    }

    private void addNewChatButton() {
        chats.getChildren().add(newChat);
    }
    private ArrayList<Button> createChatButtons(Map<Integer, Map<Label, ImageView>> chatMap) {
        ArrayList<Button> buttons = new ArrayList<>();

        for (Integer chatId : chatMap.keySet()) {
            Map<Label, ImageView> chatMessages = new LinkedHashMap<>(chatMap.get(chatId));
            System.out.println("Chat ID: " + chatId);

            Button button = createChatButton(chatId, chatMessages);
            buttons.add(button);
        }

        return buttons;
    }
    private Button createChatButton(Integer chatId, Map<Label, ImageView> chatMessages) {
        Button button = new Button("Chat ID: " + chatId);
        button.setOnAction(event -> {
            messageBox.getChildren().clear();
            currentChatId = chatId;
            displayChatMessage(chatMessages);
            System.out.println("Clicked on Chat ID: " + chatId);
            System.out.println("Chat Messages: " + chatMessages);
        });

        String style = Settings.isDarkMode ? "-fx-border-color: #385CA9; -fx-background-color: #232323; -fx-text-fill: #FFFFFF"
                : "-fx-border-color: #385CA9; -fx-background-color: #dbe7fe; -fx-text-fill: #FFFFFF";
        button.setStyle(style);

        return button;
    }

    private void addButtonsToChats(ArrayList<Button> buttons) {
        chats.getChildren().addAll(buttons);
    }

    /**
     * Vult de chatbox met de message van de geselecteerde chat.
     */
    private void displayChatMessage(Map<Label, ImageView> chatMessages) {
        messageBox.getChildren().clear();
        for (Map.Entry<Label, ImageView> entry : chatMessages.entrySet()) {
            Label a = entry.getKey();
            ImageView b = entry.getValue();
            messageBox.getChildren().addAll(a, b);
        }
    }








}
