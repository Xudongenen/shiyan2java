import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

public class GrammarTranslation {

    /**
     * 判断是否可以继续执行
     */
    private static boolean flag = true;

    /**
     * 栈
     * */
    private static StringBuilder stack = new StringBuilder("#");

    /**
     * 输入缓冲区
     * */
    private static StringBuilder buffer = new StringBuilder();

    /**
     * 步骤
     */
    private static int step = 1;

    /**
     * 四元式序号
     */
    private static int siYianShi = 0;

    /**
     * 存放N对应的值
     */
    private static String nValue[] = new String[10];

    /**
     * 存放结果Tn
     */
    private static int result = 1;

    /**
     * 栈顶第一个终结符
     */
    private static int top;

    /**
     * 字符顺序表 **暂时用/表示
     */
    private static String sequenceList = "+*/i()#";

    /**
     * 优先关系表,其中1为高于，0为等于，-1为低于，-2为不能比
     */
    private static int relation[][] = { { 1, -1, -1, -1, -1, 1, 1 },
            { 1, 1, -1, -1, -1, 1, 1 }, { 1, 1, -1, -1, -1, 1, 1 },
            { 1, 1, 1, -2, -2, 1, 1 }, { -1, -1, -1, -1, -1, 0, -2 },
            { 1, 1, 1, -2, -2, 1, 1 }, { -1, -1, -1, -1, -1, -2, 0 } };

    /**
     * 主程序
     */
    static File AnalysisProcess =new File("D:\\作业\\编译原理\\shiyan2java\\AnalysisProcess.txt");
    static File fouryuanStyle =new File("D:\\作业\\编译原理\\shiyan2java\\fouryuanStyle.txt");
    public static void main(String[] args) {
        GrammaticalAnalysis.writerFile(AnalysisProcess,"",false);
        GrammaticalAnalysis.writerFile(fouryuanStyle,"",false);
        System.out.print("请输入字符串：");
        Scanner s = new Scanner(System.in);
        File sentence=new File("D:\\作业\\编译原理\\shiyan2java\\sentence.txt");
        String resourceCode = readerFile(sentence);
        buffer.append(resourceCode);
        if (CheckHeaderCharacters(AnalysisProcess)) return;
        if (buffer.charAt(0) != 'i' && buffer.charAt(0) != '(') {// 不会出现第一个字符不是i和（的时候
            flag = false;
            System.err.println("\t你的语法混乱，这不是句子！！！");
            GrammaticalAnalysis.writerFile(AnalysisProcess,"\t你的语法混乱，这不是句子！！！"+"\n",true);
            return;
        }
        for (int i = 1; i < buffer.length() - 2; i++) {
            if (buffer.charAt(i) != 'i') {
                if (buffer.charAt(i) == '(') {// 左括号后面只能接i
                    if (buffer.charAt(i + 1) == ')'
                            || buffer.charAt(i + 1) == '+'
                            || buffer.charAt(i + 1) == '*'
                            || buffer.charAt(i + 1) == '/') {

                        flag = false;
                        System.err.println("\t你的语法混乱，这不是句子！！！");
                        GrammaticalAnalysis.writerFile(AnalysisProcess,"\t你的语法混乱，这不是句子！！！"+"\n",true);
                        return;
                    }
                }
            }
        }
        System.out.println("***************************************");
        System.out.println("步骤\t栈\t输入缓冲区");
        GrammaticalAnalysis.writerFile(AnalysisProcess,"步骤\t栈\t输入缓冲区"+"\n",true);

        //System.out.println(step++ + "\t" + stack + "\t" + buffer);
        GrammaticalAnalysis.writerFile(AnalysisProcess,step++ + "\t" + stack + "\t" + buffer+"\n",true);
        move();
        do {
            top = stack.length() - 1;// top指向栈顶第一个非终结符
            if (buffer.charAt(0) == '#') {// 判断退出
                if (stack.charAt(stack.length() - 2) == '#'
                        && stack.charAt(stack.length() - 1) == 'N') {
                    System.out.println();
                    System.out.println("\t该输入串是句子，可以放心使用。");
                    GrammaticalAnalysis.writerFile(AnalysisProcess,"分析成功！"+"\n",true);
                    break;
                }
            }

            if (stack.charAt(top) == 'N') {
                top--;
            }

            if (CheckHeaderCharacters(AnalysisProcess)) return;

            compare(stack.charAt(top), buffer.charAt(0));
        } while (flag);
    }

