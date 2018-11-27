import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
public class GrammaticalAnalysis {
    private static File FIRSTVTfile = new File("D:\\��ҵ\\����ԭ��\\shiyan2java\\FIRSTVTfile.txt");
    private static File LASTVTfile = new File("D:\\��ҵ\\����ԭ��\\shiyan2java\\LASTVTfile.txt");
    private static File operatorPrecedenceTable = new File("D:\\��ҵ\\����ԭ��\\shiyan2java\\operatorPrecedenceTable.txt");
    public static void main(String[] args) throws IOException {
        writerFile(FIRSTVTfile,"",false);
        writerFile(LASTVTfile,"",false);
        writerFile(operatorPrecedenceTable,"",false);
        String fileName = "D:\\��ҵ\\����ԭ��\\shiyan2java\\input.txt";
        ReadInfo ri = new ReadInfo();
        String[][] ll = ri.gettext(fileName);
        GetFL fl = new GetFL(ll);
        //�ٶ�ȡ���ķ�����ll[][]��ά�����У��ɸ��������ע�������д�ӡ
        System.out.println(" �ķ�G" + "[" + ll[0][0] + "]" + ":");
        System.out.println(" " + ll[0][0] + "'" + "->" + "#" + ll[0][0] + "#");
        for (int i = 0; i < ll[0].length; i++) {
            System.out.print(" " + ll[0][i]);
            System.out.print("->");
            System.out.println(ll[1][i]);
        }
        System.out.println(" ");
        //���ս������NT[]�У�not terminate����ͨ������ע�������д�ӡ
        String[] NT = fl.getNT();
        System.out.print(" ���ս������");
        System.out.print("{");
        for (int i = 0; i < NT.length; i++) {
            //if(NT[i]=="\\'"){System.out.print("�ף��۽�����˺��С��������ķ�");}
            if (NT.length - 1 == i)
                System.out.print(NT[i]);
            else
                System.out.print(NT[i] + ",");
        }
        System.out.println("}");

        //���ս������T[]�У�teminate�� ��ͨ������ע�������д�ӡ
        String[] T = fl.getT();
        System.out.print(" �ս������");
        System.out.print("{");
        for (int i = 0; i < T.length; i++) {
            if (T.length - 1 == i)
                System.out.print(T[i]);
            else
                System.out.print(T[i] + ",");
        }
        System.out.println("}");
        System.out.println(" ");


        //��FIRSTVT������
        String[][] FirstVT = fl.getFirstVT();
        System.out.println(" FIRSTVT����");
        writerFile(FIRSTVTfile, " FIRSTVT����" + "\n",true);
        System.out.println(" LASTVT(" + FirstVT[0][0] + "'" + ")" + "=" + "{" + "#" + "}");
        writerFile(FIRSTVTfile, " LASTVT(" + FirstVT[0][0] + "'" + ")" + "=" + "{" + "#" + "}" + "\n",true);
        for (int i = 0; i < FirstVT[0].length; i++) {
            System.out.print(" FIRSTVT(" + FirstVT[0][i] + ")" + "=" + "{");
            writerFile(FIRSTVTfile, " FIRSTVT(" + FirstVT[0][i] + ")" + "=" + "{",true);
            //System.out.println(FirstVT[1][i]);
            //if(NT[i]=="\\'"){System.out.print("�ף��۽�����˺��С��������ķ�");}
            char[] ch = FirstVT[1][i].toCharArray();
            for (int j = 0; j < ch.length; j++) {
                if (ch.length - 1 == j) {
                    System.out.print(ch[j]);
                    writerFile(FIRSTVTfile, String.valueOf(ch[j]),true);
                } else {
                    System.out.print(ch[j] + ",");
                    writerFile(FIRSTVTfile, ch[j] + ",",true);
                }
            }
            System.out.println("}");
            writerFile(FIRSTVTfile, "}" + "\n",true);
        }
        System.out.println(" ");
        writerFile(FIRSTVTfile, " " + "\n",true);


        //��LASTVT������
        String[][] LastVT = fl.getLastVT();

        System.out.println(" LASTVT����");
        writerFile(LASTVTfile, " LASTVT����" + "\n",true);
        System.out.println(" LASTVT(" + LastVT[0][0] + "'" + ")" + "=" + "{" + "#" + "}");
        writerFile(LASTVTfile, " LASTVT(" + LastVT[0][0] + "'" + ")" + "=" + "{" + "#" + "}" + "\n",true);
        for (int i = 0; i < LastVT[0].length; i++) {
            System.out.print(" LASTVT(" + LastVT[0][i] + ")" + "=" + "{");
            writerFile(LASTVTfile, " LASTVT(" + LastVT[0][i] + ")" + "=" + "{",true);
            char[] ch = LastVT[1][i].toCharArray();
            for (int j = 0; j < ch.length; j++) {
                if (ch.length - 1 == j) {
                    System.out.print(ch[j]);
                    writerFile(LASTVTfile, ch[j] + "",true);
                } else {
                    System.out.print(ch[j] + ",");
                    writerFile(LASTVTfile, ch[j] + ",",true);
                }
            }
            System.out.println("}");
            writerFile(LASTVTfile, "}" + "\n",true);
        }
        System.out.println();
        writerFile(LASTVTfile, "\n",true);
        //�����������Ⱦ���
        System.out.println("������ȹ�ϵ����");
        writerFile(operatorPrecedenceTable, "������ȹ�ϵ����" + "\n",true);
        //System.out.println("-------------------------------------------------------------------");
        writerFile(operatorPrecedenceTable, "-----------------------------------------------------------------------------------------------------------------------------------------------------------" + "\n",true);
        System.out.print("\t");
        writerFile(operatorPrecedenceTable, "\t",true);
        for (String aT : T) {
            System.out.print(aT + "\t");
            writerFile(operatorPrecedenceTable, aT + "\t",true);
        }
        System.out.println();
        writerFile(operatorPrecedenceTable, "\n",true);
        System.out.println("------------------------------------------------------------------------------------------------------------");
        writerFile(operatorPrecedenceTable, "-----------------------------------------------------------------------------------------------------------------------------------------------------------" + "\n",true);
        String[][] Op = fl.getMatrix();
        for (int i = 0; i < Op[0].length; i++) {
            System.out.print(" " + T[i]);
            writerFile(operatorPrecedenceTable, " " + T[i],true);
            for (int j = 0; j < Op[0].length; j++) {
                System.out.print("\t" + Op[i][j]);
                writerFile(operatorPrecedenceTable, "\t" + Op[i][j],true);
            }
            System.out.println();
            writerFile(operatorPrecedenceTable, "\n",true);
            System.out.println("------------------------------------------------------------------------------------------------------------");
            writerFile(operatorPrecedenceTable, "-----------------------------------------------------------------------------------------------------------------------------------------------------------" + "\n",true);
        }
        if (!fl.isOP) System.out.print("���ķ�����������ķ�");
  }

