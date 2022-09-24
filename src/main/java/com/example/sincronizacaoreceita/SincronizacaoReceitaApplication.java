package com.example.sincronizacaoreceita;

import com.example.sincronizacaoreceita.service.ReceitaService;
import com.example.sincronizacaoreceita.service.ReceitaServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.util.logging.Logger;

@SpringBootApplication
public class SincronizacaoReceitaApplication {
  private static Logger logger =
      java.util.logging.Logger.getLogger(String.valueOf(SincronizacaoReceitaApplication.class));

  public static void main(String[] args) {
    SpringApplication.run(SincronizacaoReceitaApplication.class, args);

    ReceitaService receitaService = new ReceitaServiceImpl();
    try {
      File file = new File(args[0]);
      receitaService.readCsv(file);
    } catch (ArrayIndexOutOfBoundsException e) {
      logger.severe("O caminho do arquivo nao pode ser vazio");
    }
  }
}
