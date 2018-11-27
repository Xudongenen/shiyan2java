import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
public class GrammaticalAnalysis {
    private static File FIRSTVTfile = new File("D:\\作业\\编译原理\\shiyan2java\\FIRSTVTfile.txt");
    private static File LASTVTfile = new File("D:\\作业\\编译原理\\shiyan2java\\LASTVTfile.txt");
    private static File operatorPrecedenceTable = new File("D:\\作业\\编译原理\\shiyan2java\\operatorPrecedenceTable.txt");
    public static void main(String[] args) throws IOException {
        writerFile(FIRSTVTfile,"",false);
        writerFile(LASTVTfile,"",false);
        writerFile(operatorPrecedenceTable,"",false);
        String fileName = "D:\\作业\\编译原理\\shiyan2java\\input.txt";
        ReadInfo ri = new ReadInfo();
        String[][] ll = ri.gettext(fileName);
        GetFL fl = new GetFL(ll);
        //①读取的文法放在ll[][]二维数组中，可根据下面的注释语句进行打印
        System.out.println(" 文法G" + "[" + ll[0][0] + "]" + ":");
        System.out.println(" " + ll[0][0] + "'" + "->" + "#" + ll[0][0] + "#");
        for (int i = 0; i < ll[0].length; i++) {
            System.out.print(" " + ll[0][i]);
            System.out.print("->");
            System.out.println(ll[1][i]);
        }
        System.out.println(" ");
        //②终结符放在NT[]中（not terminate）可通过下面注释语句进行打印
        String[] NT = fl.getNT();
        System.out.print(" 非终结符集：");
        System.out.print("{");
        for (int i = 0; i < NT.length; i++) {
            //if(NT[i]=="\\'"){System.out.print("亲！咱解决不了含有“‘”的文法");}
            if (NT.length - 1 == i)
                System.out.print(NT[i]);
            else
                System.out.print(NT[i] + ",");
        }
        System.out.println("}");

        //③终结符放在T[]中（teminate） 可通过下面注释语句进行打印
        String[] T = fl.getT();
        System.out.print(" 终结符集：");
        System.out.print("{");
        for (int i = 0; i < T.length; i++) {
            if (T.length - 1 == i)
                System.out.print(T[i]);
            else
                System.out.print(T[i] + ",");
        }
        System.out.println("}");
        System.out.println(" ");


        //④FIRSTVT求解过程
        String[][] FirstVT = fl.getFirstVT();
        System.out.println(" FIRSTVT集：");
        writerFile(FIRSTVTfile, " FIRSTVT集：" + "\n",true);
        System.out.println(" LASTVT(" + FirstVT[0][0] + "'" + ")" + "=" + "{" + "#" + "}");
        writerFile(FIRSTVTfile, " LASTVT(" + FirstVT[0][0] + "'" + ")" + "=" + "{" + "#" + "}" + "\n",true);
        for (int i = 0; i < FirstVT[0].length; i++) {
            System.out.print(" FIRSTVT(" + FirstVT[0][i] + ")" + "=" + "{");
            writerFile(FIRSTVTfile, " FIRSTVT(" + FirstVT[0][i] + ")" + "=" + "{",true);
            //System.out.println(FirstVT[1][i]);
            //if(NT[i]=="\\'"){System.out.print("亲！咱解决不了含有“‘”的文法");}
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


        //⑤LASTVT求解过程
        String[][] LastVT = fl.getLastVT();

        System.out.println(" LASTVT集：");
        writerFile(LASTVTfile, " LASTVT集：" + "\n",true);
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
        //⑥求解算符优先矩阵
        System.out.println("算符优先关系矩阵");
        writerFile(operatorPrecedenceTable, "算符优先关系矩阵" + "\n",true);
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
        if (!fl.isOP) System.out.print("此文发非算符优先文法");
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
    //构造函数对象进行初始化
    GetFL(String[][] ll){
        str=ll;
    }
    //①求FIRSTVT
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
    //获取非终结符放到NT数组中
    String[] getNT(){
        //String [][] Follow=getFollow();
        String[] NT=new String[str[0].length];
        System.arraycopy(str[0], 0, NT, 0, str[0].length);
        return NT;
    }
    //获取终结符  放到T数组中
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
    //递归
    private void getFVT(String str3, String str4){
        String[] s;
        if(str4.contains("|")){
            s=str4.split("\\|");
            for (String value : s) {
                 getFVT(str3, value);
            }
        }
        else{
            //1	//*str4的长度为1
            if(str4.length()==1){//*str4的长度为1且为终结符的时候直接加入
                if(!('A'<=str4.charAt(0)&&str4.charAt(0)<='Z')){
                    if(!FIRSRVTstring.contains(String.valueOf(str4.charAt(0))))
                        FIRSRVTstring=FIRSRVTstring.concat(String.valueOf(str4.charAt(0)));
                }
                //2	//*str4的长度为1且为非终结符，此时应查找其str[2][]中str[0][i]为str4的右边表达式getFVT(str[1][i])
                else{
                    for(int i=0;i<str[0].length;i++){
                        if(str[0][i].equals(String.valueOf(str4.charAt(0)))){
                            if(!str3.equals(String.valueOf(str4.charAt(0))))
                                getFVT(str[0][i],str[1][i]);
                        }
                    }
                }
            }
            //3	//*str4的长度大于2
            if(str4.length()>=2){
                //①第一个为终结符时
                if(!('A'<=str4.charAt(0)&&str4.charAt(0)<='Z')){
                    if(!FIRSRVTstring.contains(String.valueOf(str4.charAt(0))))
                        FIRSRVTstring=FIRSRVTstring.concat(String.valueOf(str4.charAt(0)));
                }
                //②第一个非终结符且第二个为终结符
                if('A'<=str4.charAt(0)&&str4.charAt(0)<='Z'&&!('A'<=str4.charAt(1)&&str4.charAt(1)<='Z')){
                    //先加入这个终结符号
                    if(!FIRSRVTstring.contains(String.valueOf(str4.charAt(1))))
                        FIRSRVTstring=FIRSRVTstring.concat(String.valueOf(str4.charAt(1)));
                    //再循环查找这个非终极符的情况
                    //**找到第一个为终结符
                    for(int i=0;i<str[0].length;i++){
                        //if条件用于消除自身进行循环
                        if(str[0][i].equals(String.valueOf(str4.charAt(0))))//&&!str[0][i].equals(String.valueOf(str[1][i].charAt(0))))
                            if(!str3.equals(String.valueOf(str4.charAt(0))))
                                getFVT(str[0][i],str[1][i]);
                    }
                }
                //③第一个非终结符且第二个也为非终结符
                if('A'<=str4.charAt(0)&&str4.charAt(0)<='Z'&&'A'<=str4.charAt(1)&&str4.charAt(1)<='Z'){
                    for(int i=0;i<str[0].length;i++){
                        //if条件用于消除自身进行循环
                        if(str[0][i].equals(String.valueOf(str4.charAt(0))))//&&!str[0][i].equals(String.valueOf(str[1][i].charAt(0))))
                            if(!str3.equals(String.valueOf(str4.charAt(0))))
                                getFVT(str[0][i],str[1][i]);
                        if(str[0][i].equals(String.valueOf(str4.charAt(1))))//&&!str[0][i].equals(String.valueOf(str[1][i].charAt(0))))
                            if(!str3.equals(String.valueOf(str4.charAt(1))))
                                getFVT(str[0][i],str[1][i]);

                    }

                    //**第二个也为非终结符
                    // if('A'<=str4.charAt(0)&&str4.charAt(1)<='Z'){//第二个也非终结符
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

    //②求LASTVT
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
    //递归
    private void getLVT(String string1, String string2) {
        String[] s;
        if(string2.contains("|")){
            s=string2.split("\\|");
            for (String value : s) {
                getLVT(string1, value);
            }
        }
        else{
            //1	   //*string2的长度为1
            if(string2.length()==1){//*string2的长度为1且为终结符的时候直接加入
                if(!('A'<=string2.charAt(0)&&string2.charAt(0)<='Z')){
                    if(!LASTVTstring.contains(String.valueOf(string2.charAt(string2.length()-1))))
                        LASTVTstring=LASTVTstring.concat(String.valueOf(string2.charAt(string2.length()-1)));
                }
                //2	  //*string2的长度为1且为非终结符，此时应查找其str[2][]中str[0][i]为string2的右边表达式getLVT(str[1][i])
                else{
                    for(int i=0;i<str[0].length;i++){
                        if(str[0][i].equals(String.valueOf(string2.charAt(string2.length()-1)))){
                            if(!string1.equals(String.valueOf(string2.charAt(string2.length()-1))))
                                getLVT(str[0][i],str[1][i]);
                        }
                    }
                }
            }
            //3   //*string2的长度大2
            if(string2.length()>=2){
                //①当string2最后一个为终结符时
                if(!('A'<=string2.charAt(string2.length()-1) && string2.charAt(string2.length()-1)<='Z')){
                    if(!LASTVTstring.contains(String.valueOf(string2.charAt(string2.length()-1))))
                        LASTVTstring=LASTVTstring.concat(String.valueOf(string2.charAt(string2.length()-1)));
                }

                //②当string2倒数第二个为终结符时且最后一个为非终结符时
                if(('A'<=string2.charAt(string2.length()-1) && string2.charAt(string2.length()-1)<='Z')&&(!('A'<=string2.charAt(string2.length()-2) && string2.charAt(string2.length()-2)<='Z'))){
                    //将终结符加入
                    if(!LASTVTstring.contains(String.valueOf(string2.charAt(string2.length()-2))))
                        LASTVTstring=LASTVTstring.concat(String.valueOf(string2.charAt(string2.length()-2)));
                    //循环中后一个非终结符加入
                    for(int i=0;i<str[0].length;i++){
                        //if条件用于消除自身进行循环
                        if(str[0][i].equals(String.valueOf(string2.charAt(string2.length()-1))))//&&!str[0][i].equals(String.valueOf(str[1][i].charAt(0))))
                            if(!string1.equals(String.valueOf(string2.charAt(string2.length()-1))))
                                getLVT(str[0][i],str[1][i]);
                    }
                }
                ////③当string2倒数第二个为终结符时且最后一个也为终结符时
                if(('A'<=string2.charAt(string2.length()-1) && string2.charAt(string2.length()-1)<='Z')&&('A'<=string2.charAt(string2.length()-2) && string2.charAt(string2.length()-2)<='Z')){
                    for(int i=0;i<str[0].length;i++){
                        //if条件用于消除自身进行循环
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
    //③求各终结符的算符优先关系
    //利用二维矩阵来存放算符优先关系-1表示无关系，0表示相等关系，1表示小于关系，2表示大于关系
    //先找到形如A—>…a…和A->…aBb…  相等关系 放入equal[][]数组中
    //再找到形如A->…aB…       小于关系   放入lessthan[][]数组中
    //最后找到形如A->…Bb…    大于关系   放入 morethan[][]数组中
    //将以上封装到函数中返回二维数组放到int型op_matrix中
    String[][] getMatrix(){
        String[][] LastVT = getLastVT();
        String[][] FirstVT = getFirstVT();
        String[] T=getT();
        String[][] op_matrix=new String[T.length][T.length];
        //先找到形如A—>…ab…和A->…aBb…  相等关系 放入equal[][]数组中
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
        //再找到形如A->…aB…       小于关系   放入lessthan<String><String> HashTable中
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
        //最后找到形如A->…Bb…    大于关系   放入 morethan[][]数组中
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
        }//将morethan转化为数组more中便于以下操作
        Object[] str=morethan.keySet().toArray();
        Object[] str1=morethan.values().toArray();
        String[][] more=new String[2][morethan.size()];
        for(int i=0;i<str.length;i++){
            more[0][i]=str[i].toString();
            more[1][i]=str1[i].toString();
        }
        //给二维数组赋初值-1,尚未发生关系
        for(int i=0;i<T.length;i++){
            for(int j=0;j<T.length;j++){
                op_matrix[i][j]="\f";
            }
        }
        //进行逻辑赋值

        for(int i=0;i<T.length;i++){
            //查找eaual中两个相等关系符号在T中的位置

            //  for(int k=0;k<equal.size();k++){
            if(equal.containsKey(T[i])){
                for(int j=0;j<T.length;j++){
                    if(equal.get(T[i]).contains(T[j]))
                        if (!op_matrix[i][j].equals(">") && !op_matrix[i][j].equals("<"))
                            op_matrix[i][j]="=";
                        else isOP=false;
                }
            }

            //查找lessthan中两个小于关系符号在T中的位置

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

            //查找morethan中两个小于关系符号在T中的位置

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
        //返回二维数组
        //System.out.print(equal.toString());
        //System.out.print(lessthan.toString());
        //System.out.print(morethan.toString());
        return op_matrix;
    }

    //④对句子进行分析


}
class ReadInfo{
    //读取input.txt文件中的内容
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
        String arrNT[]=new String[list.size()];//非终结符数组
        String arrT[]=new String[list.size()];//终结符号数组
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
