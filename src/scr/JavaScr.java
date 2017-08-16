/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scr;

import java.awt.Point;

/**
 *
 * @author keliyi
 */
public class JavaScr {
    
	private static JavaScrFrm jsf=new JavaScrFrm();  
	
    private JavaScr(){}
    
    
    public static JavaScrFrm getScrFrm(){
    	return jsf;
    }
    
    public static void setScrLoc(int x,int y){
    	jsf.setFrmLoc(x,y);
    }
    
    
}
