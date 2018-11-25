//����
import java.io.*;
import java.util.ArrayList;
import java.util.List;

//�ʷ���������
public class LexicalAnalysis {
    //�ؼ��ֱ�
    private static String KeyWordTable[] = {"begin", "end", "if", "then", "else", "while", "do"};
    //������
    private static final int ID = 8;
    private static final int UCON = 9;
    private static final int LT = 10;
    private static final int LE = 11;
    private static final int EQ = 12;
    private static final int NE = 13;
    private static final int GT = 14;
    private static final int GE = 15;
    private static final int IS = 16;
    private static final int PL = 17;
    private static final int MI = 18;
    private static final int MU = 19;
    private static final int DI=20;
    private static final int INT = 21;
    private static final int LP = 22;
    private static final int RP = 23;
    private static final int end = 24;


    //�洢�����ַ�
    private static List<String> TOKEN = new ArrayList<>();

    //�鱣���ֱ��ж��Ƿ�Ϊ�ؼ���
    private static int lookup(List<String> TOKEN)
    {
        String token = String.join("", TOKEN);//List ת��Ϊ String
        int n = 0;
        while (n != 7) {
            if (KeyWordTable[n].equals(token)) {
                return n + 1;                    //���ݵ��ʷ������������ȷ�Ĺؼ�������룬�����ش������
            }
            n++;
        }
        return 0;                                //���ʲ��ǹؼ��֣����Ǳ�ʶ��
    }



    //��ʽ�����
    private static void out(int a, List<String> p) {
        String sp = String.join("", p);
        String x = null;
        String outtext;
        switch (a) {
            case 1:
                x = "BEGIN";
                break;
            case 2:
                x = "END";
                break;
            case 3:
                x = "IF";
                break;
            case 4:
                x = "THEN";
                break;
            case 5:
                x = "ELSE";
                break;
            case 6:
                x = "WHILE";
                break;
            case 7:
                x = "DO";
                break;
            case 8:
                x = "ID";
                break;
            case 9:
                x = "UCON";
                break;
            case 10:
                x = "LT";
                break;
            case 11:
                x = "LE";
                break;
            case 12:
                x = "EQ";
                break;
            case 13:
                x = "NE";
                break;
            case 14:
                x = "GT";
                break;
            case 15:
                x = "GE";
                break;
            case 16:
                x = "IS";
                break;
            case 17:
                x = "PL";
                break;
            case 18:
                x = "MI";
                break;
            case 19:
                x = "MU";
                break;
            case 20:
                x = "DI";
                break;
            case 21:
                x = "INT";
                break;
            case 22:
                x = "LP";
                break;
            case 23:
                x = "RP";
                break;
            case 24:
                x = "end";
        }
        outtext="("+x+","+sp+")"+"\r\n";
        File file=new File("D:\\��ҵ\\����ԭ��\\shiyan2java\\LexicalAnalysisOut.txt");
        savef(outtext, file);
        System.out.printf("(%s,%s)\n", x, sp);
    }

     //���ڲ���ʶ���ַ���������
     private static void report_error(char a)
     {
         String errorouttext="ERROR:"+a+'\n';
         File file=new File("D:\\��ҵ\\����ԭ��\\shiyan2java\\LexicalAnalysisOut.txt");
         savef(errorouttext, file);
         System.out.printf("%c is error\n",a);
     }

    private static void savef(String outtext, File file) {
        try {
            if (!file.exists()) file.createNewFile();
            FileWriter fileWriter=new FileWriter(file.getName(),true);
            fileWriter.write(outtext);
            fileWriter.close();
        }catch (IOException e){
            System.err.println(e);
        }
    }

