/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Bushra
 */
public class Preprocessor {
    // Original Input Units
    private String originalFullText;
    private String originalTitle;
    private String originalQuery;
    // Original Input's Text units
    private ArrayList<String> listOfOriginalSents = new ArrayList<>();
    private String [] originalTxtWords;
    private String [] originaTitlelWords;
    private String [] originalQueryWords;
    private String [] allOriginalWords;
    // Processed Text units
    private ArrayList<String> listOfProcessedSents = new ArrayList<>();
    private String processedTitle;
    private String processedQuery;
    private String [] processedTxtWords;
    private String [] processedTitlelWords;
    private String [] processedQueryWords;
    private String [] processedAllWrds;
    private ArrayList<String> uniqTerms= new ArrayList<>();
    // Numbers, Statistics
    private int numOfSents;
    private int numOfAllOrigWrds;
    private int numOfAllProcWrds;
    private int numOfUniqTrms;
    // Resources (Language, System resources)
    private ArrayList<String> stopWords= new ArrayList<>();
    private ArrayList<String> shortWords= new ArrayList<>();
    private HashMap<String, String> abbrevateWords=new HashMap<>();
    // cue words
//-------------------------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    public Preprocessor(String fullText, String title, String query){
        this.originalFullText=fullText.trim();
        this.originalTitle=title.trim();
        this.originalQuery=query.trim();
        
        process();
    }
    // PROCESSOR
    private void process() {
    // 1. Loading & Initializing Language Resources
        populateStopWordList();
        populateAbbrevWordList();
    // 2. Sentence Segmentation & Sentence Processing
        parseSentences();
        processSents();
    // 3. Tokenization & Word processing
        if(!this.originalFullText.isEmpty()){
            this.originalTxtWords= tokenizeTxt(originalFullText);
            this.processedTxtWords=processWords(originalTxtWords);
        }
    // 4. Title processing
       if(this.originalTitle!=null && !this.originalTitle.isEmpty()){
           this.originaTitlelWords=tokenizeTxt(this.originalTitle);
           this.processedTitlelWords=processWords(this.originaTitlelWords);
           this.processedTitle=rebuildPTxt(processedTitlelWords);
       }
    // 5. Query Processing
       if(this.originalQuery!=null && !this.originalQuery.isEmpty()){
           this.originalQueryWords=tokenizeTxt(this.originalQuery);
           this.processedQueryWords=processWords(this.originalQueryWords);
           this.processedQuery=rebuildPTxt(processedQueryWords);
       }
    // 6. Organizing words
       organizeWords();
    // 7. Computing statistics/numbers
       this.numOfSents=this.listOfProcessedSents.size();
       this.numOfAllOrigWrds=this.allOriginalWords.length;
       this.numOfAllProcWrds=this.processedAllWrds.length;
       this.numOfUniqTrms=this.uniqTerms.size();
    }
