
# springboot集成Neo4j数据库

最新版。

## springboot 

由于Neo4j本来是由Java开发的，应该对Java支持得更好。但是当我在用的过程中才发现，理想和现实的差距不是一点半点。

首先Neo4j针对springboot的给的接口基本上每年都在变，最初我在网上搜到的例子已经完全行不通，基本被淘汰了。

只能硬着头皮去读Neo4j给的英文文档，也并不是读过一遍就能懂。只会发现新的用法跟旧的用法有什么区别。

借了好几本关于Neo4j的书，但都没有全部看完，毕竟书上的，计算机方面的知识迭代得还没网上的快。

最后还是得自己慢慢摸索springboot操作neo4j的接口。虽然没有全部掌握，增删改查也就够了。

### 基本测试代码

```java
//通过构造方法创建节点
    @Test
    public void createUser() {
//        Person person = new Person("代晓涛",1999);
//        personRepository.save(person);
        Movie movie = new Movie("红楼梦", 123, "nice Movie");
        movieRepository.save(movie);
    }

    //通过节点Id删除节点
    @Test
    public void deleteUser() {
        personRepository.deleteById(2440L);
    }

    //通过查找名字删除节点以及节点相邻的关系
    @Test
    public void delete() {
//        personRepository.deleteRelationAndPersonByName("代晓涛");
        personRepository.SaveNode("嘉兴学院");
    }


    //多个不存在的节点创建关系
    @Test
    public void createRelation() {
        Person person = new Person("六小龄童", 1988);
        Person person1 = new Person("猪八戒", 1988);
        Movie movie = new Movie("西游记", 5, "这部电视剧也");
        List<Movie> list = new ArrayList<>();
        list.add(movie);
        movieRepository.save(movie);
        person.setDirected(list);
        person1.setDirected(list);
        personRepository.save(person);
        personRepository.save(person1);
    }

    //已存在的两个节点创建关系,失败了，好像只能通过单独写语句创建
    @Test
    public void createRelationBy() {
//        Person person = personRepository.getPersonByName("猪八戒");
//        Movie movie = movieRepository.getMovieByTitle("我和我的祖国");
//        List<Movie>list = new ArrayList<>();
//        list.add(movie);
//        person.setActedIn(list);

        personRepository.createDIRECTEDRelationWithExistPerson("代涛涛", "喜欢", "我和我的祖国");//这个可以
    }

    //通过关系名删除两个节点之间的关系
    @Test
    public void deleteByRelationName() {
        personRepository.deleteDIRECTEDRelationWithExistPerson("六小龄童", "DDDDDD", "我和我的祖国");//这个可以

    }

    //查询一个节点所有关系
    //刚开始失败的原因一直是Stack Flow Over
    //后来才发现原来是对象A中的属性中包含对象B，而对象B中的属性又包含对象A，造成无限循环执行对象的toString()函数，才会栈溢出
    @Test
    public void findRelationByPerson() {
//        System.out.println(personRepository.getPersonsWhoActAndDirect());
        List<Person> list = new ArrayList<>();
        list = personRepository.findRelationByPerson("Joney");
//        System.out.println(list);
        for (Person person : list) {
            System.out.println(person.toStringq());
        }
    }
    //至此终于实现了数据的增删改查

    @Test
    public void saveNode(){
        personRepository.SaveNode("daixiaotaoaaa");
    }
```

```java
@Repository
public interface PersonRepository extends Neo4jRepository<Person, Long> {

    Person getPersonByName(String name);

    Iterable<Person> findPersonByNameLike(String name);

    @Query("MATCH (am:Movie)<-[ai:ACTED_IN]-(p:Person)-[d:DIRECTED]->(dm:Movie) return p, collect(ai), collect(d), collect(am), collect(dm)")
    List<Person> getPersonsWhoActAndDirect();

    //@Query("Match (n:User{name:{0}})-[r]-() delete n,r")
    @Query("Match (n:Person{name:{0}})-[r]->() delete n,r")
    void deleteRelationAndPersonByName(String name);

    //MATCH (a:LabeofNode1), (b:LabeofNode2)
    //WHERE a.name = "nameofnode1" AND b.name = " nameofnode2"
    //CREATE (a)-[: Relation]->(b)
    //Match (a:Person{name:"六小龄童"}),(b:Movie{title:"长津湖"}) create (a)-[r:DIRECTED]->(b)
    @Query("Match (a:Person{name:{0}}),(b:Movie{title:{2}}) merge (a)-[r:DIRECTED{name:{1}}]->(b)")
    void createDIRECTEDRelationWithExistPerson(String from,String relation,String to);
    @Query("Match (a:Person{name:{0}})-[r:DIRECTED{name:{1}}]->(b:Movie{title:{2}}) delete r")
    void deleteDIRECTEDRelationWithExistPerson(String from,String relation,String to);


    //查询一个节点所有关系
    //    @Query("Match (a:Person{name:{0}})-[r]->(m) return a,r,m")//失败
    @Query("Match (a:Person{name:{0}})-[r]->(m) return a,r,m")
    List<Person> findRelationByPerson(String name);

    @Query("Match (a:Person{name:{0}})-[r]->(m) or (a:Movie{title:{0}}-[r]->(m)) return a,r,m")
    List<Person> findAllRelation(String name);

    @Query("Merge (n:Person{name:$name})")//新语法
    void SaveNode(String name);
}
```

## 自然语言处理

其实我只实现了分词，现在针对自然语言处理这个领域的工具大部分都是用python语言来实现的，而我想在springboot上使用，又花了大部分时间来找工具。

最后用的是FudanNLP，复旦大学针对自然语言处理开发的一个工具包。由于是几年前的工具，jdk版本比较低，放到项目中依然处处报错。

我只用了这个工具的一个分词和词性标注的功能。所以理解并不深，毕竟天赋有限。我也没有这么多时间能够耗在这上面，至少还是要完成整个项目的样板吧。

[FudanNLP GitHub]: https://github.com/FudanNLP/fnlp	"FudanNLP GitHub"

## Echart

是这个功能实现从java提取数据到前端绘制图像。因为我之前在Django模板中实现过这个功能，所以，我第一个想到的就是他。

不负所望。

[Echarts官网]: https://echarts.apache.org/zh/index.html

如果有想用的可以自己去官网。实现不难，但是想实现自己的功能，就需要花时间去研究了。特别是各个模板之间怎么传递参数，技术更新太快，已经没有巨人的肩膀能给你站，只能自己想办法站得更高。

基本上就这些了吧。虽然还有踩过的坑，就是当时调了好长时间的bug，现在也记不太清了。

总之功能算是实现了。

纪念一下。
分词分得很草率，多多包涵。
![在这里插入图片描述](https://img-blog.csdnimg.cn/7b407c38549a46169e61ef3d60ae52c7.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBARHJlYW1lckZvckxpZmU=,size_20,color_FFFFFF,t_70,g_se,x_16)
[博客地址](http://dxt.ink)
还是不太希望大家来测试，毕竟，我的服务器是50块一年的，性能不太行。
而实现这个功能的步骤又比较复杂。因为每次你看到的知识图谱都是点完链接以后分析完整篇博客才生成的，所以服务器容易罢工。我也懒得天天维护，希望你们别点这个功能了。看看博客就行了。