    //ɨ�赥�ʺ���
    private static void scanner_example(String fp)
    {
        ArrayList<String> t = new ArrayList<>();//�����List
        t.add(" ");
        char ch;
        int i = 0;
        while (i < fp.length()) {
            if (i == fp.length() - 1)
                break;
                //System.exit(0);
            ch = fp.charAt(i);
            while (fp.charAt(i) == ' ' || fp.charAt(i) == '\r' || fp.charAt(i) == '\n') {
                ch = fp.charAt(++i);
            }
            if (Character.isLetter(ch))                //�ж��Ƿ�Ϊ��ĸ
            {
                int strat = TOKEN.size();
                TOKEN.add(String.valueOf(ch));
                ch = fp.charAt(++i);
                while (Character.isLetter(ch)||Character.isDigit(ch)) {
                    TOKEN.add(String.valueOf(ch));
                    i++;
                    ch = fp.charAt(i);
                }
                int c = lookup(TOKEN.subList(strat, TOKEN.size()));
                if (c == 0) out(ID, TOKEN.subList(strat, TOKEN.size()));            //��ʶ��
                else out(c, t);                        //��ʱΪ�ؼ���
            } else {
                if (Character.isDigit(ch))            //�ж�����
                {
                    int start = TOKEN.size();
                    TOKEN.add(String.valueOf(ch));
                    ch = fp.charAt(++i); //i = 1;
                    while (Character.isDigit(ch)) {
                        TOKEN.add(String.valueOf(ch));
                        i++;
                        ch = fp.charAt(i);
                    }
                    if (ch == '.') {
                        TOKEN.add(String.valueOf(ch));
                        i++;
                        ch = fp.charAt(i);
                        while (Character.isDigit(ch)) {
                            TOKEN.add(String.valueOf(ch));
                            i++;
                            ch = fp.charAt(i);
                        }
                        out(UCON, TOKEN.subList(start, TOKEN.size()));        //������
                    } else {
                        out(INT, TOKEN.subList(start, TOKEN.size()));            //����
                    }
                } else {//������������߼������
                    if (ch=='/'){
                        ch = fp.charAt(++i);
                        if (ch == '/') {
                            break;
                        }
                        ch = fp.charAt(--i);
                    }
                    switch (ch) {
                        case '<':
                            ch = fp.charAt(++i);
                            if (ch == '=') {
                                out(LE, t);
                                i++;
                            } else if (ch == '>') {
                                out(NE, t);
                                i++;
                            } else {
                                out(LT, t);
                                i++;
                            }
                            break;
                        case '=':
                            out(EQ, t);
                            i++;
                            break;
                        case '(':
                            out(LP, t);
                            i++;
                            break;
                        case ')':
                            out(RP, t);
                            i++;
                            break;
                        case '>':
                            ch = fp.charAt(++i);
                            if (ch == '=') {
                                out(GE, t);
                                i++;
                            } else {
                                out(GT, t);
                            }
                            break;
                        case ':':
                            ch = fp.charAt(++i);
                            if (ch == '=') {
                                out(IS, t);
                                i++;
                            }
                            break;
                        case '*':
                            out(MU, t);
                            i++;
                            break;
                        case '+':
                            out(PL, t);
                            i++;
                            break;
                        case '-':
                            out(MI, t);
                            i++;
                            break;
                        case '#':
                            out(end,t);
                            i++;
                        case '/':
                            out(DI, t);
                            i++;
                            break;
                        default:
                            report_error(ch);
                            i++;
                    }
                }
            }
        }
    }


    public static void main(String[] arge) {
        try {//�򿪽������ļ���û�д����ļ�����������ļ�
            File outfile=new File("D:\\��ҵ\\����ԭ��\\shiyan2java\\LexicalAnalysisOut.txt");
            if (!outfile.exists())
                outfile.createNewFile();
            FileWriter fileWriter=new FileWriter(outfile.getName());
            fileWriter.write("");
            //fileWriter.close();
        }catch (IOException e){
            System.err.println(e);
        }
        String fp;
        try {
            File infile = new File("D:\\��ҵ\\����ԭ��\\shiyan2java\\sentence.txt");//�����ļ�����
            BufferedReader Breader = null;
            Breader = new BufferedReader(new FileReader(infile));
            while ((fp=Breader.readLine())!=null){           //���ж�ȡ�ļ�
                fp = fp + "#";
                scanner_example(fp);
            }
            Breader.close();
        } catch (Exception e) {
            System.err.print(e);
        }
    }
}
