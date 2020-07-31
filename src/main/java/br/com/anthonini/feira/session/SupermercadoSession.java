package br.com.anthonini.feira.session;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.annotation.SessionScope;

import br.com.anthonini.feira.model.Supermercado;

@Component
@SessionScope
public class SupermercadoSession {

	private Map<String,Supermercado> supermercados = new HashMap<>();

    public Supermercado getSupermercado(String uuid) {
        return supermercados.get(uuid);
    }

    public void adicionarSupermercado(Supermercado supermercado) {
        if(StringUtils.isEmpty(supermercado.getUuid())) {
            supermercado.setUuid(UUID.randomUUID().toString());
        }
        this.supermercados.put(supermercado.getUuid(), supermercado);
    }

    public void remover(String uuid) {
        supermercados.remove(uuid);
    }

}
