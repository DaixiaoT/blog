/**
 *  This file is part of FNLP (formerly FudanNLP).
 *
 *  FNLP is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  FNLP is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with FudanNLP.  If not, see <http://www.gnu.org/licenses/>.
 *
 *  Copyright 2009-2014 www.fnlp.org. All rights reserved.
 */

package org.fnlp;


import org.fnlp.ml.types.Dictionary;
import org.fnlp.nlp.cn.tag.CWSTagger;
import org.fnlp.nlp.cn.tag.POSTagger;

/**
 * 词性标注使用示例
 * @author xpqiu
 *
 */
public class PartsOfSpeechTag {

    static POSTagger tag;

    /**
     * 主程序
     * @param args
     * @throws Exception
     * @throws
     */
    public static void main(String[] args) throws Exception {


        CWSTagger cws = new CWSTagger("models/seg.m");
        tag = new POSTagger(cws,"models/pos.m");

        CWSTagger cws2 = new CWSTagger("models/seg.m", new Dictionary("models/dict.txt"));

        //bool值指定该dict是否用于cws分词（分词和词性可以使用不同的词典）
        tag = new POSTagger(cws2, "models/pos.m"
                , new Dictionary("models/dict.txt"), true);//true就替换了之前的dict.txt



        String str=tag.tag("我今天很开心");
        String s4 = tag.tagFile("example-data/data-tag.txt");
//        System.out.println("\n处理文件：");
        System.out.println(s4);
    }
    public String PartsOfSpeech(String str) throws Exception {
//        CWSTagger cws = new CWSTagger("models/seg.m");
//        tag = new POSTagger(cws,"models/pos.m");

        CWSTagger cws2 = new CWSTagger("models/seg.m", new Dictionary("models/dict.txt"));

        //bool值指定该dict是否用于cws分词（分词和词性可以使用不同的词典）
        tag = new POSTagger(cws2, "models/pos.m"
                , new Dictionary("models/dict.txt"), true);//true就替换了之前的dict.txt

        return tag.tag(str);
    }

}