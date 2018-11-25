import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;

public class GrammarTranslationUI {
    private JTextArea textArea1;
    private JPanel panel1;
    private JButton ��ʼ�ʷ�����Button;
    private JTextArea textArea2;
    private JButton ѡ������ļ�Button;
    private JButton ѡ���ķ��ļ�Button;
    private JButton ��ʼ����Button;
    private JTextArea textArea3;
    private JTextArea textArea4;
    private JTextArea textArea5;
    private JTextArea textArea6;
    private JTextArea textArea7;
    private JTextArea textArea8;


    public GrammarTranslationUI() {

        ��ʼ����Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                File FIRSTVTfile=new File("D:\\��ҵ\\����ԭ��\\shiyan2java\\FIRSTVTfile.txt");
                File LASTVTfile = new File("D:\\��ҵ\\����ԭ��\\shiyan2java\\LASTVTfile.txt");
                File operatorPrecedenceTable=new File("D:\\��ҵ\\����ԭ��\\shiyan2java\\operatorPrecedenceTable.txt");
                File AnalysisProcess =new File("D:\\��ҵ\\����ԭ��\\shiyan2java\\AnalysisProcess.txt");
                File fouryuanStyle = new File("D:\\��ҵ\\����ԭ��\\shiyan2java\\fouryuanStyle.txt");
                readerFile(FIRSTVTfile,textArea4);
                readerFile(LASTVTfile,textArea5);
                readerFile(operatorPrecedenceTable,textArea6);
                readerFile(AnalysisProcess,textArea7);
                readerFile(fouryuanStyle,textArea8);
            }
        });
        ѡ������ļ�Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                readerFile(chooserFile(),textArea1);

            }
        });
        ѡ���ķ��ļ�Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                readerFile(chooserFile(),textArea2);
            }
        });
        ��ʼ�ʷ�����Button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                File LexicalAnalysisOut=new File("D:\\��ҵ\\����ԭ��\\shiyan2java\\LexicalAnalysisOut.txt");
                readerFile(LexicalAnalysisOut,textArea3);
            }
        });
    }
    public static File chooserFile(){
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));
        chooser.setFileFilter(new FileFilter(){
            //@Override
            public boolean accept(File f) {
                return f.getName().toLowerCase().endsWith(".txt");
            }
            //@Override
            public String getDescription() {
                return ".txt";
            }
        });
        chooser.showOpenDialog(null);
//        int r = chooser.showSaveDialog();
//        if(r!=JFileChooser.APPROVE_OPTION) return;
        return chooser.getSelectedFile();
    }
    public static void readerFile(File file,JTextArea textArea){
        try {
            BufferedReader Breader = null;
            Breader = new BufferedReader(new FileReader(file));
            String fp = null;
            while ((fp = Breader.readLine()) != null) {//���ж�ȡ�ļ�
                //fp=Breader.readLine()+"\r\n";
                textArea.append(fp + "\r\n");
            }
            //return fp;
            Breader.close();
        } catch (Exception e1) {
            System.err.print(e1);
        }
    }
    public static void main(String[] args) {
        try {
            JFrame frame = new JFrame("GrammarTranslationUI");
            frame.setContentPane(new GrammarTranslationUI().panel1);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
            LexicalAnalysis.main(null);
            GrammaticalAnalysis.main(null);
            GrammarTranslation.main(null);
        }catch (Exception e){

        }

    }
}
