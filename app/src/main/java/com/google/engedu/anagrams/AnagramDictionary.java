/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

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

    private ArrayList<String> wordList= new ArrayList<>();
    private HashSet<String> wordSet = new HashSet<>();
    private HashMap<String,ArrayList<String>> lettersToWord = new HashMap<>();
    private String baseWord;

    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordList.add(word);
            wordSet.add(word);
            String sortedWord = sortLetters(word);
            if (lettersToWord.containsKey(sortedWord)){
                lettersToWord.get(sortedWord).add(word);
            }else{
                ArrayList<String> newList = new ArrayList<>();
                newList.add(word);
                lettersToWord.put(sortedWord, newList);
            }
        }
    }

    public boolean isGoodWord(String word, String base) {
        return wordSet.contains(word) && !word.contains(base);
    }
//todo: add to getAnagramsWithOneMoreLetter result once updated to use hashsets
    public List<String> getAnagrams(String targetWord) {
        String sortedWord = sortLetters(targetWord);
        ArrayList<String> result = new ArrayList<String>(lettersToWord.get(sortedWord));
        result.remove(targetWord);
        return result;
    }

    private String sortLetters(String word){
        char[] wordArray = word.toCharArray();
        Arrays.sort(wordArray);
        return new String(wordArray);
    }

    public List<String> getAnagramsWithOneMoreLetter(String word) {
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        ArrayList<String> result = new ArrayList<String>();
        for (char letter: alphabet){
            String wordPlus = sortLetters(word+letter);
            if (lettersToWord.containsKey(wordPlus)){
                for (String CandidateWord: lettersToWord.get(wordPlus)){
                    if (isGoodWord(CandidateWord, baseWord)){
                        result.add(CandidateWord);
                    }
                }
            }
        }
        result.addAll(getAnagrams(word));
        return result;
    }

    public String pickGoodStarterWord() {
        baseWord ="top";
        return "top";
        /*
        int len = wordList.size();
        while(true){
            String chosenWord = wordList.get(random.nextInt(len));
            if(lettersToWord.get(sortLetters(chosenWord)).size()>MIN_NUM_ANAGRAMS && chosenWord.length()>=DEFAULT_WORD_LENGTH && chosenWord.length()<= MAX_WORD_LENGTH){
                baseWord = ChosenWord
                return chosenWord;
            }
        }
        */
    }
}
