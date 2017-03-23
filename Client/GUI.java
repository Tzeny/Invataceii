
import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.traces.Trace2DLtd;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GUI extends JFrame {
    private static JTextArea message;
    private static restApiExample rest;
    private static TextField IPInput;
    private static TextField PortInput;
    private static JFrame mainWindow;
    private static boolean isConnected = false;
    private static Chart2D chart;
    private static ITrace2D trace;

    private GUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(container());
        setResizable(false);
        setTitle("ClientSide GUI");
        setSize(300, 200);
        setVisible(true);
    }

    private static JPanel container(){
        JPanel contentPane = new Content();
        JPanel contentContainer = new JPanel();

        contentContainer.setLayout(new GridLayout(2, 1));

        contentContainer.add(contentPane);

        JButton dataButton = new JButton("Start Data");
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
        return superContainer;
    }

    public static void main(String[] args) {
        mainWindow = new GUI();
    }

    private static class Content extends JPanel {

        private Content() {
            setLayout(new FlowLayout());
            JLabel IP = new JLabel("IP:");
            JLabel Port = new JLabel("Port:");
            IPInput = new TextField("           ");
            PortInput = new TextField("    ");
            add(IP);
            add(IPInput);
            add(Port);
            add(PortInput);

        }
    }
    private static void  dynamicChart(){
            double startTime = System.currentTimeMillis();
            chart= new Chart2D();
            trace = new Trace2DLtd(200);
            trace.setColor(Color.RED);
            chart.addTrace(trace);
            Timer timer = new Timer(500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    double point = rest.getSensor();
                    trace.addPoint((double) System.currentTimeMillis() - startTime, point);
                }
            });
            mainWindow = new JFrame("MinimalDynamicChart");
            // add the chart to the frame:
            mainWindow.getContentPane().add(chart);
            mainWindow.setSize(400,300);
            // Enable the termination button [cross on the upper right edge]:
            mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainWindow.setVisible(true);
            timer.start();
        }
    private static class GetDataAction implements ActionListener {
        static boolean start = false;
        public void actionPerformed(ActionEvent evt) {
            try {
                f();
                if(!start) {
                    ((JButton)evt.getSource()).setText("Stop Data");
                    start = !start;
                    dynamicChart();
                }
                else{
                    ((JButton)evt.getSource()).setText("Start Data");
                    start = !start;
                }

            } catch (Exception e) {
                message.append("Server down.\n");
            }
        }

        private void f() throws java.net.ConnectException {
            if (rest != null && rest.getSensor() != -1) {
                message.append("data fetched " + rest.getSensor() + "\n");
            }
            else
                message.append("Connection not established.\n");
        }

    }

    private static class ConnectAction implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            try {
                f();
                if(rest.getSensor() != -1) {
                    message.append("connected successfully!\n");
                    isConnected = true;
                }
                else {
                    throw new java.net.ConnectException();
                }
            } catch (Exception e) {
                message.append("Server down.\n");
                isConnected = false;
            }
        }

        private void f() {
            try {
                rest = new restApiExample(IPInput.getText().trim(), PortInput.getText().trim());
            }
            catch(Exception e){
                message.append("Server down.\n");
            }
        }
    }
}