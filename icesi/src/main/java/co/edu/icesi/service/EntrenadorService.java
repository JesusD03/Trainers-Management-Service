package co.edu.icesi.service;

import co.edu.icesi.config.KafkaProducer;
import co.edu.icesi.model.Entrenador;
import co.edu.icesi.repository.EntrenadorRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

@Service
public class EntrenadorService {
    private final EntrenadorRepository entrenadorRepository;
    private final KafkaProducer kafkaProducer;
    private RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public EntrenadorService(EntrenadorRepository entrenadorRepository, KafkaProducer kafkaProducer) {
        this.entrenadorRepository = entrenadorRepository;
        this.kafkaProducer = kafkaProducer;
        this.rabbitTemplate = rabbitTemplate;
    }

    public Entrenador agregarEntrenador(Entrenador entrenador) {
        Entrenador nuevoEntrenador = entrenadorRepository.save(entrenador);
        try {
            String mensaje = objectMapper.writeValueAsString(nuevoEntrenador);
            kafkaProducer.enviarMensaje(mensaje);
            rabbitTemplate.convertAndSend("entrenador.exchange", "entrenador.routingkey", mensaje);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nuevoEntrenador;
    }

    public List<Entrenador> listarEntrenadores() {
        return entrenadorRepository.findAll();
    }

    public Entrenador obtenerEntrenador(Long id) {
        return entrenadorRepository.findById(id).orElse(null);
    }

}

