package br.com.itilh.bdpedidos.sistemapedidos.repository;


import java.math.BigInteger;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import br.com.itilh.bdpedidos.sistemapedidos.model.Produto;

@Repository
public interface ProdutoRepository extends CrudRepository<Produto , BigInteger > {

     // Select * from tb_produtos where tx_nome = 'nome'//
    List<Produto> findByDescricao(String nome);

    // Select * from tb_produtos where UPPER(tx_nome) like UPPER('nome%')//
    List<Produto> findByDescricaoStartingWithIgnoreCase(String nome);

    // Select * from tb_produtos where UPPER(tx_nome) like UPPER('%nome')//
    List<Produto> findByDescricaoEndingWithIgnoreCase(String nome);

    // Select * from tb_produtos where UPPER(tx_nome) like UPPER('%nome%')//
    List<Produto> findByDescricaoContainingIgnoreCase(String nome);

   // @Query("FROM Produto e WHERE e.txDescricao like %?1")
    //List<Produto> findByMinhaQuery(String nome);
}