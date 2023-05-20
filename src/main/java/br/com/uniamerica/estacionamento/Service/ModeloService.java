package br.com.uniamerica.estacionamento.Service;

import br.com.uniamerica.estacionamento.Repository.MarcaRepository;
import br.com.uniamerica.estacionamento.Repository.ModeloRepository;
import br.com.uniamerica.estacionamento.Repository.VeiculoRepository;
import br.com.uniamerica.estacionamento.Entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class ModeloService {

    @Autowired
    private ModeloRepository modeloRepository;
    @Autowired
    private MarcaRepository marcaRepository;

    public List<Modelo> findAllModelos(){

        return this.modeloRepository.findAll();
    }


    public List<Modelo> findAllModelosAtivos(){

        return  this.modeloRepository.findByAtivo(true);
    }


    public Modelo findModeloById(Long id){

        Optional<Modelo> modeloBD = modeloRepository.findById(id);
        Assert.isTrue(!modeloBD.isEmpty(), "Modelo não encontrado");

        return modeloBD.get();

    }


    @Transactional(rollbackFor = Exception.class)
    public Modelo cadastrarModelo(Modelo modelo){


        Optional<Modelo> modeloBD = modeloRepository.findById(modelo.getId());
        Assert.isTrue(modeloBD.isEmpty(),"Id do Modelo já existe");
        Assert.isNull(modeloRepository.findByNome(modelo.getNome()),"Modelo já cadastrado com esse nome");
        Assert.isTrue(modelo.getNome().length() >= 2,"Nome do modelo requer minimo 2 caracteres");
        Assert.isTrue(modelo.getNome().length() <= 15,"Nome do modelo requer maximo 15 caracteres");
        Assert.notNull(modelo.getMarca(),"Precisa asociar o modelo com uma marca");

        Optional<Marca> marca = marcaRepository.findById(modelo.getMarca().getId());
        Assert.isTrue(!marca.isEmpty(),"A marca que está tentando asociar não cadastrada");
        Assert.notNull(marcaRepository.findByNome(modelo.getMarca().getNome()),"O nome do modelo que esta tentando asociar com a marca não existe");



        return modeloRepository.save(modelo);

    }


    @Transactional(rollbackFor = Exception.class)
    public Modelo modificarModelo(Modelo modelo, Long id){
        modelo.setId(id);

        /*
        Optional<Modelo> modeloBD = modeloRepository.findById(modelo.getId());
        Assert.isTrue(!modeloBD.isEmpty(),"Modelo não existe");
        Assert.isTrue(modeloBD.get().getId() != modelo.getId(),"Id do Modelo já existe");
        Assert.isTrue(modeloBD.get().getNome() != modelo.getNome(),"Modelo já cadastrado");
        */
        return modeloRepository.save(modelo);
    }





    @Transactional(rollbackFor = Exception.class)
    public void deletarModelo(Long id) {

        modeloRepository.deleteById(id);

        /*

       Optional<Modelo> modeloBD = this.modeloRepository.findById(id);
        Assert.isTrue(!modeloBD.isEmpty(),"Modelo não existe");
        this.modeloRepository.delete(modeloBD.get());
        */
    }






}
