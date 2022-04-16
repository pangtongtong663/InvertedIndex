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
    * ```List<AbstractTermTuple> tuples···：文档解析完后得到的所有term的三元组
* AbstractPosting：其具体子类实例为倒排索引里的一个Posting，其中包含三个数据成员：
    * ```docId```：单词出现的文档Id
    * ```freq```：单词出现频率
    * ```positions```：单词出现位置列表
* AbstractPostingList：

