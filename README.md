# InvertedIndex
## Background
本项目是用Java实现的基于倒排索引的搜索引擎，在实现过程中采用了装饰者模式、迭代器模式以及工厂方法模式。
## Structure

## Compositions
* AbstractTerm：其具体子类实例为一个单词term
* AbstractTermTuple：其具体子类实例为和单词term相关的三元组，包括三个数据成员：
    * ```AbstractTerm```：代表当前解析得到的一个term
    * ```final int freq = 1```：因为解析得到了一个term，该term出现了一次，因此其频率为1
    * ```int curPos```：该term的位置（注意位置序号是以term为单位不是以字符为单位）
* AbstractDocument：其具体子类实例为，解析完一个文档后文档在内存中的表示。因为当解析完文档后，文档需要一种中间类型的数据结构表示，以方便后面倒排索引的建立。它包括三个数据成员：
    * ```int docId```：文档Id
    * ```String docPath```：文档绝对路径
    * ```List<AbstractTermTuple> tuples```：文档解析完后得到的所有term的三元组
* AbstractPosting：其具体子类实例为倒排索引里的一个Posting，其中包含三个数据成员：
    * ```docId```：单词出现的文档Id
    * ```freq```：单词出现频率
    * ```List<Integer> positions```：单词出现位置列表
* AbstractPostingList：其具体子类实例为倒排索引里一个单词对应的PostingList，其中包含一个List<AbstractPosting>类型的数据成员存放这个PostingList包含的多个Posting
* AbstractIndex：其具体子类实例为内存中的整个倒排索引结构，其中包含了二个数据成员：
    * ```Map<Integer, String> docIdToDocPathMapping```：保存了文档Id和文档绝对路径之间的映射关系（开始构建索引时我们只有每个文档的绝对路径，因此内部需要维护一个文档Id的计数计数器，每将一个文档加入到倒排索引，文档Id计数器加1）
    * ```Map<AbstractTerm, AbstractPostingList> termToPostingListMapping```：保存了每个单词与其对应的PostingList的映射关系。需要特别说明的是这里没有必要用专门的数据结构来存放字典内容，我们直接通过termToPostingListMapping.keySet( )方法就可以得到字典。
* AbstractDocumentBuilder：里面包含一个数据成员，定义了抽象方法，分别为：
    * ```public abstract AbstractDocumentBuilder docBuilder();```
    * ```public abstract AbstractIndex buildIndex(String rootDirectory);```
* AbstractIndexBuilder：里面包含一个数据成员，定义了抽象方法，分别为：
    * ```public abstract AbstractDocumentBuilder docBuilder();```
    * ```public abstract AbstractIndex buildIndex(String rootDirectory);```
    * 注意：构建AbstractIndexBuilder子类对象时必须传入先构造好的AbstractDocumentBuilder子类对象
* AbstractTermTupleStream：读取文本构建单词三元组的抽象父类流，定义了两个抽象方法：
    * ```public abstract AbstractTermTuple next();``` 从流中获得下一个三元组
    * ```public abstract void close();``` 关闭流
* AbstractTermTupleScanner：AbstractTermTupleStream的具体实现类，负责读取文本，构建单词三元组
* AbstractTermTupleFilter：继承AbstractTermTupleStream类，同时作为过滤器的抽象类
* LengthTermTupleFilter： 单词长度过滤器，可以基于```public static int TERM_FILTER_MAXLENGTH```与```public static int TERM_FILTER_MINLENGTH```过滤单词
* StopWordTermTupleFilter： 基于停用词表来过滤单词
* PatternTermTupleFilter：基于正则表达式来过滤单词，在本项目中默认配置为```public static String TERM_FILTER_PATTERN = "[a-zA-Z]+";```
* AbstractHit：是搜索命中结果的抽象类，定义的数据成员有：
    * ```int docId```：文档Id
    * ```String docPath```：文档绝对路径
    * ```String content```：文档内容
    * ```double score = 1.0```: 命中结果得分，默认值为1.0，得分通过Sort接口计算
    * ```Map<AbstractTerm, AbstractPosting> termPostingMapping```：命中的单词和对应的Posting键值对，对计算文档得分有用，对于一个查询命中结果（一个文档），一个term对应的是Posting而不是PostingList
* AbstractIndexSearcher：进行全文检索的抽象类，定义了如下成员及方法
    * ```public abstract void open(String indexFile);```：从指定索引文件打开索引，加载到index对象里
    * ```public abstract AbstractHit[] search(AbstractTerm queryTerm, Sort sorter);```：根据单个检索词进行搜索
    * ```public abstract AbstractHit[] search(AbstractTerm queryTerm1, AbstractTerm queryTerm2, Sort sorter, LogicalCombination combine);```：根据二个检索词进行搜索
    * ```public static enum LogicalCombination```：多个检索词的逻辑组合
* Sort：定义了对搜索结果排序的接口，包含两个接口方法：
    * ```void sort(List<AbstractHit> hits);```：对命中结果集合根据文档得分排序
    * ```double score(AbstractHit hit);```：计算命中文档的得分, 作为命中结果排序的依据
## Design Pattern
### 装饰者模式
