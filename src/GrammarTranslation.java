import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

public class GrammarTranslation {

    /**
     * �ж��Ƿ���Լ���ִ��
     */
    private static boolean flag = true;

    /**
     * ջ
     * */
    private static StringBuilder stack = new StringBuilder("#");

    /**
     * ���뻺����
     * */
    private static StringBuilder buffer = new StringBuilder();

    /**
     * ����
     */
    private static int step = 1;

    /**
     * ��Ԫʽ���
     */
    private static int siYianShi = 0;

    /**
     * ���N��Ӧ��ֵ
     */
    private static String nValue[] = new String[10];

    /**
     * ��Ž��Tn
     */
    private static int result = 1;

    /**
     * ջ����һ���ս��
     */
    private static int top;

    /**
     * �ַ�˳��� **��ʱ��/��ʾ
     */
    private static String sequenceList = "+*/i()#";

    /**
     * ���ȹ�ϵ��,����1Ϊ���ڣ�0Ϊ���ڣ�-1Ϊ���ڣ�-2Ϊ���ܱ�
     */
    private static int relation[][] = { { 1, -1, -1, -1, -1, 1, 1 },
            { 1, 1, -1, -1, -1, 1, 1 }, { 1, 1, -1, -1, -1, 1, 1 },
            { 1, 1, 1, -2, -2, 1, 1 }, { -1, -1, -1, -1, -1, 0, -2 },
            { 1, 1, 1, -2, -2, 1, 1 }, { -1, -1, -1, -1, -1, -2, 0 } };