    static void writerFile(File file, String s, boolean tag) {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file.getName(), tag);
            fileWriter.write(s);
            fileWriter.close();
        } catch (Exception ignored) {

        }
    }



}
class GetFL {
    private String [][]str;
    private  String FIRSRVTstring="";
    private  String LASTVTstring="";
    boolean isOP=true;
    //static String Tstringselect="";
    //���캯��������г�ʼ��
    GetFL(String[][] ll){
        str=ll;
    }
    //����FIRSTVT
    String [][] getFirstVT(){
        String[][] FirstVT = new String[2][str[0].length];
        for(int i=0;i<str[0].length;i++){
            FirstVT[0][i]=str[0][i];
            getFVT(str[0][i],str[1][i]);
            FirstVT[1][i]=FIRSRVTstring;
            FIRSRVTstring="";
        }
        return FirstVT;
    }
    //��ȡ���ս���ŵ�NT������
    String[] getNT(){
        //String [][] Follow=getFollow();
        String[] NT=new String[str[0].length];
        System.arraycopy(str[0], 0, NT, 0, str[0].length);
        return NT;
    }
    //��ȡ�ս��  �ŵ�T������
    String[] getT(){
        String[] NT1 = getNT();
        StringBuilder temp= new StringBuilder();
        for(int i=0;i<str[1].length;i++){
            temp.append(str[1][i]);
        }
        for (String aNT1 : NT1) {
            temp = new StringBuilder(temp.toString().replaceAll(aNT1, ""));
        }
        temp = new StringBuilder(temp.toString().replaceAll("\\|", ""));
        temp = new StringBuilder(temp.toString().replaceAll("'", ""));
        //System.out.println(temp);
        char[] ch;
        ch= temp.toString().toCharArray();

        String resultString = "";
        for (char aCh : ch) {
            if (!resultString.contains(String.valueOf(aCh)))
                resultString = resultString.concat(String.valueOf(aCh));
        }
        //System.out.println(resultString);
        String[] T=new String[resultString.length()+1];
        T[resultString.length()]="#";
        for(int i=0;i<resultString.length();i++){
            T[i]= String.valueOf(resultString.charAt(i));
            //System.out.println(T[i]);
        }
        return T;
    }
    //�ݹ�
    private void getFVT(String str3, String str4){
        String[] s;
        if(str4.contains("|")){
            s=str4.split("\\|");
            for (String value : s) {
                 getFVT(str3, value);
            }
        }
        else{
            //1	//*str4�ĳ���Ϊ1
            if(str4.length()==1){//*str4�ĳ���Ϊ1��Ϊ�ս����ʱ��ֱ�Ӽ���
                if(!('A'<=str4.charAt(0)&&str4.charAt(0)<='Z')){
                    if(!FIRSRVTstring.contains(String.valueOf(str4.charAt(0))))
                        FIRSRVTstring=FIRSRVTstring.concat(String.valueOf(str4.charAt(0)));
                }
                //2	//*str4�ĳ���Ϊ1��Ϊ���ս������ʱӦ������str[2][]��str[0][i]Ϊstr4���ұ߱��ʽgetFVT(str[1][i])
                else{
                    for(int i=0;i<str[0].length;i++){
                        if(str[0][i].equals(String.valueOf(str4.charAt(0)))){
                            if(!str3.equals(String.valueOf(str4.charAt(0))))
                                getFVT(str[0][i],str[1][i]);
                        }
                    }
                }
            }
            //3	//*str4�ĳ��ȴ���2
            if(str4.length()>=2){
                //�ٵ�һ��Ϊ�ս��ʱ
                if(!('A'<=str4.charAt(0)&&str4.charAt(0)<='Z')){
                    if(!FIRSRVTstring.contains(String.valueOf(str4.charAt(0))))
                        FIRSRVTstring=FIRSRVTstring.concat(String.valueOf(str4.charAt(0)));
                }
                //�ڵ�һ�����ս���ҵڶ���Ϊ�ս��
                if('A'<=str4.charAt(0)&&str4.charAt(0)<='Z'&&!('A'<=str4.charAt(1)&&str4.charAt(1)<='Z')){
                    //�ȼ�������ս����
                    if(!FIRSRVTstring.contains(String.valueOf(str4.charAt(1))))
                        FIRSRVTstring=FIRSRVTstring.concat(String.valueOf(str4.charAt(1)));
                    //��ѭ������������ռ��������
                    //**�ҵ���һ��Ϊ�ս��
                    for(int i=0;i<str[0].length;i++){
                        //if�������������������ѭ��
                        if(str[0][i].equals(String.valueOf(str4.charAt(0))))//&&!str[0][i].equals(String.valueOf(str[1][i].charAt(0))))
                            if(!str3.equals(String.valueOf(str4.charAt(0))))
                                getFVT(str[0][i],str[1][i]);
                    }
                }
                //�۵�һ�����ս���ҵڶ���ҲΪ���ս��
                if('A'<=str4.charAt(0)&&str4.charAt(0)<='Z'&&'A'<=str4.charAt(1)&&str4.charAt(1)<='Z'){
                    for(int i=0;i<str[0].length;i++){
                        //if�������������������ѭ��
                        if(str[0][i].equals(String.valueOf(str4.charAt(0))))//&&!str[0][i].equals(String.valueOf(str[1][i].charAt(0))))
                            if(!str3.equals(String.valueOf(str4.charAt(0))))
                                getFVT(str[0][i],str[1][i]);
                        if(str[0][i].equals(String.valueOf(str4.charAt(1))))//&&!str[0][i].equals(String.valueOf(str[1][i].charAt(0))))
                            if(!str3.equals(String.valueOf(str4.charAt(1))))
                                getFVT(str[0][i],str[1][i]);

                    }

                    //**�ڶ���ҲΪ���ս��
                    // if('A'<=str4.charAt(0)&&str4.charAt(1)<='Z'){//�ڶ���Ҳ���ս��
					/*
					   for(int i=0;i<str[0].length;i++){
							if(	str[0][i].equals(str4))
								getFVT(str[0][i],str[1][i]);
							}*/
                    //   }
				 /*  else{
					   FIRSRVTstring=FIRSRVTstring.concat(String.valueOf(str4.charAt(1)));
				       	}*/
                }
            }
        }
    }

