package com.google.engedu.anagrams;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    ArrayList<String> wordList=new ArrayList();
    HashSet<String> wordSet=new HashSet<>();
    HashMap<String,ArrayList<String>> lettersToWord=new HashMap<>();
    HashMap<Integer,ArrayList<String>> sizeToWord=new HashMap<>();
    int Default_Length=DEFAULT_WORD_LENGTH;

    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordList.add(word);
            wordSet.add(word);
            String sortWord = sortLetters(word);
            if ((lettersToWord.containsKey(sortWord))) {
                ArrayList<String> sd=lettersToWord.get(sortWord);
                sd.add(word);
                lettersToWord.put(sortWord,sd);
            } else {
                ArrayList<String> sd=new ArrayList();
                sd.add(word);
                lettersToWord.put(sortWord,sd);
            }
            if ((sizeToWord.containsKey(word.length()))) {
                ArrayList<String> sd= sizeToWord.get(word.length());
                sd.add(word);
                sizeToWord.put(word.length(),sd);
            } else {
                ArrayList<String> sd=new ArrayList();
                sd.add(word);
                sizeToWord.put(word.length(),sd);
            }
        }
    }

    public boolean isGoodWord(String word, String base) {
        if(wordSet.contains(word) && !word.contains(base))
            return true;
        else
            return false;
    }

    public ArrayList<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();
        String sortWord=sortLetters(targetWord);
       /* for(String y:wordList)
           if(sortWord.equals(sortLetters(y)))
               result.add(y);*/
        if(lettersToWord.containsKey(sortWord))
            result= lettersToWord.get(sortWord);
        result.remove(targetWord);
        return result;
    }

    public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> temp;
        ArrayList<String> result = new ArrayList<String>();
        for(char alpha='a';alpha<='z';alpha++)
        {
            String target=word+alpha;
            String sortWord=sortLetters(target);
            if(lettersToWord.containsKey(sortWord)){
                temp=lettersToWord.get(sortWord);
                for(int i=0;i<temp.size();i++)
                    if(!(temp.get(i).contains(word)))
                        result.add(temp.get(i));}
        }
        return result;
    }

    public String pickGoodStarterWord() {
      /* while(true) {
           Random r = new Random();
           String f = null;
           f= wordList.get(r.nextInt(wordList.size()));
           if ((getAnagramsWithOneMoreLetter(f).size())>MIN_NUM_ANAGRAMS)
               return f;

       }*/
        String d="";
        while(true)
        {
            ArrayList<String> temp=sizeToWord.get(Default_Length);
            //Log.i("AAA1",temp.toString());
            d=temp.get((random.nextInt(temp.size())));
            //Log.i("AAA2",d);
            List<String> arr=getAnagramsWithOneMoreLetter(d);
            // Log.i("AAA3",arr.toString());
            if(arr.size()>=MIN_NUM_ANAGRAMS)
                break;
        }
        Default_Length++;
        return d;
    }

    public String sortLetters(String targetWord)
    {
        char[] ch=targetWord.toCharArray();
        Arrays.sort(ch);
        String sortWord=new String(ch);
        return sortWord;
    }
}
