package com.example.voicecommand;

import java.util.List; /**
 * Created by lisongting on 2017/6/28.
 */

/**
  从服务端获得的JSON样本为：
 {"sn":1,"ls":false,"bg":0,"ed":0,"ws":[{"bg":0,"cw":[{"sc":0.00,"w":"开始"}]},{"bg":0,"cw":[{"sc":0.00,"w":"播放"}]}]}

 实际上是（上面的是简写的，真不知道为啥要搞这种简写）：
 {"sentence":2,"lastSentence":true,"begin":0,"end":0,"words":[{"begin":0,"chineseWords":[{"score":0,"word":"准备"}]}
 ,{"begin":0,"chineseWords":[{"score":0,"word":"开始"}]}]}


 */
public class SpeechRecogResult2 {


    /**
     * sentence : 2
     * lastSentence : true
     * begin : 0
     * end : 0
     * words : [{"begin":0,"chineseWords":[{"score":0,"word":"准备"}]},{"begin":0,"chineseWords":[{"score":0,"word":"开始"}]}]
     */

    private int sentence;
    private boolean lastSentence;
    private int begin;
    private int end;
    private List<WordsBean> words;

    public int getSentence() {
        return sentence;
    }

    public void setSentence(int sentence) {
        this.sentence = sentence;
    }

    public boolean isLastSentence() {
        return lastSentence;
    }

    public void setLastSentence(boolean lastSentence) {
        this.lastSentence = lastSentence;
    }

    public int getBegin() {
        return begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public List<WordsBean> getWords() {
        return words;
    }

    public void setWords(List<WordsBean> words) {
        this.words = words;
    }

    public static class WordsBean {
        /**
         * begin : 0
         * chineseWords : [{"score":0,"word":"准备"}]
         */

        private int begin;
        private List<ChineseWordsBean> chineseWords;

        public int getBegin() {
            return begin;
        }

        public void setBegin(int begin) {
            this.begin = begin;
        }

        public List<ChineseWordsBean> getChineseWords() {
            return chineseWords;
        }

        public void setChineseWords(List<ChineseWordsBean> chineseWords) {
            this.chineseWords = chineseWords;
        }

        public static class ChineseWordsBean {
            /**
             * score : 0
             * word : 准备
             */

            private int score;
            private String word;

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }

            public String getWord() {
                return word;
            }

            public void setWord(String word) {
                this.word = word;
            }
        }
    }

    @Override
    public String toString() {
        return "SpeechRecogResult2{" +
                "sentence=" + sentence +
                ", lastSentence=" + lastSentence +
                ", begin=" + begin +
                ", end=" + end +
                ", words=" + words +
                '}';
    }
}
