package com.example.chatbot42_project2_groep_7a.ui;

import com.example.chatbot42_project2_groep_7a.db.DatabaseConnection;
import com.example.chatbot42_project2_groep_7a.ui.Settings.Settings;
import com.example.chatbot42_project2_groep_7a.util.ChatManager;
import com.example.chatbot42_project2_groep_7a.util.queryResolution.QueryResolutionForm;
import com.example.chatbot42_project2_groep_7a.util.queryResolution.QueryResolutionResult;
import com.example.chatbot42_project2_groep_7a.util.queryResolution.QueryResolutionStrategy;
import com.example.chatbot42_project2_groep_7a.util.queryResolution.QueryResolutionStrategyFactory;
import com.example.chatbot42_project2_groep_7a.util.queryResolution.textToText.TextToTextResolutionStrategyFactory;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Chat implements eventhandler {

    protected Stage primaryStage;
    private TextField textField;
    private Button newChat = new Button("+ Nieuwe Chat");
    private Button textImageChat = new Button("Tekst naar Foto Chat");
    private Button imageTextChat = new Button("Foto naar Text Chat");
    private Button deleteChats = new Button("Verwijder Chats");
    private Button instellingen = new Button("Instellingen");
    private Button signOut = new Button("Log Uit");
    private Button chat = new Button();
    private Button submitButton = new Button();
    private VBox chats = new VBox();
    private VBox chatBox = new VBox();
    private HBox hBoxBottom = new HBox();
    private ScrollPane scrollPaneChats;
    private TextField promptInput = new TextField();
    private TextArea textArea;
    public static String account;
    private DatabaseConnection databaseConnection;
    private BorderPane root = new BorderPane();
    private Scene scene = new Scene(root, 1280, 720);
    private Map<Integer, String> chatTopics = new HashMap<>();
    private int currentChatId = 0;

    public Chat(Stage primaryStage, String account) {
        initializeVariables(primaryStage);
        this.account = account;
    }

    /**
     * Hier wordt de chatinterface opgebouwd in een aparte methode.
     * Maak hier dus aanpassingen als je iets nieuws wilt toevoegen.
     */
    private void initializeVariables(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.textField = new TextField();
        VBox vBox = new VBox();
        this.textArea = new TextArea();
        this.databaseConnection = new DatabaseConnection();
    }

    /**
     * Hier halen wij de laatste gesprekken op en voegen dit aan de linkerkant toe.
     */
    public ArrayList<Button> loadLastChats(Map<Integer, Map<String, Object>> chatMap) {
        chats.getChildren().clear();
        ArrayList<Button> buttons = new ArrayList<>();
        chats.getChildren().add(newChat);

        for (Integer chatId : chatMap.keySet()) {
            Map<String, Object> chatMessages = new LinkedHashMap<>(chatMap.get(chatId));
            System.out.println("Chat ID: " + chatId);

            Button button;

            if (chatTopics == null || chatTopics.get(chatId) == null) {
                button = new Button("Chat ID: " + chatId);
            } else {
                Map <Integer, String> chatTopic = new LinkedHashMap<>(ChatManager.chatTopicMap.get("TextToText"));
                button = new Button(chatTopic.get(chatId));
            }
            button.setOnMouseClicked(event -> {
                textArea.clear();
                currentChatId = chatId;
                displayChatMessage(chatMessages);
                System.out.println("Clicked on Chat ID: " + chatId);
                System.out.println("Chat Messages: " + chatMessages);

                    if (event.getButton() == MouseButton.SECONDARY) {
                        ChatManager chatManager = ChatManager.getInstance();
                        button.setText(chatManager.changeTopic("TextToText", currentChatId));
                        chatTopics.putAll(ChatManager.chatTopicMap.get("TextToText"));
                    }
            });
            if (Settings.isDarkMode){
                button.setStyle("-fx-border-color: #385CA9;" +
                        "-fx-background-color: #232323;" +
                        "-fx-text-fill: #FFFFFF");
                chats.getChildren().add(button);
            }
            else{
                button.setStyle("-fx-border-color: #385CA9;" +
                        "-fx-background-color: #dbe7fe;" +
                        "-fx-text-fill: #FFFFFF");
                chats.getChildren().add(button);
            }
        }

        return buttons;
    }


    /**
     * Vult de chatbox met de message van de geselecteerde chat.
     */
    private void displayChatMessage(Map<String, Object> chatMessages) {
        for (Map.Entry<String, Object> entry : chatMessages.entrySet()) {
            String formattedPrompt = entry.getKey();
            Object promptAnswer = entry.getValue();
            String message = "\n" + formattedPrompt + "\n" + promptAnswer + "\n";
            textArea.appendText(message);
        }
    }

    public void chats(){
        ChatManager chatManager = ChatManager.getInstance();
        HashMap<Integer, Map<String, Object>> chatMap = chatManager.getChatMap();
        chatTopics.putAll(ChatManager.chatTopicMap.get("TextToText"));
        ArrayList<Button> buttons = loadLastChats(chatMap);
        chats.getChildren().addAll(buttons);
        chats.setSpacing(10);
        chats.setPadding(new Insets(10));
        chats.setPrefHeight(600);
        chats.setPrefWidth(300);
    }
    public void alleButtons(){
        primaryStage.setTitle("ChatBot42");
        primaryStage.setScene(scene);
        ChatManager chatManager = ChatManager.getInstance();
        HashMap<Integer, Map<String, Object>> chatMap = chatManager.getChatMap();
        chatTopics.putAll(ChatManager.chatTopicMap.get("TextToText"));
        ArrayList<Button> buttons = loadLastChats(chatMap);
        // DELETE CHATS
        deleteChats.setOnMouseClicked(event -> {
            textArea.appendText(ChatManager.getInstance().getChatMessages(currentChatId).toString());
        });
        // text naar image knop
        textImageChat.setOnMouseClicked(event -> {
            TextToImage imageInterface = new TextToImage(primaryStage, account);
            imageInterface.show();
        });

        imageTextChat.setOnMouseClicked(event -> {
            ImageToText imageInterface = new ImageToText(primaryStage, account);
            imageInterface.show();
        });

        // image naar text knop

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
                textArea.clear();
                currentChatId++;
                System.out.println(currentChatId);
                addChat();

        });
    }
    public void textAreaChats(){
        textArea.setWrapText(true);
        textArea.setEditable(false);
        textArea.setPrefHeight(600);
        textArea.setPrefWidth(600);
    }
    public void setScrollPaneChats(){
        scrollPaneChats = new ScrollPane(chats);
        scrollPaneChats.setFitToWidth(true);
        scrollPaneChats.setPrefViewportHeight(600);
    }
    public void inputcontainer(){
        submitButton.setMinSize(100, 20);
        promptInput.setPromptText("Explain quantum computing to me.");
        promptInput.setPrefWidth(900);
        HBox inputContainer = new HBox(promptInput, submitButton);
        inputContainer.setSpacing(10);
        chatBox.setFillWidth(true);
        chatBox.getChildren().addAll(textArea, inputContainer);
        chatBox.setAlignment(Pos.CENTER);
    }
    public void sethBoxBottom(){
        hBoxBottom.setSpacing(10);
        hBoxBottom.setPadding(new Insets(10));
        hBoxBottom.setPrefHeight(120);
        hBoxBottom.getChildren().addAll(textImageChat, imageTextChat ,deleteChats, instellingen, signOut);
        hBoxBottom.setAlignment(Pos.BOTTOM_CENTER);
    }
    public void setTaal(){
        Locale currentLocale = Settings.isNederlandseTaal ? new Locale("nl") : Locale.ENGLISH;
        ResourceBundle messages = ResourceBundle.getBundle("messages", currentLocale);

        newChat.setText(messages.getString("newChat"));
        textImageChat.setText(messages.getString("textImageChat"));
        imageTextChat.setText(messages.getString("imageTextChat"));
        deleteChats.setText(messages.getString("deleteChats"));
        submitButton.setText(messages.getString("submitButton"));
        signOut.setText(messages.getString("signOut"));
        instellingen.setText(messages.getString("settings"));
    }
    public void creerScene(){
        if (Settings.isDarkMode){
            scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        }
        else{
            scene.getStylesheets().add(getClass().getResource("/styles-light.css").toExternalForm());}
    }
    public void rootcontainer(){
        root.setLeft(scrollPaneChats);
        root.setCenter(chatBox);
        root.setBottom(hBoxBottom);
    }
    public void show() {
        chats();
        setScrollPaneChats();
        sethBoxBottom();
        inputcontainer();
        textAreaChats();
        setTaal();
        rootcontainer();
        creerScene();
        Event1();
        Event2();

        alleButtons();
        primaryStage.show();
    }


    /**
     * Chat berichten worden nog niet correct aangepast per chat_id maakt nu telkens een nieuwe chat_id maar wel met alle nieuwe prompts.
     */

    private QueryResolutionResult resolveQuery(String prompt) {
        QueryResolutionStrategyFactory factory = new TextToTextResolutionStrategyFactory();
        QueryResolutionStrategy strategy = factory.createQueryResolutionStrategy();
        QueryResolutionForm<String> form = new QueryResolutionForm<>(prompt);
        return strategy.resolve(form);
    }

    public void addChat() {
        Button chat = new Button("Chat" + (chats.getChildren().size() - 1));
        if(Settings.isDarkMode){
            chat.setStyle("-fx-border-color: #385CA9;" +
                    "-fx-background-color: #232323;" +
                    "-fx-text-fill: #FFFFFF");
            chats.getChildren().add(chat);
        }
        else {
            chat.setStyle("-fx-border-color: #385CA9;" +
                    "-fx-background-color: #DBE7FE;" +
                    "-fx-text-fill: #000000;");
            chats.getChildren().add(chat);
        }

        chat.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                ChatManager chatManager = ChatManager.getInstance();
                chat.setText(chatManager.changeTopic("TextToText", currentChatId));
            } else {
                // Clear the text area and text field
                textArea.clear();
                textField.clear();

                // Remove the event handler from the text field
                textField.setOnKeyPressed(null);
            }
        });

    }
    private void callApi(TextField promptInput) {
        ChatManager chatManager = ChatManager.getInstance();
        Map<String, Object> chatMessages = chatManager.getChatMessages(currentChatId);
        HashMap<Integer, Map<String, Object>> chatMap = chatManager.getChatMap();
        String prompt = promptInput.getText();
        // Format de prompt met de huidige tijd en de gebruikersnaam
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedPrompt = currentTime.format(formatter) + " [" + account + "]: " + prompt;

        // Resolve de prompt
        Object promptAnswer;

        // String resolve
        QueryResolutionResult result = resolveQuery(prompt);
        promptAnswer = result.resultData();
        textArea.appendText("\n" + formattedPrompt + "\n" + promptAnswer + "\n");
        // Als de chatMessages null zijn, maak een nieuwe LinkedHashMap aan om de chatMessages in op te slaan
        if (chatMessages == null) {
            chatMessages = new LinkedHashMap<>();
        }
        // Voeg de prompt en het antwoord toe aan de chatMessages
        chatMessages.put(formattedPrompt, promptAnswer);
        chatManager.saveChatMessages(currentChatId, chatMessages);
        loadLastChats(chatMap);
        System.out.println(chatManager.getChatMessages(currentChatId));
        promptInput.clear();
    }

    @Override
    public void Event1() {
        promptInput.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                callApi(promptInput);
            }
        });
    }
    public void Event2(){
        submitButton.setOnAction(event -> callApi(promptInput));
    }

}