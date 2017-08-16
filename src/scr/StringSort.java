/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scr;

/**
 *
 * @author keliyi
 */
public class StringSort {

    private String finalString = new String();

    public StringSort(int dlmSize, int talength, String dlmstr, String tastr) {
        this.finalString  =sortString(dlmSize, talength, dlmstr, tastr);
    }

    public String sortString(int dlmSize, int talength, String dlmstr, String tastr) {

        String finalString = new String("");
        int CharNum;
        for (int i = 0; i < dlmSize; i++) {
            if (dlmstr.length() >= talength) {
                CharNum = talength;
            } else {
                CharNum = dlmstr.length();
            }
            int count = 0;

            for (int j = 0; j < CharNum; j++) {

                if (dlmstr.charAt(j) == tastr.charAt(j)) {

                    count += 1;
                } else {
                    break;
                }

            }
            if (count == CharNum) {
                finalString = (count + " " + dlmstr);
                return finalString;
            } else {
                finalString = "";
            }
        }
        return finalString;
    }
    
    public String getFinalString(){
        return finalString;
    }
}
