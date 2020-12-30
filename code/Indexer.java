//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Name: Vuochlang Chang                                                                                                            //
// Class: CSE223        Spring 2020                                                                                                 //
// Date: 05/04/2020                                                                                                                 //
// Assignment: PA2 - Indexer Processor                                                                                              //
// This code will  implement a new Java class named Indexer. It allows user to read the contents of a textfile and create an index  //
// using HashMap and Linkedlist to store and  show the position(s) of each word in the file.                                        //
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

import java.util.HashMap;
import java.util.Scanner;
import java.util.LinkedList;
import java.io.File;
import java.io.FileNotFoundException;

public class Indexer{
  boolean isProcess=false;//this will be using in most of the methods to check if the the textfile has processed successfully
  LinkedList<Integer> ll; //initialize a LinkedList of Integer named "ll"
  private HashMap<String,LinkedList<Integer>> index;//initialize a private HashMap with key of String and value of LinkedList<Integer>

  public boolean processFile(String file)//this will use to process the given file
  {       int placeCounter=0;
    String word="";
    File inputFile=new File(file);//open the given file
    Scanner scanInput;

    try{
      scanInput=new Scanner(inputFile);
    }catch(FileNotFoundException exception){
      return false;//this means the given file is not processed and will exit the program
    }

    index=new HashMap<String,LinkedList<Integer>>();//create a HashMap named "index" to store the words from the textfile
    isProcess=true; //change the flag to true when the file is processed
    while(scanInput.hasNext())//keep reading the input word by word from the file
    {       String newWord=cleanupWord(scanInput.next());//call the cleanupWord function to eliminate unwanted character of the word
      if(newWord.length()!=0) //if the "newWord" is not an empty string
      {       ++placeCounter;
        addReference(newWord,placeCounter);//call addReference to add "newWord" "placeCounter" to the map accordingly
      }
    }
    scanInput.close();//close the scanner when reached the end of the file
    return true;//or just return "isProcess" which is true
  }

  public int numberOfInstances(String word)//this function will return the number of times that a word appeared in the file
  { if(isProcess==false) return -1;//return -1 when the file is not processed
    if(index.get(word)==null) return 0;//return -1 when there's no such "word" stored in the HashMap
    LinkedList listOfWord=index.get(word);//get the linkedlist associate with the word
    return(listOfWord.size());//return the linkedlist size == how many times it appeared
  }

  public int locationOf(String word, int instanceNum)//this will tell the location of the "word" with the n^th time
  { if(isProcess==false) return -1;
    if(index.get(word)==null) return -1;
    if(instanceNum<0 || instanceNum>=numberOfInstances(word)) return -1;//if the instanceNum is not in the valid range
    LinkedList<Integer> returnList=index.get(word);//get the linkelist connected with the "word" in the HashMap
    Integer number=returnList.get(instanceNum);//get the Integer stored in the LinkedList in the instanceNum index/place
    return(number.intValue());//return the int value of the Integer
  }

  public int numberOfWords()//this function will return the number of the words in the HashMap
  { if(isProcess==false) return -1;
    return(index.size());//return the size of HashMap == number of words stored
  }

  //DONT NEED THIS ONE,this is too much works and not useful
/*      public String toString()
        {       if(isProcess==false) return null;
                int size=index.size();//get the size of HashMap
                String result="";//create empty string
                result+="{";
                result+=(index.entrySet()+"}");//combine the "{" & "}" with the entrySet of the HashMap == combine input into a string
                int length=result.length();//get the length of the string
                StringBuffer newResult=new StringBuffer(result);//change the string to StringBuffer
                newResult.deleteCharAt(1);//delete the "[" at the beginning of the entrySet
                newResult.deleteCharAt(length-3);//delete the "]" at the end of the entrySet
                String lastResult=new String(newResult);//copy the updated StringBuffer into a new string
                return lastResult;
        }
*/
  public String toString()
  { if(isProcess==false) return null;//if the file has not been processed then return null
    return (index.toString());//else print the element in hashMap
  }

  private String cleanupWord(String word)
  {       String newWord="";//create a new empty string
    for(int i=0; i<word.length(); i++)//loop to go through each character in the word
    {       char letter=word.charAt(i);//get a chracter
      if(Character.isLetter(letter))//check if the chracter is a letter (not a symbol and not a number)
      {       newWord+=Character.toUpperCase(letter);//change the character to uppcase and add to the string "newWord"
      }
    }
    return newWord;
  }

  private void addReference(String word, int location)
  { if(isProcess==false) return;
    LinkedList<Integer> list=index.get(word);//get the linkedlist associated with the "word" in the HashMap
    if(list==null)//if the list is null == no such "word" stored in the map
    {       ll=new LinkedList<Integer>();//creating a new linkedlist of integer
      index.put(word,ll);//add the word and the new linkedlist into the HashMap "index"
      ll.push(location);//add number/location into the linkedlist
      return;
    }
    list.addLast(location);//this will run when the "word" already stored in the HashMap and just add the (new)location to the end of the list
    return;
  }

}
