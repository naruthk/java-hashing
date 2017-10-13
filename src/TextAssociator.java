/* Naruth Kongurai (1429760)
 * CSE 373 AB
 * TA: Raquel Van Hofwegen
 * 02/10/17
 */ 

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/* CSE 373 Starter Code
 * @Author Kevin Quinn
 * 
 * TextAssociator represents a collection of associations between words.
 * See write-up for implementation details and hints
 * 
 */
public class TextAssociator {
	private WordInfoSeparateChain[] table;
	private final int DEFAULT_CAPACITY = 31;
	private int size;

	/* INNER CLASS
	 * Represents a separate chain in your implementation of your hashing
	 * A WordInfoSeparateChain is a list of WordInfo objects that have all
	 * been hashed to the same index of the TextAssociator
	 */
	private class WordInfoSeparateChain {
		private List<WordInfo> chain;
		
		/* Creates an empty WordInfoSeparateChain without any WordInfo
		 */
		public WordInfoSeparateChain() {
			this.chain = new ArrayList<WordInfo>();
		}
		
		/* Adds a WordInfo object to the SeparateCahin
		 * Returns true if the WordInfo was successfully added, false otherwise
		 */
		public boolean add(WordInfo wi) {
		        if (!this.chain.contains(wi)) {
		                this.chain.add(wi);
		                return true;
		        }
			return false;
		}
		
		/* Removes the given WordInfo object from the separate chain
		 * Returns true if the WordInfo was successfully removed, false otherwise
		 */
		public boolean remove(WordInfo wi) {
		        if (this.chain.contains(wi)) {
		                this.chain.remove(wi);
		                return true;
		        }
			return false;
		}
		
		// Returns the size of this separate chain
		public int size() {
			return chain.size();
		}
		
		// Returns the String representation of this separate chain
		@Override
                public String toString() {
			return chain.toString();
		}
		
		// Returns the list of WordInfo objects in this chain
		public List<WordInfo> getElements() {
			return chain;
		}
		
	}
	
	
	/* Creates a new TextAssociator without any associations 
	 */
	public TextAssociator() {
	    this.table = new WordInfoSeparateChain[DEFAULT_CAPACITY];
	    this.size = 0;
	}


	/* Adds a word with no associations to the TextAssociator 
	 * Returns False if this word is already contained in your TextAssociator ,
	 * Returns True if this word is successfully added
	 */
	public boolean addNewWord(String word) {
	    int hashValue = this.getHashValue(word);  
	    if (this.isEmptyTable(hashValue)) {
	        this.table[hashValue] = new WordInfoSeparateChain();
	    } 
	    for (WordInfo wordObject : this.table[hashValue].getElements()) {
	        if (wordObject.getWord().equalsIgnoreCase(word)) {
	            return false;
	        }  
	    }    
	    WordInfo wordItem = new WordInfo(word);
	    this.table[hashValue].add(wordItem);      
	    if (this.size / this.table.length >= 1) {
	        this.resize();
	        this.size++; 
	    }     

	    return true;
	}


	/* Adds an association between the given words. Returns true if association correctly added, 
	 * returns false if first parameter does not already exist in the TextAssociator or if 
	 * the association between the two words already exists
	 */
	public boolean addAssociation(String word, String association) {
	    int hashValue = this.getHashValue(word);
	    if (this.isEmptyTable(hashValue)) {
	        return false;
	    } 
	    for (WordInfo wordObject : this.table[hashValue].getElements()) {
	        if (wordObject.getWord().equalsIgnoreCase(word)) {
	            return wordObject.addAssociation(association);
	        }
	    }
	    return false;
	}


	/* Remove the given word from the TextAssociator, returns false if word 
	 * was not contained, returns true if the word was successfully removed.
	 * Note that only a source word can be removed by this method, not an association.
	 */
	public boolean remove(String word) {
	    int hashValue = this.getHashValue(word); 
	    if (this.isEmptyTable(hashValue)) {
	        return false;
	    } 
	    for (WordInfo wordObject : this.table[hashValue].getElements()) {
	        if (wordObject.getWord().equalsIgnoreCase(word)) {
	            this.table[hashValue].remove(wordObject);
	            this.size--;
	            return true;
	        }
	    }
	    return false;
	}


	/* Returns a set of all the words associated with the given String  
	 * Returns null if the given String does not exist in the TextAssociator
	 */
	public Set<String> getAssociations(String word) {
	    int hashValue = this.getHashValue(word);
	    if (this.isEmptyTable(hashValue)) {
	        return null;
	    } 
	    for (WordInfo wordObject : this.table[hashValue].getElements()) {
	        if (wordObject.getWord().equalsIgnoreCase(word)) {
	            return wordObject.getAssociations();
	        }
	    }
	    return null;
	}
	
	
	/* Prints the current associations between words being stored
	 * to System.out
	 */
	public void prettyPrint() {
		System.out.println("Current number of elements : " + size);
		System.out.println("Current table size: " + table.length);
		
		//Walk through every possible index in the table
		for (int i = 0; i < table.length; i++) {
			if (table[i] != null) {
				WordInfoSeparateChain bucket = table[i];
				
				//For each separate chain, grab each individual WordInfo
				for (WordInfo curr : bucket.getElements()) {
					System.out.println("\tin table index, " + i + ": " + curr);
				}
			}
		}
		System.out.println();
	}

	/* 
	 * Checks if the table's values at the given index is empty
	 */
	private boolean isEmptyTable(int hash) {
	    return this.table[hash] == null;
	}
	
	/*
	 * Returns a positive hash value for a given word.
	 * Assumes that the word is not empty.
	 */
        private int getHashValue(String word) {
            return new WordInfo(word).hashCode() % this.table.length;
        }

        /*
         * Resizes the table to become bigger while keeping existing values
         */
        private void resize() {
            WordInfoSeparateChain[] tempChain = 
                    new WordInfoSeparateChain[2 * this.table.length];
            for (WordInfoSeparateChain theChain : this.table) {
                if (theChain != null) {
                    for (WordInfo wordObject : theChain.getElements()) {
                        int hashValue = 
                                this.getHashValue(wordObject.getWord());
                        if (tempChain[hashValue] == null) {
                            tempChain[hashValue] = new WordInfoSeparateChain();
                        }
                        tempChain[hashValue].add(wordObject);
                    }
                }
            }
            this.table = tempChain;
        }
}