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
import br.com.itilh.bdpedidos.sistemapedidos.model.Produto;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ProdutoRepository;
import br.com.itilh.bdpedidos.sistemapedidos.util.ModoBusca;

@RestController
public class ProdutoController {

    private final ProdutoRepository repositorio;

    public ProdutoController(ProdutoRepository repositorio) {
        this.repositorio = repositorio;
    }

    @GetMapping("/produtos")
    public List<Produto> getTodos() {
        return (List<Produto>) repositorio.findAll();
    }

    @GetMapping("/produtos/nome/{nome}")
    public List<Produto> getProdutosPorNome(@PathVariable String nome,
                                            @RequestParam(required = true) ModoBusca modoBusca) {
        if (modoBusca.equals(ModoBusca.EXATO)) {
            return repositorio.findByDescricao(nome);
        } else if (modoBusca.equals(ModoBusca.INICIADO)) {
            return repositorio.findByDescricaoStartingWithIgnoreCase(nome);
        } else if (modoBusca.equals(ModoBusca.FINALIZADO)) {
            return repositorio.findByDescricaoEndingWithIgnoreCase(nome);
        } else {
            return repositorio.findByDescricaoContainingIgnoreCase(nome);
        }
    }

    @GetMapping("/produto/{id}")
    public Produto getPorId(@PathVariable BigInteger id) throws Exception {
        return repositorio.findById(id).orElseThrow(
                () -> new Exception("ID inválido.")
        );
    }

    @PostMapping("/produto")
    public Produto criarProduto(@RequestBody Produto entity) throws Exception {
        try {
            return repositorio.save(entity);
        } catch (Exception e) {
            throw new Exception("Erro ao salvar o produto.");
        }
    }

    @PutMapping("/produto/{id}")
    public Produto alterarProduto(@PathVariable BigInteger id,
                                  @RequestBody Produto novosDados) throws Exception {
        Optional<Produto> produtoArmazenado = repositorio.findById(id);
        if (produtoArmazenado.isPresent()) {
            Produto produto = produtoArmazenado.get();
            produto.setDescricao(novosDados.getDescricao());
            return repositorio.save(produto);
        }
        throw new Exception("Alteração não foi realizada.");
    }

    @DeleteMapping("/produto/{id}")
    public String deletePorId(@PathVariable BigInteger id) throws Exception {
        Optional<Produto> produtoArmazenado = repositorio.findById(id);
        if (produtoArmazenado.isPresent()) {
            repositorio.delete(produtoArmazenado.get());
            return "Excluído";
        }
        throw new Exception("ID não encontrado para a exclusão.");
    }
}
