package com.produtos.apirest.service;

import com.produtos.apirest.models.Expertise;
import com.produtos.apirest.models.Veterinary;
import com.produtos.apirest.repository.VeterinaryRepo;
import com.produtos.apirest.service.excecoes.RegraNegocioRunTime;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class VeterinarioService {

    private final VeterinaryRepo repo;

    public VeterinarioService(VeterinaryRepo veterinaryRepo){
        this.repo = veterinaryRepo;
    }

    public static void verifyIsNull(Veterinary veterinary){
        if (veterinary == null)
            throw new NullPointerException("Veterinary must not be null!");
    }

    public static void verifyHasName(Veterinary veterinary){
        if(veterinary.getName().equals(""))
            throw new RegraNegocioRunTime("The veterinary should have a name!");
    }

    public static void verifyHasCPF(Veterinary veterinary){
        if (veterinary.getCpf().equals(""))
            throw new RegraNegocioRunTime("The veterinary should have a CPF!");
    }

    public static void verifyHasExpertise(Veterinary veterinary){
        if (veterinary.getExpertise() == null)
            throw new RegraNegocioRunTime("The veterinary should have a Expertise!");
    }

    public static void verifyHasId(Veterinary veterinary){
        if (veterinary.getVeterinaryId() <= 0)
            throw new RegraNegocioRunTime("The veterinary should have a id!");
    }

    public static void verifyHasId(Long id){
        if (id <= 0)
            throw new RegraNegocioRunTime("The veterinary should have a id!");
    }

    public static void verifyAllRules(Veterinary veterinary){
        verifyIsNull(veterinary);
        verifyHasId(veterinary);
        verifyHasName(veterinary);
        verifyHasCPF(veterinary);
        verifyHasExpertise(veterinary);
    }

    public static void verifyIsNullHasNameHasCPFHasExpertise(Veterinary veterinary){
        verifyIsNull(veterinary);
        verifyHasName(veterinary);
        verifyHasCPF(veterinary);
        verifyHasExpertise(veterinary);
    }

    public static Example<Veterinary> generateFilter(Veterinary veterinary){
        return Example.of(veterinary, ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
    }

    @Transactional
    public Veterinary save(Veterinary veterinary){
        verifyIsNullHasNameHasCPFHasExpertise(veterinary);
        return repo.save(veterinary);
    }

    @Transactional
    public Veterinary update(Veterinary veterinary){
        verifyAllRules(veterinary);
        return repo.save(veterinary);
    }

    @Transactional
    public void removeById(Long id){
        verifyHasId(id);
        repo.deleteById(id);
    }

    @Transactional
    public Veterinary removeByIdWithFeedback(Long id){
        verifyHasId(id);
        Veterinary feedback = repo.findByVeterinaryId(id);
        repo.delete(feedback);
        return feedback;
    }

    @Transactional
    public Veterinary findById(Long id){
        verifyHasId(id);
        return repo.findByVeterinaryId(id);
    }

    @Transactional
    public List<Veterinary> find(Veterinary veterinary){
        verifyIsNull(veterinary);
        Example<Veterinary> example = generateFilter(veterinary);
        return repo.findAll(example);
    }

    @Transactional
    public List<Veterinary> findAll(){
        return repo.findAll();
    }

    @Transactional
    public Expertise findExpertisesById(Long id){
        verifyHasId(id);
        Veterinary veteFind = repo.findByVeterinaryId(id);
        return veteFind.getExpertise();
    }

    @Transactional
    public Veterinary updateExpertise(Veterinary destiny, Expertise newExpertise){
        EspecialidadeService.verifyAllRules(newExpertise);
        verifyAllRules(destiny);
        destiny.setExpertise(newExpertise);
        return repo.save(destiny);
    }
}