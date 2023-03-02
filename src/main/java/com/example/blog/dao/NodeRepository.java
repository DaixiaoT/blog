package com.example.blog.dao;


import com.example.blog.po.Node;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;

@Repository
public interface NodeRepository extends Neo4jRepository<Node,Long> {
    @Query("MATCH (am:Movie)<-[ai:ACTED_IN]-(p:Person)-[d:DIRECTED]->(dm:Movie) return p, collect(ai), collect(d), collect(am), collect(dm)")
    List<Node> getPersonsWhoActAndDirect();

    //@Query("Match (n:User{name:{0}})-[r]-() delete n,r")
    @Query("Match (n:Person{name:$name})-[r]->() delete n,r")
    void deleteRelationAndPersonByName(String name);

    //MATCH (a:LabeofNode1), (b:LabeofNode2)
    //WHERE a.name = "nameofnode1" AND b.name = " nameofnode2"
    //CREATE (a)-[: Relation]->(b)

    @Query("Match (a:Person{name:$from})-[r:DIRECTED{name:$relation}]->(b:Movie{title:$to}) delete r")
    void deleteDIRECTEDRelationWithExistPerson(String from,String relation,String to);




//    ("merge (a:Node{name:$from})-[r:Relation{name:$relation}]-(b:Node{name:$to})")
    //Match (a:Person{name:"六小龄童"}),(b:Movie{title:"长津湖"}) create (a)-[r:DIRECTED]->(b)
//    @Query("Match (a:Node{name:$from}),(b:Node{name:$to}) Merge (a)-[r:Relation{name:$relation}]->(b)")
    @Query("merge (a:Node{name:$from})-[r:Relation{name:$relation}]-(b:Node{name:$to})")
    void createRelationWithExistNode(String from,String relation,String to);

    //查询一个节点所有关系
    //    @Query("Match (a:Person{name:{0}})-[r]->(m) return a,r,m")//失败
    @Query("MATCH (p)-[r:Relation]->() RETURN p.name")
    HashSet<String> findAllNode();

    @Query("Match (a:Node{name:$name})-[r]->(m) return m.name")
    HashSet<String> findLastByFirst(String name);

    @Query("Match (a:Node{name:$first})-[r]->(m:Node{name:$last}) return r.name")
    List<String> findRelationByFirstAndLast(String first,String last);

    @Query("Match (a:Node{name:$name}) detach delete a")
    void DeleteByName(String name);

    @Query("Merge (n:Node{name:$name})")
    void SaveNode(String name);
    //match(a:Node{name:"代晓涛"}),(b:Node{name:"计算机"})create (a)-[r:Relation{name:"喜欢"}]->(b)
    //Merge(:Node{name:"计算机"})
    //Merge(:Node{name:"代晓涛"})
    @Query("Merge(:Node{name:\"代晓涛\"})")
    void init1();
    @Query(" Merge(:Node{name:\"计算机\"});")
    void init2();
    @Query("match(a:Node{name:\"代晓涛\"}),(b:Node{name:\"计算机\"})create (a)-[r:Relation{name:\"喜欢\"}]->(b)")
    void init3();

    default void init(){
        deleteAll();
        init1();
        init2();
        init3();
    }
    @Query("match (n:Node) detach delete n")
    void deleteAll();



}
