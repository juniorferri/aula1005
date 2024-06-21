package br.com.itilh.bdpedidos.sistemapedidos.controller;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import br.com.itilh.bdpedidos.sistemapedidos.model.Cliente;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ClienteRepository;
import br.com.itilh.bdpedidos.sistemapedidos.util.ModoBusca;


@RestController
public class ClienteController {

    private final ClienteRepository repositorio;

public ClienteController(ClienteRepository repositorio){
        this.repositorio = repositorio;
    }

    @GetMapping("/clientes")
    public List<Cliente> getTodos() {
        return  (List<Cliente>) repositorio.findAll();
    }

    @GetMapping("/clientes/nome/{nome}")
    public List<Cliente> getClientesPorNome(@PathVariable String nome,
    @RequestParam(required = true) ModoBusca modoBusca) {
        if(modoBusca.equals(ModoBusca.EXATO)){
            return repositorio.findByNomeRazaoSocial(nome);
        }else if (modoBusca.equals(ModoBusca.INICIADO)){
            return repositorio.findByNomeRazaoSocialStartingWithIgnoreCase(nome);
        }else if (modoBusca.equals(ModoBusca.FINALIZADO)){
            return repositorio.findByNomeRazaoSocialEndingWithIgnoreCase(nome);
        }else{
            return repositorio.findByNomeRazaoSocialContainingIgnoreCase(nome);
        }       
    }
    
    
    @GetMapping("/cliente/{id}")
    public Cliente getPorId(@PathVariable BigInteger id) throws Exception {
        return repositorio.findById(id).orElseThrow(
            () -> new Exception("ID inválido.")
         );
    }    


    @PostMapping("/cliente")
    public Cliente criarCliente(@RequestBody Cliente entity) throws Exception { 
        try{               
            return repositorio.save(entity);
        }catch(Exception e){
            throw new Exception("Erro ao salvar o cliente.");
        }
    }

    @PutMapping("/cliente/{id}")
    public Cliente alterarCliente(@PathVariable BigInteger id, 
                                @RequestBody Cliente novosDados) throws Exception {

        Optional<Cliente> clienteAmazenado = repositorio.findById(id);
        if(clienteAmazenado.isPresent()){
            //Atribuir novo nome ao objeto já existem no banco de dados
            clienteAmazenado.get().setNomeRazaoSocial(novosDados.getNomeRazaoSocial());
            //
            return repositorio.save(clienteAmazenado.get());
        }        
        throw new Exception("Alteração não foi realizada.");
    }

    @DeleteMapping("/cliente/{id}")
    public String deletePorId(@PathVariable BigInteger id) throws Exception {

        Optional<Cliente> estadoAmazenado = repositorio.findById(id);
        if(estadoAmazenado.isPresent()){
            repositorio.delete(estadoAmazenado.get());
            return "Excluído";
        }
        throw new Exception("Id não econtrado para a exclusão");
    }    





}
