package firstvt;
import java.util.ArrayList;

public class firstvt {
  private static String[] EFT={"E->T+F|T","T->T*F|F","F->(E)|i"};
  private static char NT[]={'E','T','F'};
  private static ArrayList fi=new ArrayList();

    private static void getfirst(String NT1){
      for (int i=0;i<NT.length;i++)
      {
          if (NT[i]==NT1.charAt(0))
              NT1=EFT[i];
      }
      String p = NT1.substring(3);
      String pSpilt[]= p.split("\\|");
      for (int j=0;j<2;j++){
          if (pSpilt[j].length()>2){
              if ('A'<=pSpilt[j].charAt(0)&&pSpilt[j].charAt(0)<='Z'){
                  fi.add(String.valueOf(pSpilt[j].charAt(1)));
              }else {
                  fi.add(String.valueOf(pSpilt[j].charAt(0)));
              }
          }else {
              if ('A'<=pSpilt[j].charAt(0)&&pSpilt[j].charAt(0)<='Z'){
                  getfirst(String.valueOf(pSpilt[j].charAt(0)));
              }else {
                  fi.add(String.valueOf(pSpilt[j].charAt(0)));
              }
          }
          }
      }
    public static void main(String[] arge){
        String f = "F->(E)|i";
        getfirst(f);//传入想求得非终结符
        System.out.println(fi);
        String e = "E->T+F|T";
        getfirst(e);
        String t = "T->T*F|F";
        getfirst(t);
    }
}