// ------------------- METHOD or OPERATION---------------------------------------------------------------------------------------------------------------------------
    // Sentence Segmentation
    private void parseSentences(){
        String currentSentence;
        int currentChar = 0;
        int previousStop = 0;
        while (currentChar < originalFullText.length()){
            if (originalFullText.charAt(currentChar) == '?' || originalFullText.charAt(currentChar) == '!' || originalFullText.charAt(currentChar) == '።' ) {
                // end of sentence
                currentSentence = originalFullText.substring(previousStop, currentChar + 1);
                currentSentence=currentSentence.replace('\n', ' ');
                this.listOfOriginalSents.add(currentSentence.trim());
                currentChar++;
                previousStop = currentChar;
            }
            currentChar++;
        }
        if(currentChar-1>previousStop){
            String currentSentence2 = originalFullText.substring(previousStop, currentChar);
            currentSentence2=currentSentence2.replace('\n', ' ');
            this.listOfOriginalSents.add(currentSentence2.trim());
        }
    }
    // Sentence Processing 
    private void processSents() {
        for(int i=0; i<listOfOriginalSents.size(); i++){
            String currentSent=listOfOriginalSents.get(i);
            // Character c=currentSent.charAt(currentSent.length()-1);
          String [] sentWords=tokenizeTxt(currentSent);
          String []processedSentWrds=processWords(sentWords);
          String processedSent=rebuildPTxt(processedSentWrds);
          
          this.listOfProcessedSents.add(processedSent);  
        }
    }
    
    private String[] processWords(String[] inWords) {
        String [] tempList=inWords;
        String [] processedList;
       // Normalization
       String [] tempList2= normalizeWrds(tempList);
       // Stop word removal
       ArrayList<String> tempList3= new ArrayList<>();
       for(int j=0; j<tempList2.length; j++){
           String temp=tempList[j];
           String temp2=tempList2[j];
           if(!stopWords.contains(temp)){
               tempList3.add(temp2);
           }
       }
       // Stemming
       int n=tempList3.size();
       String [] tempList4=new String[n];
       for(int j=0; j<tempList3.size(); j++){
           String currentWrd=tempList3.get(j);
           String temp3=wrdStemmer(currentWrd);
           tempList4[j]=temp3;
       }
       // finished
       processedList=tempList4;
       return processedList;
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
    
    private String[] normalizeWrds(String[] inWords) {
        int size=inWords.length;
        String [] txtWords= new String[size];
        for(int i=0; i<inWords.length; i++){
            String currentWrd=inWords[i];
            String temp=wrdNormalize(currentWrd);
            txtWords[i]=temp;
        }
        return txtWords;
    }
    
    private String wrdNormalize(String inWrd) {
        String currentWrd=inWrd;
        String normalizedWrd=currentWrd;
        
         // Word Expander check 1
        if (shortWords.contains(normalizedWrd)) {
               String expandedForm=this.abbrevateWords.get(normalizedWrd);
               normalizedWrd=expandedForm;
            }

        // Character Replacement
        normalizedWrd=normalizedWrd.replace('ሐ', 'ሀ');
        normalizedWrd=normalizedWrd.replace('ሑ', 'ሁ');
        normalizedWrd=normalizedWrd.replace('ሒ', 'ሂ');
        normalizedWrd=normalizedWrd.replace('ሓ', 'ሀ');
        normalizedWrd=normalizedWrd.replace('ሔ', 'ሄ');
        normalizedWrd=normalizedWrd.replace('ሕ', 'ህ');
        normalizedWrd=normalizedWrd.replace('ሖ', 'ሆ');

        normalizedWrd=normalizedWrd.replace('ኅ', 'ሀ');
        normalizedWrd=normalizedWrd.replace('ኁ', 'ሁ');
        normalizedWrd=normalizedWrd.replace('ኂ', 'ሂ');
        normalizedWrd=normalizedWrd.replace('ኃ', 'ሀ');
        normalizedWrd=normalizedWrd.replace('ኄ', 'ሄ');
        normalizedWrd=normalizedWrd.replace('ኅ', 'ህ');
        normalizedWrd=normalizedWrd.replace('ኆ', 'ሆ');

        normalizedWrd=normalizedWrd.replace('ዐ', 'አ');
        normalizedWrd=normalizedWrd.replace('ዑ', 'ኡ');
        normalizedWrd=normalizedWrd.replace('ዒ', 'ኢ');
        normalizedWrd=normalizedWrd.replace('ዓ', 'አ');
        normalizedWrd=normalizedWrd.replace('ዔ', 'ኤ');
        normalizedWrd=normalizedWrd.replace('ዕ', 'እ');
        normalizedWrd=normalizedWrd.replace('ዖ', 'ኦ');

        normalizedWrd=normalizedWrd.replace('ሠ', 'ሰ');
        normalizedWrd=normalizedWrd.replace('ሡ', 'ሱ');
        normalizedWrd=normalizedWrd.replace('ሢ', 'ሲ');
        normalizedWrd=normalizedWrd.replace('ሣ', 'ሳ');
        normalizedWrd=normalizedWrd.replace('ሤ', 'ሴ');
        normalizedWrd=normalizedWrd.replace('ሥ', 'ስ');
        normalizedWrd=normalizedWrd.replace('ሦ', 'ሶ');

        normalizedWrd=normalizedWrd.replace('ጸ', 'ፀ');
        normalizedWrd=normalizedWrd.replace('ጹ', 'ፁ');
        normalizedWrd=normalizedWrd.replace('ጺ', 'ፂ');
        normalizedWrd=normalizedWrd.replace('ጻ', 'ፃ');
        normalizedWrd=normalizedWrd.replace('ጼ', 'ፄ');
        normalizedWrd=normalizedWrd.replace('ጽ', 'ፅ');
        normalizedWrd=normalizedWrd.replace('ጾ', 'ፆ');

        normalizedWrd=normalizedWrd.replace('ሃ', 'ሀ');
        normalizedWrd=normalizedWrd.replace('ኻ', 'ሀ');
        normalizedWrd=normalizedWrd.replace('ኣ', 'አ');
        normalizedWrd=normalizedWrd.replace('ኧ', 'አ');
        normalizedWrd=normalizedWrd.replace('ዉ', 'ው');
        normalizedWrd=normalizedWrd.replace('ጎ', 'ጐ');
        normalizedWrd=normalizedWrd.replace('ኰ', 'ኮ');

        // Word Expander
        if (shortWords.contains(normalizedWrd)) {
               String expandedForm=this.abbrevateWords.get(normalizedWrd);
               normalizedWrd=expandedForm;
            }

        return normalizedWrd;
    }
    
    
    private String wrdStemmer(String word) {
        String x=word;
        return x;
    }
    
    
    
    private void populateStopWordList() {
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(new File("").getAbsolutePath() + "/src/Resources/stopWordList.txt")));
            String line = bufferedReader.readLine();
            while(line != null){
                if(!line.isEmpty())
                    this.stopWords.add(line.trim());
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
        }
        catch(Exception e){
            System.out.println("Stop word list file does not exist. Set correct path");
            e.printStackTrace();
        }
    }

    private void populateAbbrevWordList() {
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(new File("").getAbsolutePath() + "/src/Resources/AbbreviateWords.txt")));
            String line = bufferedReader.readLine();
            while(line != null){
                if(!line.isEmpty()){
                    String [] abbr=line.split("[=]");
                    String shortForm=abbr[0].trim();
                    String expandedForm=abbr[1].trim();
                    this.shortWords.add(shortForm);
                    this.abbrevateWords.put(shortForm, expandedForm);
                }
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
        }
        catch(Exception e){
            System.out.println("AbbrevWordList file does not exist. Set correct path");
            e.printStackTrace();
        }
    }

    

    private String rebuildPTxt(String[] inPtxtWrds) {
        // Rebuild new preprocessed text unit
        String processedTxtUnit="";
        for(int j=0; j<inPtxtWrds.length; j++){
            processedTxtUnit+=inPtxtWrds[j]+" ";
        }
        return processedTxtUnit.trim();
    }

    private void organizeWords() {
        ArrayList<String> allOrigWrds= new ArrayList<>();
        ArrayList<String> allProcWrds= new ArrayList<>();
        // From full text
        if(!this.originalFullText.isEmpty()){
        for(int i=0; i<this.originalTxtWords.length; i++){
            allOrigWrds.add(this.originalTxtWords[i]);
        }
        
        for(int i=0; i<this.processedTxtWords.length; i++){
            allProcWrds.add(this.processedTxtWords[i]);
        }
        }
        /// from title
        if(this.originalTitle!=null && !this.originalTitle.isEmpty()){
            for(int i=0; i<this.originaTitlelWords.length; i++){ 
                allOrigWrds.add(this.originaTitlelWords[i]);
            }
            
            for(int i=0; i<this.processedTitlelWords.length; i++){ 
                allProcWrds.add(this.processedTitlelWords[i]);
            }
        }
        // from query
        if(this.originalQuery!=null && !this.originalQuery.isEmpty()){
            for(int i=0; i<this.originalQueryWords.length; i++){ 
                allOrigWrds.add(this.originalQueryWords[i]);
            }
            
            for(int i=0; i<this.processedQueryWords.length; i++){ 
                allProcWrds.add(this.processedQueryWords[i]);
            }
        }
        // get all original and all processed words
        int s1=allOrigWrds.size();
        this.allOriginalWords=new String[s1];
        for(int j=0; j<s1; j++){
            this.allOriginalWords[j]=allOrigWrds.get(j);
        }
        int s2=allProcWrds.size();
        this.processedAllWrds=new String[s2];
        for(int j=0; j<s2; j++){
            this.processedAllWrds[j]=allProcWrds.get(j);
        }
        // Find Uniq Terms, from all processed words
        for (int i = 0; i < this.processedAllWrds.length; i++){
           String currentWord = processedAllWrds[i];
           if (!uniqTerms.contains(currentWord)) {
               uniqTerms.add(currentWord);
            }
       }
        
    }

    
