/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VSM;

import Jama.Matrix;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import tools.Preprocessor;

/**
 *
 * @author Bushra
 */
public class SentenceTermMatrix extends IRM {
    private Preprocessor input;
    private int twm=1;        // term weighting method
    
    private ArrayList<String> listOfProcessedSents = new ArrayList<>();
    private ArrayList<String> uniqTerms= new ArrayList<>();
    private int numSents;
    private int numUniqTerms;
    
    private Matrix mainMatr;    // sentence by term matrix
    private TermWeighting tw;
    
    private HashMap<String, Integer> GTF = new HashMap<>();
    private HashMap<String, Double> WP = new HashMap<>();
    private HashMap<String, Integer> Nj = new HashMap<>();
    private HashMap<String, Double> ISF = new HashMap<>();
    
    
    
    
    public SentenceTermMatrix(Preprocessor p, int x){
        this.input=p;
        this.twm=x;
        process();
    }

    private void process() {
        this.listOfProcessedSents=input.getProcessedSents();
        this.uniqTerms=input.getUniqTerms();
        
        this.numSents=listOfProcessedSents.size();
        this.numUniqTerms=uniqTerms.size();
        
        this.mainMatr=new Matrix(numSents, numUniqTerms);
        this.tw=new TermWeighting(input);
        
        this.GTF=tw.getGTF();
        this.WP=tw.getWP();
        this.Nj=tw.getNj();
        this.ISF=tw.getISF();
        
        switch(this.twm){
            case 1:
                // LTF method is selected
                fillLTF();
                break;
            case 2:
                // GTF method is selected
                fillGTF();
                break;
            case 3:
                // WP method is selected
                fillWP();
                break;
            case 4:
                // IDF method is selected
                fillIDF();
                break;
            case 5:
                // LTF*GTF method is selected
                fillLTF_GTF();
                break;
            case 6:
                // TF-IDF method is selected
                fillTF_IDF();
                break;
            default:
                // LTF method is selected as default method
                fillLTF();
                break;   
        }
        
        
    }

    // filling MainMatrix with LTF 
    private void fillLTF() {
        int n=this.numSents; 
        int t=this.numUniqTerms;
        for(int i=0; i<n; i++){
            String s1=listOfProcessedSents.get(i);
            String [] s2=tokenizeTxt(s1);
            int sSiz=0;
            if(s2!=null)
                sSiz=s2.length;
            for(int j=0; j<t; j++){
                double tLTF=0.00;
                String s3=uniqTerms.get(j);
                for(int k=0; k<sSiz; k++){
                    String s4=s2[k];
                    if(s3.equals(s4))
                        tLTF++;
                }
                this.mainMatr.set(i, j, tLTF);
            }
        }
    }

    // filling MainMatrix with GTF 
    private void fillGTF() {
        int n=this.numSents; 
        int t=this.numUniqTerms;
        for(int i=0; i<n; i++){
            String s1=listOfProcessedSents.get(i);
            String [] s2=tokenizeTxt(s1);

            for(int j=0; j<t; j++){
                double tGTF=0.00;
                String s3=uniqTerms.get(j);
                if(s2!=null && Arrays.asList(s2).contains(s3))
                    tGTF=this.GTF.get(s3);
                this.mainMatr.set(i, j, tGTF);
            }
        }
    }

    // filling MainMatrix with WP
    private void fillWP() {
        int n=this.numSents; 
        int t=this.numUniqTerms;
        for(int i=0; i<n; i++){
            String s1=listOfProcessedSents.get(i);
            String [] s2=tokenizeTxt(s1);

            for(int j=0; j<t; j++){
                double tWP=0.00;
                String s3=uniqTerms.get(j);
                if(s2!=null && Arrays.asList(s2).contains(s3))
                    tWP=this.WP.get(s3);
                this.mainMatr.set(i, j, tWP);
            }
        }
    }

    // filling MainMatrix with IDF
    private void fillIDF() {
        int n=this.numSents; 
        int t=this.numUniqTerms;
        for(int i=0; i<n; i++){
            String s1=listOfProcessedSents.get(i);
            String [] s2=tokenizeTxt(s1);

            for(int j=0; j<t; j++){
                double tIDF=0.00;
                String s3=uniqTerms.get(j);
                if(s2!=null && Arrays.asList(s2).contains(s3))
                    tIDF=this.ISF.get(s3);
                this.mainMatr.set(i, j, tIDF);
            }
        }
    }

    // filling MainMatrix with LTF * GTF
    private void fillLTF_GTF() {
        int n=this.numSents; 
        int t=this.numUniqTerms;
        
        Matrix m1=new Matrix(n, t);
        Matrix m2=new Matrix(n, t);
        this.mainMatr=new Matrix(n, t);
        fillLTF();
        m1=mainMatr.copy();
        this.mainMatr=new Matrix(n, t);
        fillGTF();
        m2=mainMatr.copy();
        this.mainMatr=new Matrix(n, t);
        for(int i=0; i<n; i++){
            for(int j=0; j<t; j++){
                double x1=m1.get(i, j);
                double x2=m2.get(i, j);
                double x3=x1*x2;
                this.mainMatr.set(i, j, x3);
            }
        }
    }

    // filling MainMatrix with LTF * IDF
    private void fillTF_IDF() {
        int n=this.numSents; 
        int t=this.numUniqTerms;
        
        Matrix m1=new Matrix(n, t);
        Matrix m2=new Matrix(n, t);
        this.mainMatr=new Matrix(n, t);
        fillLTF();
        m1=mainMatr.copy();
        this.mainMatr=new Matrix(n, t);
        fillIDF();
        m2=mainMatr.copy();
        this.mainMatr=new Matrix(n, t);
        for(int i=0; i<n; i++){
            for(int j=0; j<t; j++){
                double x1=m1.get(i, j);
                double x2=m2.get(i, j);
                double x3=x1*x2;
                this.mainMatr.set(i, j, x3);
            }
        }
    }
    
    private String[] tokenizeTxt(String in) {
        String text1=in.trim();
        String [] textWords1=text1.split("[፡፣፥፤፦፧?!። \\n]+");
        String [] textWords2=new String[textWords1.length];

        for(int i=0; i<textWords1.length; i++){
            String s2=textWords1[i].trim();
            textWords2[i]=s2;
        }
        return textWords2;
    }
    
    
    // public Returner
    public Matrix getSenTrmMatrix(){
        return this.mainMatr;
    }
    
    public Preprocessor getInputDoc(){
        return this.input;
    }
    
    public TermWeighting getTWing(){
        return this.tw;
    }
}
