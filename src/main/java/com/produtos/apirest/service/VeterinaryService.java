package com.produtos.apirest.service;

import com.produtos.apirest.models.Expertise;
import com.produtos.apirest.models.Veterinary;
import com.produtos.apirest.repository.VeterinaryRepo;
import com.produtos.apirest.service.exceptions.BusinessRuleException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class VeterinaryService {

    private final VeterinaryRepo repo;

    public VeterinaryService(VeterinaryRepo veterinaryRepo){
        this.repo = veterinaryRepo;
    }

    public static void isNotNull(Veterinary veterinary){
        if (veterinary == null)
            throw new NullPointerException("Veterinary must not be null!");
    }

    public static void hasName(Veterinary veterinary){
        if(veterinary.getName().equals(""))
            throw new BusinessRuleException("The veterinary should have a name!");
    }

    public static void hasCPF(Veterinary veterinary){
        if (veterinary.getCpf().equals(""))
            throw new BusinessRuleException("The veterinary should have a CPF!");
    }

    public static void hasExpertise(Veterinary veterinary){
        if (veterinary.getExpertise() == null)
            throw new BusinessRuleException("The veterinary should have a Expertise!");
    }

    public static void hasId(Veterinary veterinary){
        if (veterinary.getVeterinaryId() <= 0)
            throw new BusinessRuleException("The veterinary should have a id!");
    }

    public static void hasId(Long id){
        if (id <= 0)
            throw new BusinessRuleException("The veterinary should have a id!");
    }

    public static void verifyAllRules(Veterinary veterinary){
        isNotNull(veterinary);
        hasId(veterinary);
        hasName(veterinary);
        hasCPF(veterinary);
        hasExpertise(veterinary);
    }

    public static void IsNotNullHasNameHasCPFHasExpertise(Veterinary veterinary){
        isNotNull(veterinary);
        hasName(veterinary);
        hasCPF(veterinary);
        hasExpertise(veterinary);
    }

    public static Example<Veterinary> generateExample(Veterinary veterinary){
        return Example.of(veterinary, ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
    }

    @Transactional
    public Veterinary save(Veterinary veterinary){
        IsNotNullHasNameHasCPFHasExpertise(veterinary);
        return repo.save(veterinary);
    }

    @Transactional
    public Veterinary update(Veterinary veterinary){
        verifyAllRules(veterinary);
        return repo.save(veterinary);
    }

    @Transactional
    public void removeById(Long id){
        hasId(id);
        repo.deleteById(id);
    }

    @Transactional
    public Veterinary removeByIdWithFeedback(Long id){
        hasId(id);
        Veterinary feedback = repo.findByVeterinaryId(id);
        repo.delete(feedback);
        return feedback;
    }

    @Transactional
    public Veterinary findById(Long id){
        hasId(id);
        return repo.findByVeterinaryId(id);
    }

    @Transactional
    public List<Veterinary> find(Veterinary veterinary){
        isNotNull(veterinary);
        Example<Veterinary> example = generateExample(veterinary);
        return repo.findAll(example);
    }

    @Transactional
    public List<Veterinary> findAll(){
        return repo.findAll();
    }

    @Transactional
    public Expertise findExpertisesById(Long id){
        hasId(id);
        Veterinary veteFind = repo.findByVeterinaryId(id);
        return veteFind.getExpertise();
    }

    @Transactional
    public Veterinary updateExpertise(Veterinary destiny, Expertise newExpertise){
        ExpertiseService.verifyAllRules(newExpertise);
        verifyAllRules(destiny);
        destiny.setExpertise(newExpertise);
        return repo.save(destiny);
    }
}