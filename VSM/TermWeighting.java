/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modules.IRM;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import modules.preprocessing.Preprocessor;

/**
 *
 * @author Busha
 */
public class TermWeighting {
    private Preprocessor input;
    private ArrayList<String> uniqTerms= new ArrayList<>();
    private ArrayList<String> listOfProcessedSents = new ArrayList<>();
    private String [] processedTxtWords;
    private String [] processedTitlelWords;
    private String [] processedQueryWords;
    
    private int numSent;
    private int numWrds;
    private int numUniqTerms;
    
    private HashMap<String, Integer> GTF = new HashMap<>();
    private HashMap<String, Integer> TFinT = new HashMap<>();
    private HashMap<String, Integer> TFinQ = new HashMap<>();
    private HashMap<String, Double> WP = new HashMap<>();
    private HashMap<String, Integer> Nj = new HashMap<>();
    private HashMap<String, Double> ISF = new HashMap<>();
    
    
    public TermWeighting(Preprocessor p){
        this.input=p;
        process();
    }

    private void process() {
        this.uniqTerms=input.getUniqTerms();
        this.listOfProcessedSents=input.getProcessedSents();
        this.processedTxtWords=input.getProcessedTxtWords();
        this.processedTitlelWords=input.getProcessedTitlelWords();
        this.processedQueryWords=input.getProcessedQueryWords();
        
        this.numSent=listOfProcessedSents.size();
        this.numUniqTerms=uniqTerms.size();
        if(this.processedTxtWords!=null){
            this.numWrds=processedTxtWords.length;
        }else{
            this.numWrds=0;
        }
        
        computeGTF();
        computeTFinT();
        computeTFinQ();
        computeWP();
        computeNj();
        computeISF();
        
    }
    
    
    // compute GTF for each uniq terms 
    private void computeGTF() {
        for (int i = 0; i < uniqTerms.size(); i++) {
            int temp1=0;
            String currentwrd1=uniqTerms.get(i);
            for(int j=0; j<this.numWrds; j++){
                if(currentwrd1.equals(processedTxtWords[j]))
                    temp1++;  
            }
            this.GTF.put(currentwrd1, temp1);
        }
    }
    
    
    //Compute Term Frequency in the title, for each uniq terms
    private void computeTFinT(){
        int size=0;
        if(this.processedTitlelWords!=null)
            size=this.processedTitlelWords.length;
        
        for(int i=0; i<uniqTerms.size(); i++){
            int tTF=0;
            String crntUnqTrm=uniqTerms.get(i);
            for(int j=0; j<size; j++){
                String temp=processedTitlelWords[j];
                if(crntUnqTrm.equals(temp)) 
                    tTF++; 
            } 
            this.TFinT.put(crntUnqTrm, tTF);
        }
    }
    
    //Compute Term Frequency in the query, for each uniq terms
    private void computeTFinQ(){
        int size=0;
        if(this.processedQueryWords!=null)
            size=this.processedQueryWords.length;
        
        for(int i=0; i<uniqTerms.size(); i++){
            int qTF=0;
            String s3=uniqTerms.get(i); 
            for(int j=0; j<size; j++){
                String s4=processedQueryWords[j];
                if(s3.equals(s4)) 
                    qTF++; 
            } 
            this.TFinQ.put(s3, qTF);
        }
    }
    
    //Compute Word Probability for each uniq terms; =GTF/numAllWrds
    private void computeWP(){
        for(int i=0; i<uniqTerms.size(); i++){
            String currentWrd=uniqTerms.get(i);
            double gtf=GTF.get(currentWrd);
            double N=this.numWrds;
            if(N==0)
                N=1;
            double wp=gtf/N;
            this.WP.put(currentWrd, wp);
        }
    }
    
    //Compute Nj for each uniq terms, i.e, the  total number of sentences where the term appears
    private void computeNj(){
        for(int i=0; i<uniqTerms.size(); i++){
            String curntUnqTrm=uniqTerms.get(i);
            int count=0;
            for(int j=0; j<numSent; j++){
                String s1=this.listOfProcessedSents.get(j).trim();
                String [] sentWords1=s1.split("[፡፣፥፤፦፧?!። \\n]+");
                String [] sentWords2=new String[sentWords1.length];
                
                for(int k=0; k<sentWords2.length; k++){
                    String s2=sentWords1[k].trim();
                    sentWords2[k]=s2;
                }
                
                if(Arrays.asList(sentWords2).contains(curntUnqTrm)){
                    count++;
                }
            }
            this.Nj.put(curntUnqTrm, count);
        }
    }
    
    //Compute Inverse Sentence Frequency (ISF) for each uniq terms, 
    private void computeISF(){
        double d1=(double)this.numSent;
        for(int i=0; i<uniqTerms.size(); i++){
            String temp=uniqTerms.get(i);
            double isf=0.00;
            if(d1==0){
                this.ISF.put(temp, isf);
            }
            else{
                double d2=(double)this.Nj.get(temp);
                if(d2==0)
                    d2=1;
                double d3=d1/d2;
                isf=(Math.log10(d3));   // common logarithm i.e base 10
                // double isf=(Math.log(d3)); 
                this.ISF.put(temp, isf);
            }
        }
    }
    
    
    
    
    
    // public Returners
    
    public HashMap<String, Integer> getGTF(){
        return this.GTF;
    }
    
    public HashMap<String, Integer> getTFinT(){
        return this.TFinT;
    }
    
    public HashMap<String, Integer> getTFinQ(){
        return this.TFinQ;
    }
    
    public HashMap<String, Double> getWP(){
        return this.WP;
    }
    
    public HashMap<String, Integer> getNj(){
        return this.Nj;
    }
    
    public HashMap<String, Double> getISF(){
        return this.ISF;
    }
    
    public ArrayList<String> getUniqTrm(){
        return this.uniqTerms;
    }
}
    
    

