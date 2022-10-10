package com.produtos.apirest.service.excecoes;

//TODO: ### ** Desfazer todas as Exceções
// ### Exceções:
// - [X] RegraNegocioRuntime
// - [ ] Outra se Necessário
//TODO: ### ** Corrigir Exceções Lançadas nos Serviços **
// - [ ] AnimalService
// - [ ] AreaService
// - [ ] ConsultaService
// - [ ] DonoService
// - [ ] EspecialidadeService
// - [ ] TipoAnimalService
// - [ ] TipoConsultaService
// - [ ] UsuarioService
// - [ ] VeterinarioService

public class RegraNegocioRunTime extends RuntimeException{
    public RegraNegocioRunTime(String msg){
        super(msg);
    }
}
