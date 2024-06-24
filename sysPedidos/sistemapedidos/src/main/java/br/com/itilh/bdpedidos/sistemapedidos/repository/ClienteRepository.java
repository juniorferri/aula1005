package br.com.itilh.bdpedidos.sistemapedidos.repository;

import java.math.BigInteger;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import br.com.itilh.bdpedidos.sistemapedidos.model.Cliente;

@Repository
public interface ClienteRepository extends CrudRepository<Cliente , BigInteger > {

     // Select * from tb_clientes where tx_nome = 'nome'//
    List<Cliente> findByNomeRazaoSocial(String nomeRazaoSocial);

    // Select * from tb_clientes where UPPER(tx_nome) like UPPER('nome%')//
    List<Cliente> findByNomeRazaoSocialStartingWithIgnoreCase(String nomeRazaoSocial);

    // Select * from tb_clientes where UPPER(tx_nome) like UPPER('%nome')//
    List<Cliente> findByNomeRazaoSocialEndingWithIgnoreCase(String nomeRazaoSocial);

    // Select * from tb_clientes where UPPER(tx_nome) like UPPER('%nome%')//
    List<Cliente> findByNomeRazaoSocialContainingIgnoreCase(String nomeRazaoSocial);

    @Query("FROM Cliente e WHERE e.nomeRazaoSocial like %?1")
    List<Cliente> findByMinhaQuery(String nomeRazaoSocial);
}