// Public Getters
    
    
    public String getOriFullTxt(){return this.originalFullText;}
    public String getOriTitle(){return this.originalTitle;}
    public String getOriQuery(){return this.originalQuery;}
    public String getProcTitle(){return this.processedTitle;}
    public String getProcQuery(){return this.processedQuery;}
    
    public String [] getOriginalTxtWords(){return this.originalTxtWords;}
    public String [] getOriginaTitlelWords(){return this.originaTitlelWords;}
    public String [] getOriginalQueryWords(){return this.originalQueryWords;}
    public String [] getAllOriginalWords(){return this.allOriginalWords;}
    public String [] getProcessedTxtWords(){return this.processedTxtWords;}
    public String [] getProcessedTitlelWords(){return this.processedTitlelWords;}
    public String [] getProcessedQueryWords(){return this.processedQueryWords;}
    public String [] getProcessedAllWrds(){return this.processedAllWrds;}
    
    public ArrayList<String> getOriginalSents(){return this.listOfOriginalSents;}
    public ArrayList<String> getProcessedSents(){return this.listOfProcessedSents;}
    public ArrayList<String> getUniqTerms(){return this.uniqTerms;}
    public ArrayList<String> getStopWords(){return this.stopWords;}
    public ArrayList<String> getShortWords(){return this.shortWords;}
    public HashMap<String, String> getAbbrevateWords(){return this.abbrevateWords;}
    
    public int getNumOfSent(){return this.numOfSents;}
    public int getNumOfAllOrigWrds(){return this.numOfAllOrigWrds;}
    public int getNumOfAllProcWrds(){return this.numOfAllProcWrds;}
    public int getNumOfUniqTerms(){return this.numOfUniqTrms;}
    
}
