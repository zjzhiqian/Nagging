package org.apache.lucene.search.highlight;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;

/**
 * 自定义的Highlighter,避免了使用同义词,拼音分词时高亮的报错
 * @author huangzhiqian
 *
 */
public class CusHzqHighlighter extends Highlighter{

	public CusHzqHighlighter(Formatter formatter, Scorer fragmentScorer) {
		super(formatter, fragmentScorer);
	}
	
	@Override
	protected TextFragment[] getBestTextFragments(TokenStream tokenStream, String text, boolean mergeContiguousFragments, int maxNumFragments) throws IOException, InvalidTokenOffsetsException {
		ArrayList<TextFragment> docFrags = new ArrayList<>();
		StringBuilder newText = new StringBuilder();

		OffsetAttribute offsetAtt = tokenStream.addAttribute(OffsetAttribute.class);
		TextFragment currentFrag = new TextFragment(newText, newText.length(), docFrags.size());

		if (fragmentScorer instanceof QueryScorer) {
			((QueryScorer) fragmentScorer).setMaxDocCharsToAnalyze(maxDocCharsToAnalyze);
		}

		TokenStream newStream = fragmentScorer.init(tokenStream);
		if (newStream != null) {
			tokenStream = newStream;
		}
		fragmentScorer.startFragment(currentFrag);
		docFrags.add(currentFrag);

		FragmentQueue fragQueue = new FragmentQueue(maxNumFragments);

		try {

			String tokenText;
			int startOffset;
			int endOffset;
			int lastEndOffset = 0;
			textFragmenter.start(text, tokenStream);

			TokenGroup tokenGroup = new TokenGroup(tokenStream);

			tokenStream.reset();
			for (boolean next = tokenStream.incrementToken(); next && (offsetAtt.startOffset() < maxDocCharsToAnalyze); next = tokenStream.incrementToken()) {
//				if ((offsetAtt.endOffset() > text.length()) || (offsetAtt.startOffset() > text.length())) {
//					throw new InvalidTokenOffsetsException("Token " + termAtt.toString() + " exceeds length of provided text sized " + text.length());
//				}
				if ((tokenGroup.getNumTokens() > 0) && (tokenGroup.isDistinct())) {
					// the current token is distinct from previous tokens -
					// markup the cached token group info
					startOffset = tokenGroup.getStartOffset();
					endOffset = tokenGroup.getEndOffset();
					tokenText = text.substring(startOffset, endOffset);
					String markedUpText = formatter.highlightTerm(encoder.encodeText(tokenText), tokenGroup);
					// store any whitespace etc from between this and last group
					if (startOffset > lastEndOffset)
						newText.append(encoder.encodeText(text.substring(lastEndOffset, startOffset)));
					newText.append(markedUpText);
					lastEndOffset = Math.max(endOffset, lastEndOffset);
					tokenGroup.clear();

					// check if current token marks the start of a new fragment
					if (textFragmenter.isNewFragment()) {
						currentFrag.setScore(fragmentScorer.getFragmentScore());
						// record stats for a new fragment
						currentFrag.textEndPos = newText.length();
						currentFrag = new TextFragment(newText, newText.length(), docFrags.size());
						fragmentScorer.startFragment(currentFrag);
						docFrags.add(currentFrag);
					}
				}

				tokenGroup.addToken(fragmentScorer.getTokenScore());

				// if(lastEndOffset>maxDocBytesToAnalyze)
				// {
				// break;
				// }
			}
			currentFrag.setScore(fragmentScorer.getFragmentScore());

			if (tokenGroup.getNumTokens() > 0) {
				// flush the accumulated text (same code as in above loop)
				startOffset = tokenGroup.getStartOffset();
				endOffset = tokenGroup.getEndOffset();
				tokenText="";
				if(endOffset>text.length()){
					tokenText=text.substring(startOffset, text.length());
				}else{
					tokenText = text.substring(startOffset, endOffset);
				}
				
				String markedUpText = formatter.highlightTerm(encoder.encodeText(tokenText), tokenGroup);
				// store any whitespace etc from between this and last group
				if (startOffset > lastEndOffset)
					newText.append(encoder.encodeText(text.substring(lastEndOffset, startOffset)));
				newText.append(markedUpText);
				lastEndOffset = Math.max(lastEndOffset, endOffset);
			}

			// Test what remains of the original text beyond the point where we
			// stopped analyzing
			if (
			// if there is text beyond the last token considered..
			(lastEndOffset < text.length()) &&
			// and that text is not too large...
					(text.length() <= maxDocCharsToAnalyze)) {
				// append it to the last fragment
				newText.append(encoder.encodeText(text.substring(lastEndOffset)));
			}

			currentFrag.textEndPos = newText.length();

			// sort the most relevant sections of the text
			for (Iterator<TextFragment> i = docFrags.iterator(); i.hasNext();) {
				currentFrag = i.next();

				// If you are running with a version of Lucene before 11th Sept
				// 03
				// you do not have PriorityQueue.insert() - so uncomment the
				// code below
				/*
				 * if (currentFrag.getScore() >= minScore) {
				 * fragQueue.put(currentFrag); if (fragQueue.size() >
				 * maxNumFragments) { // if hit queue overfull fragQueue.pop();
				 * // remove lowest in hit queue minScore = ((TextFragment)
				 * fragQueue.top()).getScore(); // reset minScore }
				 * 
				 * 
				 * }
				 */
				// The above code caused a problem as a result of Christoph
				// Goller's 11th Sept 03
				// fix to PriorityQueue. The correct method to use here is the
				// new "insert" method
				// USE ABOVE CODE IF THIS DOES NOT COMPILE!
				fragQueue.insertWithOverflow(currentFrag);
			}

			// return the most relevant fragments
			TextFragment frag[] = new TextFragment[fragQueue.size()];
			for (int i = frag.length - 1; i >= 0; i--) {
				frag[i] = fragQueue.pop();
			}

			// merge any contiguous fragments to improve readability
			if (mergeContiguousFragments) {
				mergeContiguousFragments(frag);
				ArrayList<TextFragment> fragTexts = new ArrayList<>();
				for (int i = 0; i < frag.length; i++) {
					if ((frag[i] != null) && (frag[i].getScore() > 0)) {
						fragTexts.add(frag[i]);
					}
				}
				frag = fragTexts.toArray(new TextFragment[0]);
			}

			return frag;

		} finally {
			if (tokenStream != null) {
				try {
					tokenStream.end();
					tokenStream.close();
				} catch (Exception e) {
				}
			}
		}
	}

