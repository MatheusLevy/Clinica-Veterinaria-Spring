package com.produtos.apirest.Model;

import com.produtos.apirest.models.Dono;
import com.produtos.apirest.repository.AnimalRepo;
import com.produtos.apirest.repository.DonoRepo;
import com.produtos.apirest.repository.TipoAnimalRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.Optional;
import java.util.Random;

import static com.produtos.apirest.Util.Util.random;

@SpringBootTest
public class DonoTeste {

    @Autowired
    public DonoRepo donoRepo;

    protected static  Dono getDonoInstance(Boolean temId){
        Dono dono = Dono.builder()
                .nome("nome")
                .cpf(String.valueOf(random.nextInt(9999999)))
                .telefone("telefone")
                .build();
        if (temId)
            dono.setDonoId(Long.valueOf(1));
        return dono;
    }

    @Test
    public void deveSalvarModel(){
        Dono donoSalvo = donoRepo.save(getDonoInstance(false));
        Assertions.assertNotNull(donoSalvo);
        Assertions.assertEquals(getDonoInstance(false).getNome(), donoSalvo.getNome());
        donoRepo.delete(donoSalvo);
    }

    @Test
    public void deveAtualizar(){
        Dono donoSalvo = donoRepo.save(getDonoInstance(false));
        donoSalvo.setNome("Novo nome");
        Dono donoAtualizado = donoRepo.save(donoSalvo);
        Assertions.assertNotNull(donoAtualizado);
        Assertions.assertEquals(donoAtualizado.getDonoId(), donoSalvo.getDonoId());
        donoRepo.delete(donoSalvo);
    }

    @Test
    public void deveRemoverModel(){
        Dono donoSalvo = donoRepo.save(getDonoInstance(false));
        Long id = donoSalvo.getDonoId();
        donoRepo.deleteById(id);
        Assertions.assertFalse(donoRepo.findById(id).isPresent());
    }

    @Test
    public void deveBuscarModel(){
        Dono donoSalvo = donoRepo.save(getDonoInstance(false));
        Long id = donoSalvo.getDonoId();
        Assertions.assertTrue(donoRepo.findById(id).isPresent());
        donoRepo.delete(donoSalvo);
    }
}