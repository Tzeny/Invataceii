
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    static JTextArea message;

    private GUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("ClientSide GUI");
        setSize(250, 200);
    }

    public static void main(String[] args) {

        GUI mainWindow = new GUI();

        JPanel contentPane = new Content();
        JPanel contentContainer = new JPanel();

        contentContainer.setLayout(new GridLayout(2, 1));

        contentContainer.add(contentPane);

        JButton dataButton = new JButton("Get Data");
        JButton connectButton = new JButton("Connect");

        JPanel dataButtonPanel = new JPanel();
        dataButtonPanel.setLayout(new GridLayout(1, 1));
        dataButtonPanel.add(dataButton);
        dataButtonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel connectButtonPanel = new JPanel();
        connectButtonPanel.setLayout(new GridLayout(1, 1));
        connectButtonPanel.add(connectButton);
        connectButtonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        dataButton.addActionListener(new GetDataAction());
        connectButton.addActionListener(new ConnectAction());

        JPanel buttonContainer = new JPanel();
        buttonContainer.setLayout(new GridLayout(1, 2));
        buttonContainer.add(connectButtonPanel);
        buttonContainer.add(dataButtonPanel);

        contentContainer.add(buttonContainer);

        JPanel textContainer = new JPanel();
        message = new JTextArea("\n\n\n\n");
        textContainer.setLayout(new GridLayout(1, 1));
        JScrollPane scroll = new JScrollPane(message, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        textContainer.add(scroll);

        JPanel superContainer = new JPanel();
        superContainer.setLayout(new GridLayout(2, 1));
        superContainer.add(contentContainer);
        superContainer.add(textContainer);

        mainWindow.setContentPane(superContainer);
        mainWindow.setResizable(false);
        mainWindow.setVisible(true);
        mainWindow.repaint();
    }

    private static class Content extends JPanel {

        private Content() {
            setLayout(new FlowLayout());
            Label IP = new Label("IP:");
            Label Port = new Label("Port:");
            TextField IPInput = new TextField("           ");
            TextField PortInput = new TextField("    ");
            add(IP);
            add(IPInput);
            add(Port);
            add(PortInput);

        }
    }

    private static class GetDataAction implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            message.append("data fetched.\n");
        }

    }

    private static class ConnectAction implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            message.append("connected successfully!\n");
        }
    }
}