    //����LASTVT
    String [][] getLastVT(){
        String[][] LastVT = new String[2][str[0].length];
        for(int i=0;i<str[0].length;i++){
            LastVT[0][i]=str[0][i];
            getLVT(str[0][i],str[1][i]);
            LastVT[1][i]=LASTVTstring;
            LASTVTstring="";
        }
        return LastVT;
    }
    //�ݹ�
    private void getLVT(String string1, String string2) {
        String[] s;
        if(string2.contains("|")){
            s=string2.split("\\|");
            for (String value : s) {
                getLVT(string1, value);
            }
        }
        else{
            //1	   //*string2�ĳ���Ϊ1
            if(string2.length()==1){//*string2�ĳ���Ϊ1��Ϊ�ս����ʱ��ֱ�Ӽ���
                if(!('A'<=string2.charAt(0)&&string2.charAt(0)<='Z')){
                    if(!LASTVTstring.contains(String.valueOf(string2.charAt(string2.length()-1))))
                        LASTVTstring=LASTVTstring.concat(String.valueOf(string2.charAt(string2.length()-1)));
                }
                //2	  //*string2�ĳ���Ϊ1��Ϊ���ս������ʱӦ������str[2][]��str[0][i]Ϊstring2���ұ߱��ʽgetLVT(str[1][i])
                else{
                    for(int i=0;i<str[0].length;i++){
                        if(str[0][i].equals(String.valueOf(string2.charAt(string2.length()-1)))){
                            if(!string1.equals(String.valueOf(string2.charAt(string2.length()-1))))
                                getLVT(str[0][i],str[1][i]);
                        }
                    }
                }
            }
            //3   //*string2�ĳ��ȴ�2
            if(string2.length()>=2){
                //�ٵ�string2���һ��Ϊ�ս��ʱ
                if(!('A'<=string2.charAt(string2.length()-1) && string2.charAt(string2.length()-1)<='Z')){
                    if(!LASTVTstring.contains(String.valueOf(string2.charAt(string2.length()-1))))
                        LASTVTstring=LASTVTstring.concat(String.valueOf(string2.charAt(string2.length()-1)));
                }

                //�ڵ�string2�����ڶ���Ϊ�ս��ʱ�����һ��Ϊ���ս��ʱ
                if(('A'<=string2.charAt(string2.length()-1) && string2.charAt(string2.length()-1)<='Z')&&(!('A'<=string2.charAt(string2.length()-2) && string2.charAt(string2.length()-2)<='Z'))){
                    //���ս������
                    if(!LASTVTstring.contains(String.valueOf(string2.charAt(string2.length()-2))))
                        LASTVTstring=LASTVTstring.concat(String.valueOf(string2.charAt(string2.length()-2)));
                    //ѭ���к�һ�����ս������
                    for(int i=0;i<str[0].length;i++){
                        //if�������������������ѭ��
                        if(str[0][i].equals(String.valueOf(string2.charAt(string2.length()-1))))//&&!str[0][i].equals(String.valueOf(str[1][i].charAt(0))))
                            if(!string1.equals(String.valueOf(string2.charAt(string2.length()-1))))
                                getLVT(str[0][i],str[1][i]);
                    }
                }
                ////�۵�string2�����ڶ���Ϊ�ս��ʱ�����һ��ҲΪ�ս��ʱ
                if(('A'<=string2.charAt(string2.length()-1) && string2.charAt(string2.length()-1)<='Z')&&('A'<=string2.charAt(string2.length()-2) && string2.charAt(string2.length()-2)<='Z')){
                    for(int i=0;i<str[0].length;i++){
                        //if�������������������ѭ��
                        if(str[0][i].equals(String.valueOf(string2.charAt(string2.length()-1))))//&&!str[0][i].equals(String.valueOf(str[1][i].charAt(0))))
                            if(!string1.equals(String.valueOf(string2.charAt(string2.length()-1))))
                                getLVT(str[0][i],str[1][i]);
                        if(str[0][i].equals(String.valueOf(string2.charAt(string2.length()-2))))//&&!str[0][i].equals(String.valueOf(str[1][i].charAt(0))))
                            if(!string1.equals(String.valueOf(string2.charAt(string2.length()-2))))
                                getLVT(str[0][i],str[1][i]);
                    }
                }
            }
        }
    }
    //������ս����������ȹ�ϵ
    //���ö�ά���������������ȹ�ϵ-1��ʾ�޹�ϵ��0��ʾ��ȹ�ϵ��1��ʾС�ڹ�ϵ��2��ʾ���ڹ�ϵ
    //���ҵ�����A��>��a����A->��aBb��  ��ȹ�ϵ ����equal[][]������
    //���ҵ�����A->��aB��       С�ڹ�ϵ   ����lessthan[][]������
    //����ҵ�����A->��Bb��    ���ڹ�ϵ   ���� morethan[][]������
    //�����Ϸ�װ�������з��ض�ά����ŵ�int��op_matrix��
    String[][] getMatrix(){
        String[][] LastVT = getLastVT();
        String[][] FirstVT = getFirstVT();
        String[] T=getT();
        String[][] op_matrix=new String[T.length][T.length];
        //���ҵ�����A��>��ab����A->��aBb��  ��ȹ�ϵ ����equal[][]������
        HashMap<String,String> equal= new HashMap<>();
        equal.put("#","#");
        for(int i=0;i<str[0].length;i++){
            if(str[1][i].length()==2){
                for(int j=0;j<str[1][i].length()-1;j++){
                    if(!('A'<=str[1][i].charAt(j)&&str[1][i].charAt(j)<'Z')&&(str[1][i].charAt(j)!='|'&&str[1][i].charAt(j+1)!='|')&&!('A'<=str[1][i].charAt(j+1)&&str[1][i].charAt(j+1)<'Z')){
                        equal.put(String.valueOf(str[1][i].charAt(j)),String.valueOf(str[1][i].charAt(j+1)));
                    }
                }
            }
            if(str[1][i].length()>=3){
                for(int j=0;j<str[1][i].length()-2;j++){
                    if((str[1][i].charAt(j+2)!='|'&&str[1][i].charAt(j+1)!='|'&&str[1][i].charAt(j)!='|')&&!('A'<=str[1][i].charAt(j+2)&&str[1][i].charAt(j+2)<'Z')&&!('A'<=str[1][i].charAt(j)&&str[1][i].charAt(j)<'Z')&&('A'<=str[1][i].charAt(j+1)&&str[1][i].charAt(j+1)<'Z')){
                        equal.put(String.valueOf(str[1][i].charAt(j)),String.valueOf(str[1][i].charAt(j+2)));
                    }
                }
            }
        }
        //���ҵ�����A->��aB��       С�ڹ�ϵ   ����lessthan<String><String> HashTable��
        HashMap<String,String> lessthan= new HashMap<>();
        lessthan.put("#",FirstVT[1][0]);
        for(int i=0;i<str[0].length;i++){
            if(str[1][i].length()>=2){
                for(int j=0;j<str[1][i].length()-1;j++){
                    if(!('A'<=str[1][i].charAt(j)&&str[1][i].charAt(j)<='Z')&&str[1][i].charAt(j)!='|'&&('A'<=str[1][i].charAt(j+1)&&str[1][i].charAt(j+1)<='Z')){
                        for(int k=0;k<str[1].length;k++){
                            if(str[0][k].equals(String.valueOf(str[1][i].charAt(j+1))))
                                lessthan.put(String.valueOf(str[1][i].charAt(j)),FirstVT[1][k]);
                        }
                    }
                }
            }
        }
        //����ҵ�����A->��Bb��    ���ڹ�ϵ   ���� morethan[][]������
        HashMap<String,String> morethan= new HashMap<>();
        morethan.put("#",LastVT[1][0]);
        for(int i=0;i<str[0].length;i++){
            if(str[1][i].length()>=2){
                for(int j=0;j<str[1][i].length()-1;j++){
                    if(('A'<=str[1][i].charAt(j)&&str[1][i].charAt(j)<='Z')&&!('A'<=str[1][i].charAt(j+1)&&str[1][i].charAt(j+1)<='Z')&&str[1][i].charAt(j+1)!='|'){
                        for(int k=0;k<str[1].length;k++){
                            if(str[0][k].equals(String.valueOf(str[1][i].charAt(j))))
                                morethan.put(String.valueOf(str[1][i].charAt(j+1)),LastVT[1][k]);
                        }
                    }
                }
            }
        }//��morethanת��Ϊ����more�б������²���
        Object[] str=morethan.keySet().toArray();
        Object[] str1=morethan.values().toArray();
        String[][] more=new String[2][morethan.size()];
        for(int i=0;i<str.length;i++){
            more[0][i]=str[i].toString();
            more[1][i]=str1[i].toString();
        }
        //����ά���鸳��ֵ-1,��δ������ϵ
        for(int i=0;i<T.length;i++){
            for(int j=0;j<T.length;j++){
                op_matrix[i][j]="\f";
            }
        }
        //�����߼���ֵ

        for(int i=0;i<T.length;i++){
            //����eaual��������ȹ�ϵ������T�е�λ��

            //  for(int k=0;k<equal.size();k++){
            if(equal.containsKey(T[i])){
                for(int j=0;j<T.length;j++){
                    if(equal.get(T[i]).contains(T[j]))
                        if (!op_matrix[i][j].equals(">") && !op_matrix[i][j].equals("<"))
                            op_matrix[i][j]="=";
                        else isOP=false;
                }
            }

            //����lessthan������С�ڹ�ϵ������T�е�λ��

            //for(int k=0;k<lessthan.size();k++){
            if(lessthan.containsKey(T[i])){
                for(int j=0;j<T.length;j++){
                    if(lessthan.get(T[i]).contains(T[j]))
                        if (!op_matrix[i][j].equals(">") && !op_matrix[i][j].equals("="))
                            op_matrix[i][j]="<";
                        else isOP=false;
                }
            }
            // }

            //����morethan������С�ڹ�ϵ������T�е�λ��

            for(int k=0;k<more[0].length;k++){
                String temp=more[1][k];
                if(temp.contains(T[i])){
                    for(int j=0;j<T.length;j++){
                        if(more[0][k].contains(T[j]))
                            if (!op_matrix[i][j].equals("<") && !op_matrix[i][j].equals("="))
                                op_matrix[i][j]=">";
                            else isOP=false;
                    }
                }
            }

        }
        //���ض�ά����
        //System.out.print(equal.toString());
        //System.out.print(lessthan.toString());
        //System.out.print(morethan.toString());
        return op_matrix;
    }

    //�ܶԾ��ӽ��з���


}
class ReadInfo{
    //��ȡinput.txt�ļ��е�����
    String[][] gettext(String str) throws IOException{
        FileReader fr=new FileReader(str);
        BufferedReader br=new BufferedReader(fr);
        String temp;
        ArrayList<String> list = new ArrayList<>();
        while((temp=br.readLine())!=null){
            list.add(temp);
        }
        br.close();
        String arr[]=new String[list.size()];
        String arrNT[]=new String[list.size()];//���ս������
        String arrT[]=new String[list.size()];//�ս��������
        for(int i=0;i<list.size();i++){

            arr[i]= list.get(i);
            //System.out.println(arr[i]);
        }
        for(int i=0;i<arr.length;i++){
            String[] temp1;
            temp1=arr[i].split("->");
            arrNT[i]=temp1[0];
            arrT[i]=temp1[1];
        }
        String[][] ll=new String[2][list.size()];
        for(int i=0;i<list.size();i++){
            ll[0][i]=arrNT[i];
            ll[1][i]=arrT[i];
        }
        return ll;
    }
}
