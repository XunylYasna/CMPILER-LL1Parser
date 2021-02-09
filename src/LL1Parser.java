import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class LL1Parser extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField1;
    private JLabel resultLabel;
    private JButton importButton;

    public LL1Parser() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });
        buttonOK.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 1),
                BorderFactory.createEmptyBorder(
                    buttonOK.getBorder().getBorderInsets(buttonOK).top,
                    buttonOK.getBorder().getBorderInsets(buttonOK).left,
                    buttonOK.getBorder().getBorderInsets(buttonOK).bottom,
                    buttonOK.getBorder().getBorderInsets(buttonOK).right
                )
                ));


        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });
        buttonCancel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 1),
                BorderFactory.createEmptyBorder(
                        buttonCancel.getBorder().getBorderInsets(buttonCancel).top,
                        buttonCancel.getBorder().getBorderInsets(buttonCancel).left,
                        buttonCancel.getBorder().getBorderInsets(buttonCancel).bottom,
                        buttonCancel.getBorder().getBorderInsets(buttonCancel).right
                )
        ));

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);


        importButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onImport();
            }
        });
        importButton.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 1),
                BorderFactory.createEmptyBorder(
                        importButton.getBorder().getBorderInsets(importButton).top,
                        importButton.getBorder().getBorderInsets(importButton).left,
                        importButton.getBorder().getBorderInsets(importButton).bottom,
                        importButton.getBorder().getBorderInsets(importButton).right
                )
        ));
    }

    private void onImport(){
        FileDialog fd = new FileDialog(this, "Choose test case", FileDialog.LOAD);
        fd.setVisible(true);
        String filename = fd.getFile();
        String fileLocation = fd.getDirectory() + filename;
        if (filename == null){
            resultLabel.setText("No file selected.");
        }
        else{
            resultLabel.setText("You chose " + fileLocation + ".");
        }

        try {
            FileReader fr = new FileReader(fileLocation);
            BufferedReader br =new BufferedReader(fr);
            FileWriter fileWriter = new FileWriter("output.txt");
            String line;
            Lexer lexer = new Lexer();
            Parser parser = new Parser();
            fileWriter.write("");
            while((line=br.readLine())!=null)
            {
                String lexedString = lexer.lexString(line);
                if(lexer.isError()){
                    System.out.println(lexer.getOffending());
                    fileWriter.append(line + " Rejected: " + lexer.getOffending() + "\n");
                }
                else{
                    String result = parser.parse(lexedString);
                    fileWriter.append(line + " - " + result + "\n");
                }
            }
            fileWriter.close();
            fr.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return;
    }

    private void onOK() {
        // add your code here
        String expression = textField1.getText();
        System.out.println(expression);
        Lexer lexer = new Lexer();
        String lexedString = lexer.lexString(expression);
        System.out.println(lexedString);

        if(lexer.isError()){
            System.out.println(lexer.getOffending());
            resultLabel.setText("Result: " + lexer.getOffending());
            resultLabel.setForeground(Color.RED);
        }
        else{
            Parser parser = new Parser();
            resultLabel.setText("Result: " + parser.parse(lexedString));
            if(parser.isError()){
                resultLabel.setForeground(Color.RED);
            }
            else{
                resultLabel.setForeground(Color.GREEN);
            }
        }
    }

    private void onCancel() {
        // add your code here if necessary
        resultLabel.setText("Result: ");
        resultLabel.setForeground(new Color(105, 105, 105));
        textField1.setText("");
    }

    public static void main(String[] args) {
        LL1Parser dialog = new LL1Parser();
        dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        dialog.setTitle("Sir pls ipasa mo ako, ginawa ko nang dark mode - LL1 Parser");
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);

    }
}