	/**
	 * Improves readability of a score-sorted list of TextFragments by merging
	 * any fragments that were contiguous in the original text into one larger
	 * fragment with the correct order. This will leave a "null" in the array
	 * entry for the lesser scored fragment.
	 *
	 * @param frag
	 *            An array of document fragments in descending score
	 */
	private void mergeContiguousFragments(TextFragment[] frag) {
		boolean mergingStillBeingDone;
		if (frag.length > 1)
			do {
				mergingStillBeingDone = false; // initialise loop control flag
				// for each fragment, scan other frags looking for contiguous
				// blocks
				for (int i = 0; i < frag.length; i++) {
					if (frag[i] == null) {
						continue;
					}
					// merge any contiguous blocks
					for (int x = 0; x < frag.length; x++) {
						if (frag[x] == null) {
							continue;
						}
						if (frag[i] == null) {
							break;
						}
						TextFragment frag1 = null;
						TextFragment frag2 = null;
						int frag1Num = 0;
						int frag2Num = 0;
						int bestScoringFragNum;
						int worstScoringFragNum;
						// if blocks are contiguous....
						if (frag[i].follows(frag[x])) {
							frag1 = frag[x];
							frag1Num = x;
							frag2 = frag[i];
							frag2Num = i;
						} else if (frag[x].follows(frag[i])) {
							frag1 = frag[i];
							frag1Num = i;
							frag2 = frag[x];
							frag2Num = x;
						}
						// merging required..
						if (frag1 != null) {
							if (frag1.getScore() > frag2.getScore()) {
								bestScoringFragNum = frag1Num;
								worstScoringFragNum = frag2Num;
							} else {
								bestScoringFragNum = frag2Num;
								worstScoringFragNum = frag1Num;
							}
							frag1.merge(frag2);
							frag[worstScoringFragNum] = null;
							mergingStillBeingDone = true;
							frag[bestScoringFragNum] = frag1;
						}
					}
				}
			} while (mergingStillBeingDone);
	}
	
	
	
	
	
}