    private static boolean CheckHeaderCharacters(File analysisProcess) {
        if (sequenceList.indexOf(buffer.charAt(0)) == -1) {// 每次要检测缓冲区首字符
            flag = false;
            System.err.println("\t由于包含非法字符，这不是句子！！！");
            GrammaticalAnalysis.writerFile(analysisProcess,"\t由于包含非法字符，这不是句子！！！"+"\n",true);
            return true;
        }
        return false;
    }

    public static String readerFile(File file) {
        try {
            BufferedReader Breader = null;
            Breader = new BufferedReader(new FileReader(file));
            String fp = null;
            while ((fp=Breader.readLine()) != null) {//按行读取文件
                //fp=Breader.readLine()+"\r\n";
                return fp;
            }
            //return fp;
            Breader.close();
        } catch (Exception e1) {
            System.err.print(e1);
        }
        return "0";
    }

    /**
     * 字符优先级比较、进栈、归约
     * */
    private static void compare(char a, char b) {

        int i = sequenceList.indexOf(a);// 行
        int j = sequenceList.indexOf(b);// 列
        // System.out.println(i+"       "+j);
        switch (relation[i][j]) {
            case 1:// a>b,从栈顶找第二个终结符，准备归约
                int second;
                second = top - 1;
                while (stack.charAt(second) == 'N') {
                    second--;
                }

                int n = 0;
                for (int m = 0; m < top; m++) {// 找到这个字符前面有几个N
                    if (stack.charAt(m) == 'N') {
                        n++;
                    }
                }
                // System.out.println(n);

                i = sequenceList.indexOf(stack.charAt(top));
                j = sequenceList.indexOf(stack.charAt(second));
                if (relation[j][i] == 0) {// 栈顶首终结符和第二终结符优先级相等
                    stack.replace(second, top + 1, "N");// 从第二终结符到首终结符都除去,用N替换

                } else if (relation[j][i] == -1) {// 栈顶首终结符优先级高
                    if (stack.charAt(top - 1) == 'N') {// 如果左边有N，则右边也有，和两边的N一起归约

//                        System.out.println("\t四元式" + ++siYianShi + "("
//                                + stack.charAt(top) + "," + nValue[n - 1] + ","
//                                + nValue[n] + "," + "T" + result + ")");// 输出四元式
                        GrammaticalAnalysis.writerFile(fouryuanStyle,"\t四元式" + ++siYianShi + "("
                                + stack.charAt(top) + "," + nValue[n - 1] + ","
                                + nValue[n] + "," + "T" + result + ")"+"\n",true);
                        nValue[n - 1] = "T" + result++;// 将结果Tn保存，与此N对应上
                        stack.delete(top, top + 2);// 删除掉从start到end-1的字符

                    } else {
                        nValue[n] = Character.toString((stack.charAt(top)));// 将他与N对应上
                        stack.deleteCharAt(top);// 只归约它自己,他处于栈顶
                        stack.append('N');
                    }
                }
                //System.out.println(step++ + "\t" + stack + "\t" + buffer);
                GrammaticalAnalysis.writerFile(AnalysisProcess,step++ + "\t" + stack + "\t" + buffer+"\n",true);


                break;
            case 0:// a=b,b入栈

                // break;
            case -1:// a<b,b入栈

                move();
                break;
            default:// 不能比较
                //System.err.println("   " + a + "与" + b + "此时不能比较优先级，这个输入串不是句子！！！");
                GrammaticalAnalysis.writerFile(AnalysisProcess,"   " + a + "与" + b + "此时不能比较优先级，这个输入串不是句子！！！"+"\n",true);
                flag = false;
        }

    }
    /**
     * 将缓冲区首字符移到栈顶
     */
    private static void move() {
        stack.append(buffer.charAt(0));
        buffer.deleteCharAt(0);
        //System.out.println(step++ + "\t" + stack + "\t" + buffer);
        GrammaticalAnalysis.writerFile(AnalysisProcess,step++ + "\t" + stack + "\t" + buffer+"\n",true);
    }
}