    /**
     * ������
     */
    static File AnalysisProcess =new File("D:\\��ҵ\\����ԭ��\\shiyan2java\\AnalysisProcess.txt");
    static File fouryuanStyle =new File("D:\\��ҵ\\����ԭ��\\shiyan2java\\fouryuanStyle.txt");
    public static void main(String[] args) {
        GrammaticalAnalysis.writerFile(AnalysisProcess,"",false);
        GrammaticalAnalysis.writerFile(fouryuanStyle,"",false);
        System.out.print("�������ַ�����");
        Scanner s = new Scanner(System.in);
        File sentence=new File("D:\\��ҵ\\����ԭ��\\shiyan2java\\sentence.txt");
        String resourceCode = readerFile(sentence);
        buffer.append(resourceCode);
        if (CheckHeaderCharacters(AnalysisProcess)) return;
        if (buffer.charAt(0) != 'i' && buffer.charAt(0) != '(') {// ������ֵ�һ���ַ�����i�ͣ���ʱ��
            flag = false;
            System.err.println("\t����﷨���ң��ⲻ�Ǿ��ӣ�����");
            GrammaticalAnalysis.writerFile(AnalysisProcess,"\t����﷨���ң��ⲻ�Ǿ��ӣ�����"+"\n",true);
            return;
        }
        for (int i = 1; i < buffer.length() - 2; i++) {
            if (buffer.charAt(i) != 'i') {
                if (buffer.charAt(i) == '(') {// �����ź���ֻ�ܽ�i
                    if (buffer.charAt(i + 1) == ')'
                            || buffer.charAt(i + 1) == '+'
                            || buffer.charAt(i + 1) == '*'
                            || buffer.charAt(i + 1) == '/') {

                        flag = false;
                        System.err.println("\t����﷨���ң��ⲻ�Ǿ��ӣ�����");
                        GrammaticalAnalysis.writerFile(AnalysisProcess,"\t����﷨���ң��ⲻ�Ǿ��ӣ�����"+"\n",true);
                        return;
                    }
                }
            }
        }
        System.out.println("***************************************");
        System.out.println("����\tջ\t���뻺����");
        GrammaticalAnalysis.writerFile(AnalysisProcess,"����\tջ\t���뻺����"+"\n",true);

        //System.out.println(step++ + "\t" + stack + "\t" + buffer);
        GrammaticalAnalysis.writerFile(AnalysisProcess,step++ + "\t" + stack + "\t" + buffer+"\n",true);
        move();
        do {
            top = stack.length() - 1;// topָ��ջ����һ�����ս��
            if (buffer.charAt(0) == '#') {// �ж��˳�
                if (stack.charAt(stack.length() - 2) == '#'
                        && stack.charAt(stack.length() - 1) == 'N') {
                    System.out.println();
                    System.out.println("\t�����봮�Ǿ��ӣ����Է���ʹ�á�");
                    GrammaticalAnalysis.writerFile(AnalysisProcess,"�����ɹ���"+"\n",true);
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
        if (sequenceList.indexOf(buffer.charAt(0)) == -1) {// ÿ��Ҫ��⻺�������ַ�
            flag = false;
            System.err.println("\t���ڰ����Ƿ��ַ����ⲻ�Ǿ��ӣ�����");
            GrammaticalAnalysis.writerFile(analysisProcess,"\t���ڰ����Ƿ��ַ����ⲻ�Ǿ��ӣ�����"+"\n",true);
            return true;
        }
        return false;
    }

    public static String readerFile(File file) {
        try {
            BufferedReader Breader = null;
            Breader = new BufferedReader(new FileReader(file));
            String fp = null;
            while ((fp=Breader.readLine()) != null) {//���ж�ȡ�ļ�
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
     * �ַ����ȼ��Ƚϡ���ջ����Լ
     * */
    private static void compare(char a, char b) {

        int i = sequenceList.indexOf(a);// ��
        int j = sequenceList.indexOf(b);// ��
        // System.out.println(i+"       "+j);
        switch (relation[i][j]) {
            case 1:// a>b,��ջ���ҵڶ����ս����׼����Լ
                int second;
                second = top - 1;
                while (stack.charAt(second) == 'N') {
                    second--;
                }

                int n = 0;
                for (int m = 0; m < top; m++) {// �ҵ�����ַ�ǰ���м���N
                    if (stack.charAt(m) == 'N') {
                        n++;
                    }
                }
                // System.out.println(n);

                i = sequenceList.indexOf(stack.charAt(top));
                j = sequenceList.indexOf(stack.charAt(second));
                if (relation[j][i] == 0) {// ջ�����ս���͵ڶ��ս�����ȼ����
                    stack.replace(second, top + 1, "N");// �ӵڶ��ս�������ս������ȥ,��N�滻

                } else if (relation[j][i] == -1) {// ջ�����ս�����ȼ���
                    if (stack.charAt(top - 1) == 'N') {// ��������N�����ұ�Ҳ�У������ߵ�Nһ���Լ

//                        System.out.println("\t��Ԫʽ" + ++siYianShi + "("
//                                + stack.charAt(top) + "," + nValue[n - 1] + ","
//                                + nValue[n] + "," + "T" + result + ")");// �����Ԫʽ
                        GrammaticalAnalysis.writerFile(fouryuanStyle,"\t��Ԫʽ" + ++siYianShi + "("
                                + stack.charAt(top) + "," + nValue[n - 1] + ","
                                + nValue[n] + "," + "T" + result + ")"+"\n",true);
                        nValue[n - 1] = "T" + result++;// �����Tn���棬���N��Ӧ��
                        stack.delete(top, top + 2);// ɾ������start��end-1���ַ�

                    } else {
                        nValue[n] = Character.toString((stack.charAt(top)));// ������N��Ӧ��
                        stack.deleteCharAt(top);// ֻ��Լ���Լ�,������ջ��
                        stack.append('N');
                    }
                }
                //System.out.println(step++ + "\t" + stack + "\t" + buffer);
                GrammaticalAnalysis.writerFile(AnalysisProcess,step++ + "\t" + stack + "\t" + buffer+"\n",true);


                break;
            case 0:// a=b,b��ջ

                // break;
            case -1:// a<b,b��ջ

                move();
                break;
            default:// ���ܱȽ�
                //System.err.println("   " + a + "��" + b + "��ʱ���ܱȽ����ȼ���������봮���Ǿ��ӣ�����");
                GrammaticalAnalysis.writerFile(AnalysisProcess,"   " + a + "��" + b + "��ʱ���ܱȽ����ȼ���������봮���Ǿ��ӣ�����"+"\n",true);
                flag = false;
        }

    }
    /**
     * �����������ַ��Ƶ�ջ��
     */
    private static void move() {
        stack.append(buffer.charAt(0));
        buffer.deleteCharAt(0);
        //System.out.println(step++ + "\t" + stack + "\t" + buffer);
        GrammaticalAnalysis.writerFile(AnalysisProcess,step++ + "\t" + stack + "\t" + buffer+"\n",true);
    }
}
