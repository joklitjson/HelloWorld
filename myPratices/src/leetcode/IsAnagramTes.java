package leetcode;

public class IsAnagramTes {
    public static void main(String[] args) {
        String s = "anagram", t = "nagaram";
        System.out.println(isAnagram1(s,t));
    }
    public static boolean isAnagram1(String s, String t) {
        if (s.length()!=s.length()){
            return false;
        }
        int [] arr=new int[26];
        for (int i=0;i<s.length();i++){
            int tmp=s.charAt(i)-'a';
            arr[((int)s.charAt(i)-97)]+=1;
            arr[((int)t.charAt(i)-97)]-=1;
        }
       for (int value:arr){
           if (value!=0){
               return false;
           }
       }
       return true;
    }
}
