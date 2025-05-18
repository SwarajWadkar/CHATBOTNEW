import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.net.URI;

public class ChatBot extends JFrame {
    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;
    private JScrollPane scrollPane;
    private JPanel bottomPanel;

    private final Color PRIMARY_COLOR = new Color(52, 152, 219);
    private final Color SECONDARY_COLOR = new Color(41, 128, 185);
    private final Color BACKGROUND_COLOR = new Color(236, 240, 241);
    private final Color TEXT_COLOR = new Color(44, 62, 80);

    private Random random = new Random();
    private LocalDate currentDate = LocalDate.now();
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd yyyy");

    public ChatBot() {
        super("College Information ChatBot");
        initializeUI();
        setupListeners();
    }

    private void initializeUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(Color.BLACK);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setFont(new Font("Consolas", Font.PLAIN, 16));
        chatArea.setForeground(new Color(57, 255, 20)); // Neon green
        chatArea.setBackground(new Color(13, 13, 13)); // Deep black
        chatArea.setMargin(new Insets(10, 15, 10, 15));

        scrollPane = new JScrollPane(chatArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setBackground(Color.BLACK);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));

        inputField = new JTextField();
        inputField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        inputField.setForeground(new Color(57, 255, 20));
        inputField.setBackground(Color.BLACK);
        inputField.setCaretColor(Color.GREEN);
        inputField.setBorder(BorderFactory.createLineBorder(new Color(57, 255, 20), 2));

        sendButton = new JButton("Send");
        sendButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        sendButton.setBackground(Color.WHITE);
        sendButton.setForeground(Color.BLACK);
        sendButton.setFocusPainted(false);
        sendButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        sendButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        sendButton.setOpaque(true);

        // Hover effect
        sendButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                sendButton.setBackground(new Color(135, 206, 235)); // Sky blue
            }

            public void mouseExited(MouseEvent evt) {
                sendButton.setBackground(Color.WHITE);
            }
        });

        bottomPanel.add(inputField, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        addWelcomeMessage();
    }

    private void setupListeners() {
        sendButton.addActionListener(e -> processUserInput());
        inputField.addActionListener(e -> processUserInput());

        // Set focus to input field when window is activated
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                inputField.requestFocusInWindow();
            }
        });
    }

    private void processUserInput() {
        String message = inputField.getText().trim();
        if (!message.isEmpty()) {
            displayUserMessage(message);
            inputField.setText("");
            respondToMessage(message.toLowerCase());
        }
    }

    private void displayUserMessage(String message) {
        chatArea.append("You: " + message + "\n\n");
        scrollToBottom();
    }

    private void displayBotMessage(String message) {
        chatArea.append("Bot: " + message + "\n\n");
        scrollToBottom();
    }

    private void scrollToBottom() {
        SwingUtilities.invokeLater(() -> {
            JScrollBar vertical = scrollPane.getVerticalScrollBar();
            vertical.setValue(vertical.getMaximum());
        });
    }

    private void addWelcomeMessage() {
        String welcomeMessage = "Welcome to College Information ChatBot!\n" +
                "I can provide information about MCA program, placements, and more.\n" +
                "Try asking about fees, duration, placements, or type 'help' for options.";
        displayBotMessage(welcomeMessage);
    }

    private void respondToMessage(String message) {
        // Small delay to simulate thinking
        Timer timer = new Timer(500, e -> {
            if (message.contains("hi") || message.contains("hello") || message.contains("hey")) {
                String[] greetings = { "Hello!", "Hi there!", "Hey! How can I help?", "Greetings!" };
                displayBotMessage(greetings[random.nextInt(greetings.length)]);
            } else if (message.contains("about mca")) {
                displayBotMessage("The MCA (Master of Computer Applications) is a 2-year postgraduate program " +
                        "focusing on computer applications development. It covers programming, software development, " +
                        "and computer science fundamentals.");
            } else if (message.contains("timing for mca")) {
                displayBotMessage("MCA program timings: 9:00 AM to 4:30 PM, Monday to Friday");
            } else if (message.contains("fees")) {
                displayBotMessage("The annual fees for the MCA program is ₹1.10 lakhs per year.");
            } else if (message.contains("duration")) {
                displayBotMessage("The MCA course duration is 2 years.");
            } else if (message.contains("date") || message.contains("month") || message.contains("year")
                    || message.contains("day")) {
                displayBotMessage("Today is " + currentDate.format(dateFormatter));
            } else if (message.contains("placements")) {
                displayBotMessage("Our college has an excellent placement record with over 100 companies " +
                        "participating in campus recruitment for MCA students.");
            } else if (message.contains("highest package")) {
                displayBotMessage("The highest package offered to our MCA students is ₹9 LPA by Asian Paints.");
            } else if (message.contains("average package")) {
                displayBotMessage("The average package for MCA graduates is ₹4 LPA.");
            } else if (message.contains("100% placement")) {
                displayBotMessage("Yes, we maintain a 100% placement record for eligible MCA students.");
            } else if (message.contains("product based companies")) {
                displayBotMessage("Approximately 10 product-based companies visit our campus for MCA placements.");
            } else if (message.contains("how much they offer")) {
                displayBotMessage("Product-based companies typically offer between ₹4-7 LPA for MCA graduates.");
            } else if (message.contains("rounds")) {
                displayBotMessage("The placement process consists of 3 rounds:\n" +
                        "1. Aptitude test\n" +
                        "2. Technical interview\n" +
                        "3. HR interview");
            } else if (message.contains("annual event")) {
                displayBotMessage(
                        "Our college hosts 'Eminence', an annual event featuring technical and cultural activities.");
            } else if (message.contains("placement preparation")) {
                displayBotMessage("We conduct regular placement preparation activities including mock interviews, " +
                        "aptitude tests, and technical workshops.");
            } else if (message.contains("direct admission")) {
                displayBotMessage(
                        "Admission to MCA requires qualifying the CET exam. We don't offer direct admission.");
            } else if (message.contains("industrial visits")) {
                displayBotMessage(
                        "Yes, we organize regular industrial visits to tech companies for practical exposure.");
            } else if (message.contains("prabhat")) {
                displayBotMessage(
                        "Prabhat is our college's bi-annual newsletter featuring campus news and achievements.");
            } else if (message.contains("vision")) {
                displayBotMessage("Our college vision: 'Social Transformation Through Dynamic Education'");
            } else if (message.contains("conduct workshops")) {
                displayBotMessage("We regularly conduct workshops on:\n" +
                        "- Python programming\n" +
                        "- AWS Cloud\n" +
                        "- MongoDB & PostgreSQL\n" +
                        "- Machine Learning\n" +
                        "- And many more emerging technologies");
            } else if (message.contains("top companies")) {
                displayBotMessage("Top recruiters for MCA include:\n" +
                        "- Carwala\n" +
                        "- Asian Paints\n" +
                        "- E-Emphasys\n" +
                        "- BNP Paribas\n" +
                        "- Princeton Blue\n" +
                        "- Josh Technologies");
            } else if (message.contains("enquiry number")) {
                displayBotMessage("For admissions enquiry, please call: 022-27578415");
            } else if (message.contains("internship")) {
                displayBotMessage("Yes, we facilitate summer internships for MCA students with various companies.");
            } else if (message.contains("labs")) {
                displayBotMessage("We have 6 well-equipped computer labs with latest software and hardware.");
            } else if (message.contains("technology preferred")) {
                displayBotMessage("The preferred technologies in our curriculum include:\n" +
                        "- Java\n" +
                        "- C++\n" +
                        "- .NET\n" +
                        "- Machine Learning");
            } else if (message.contains("clear") && (message.contains("screen") || message.contains("chat"))) {
                chatArea.setText("");
                displayBotMessage("Chat history cleared. How can I help you now?");
            } else if (message.contains("help")) {
                displayHelp();
            } else if (message.contains("thank")) {
                String[] thanksResponses = { "You're welcome!", "Happy to help!", "My pleasure!", "Anytime!" };
                displayBotMessage(thanksResponses[random.nextInt(thanksResponses.length)]);
            } else {
                handleUnknownQuery(message);
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void displayHelp() {
        String helpMessage = "Here are some things you can ask me about:\n" +
                "- MCA program details (fees, duration, timings)\n" +
                "- Placement information (companies, packages, process)\n" +
                "- College facilities (labs, workshops, events)\n" +
                "- Admission process\n" +
                "- Type 'date' for current date\n" +
                "- Type 'clear' to reset our conversation";
        displayBotMessage(helpMessage);
    }

    private void handleUnknownQuery(String message) {
        try {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                displayBotMessage("I'm not sure about that. Let me search the web for you...");
                Desktop.getDesktop().browse(new URI("https://www.google.com/search?q=" +
                        message.replace(" ", "+")));
            } else {
                throw new Exception("Browser not supported");
            }
        } catch (Exception e) {
            String[] sorryMessages = {
                    "I'm not sure I understand. Could you rephrase that?",
                    "I don't have information about that. Try asking something else.",
                    "My knowledge is limited to college information. Could you ask about MCA or placements?"
            };
            displayBotMessage(sorryMessages[random.nextInt(sorryMessages.length)]);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Set system look and feel
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

                // Create and show the chatbot
                ChatBot chatbot = new ChatBot();
                chatbot.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}