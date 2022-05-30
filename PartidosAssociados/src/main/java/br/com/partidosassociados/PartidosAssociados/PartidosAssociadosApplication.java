package br.com.partidosassociados.PartidosAssociados;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class PartidosAssociadosApplication {

	public static void main(String[] args) {
		SpringApplication.run(PartidosAssociadosApplication.class, args);
	}

}
