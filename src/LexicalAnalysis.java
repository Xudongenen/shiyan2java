//引用
import java.io.*;
import java.util.ArrayList;
import java.util.List;

//词法分析主类
public class LexicalAnalysis {
    //关键字表
    private static String KeyWordTable[] = {"begin", "end", "if", "then", "else", "while", "do"};
    //类别编码
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


    //存储单个字符
    private static List<String> TOKEN = new ArrayList<>();

    //查保留字表，判断是否为关键字
    private static int lookup(List<String> TOKEN)
    {
        String token = String.join("", TOKEN);//List 转换为 String
        int n = 0;
        while (n != 7) {
            if (KeyWordTable[n].equals(token)) {
                return n + 1;                    //根据单词分类码表，设置正确的关键字类别码，并返回此类别码
            }
            n++;
        }
        return 0;                                //单词不是关键字，而是标识符
    }



    //格式化输出
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
        File file=new File("D:\\作业\\编译原理\\shiyan2java\\LexicalAnalysisOut.txt");
        savef(outtext, file);
        System.out.printf("(%s,%s)\n", x, sp);
    }

     //对于不能识别字符报错处理函数
     private static void report_error(char a)
     {
         String errorouttext="ERROR:"+a+'\n';
         File file=new File("D:\\作业\\编译原理\\shiyan2java\\LexicalAnalysisOut.txt");
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

    //扫描单词函数
    private static void scanner_example(String fp)
    {
        ArrayList<String> t = new ArrayList<>();//定义空List
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
            if (Character.isLetter(ch))                //判断是否为字母
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
                if (c == 0) out(ID, TOKEN.subList(strat, TOKEN.size()));            //标识符
                else out(c, t);                        //此时为关键字
            } else {
                if (Character.isDigit(ch))            //判断数字
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
                        out(UCON, TOKEN.subList(start, TOKEN.size()));        //浮点数
                    } else {
                        out(INT, TOKEN.subList(start, TOKEN.size()));            //整数
                    }
                } else {//算数运算符、逻辑运算符
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
        try {//打开结果输出文件若没有创建文件，若有清空文件
            File outfile=new File("D:\\作业\\编译原理\\shiyan2java\\LexicalAnalysisOut.txt");
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
            File infile = new File("D:\\作业\\编译原理\\shiyan2java\\sentence.txt");//建立文件对象
            BufferedReader Breader = null;
            Breader = new BufferedReader(new FileReader(infile));
            while ((fp=Breader.readLine())!=null){           //按行读取文件
                fp = fp + "#";
                scanner_example(fp);
            }
            Breader.close();
        } catch (Exception e) {
            System.err.print(e);
        }
    }
}
